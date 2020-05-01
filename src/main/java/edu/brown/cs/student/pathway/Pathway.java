package edu.brown.cs.student.pathway;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;

/**
 * Pathway class. This class is used to create individual pathways. Each pathway takes
 * in information like each category of requirements, the number of categories, and initial
 * requirements used for pacing. It then generates a pathway based on an individual user's
 * preferences and settings, for example which courses they have received credits for, and what
 * their rising semester number is.
 */
public class Pathway {
  private static final int SEMESTER_SIZE = 4;
  private static final int SEMESTER_COUNT = 8;

  // indices are categories, values are # of credits to satisfy the category
  private int[] requirements;
  private int numCategories; // size of requirements array
  private int[] initialRequirements; // original requirements - used for pacing
  // hours ranges for lo, med and hi workloads
  private ImmutableMap<String, Range<Double>> workloads;

  private Set<Node> courses; // courses that can be taken for credit for that concentration
  private Set<String> taken; // courses the student has already taken
  private List<Semester> path; // path to be returned
  private int currSemester;

  //Creating public static final ints for the numbers that cause Maven issues
  //These numbers cause Magic Number errors, so we assign them to variables instead.
  private static final double MEDLOWBOUNDHRS = 25.0;
  private static final double MEDHIGHBOUNDHRS = 40.0;
  private static final double HIHIGHBOUNDHRS = 80.0;
  private static final double INVALIDLENGTH = 1.5;
  private static final double MAGICNUMBER7 = 7.0;
  private static final double MAGICNUMBERTHREEFOURTH = 0.75;
  private static final double MAGICNUMBERFOURTH = 0.25;
  private static final double DEFAULTPERCENTSCHEDULE = 0.625;

  /**
   * The Constructor of the pathway class. It takes in the required information to generate a
   * Pathway, like the requirements, set of taken courses, the user information like workload or
   * course credits.
   *
   * @param reqs      an integer array of the requirements for a concentration.
   * @param courseSet the Node Set.
   */
  public Pathway(int[] reqs, Set<Node> courseSet) {
    taken = new HashSet<>();
    requirements = reqs;
    numCategories = reqs.length;
    initialRequirements = Arrays.copyOf(reqs, numCategories);
    courses = courseSet;
    workloads = ImmutableMap.of("lo", Range.closedOpen(1.0, MEDLOWBOUNDHRS), "med",
        Range.closedOpen(MEDLOWBOUNDHRS, MEDHIGHBOUNDHRS), "hi",
        Range.closedOpen(MEDHIGHBOUNDHRS, HIHIGHBOUNDHRS));
  }

  /**
   * Returns a list of semesters that is the pathway.
   *
   * @return A semester list
   */
  public List<Semester> getPath() {
    return path;
  }

