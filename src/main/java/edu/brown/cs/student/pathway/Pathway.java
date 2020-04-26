package edu.brown.cs.student.pathway;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Math;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;

public class Pathway {
  private static final int SEMESTER_SIZE = 4;
  private static final int SEMESTER_COUNT = 8;

  private int[] requirements; // indices are categories, values are # of credits to satisfy the category
  private int numCategories; // size of requirements array
  private int[] initialRequirements; // original requirements - used for pacing
  private ImmutableMap<String, Range<Double>> workloads; // hours ranges for lo, med and hi workloads

  private Set<Node> courses; // courses that can be taken for credit for that concentration
  private Set<String> taken; // courses the student has already taken
  private List<Semester> path; // path to be returned
  private int currSemester;

  public Pathway(int[] reqs, Set<Node> courseSet) {
    taken = new HashSet<>();
    requirements = reqs;
    numCategories = reqs.length;
    initialRequirements = Arrays.copyOf(reqs, numCategories);
    courses = courseSet;
    path = new ArrayList<>();
    workloads = ImmutableMap.of("lo", Range.closedOpen(1.0, 25.0),
            "med", Range.closedOpen(25.0, 40.0),
            "hi", Range.closedOpen(40.0, 80.0));
  }

  public List<Semester> getPath() {
    return path;
  }

  /**
   * Generates valid Pathway that satisfies concentration requirements
   * that best fits inputted preferences, with a bit of randomness
   * (to provide variety as opposed to a one-size-fits-all)
   * @param coursesTaken courses already taken by student
   * @param risingSemester rising semester
   * @param aggressive pacing preference: fast or chilled
   * @param workload workload preference
   */
  public void makePathway(Set<Node> coursesTaken, int risingSemester, boolean aggressive, String workload) {
    currSemester = risingSemester;
    Set<Node> nextSet = new HashSet<>(); // used to handle sequences

    // Update requirements and taken with courses taken
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
      if (currSemester > 1.5 * SEMESTER_COUNT) { // case to catch db errors
        System.out.println("Invalid Pathway - 50% longer than intended. Please try again.");
        break;
      }
      List<Node> thisSemester = new ArrayList<>();
      Set<Node> sources = this.getAvailableCourses();

      // Take "next" courses if available
      Set<Node> toRemove = new HashSet<>();
      for (Node next : nextSet) {
        if (sources.contains(next)) {
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

      double thisSemAvgHours = 0.0;
      for (int i = 0; i < numCategories; i++) {
        if (coursesToTake[i] == 0) {
          continue;
        }

        int numCourses = coursesToTake[i];
        requirements[i] -= numCourses;
        List<Node> catCourses = coursesByCat[i];
        // Weighted shuffle the courses
        Node[] shuffledCatCourses = this.prioritizeAndWeightedShuffle(catCourses);
        // Sort courses by avgHrs ascending order
        Collections.sort(catCourses, Comparator.comparingDouble(Node::getAvgHrs));
        // Choose courses to take
        this.chooseCourses(catCourses, shuffledCatCourses, nextSet, thisSemester,
            workload, numCourses);
      }
      Semester semester = new Semester(currSemester, thisSemester);
      path.add(semester);
      currSemester++;
    }
  }

  /**
   * Returns boolean telling us if we have requirements left or not
   * @return boolean representing whether we have reqs left or not
   */
  private boolean requirementsLeft() {
    return !(Arrays.stream(requirements).
        reduce(0, Integer::sum) == 0);
  }

  /**
   * Returns available courses this semester
   * @return set of available courses
   */
  private Set<Node> getAvailableCourses() {
    Set<Node> sources = new HashSet<>();
    int sem = currSemester % 2;
    for (Node course : courses) {
      // continue if course is not offered this semester or we've taken the course
      if (!course.getSemestersOffered().contains(sem) || taken.contains(course.getId())) {
        continue;
      }

      // remove incoming edges from node
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
      if (course.getPrereqs().size() == 0) {
        sources.add(course);
      }
    }
    return sources;
  }


  /**
   * Need to take numCourses courses, prioritizing courses that come first
   * in shuffledCatCourses, trying to stay within the workload range:
   *  If thisSemAvgHours is less than the lowerEndpoint or well
   *  within the workload range, we will choose from the first courses in
   *  shuffledCatCourses. Only if the workload nears the upperEndpoint
   *  do we choose lower workload courses over better rated courses.
   * @param catCourses courses to take, ordered ASC by workload
   * @param shuffledCatCourses courses to take, ordered DESC by rating
   * @param nextSet nextSet, to handle sequences
   * @param thisSemester courses taking this semester
   * @param workload desired workload
   * @param numCourses # of courses to take this semester
   */
  private void chooseCourses(List<Node> catCourses, Node[] shuffledCatCourses,
                             Set<Node> nextSet, List<Node> thisSemester,
                             String workload, int numCourses) {
    double thisSemAvgHours = 0.0;
    for (int j = 0; j < numCourses; j++) {
      if (thisSemAvgHours > (workloads.get(workload).upperEndpoint() - 7.0)) {
        int numLeft = numCourses - j;
        for (int k = 0; k < numLeft; k++) {
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
   * Choose # of courses to take in a category
   * Factors to consider:
   *  - SEMESTER_COUNT & currSemester
   *  - aggressive vs. laid-back
   *  - # of courses available in categories, # of requirements left in categories
   *  - taking many courses from one category at the same time
   *  - finishing one category at a time vs taking courses from many categories
   * @param coursesByCat available courses sorted by category
   * @param max maximum number of courses we can take
   * @param aggressive boolean representing accelerated schedule
   * @return int[] telling us how many courses to take in each category
   */
  private int[] numCoursesToTake(List<Node>[] coursesByCat, int max, boolean aggressive) {
    int[] res = new int[numCategories]; // how many courses we take in each category
    if (max == 0) { // return empty res if no room left in this semester
      return res;
    }

    // Finding how many courses are available per category
    int[] reqsAhead = new int[numCategories];
    int[] numCoursesPerCat = new int[numCategories];
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
    double maxFrac = 0.625;
    double lag = semFrac - reqsFracAvg;
    if (aggressive) {
      lag += (2.0 / SEMESTER_COUNT);
    }
    if (lag <= (-1.0 / SEMESTER_COUNT)) { // ahead by 2 semesters
      maxFrac -= (1.0 / SEMESTER_SIZE);
    } else if (lag > (1.0 / SEMESTER_COUNT)) { // behind
      maxFrac += (1.0 / SEMESTER_SIZE);
    }

    int numCourses = Math.min((int) Math.ceil(maxFrac * max), count);

    for (int i = 0; i < numCategories; i++) {
      if (numCourses == 0) {
        break;
      }
      if (numCoursesPerCat[i] == 0 || requirements[i] == 0) { // if no available courses or finished req
        res[i] = 0;
        continue;
      }
      if (requirements[i] == 1) { // if need 1 more course, take it
        res[i] = 1;
        numCourses -= 1;
      } else {
        if (reqsAhead[i] >= numCourses) {
          res[i] = 1;
          numCourses -= 1;
        } else if (reqsAhead[i] > 0) {
          int taking = Math.min(numCourses - 1, numCoursesPerCat[i]);
          res[i] = taking;
          numCourses -= taking;
        } else {
          int taking = Math.min(numCourses, numCoursesPerCat[i]);
          res[i] = taking;
          numCourses -= taking;
        }
      }
    }
    return res;
  }

  /**
   * Add weights/priorities to courses:
   *  - courseRating & class_size --> Laplace's Rule of Succession
   *  - avg_hrs, max_hrs --> variance of max from avg
   *
   * Approaches to weighted random selection/shuffle:
   *  - Collections.shuffle() (no weights) --> O(nlogn)
   *  - Random Interval Selection/Linear Scan (possible duplicates) --> O(n * numCourses)
   *  - Random Binary Search (possible duplicates) --> O(logn)
   *  - Weighted Randomized Ordering --> O(W * n),
   *      W is sum of weights as integers, W > 1500
   *  - Fast Weighted Shuffle --> Amortized O(nlogn)
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
      double priority = (0.75 * trueRating) + (0.25 * avgOverMax);
      weights.add(priority);
      acc += priority;
      running.add(acc);
    }

    for (int i = 0; i < size; i++) {
      double target = Math.random() * running.get(running.size() - 1);
      int index = Collections.binarySearch(running, target);
      if (index < 0) {
        index *= -1;
        index--;
      }
      shuffledCatCourses[i] = catCourses.get(index);
      for (int k = index; k < running.size(); k++) {
        running.set(k, running.get(k) - weights.get(index));
      }
    }
    return shuffledCatCourses;
  }

}