  /**
   * Helper needed because of courseSet copying.
   * We use this in place of the Set's contains.
   *
   * @param sources sources
   * @param node    node to check
   * @return boolean
   */
  private boolean sourcesContains(Set<Node> sources, Node node) {
    for (Node source : sources) {
      if (source.getId().equals(node.getId())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Generates a valid pathway that satisfies concentration requirements
   * and best fits inputted preferences, with a bit of randomness
   * to provide variety as opposed to a one-size-fits-all pathway.
   *
   * @param coursesTaken   courses already taken by student
   * @param risingSemester rising semester
   * @param aggressive     pacing preference: fast or chilled
   * @param workload       workload preference
   */
  public void makePathway(Set<Node> coursesTaken, int risingSemester, boolean aggressive,
                          String workload) {
    path = new ArrayList<>();
    currSemester = risingSemester;
    Set<Node> nextSet = new HashSet<>();

    // Update requirements, taken and nextSet with coursesTaken
    for (Node course : coursesTaken) {
      int category = course.getCategory();
      if (requirements[category] == 0) {
        continue;
      }
      requirements[category] -= 1;
      Node next = course.getNext();
      if (next != null) {
        nextSet.add(next);
      }
      taken.add(course.getId());
    }

    // While we have requirements left
    while (this.requirementsLeft()) {
      // Case to catch db anr/or bad input errors (bad prereqs, cycles, etc.)
      if (currSemester > INVALIDLENGTH * SEMESTER_COUNT) {
        System.out.println("Invalid Pathway - 50% longer than intended.");
        break;
      }

      List<Node> thisSemester = new ArrayList<>();
      // Get courses available this semester (sources in the DAG)
      Set<Node> sources = this.getAvailableCourses();

      // Take "next" courses (second leg of a sequence) if available
      Set<Node> toRemove = new HashSet<>();
      for (Node next : nextSet) {
        if (this.sourcesContains(sources, next)) {
          thisSemester.add(next);
          taken.add(next.getId());
          toRemove.add(next);
        }
      }
      nextSet.removeAll(toRemove);
      sources.removeAll(toRemove);

      // Group sources by category
      List<Node>[] coursesByCat = new List[numCategories];
      for (int i = 0; i < numCategories; i++) {
        coursesByCat[i] = new ArrayList<>();
      }
      for (Node source : sources) {
        coursesByCat[source.getCategory()].add(source);
      }

      // Get the number and distribution of courses to take
      int[] coursesToTake =
          this.numCoursesToTake(coursesByCat, SEMESTER_SIZE - thisSemester.size(), aggressive);

      // For each category, take calculated number of courses
      for (int i = 0; i < numCategories; i++) {
        if (coursesToTake[i] == 0) {
          continue;
        }

        // Update requirements
        requirements[i] -= coursesToTake[i];
        List<Node> catCourses = coursesByCat[i];
        // Weighted shuffle the courses
        Node[] shuffledCatCourses = this.prioritizeAndWeightedShuffle(catCourses);
        // Sort courses by avgHrs, ascending order
        Collections.sort(catCourses, Comparator.comparingDouble(Node::getAvgHrs));
        // Choose courses to take
        this.chooseCourses(catCourses, shuffledCatCourses, nextSet, thisSemester, workload,
            coursesToTake[i]);
      }
      Semester semester = new Semester(currSemester, thisSemester);
      path.add(semester);
      currSemester++;
    }
  }

  /**
   * Returns boolean telling us if we have requirements left or not.
   *
   * @return boolean representing whether we have reqs left or not
   */
  private boolean requirementsLeft() {
    return !(Arrays.stream(requirements).reduce(0, Integer::sum) == 0);
  }

  /**
   * Returns available courses this semester.
   *
   * @return set of available courses
   */
  private Set<Node> getAvailableCourses() {
    Set<Node> sources = new HashSet<>();
    int sem = currSemester % 2;
    for (Node course : courses) {
      // Continue if course is not offered this semester or we've taken the course
      if (!course.getSemestersOffered().contains(sem) || taken.contains(course.getId())) {
        continue;
      }

      // Remove incoming edges from node
      Set<Set<Node>> satisfied = new HashSet<>();
      for (Set<Node> s : course.getPrereqs()) {
        for (Node c : s) {
          if (taken.contains(c.getId())) {
            satisfied.add(s);
            break;
          }
        }
      }
      for (Set<Node> s : satisfied) {
        course.removePrereq(s);
      }
      // Add to sources if all prereqs are satisfied
      if (course.getPrereqs().size() == 0) {
        sources.add(course);
      }
    }
    return sources;
  }


  /**
   * Need to take numCourses courses, prioritizing courses that come first
   * in shuffledCatCourses, trying to stay within the workload range:
   * If thisSemAvgHours is less than the lowerEndpoint or well
   * within the workload range, we will choose from the first courses in
   * shuffledCatCourses. Only if the workload nears the upperEndpoint
   * do we choose lower workload courses over better rated courses.
   *
   * @param catCourses         courses to take, ordered ASC by workload
   * @param shuffledCatCourses courses to take, ordered DESC by rating
   * @param nextSet            nextSet, to handle sequences
   * @param thisSemester       courses taking this semester
   * @param workload           desired workload
   * @param numCourses         # of courses to take this semester
   */
  private void chooseCourses(List<Node> catCourses, Node[] shuffledCatCourses, Set<Node> nextSet,
                             List<Node> thisSemester, String workload, int numCourses) {
    double thisSemAvgHours = 0.0; // workload this semester
    // Take numCourses courses
    for (int j = 0; j < numCourses; j++) {
      // If we are nearing the upper endpoint of our desired workload, switch
      // selection from better rated courses to lower workload courses
      if (thisSemAvgHours > (workloads.get(workload).upperEndpoint() - MAGICNUMBER7)) {
        for (int k = 0; k < numCourses - j; k++) {
          thisSemester.add(catCourses.get(k));
          taken.add(catCourses.get(k).getId());
          thisSemAvgHours += catCourses.get(k).getAvgHrs();
          Node next = catCourses.get(k).getNext();
          if (next != null) {
            nextSet.add(next);
          }
        }
        break;
      }
      // Add course to thisSemester and taken, update average hours
      // and put "next" course in nextSet, if there exists one
      thisSemester.add(shuffledCatCourses[j]);
      taken.add(shuffledCatCourses[j].getId());
      thisSemAvgHours += shuffledCatCourses[j].getAvgHrs();
      catCourses.remove(shuffledCatCourses[j]);
      Node next = shuffledCatCourses[j].getNext();
      if (next != null) {
        nextSet.add(next);
      }
    }
  }

  /**
   * Choose # of courses to take in a category.
   * Factors to consider:
   * - SEMESTER_COUNT & currSemester
   * - aggressive vs. laid-back
   * - # of courses available in categories, # of requirements left in categories
   * - taking many courses from one category at the same time
   * - finishing one category at a time vs taking courses from many categories
   *
   * @param coursesByCat available courses sorted by category
   * @param max          maximum number of courses we can take
   * @param aggressive   boolean representing accelerated schedule
   * @return int[] telling us how many courses to take in each category
   */
  private int[] numCoursesToTake(List<Node>[] coursesByCat, int max, boolean aggressive) {
    int[] res = new int[numCategories]; // how many courses we take in each category
    if (max == 0) { // return empty res if no room left in this semester
      return res;
    }

    // tells us how many requirement fulfilling courses lie ahead of this category -
    // helps us effectively take courses from different categories
    int[] reqsAhead = new int[numCategories];
    int[] numCoursesPerCat = new int[numCategories]; // how many courses are available per category
    int count = 0;
    for (int i = numCategories - 1; i >= 0; i--) {
      int size = coursesByCat[i].size();
      numCoursesPerCat[i] = size;
      reqsAhead[i] = count;
      count += Math.min(requirements[i], size);
    }

    double semFrac = ((double) currSemester) / SEMESTER_COUNT; // how "done" we are with school
    double reqsFracAvg = 0; // how "done" we are with requirements
    for (int i = 0; i < numCategories; i++) {
      reqsFracAvg += ((double) (initialRequirements[i] - requirements[i])) / initialRequirements[i];
    }
    reqsFracAvg /= numCategories;
    double maxFrac = DEFAULTPERCENTSCHEDULE; // by default, requirements take 62.5% of our schedule
    // lag tells us if we are ahead or behind on our requirements, relative to semester level
    double lag = semFrac - reqsFracAvg;
    if (aggressive) { // if agressive, we fake being behind so that we take more courses earlier on
      lag += (2.0 / SEMESTER_COUNT);
    }

    if (lag <= (-1.0 / SEMESTER_COUNT)) { // ahead by 2 semesters, reduce maxFrac
      maxFrac -= (1.0 / SEMESTER_SIZE);
    }
    if (lag > (1.0 / SEMESTER_COUNT)) { // behind, increase maxFrac
      maxFrac += (1.0 / SEMESTER_SIZE);
    }

    // Math.ceil(maxFrac * max) is ideally how many courses we take; we Math.min with
    // count to ensure that we can't take more courses than are currently offered
    int numCourses = Math.min((int) Math.ceil(maxFrac * max), count);

    for (int i = 0; i < numCategories; i++) {
      if (numCourses == 0) { // no more room, break
        break;
      }
      // if no available courses or finished req
      if (numCoursesPerCat[i] == 0 || requirements[i] == 0) {
        res[i] = 0;
        continue;
      }
      if (requirements[i] == 1) { // if need 1 more course, take it
        res[i] = 1;
        numCourses -= 1;
      } else {
        // if there are a lot more reqs ahead, only take 1 course
        if (reqsAhead[i] >= numCourses) {
          res[i] = 1;
          numCourses -= 1;
        } else if (reqsAhead[i] > 0) {
          // if not many reqs ahead, take as many courses as we need save 1
          int taking = Math.min(numCourses - 1, numCoursesPerCat[i]);
          res[i] = taking;
          numCourses -= taking;
        } else { // if no reqs ahead, take as many as we need
          int taking = Math.min(numCourses, numCoursesPerCat[i]);
          res[i] = taking;
          numCourses -= taking;
        }
      }
    }
    return res;
  }

  /**
   * Add weights/priorities to courses by using a weighted shuffle.
   * - courseRating & class_size --> Laplace's Rule of Succession
   * - avg_hrs, max_hrs --> variance of max from avg
   * <p>
   * Approaches to weighted random selection/shuffle:
   * - Collections.shuffle() (no weights) --> O(nlogn)
   * - Random Interval Selection/Linear Scan (possible duplicates) --> O(n * numCourses)
   * - Random Binary Search (possible duplicates) --> O(logn)
   * - Weighted Randomized Ordering --> O(W * n),
   * W is sum of weights as integers, W > 1500
   * - Fast Weighted Shuffle --> Amortized O(nlogn)
   *
   * @param catCourses courses to prioritize and shuffle
   * @return shuffled courses
   */
  private Node[] prioritizeAndWeightedShuffle(List<Node> catCourses) {
    int size = catCourses.size();
    Node[] shuffledCatCourses = new Node[size];
    List<Double> weights = new ArrayList<>();
    List<Double> running = new ArrayList<>();
    double acc = 0;
    // Prioritize courses
    for (Node course : catCourses) {
      // Rating priority
      int classSize = course.getClassSize();
      int ratingAsNum = (int) Math.round((course.getRating() / 5.0) * classSize);
      double trueRating = ((double) (ratingAsNum + 1)) / (classSize + 2);
      // Avg vs. max hours priority
      double avgOverMax = course.getAvgHrs() / course.getMaxHrs();
      // Rating has bigger impact on priority than hours variance
      double priority = (MAGICNUMBERTHREEFOURTH * trueRating) + (MAGICNUMBERFOURTH * avgOverMax);
      weights.add(priority);
      acc += priority;
      running.add(acc);
    }

    // Fast Weighted Shuffle
    for (int i = 0; i < size; i++) {
      // Generate random number and find where that number falls in running using binary search
      double target = Math.random() * running.get(running.size() - 1);
      int index = Collections.binarySearch(running, target); // log n
      if (index < 0) {
        index *= -1;
        index--;
      }
      shuffledCatCourses[i] = catCourses.get(index);
      // Update running so that we can never choose the same course twice
      for (int k = index; k < running.size(); k++) {
        running.set(k, running.get(k) - weights.get(index));
      }
    }
    return shuffledCatCourses;
  }

}
