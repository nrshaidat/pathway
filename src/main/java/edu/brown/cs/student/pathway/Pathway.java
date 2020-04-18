package edu.brown.cs.student.pathway;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.lang.Math;
import java.util.Arrays;

public class Pathway {
  private static final int SEMESTER_SIZE = 4;
  private static final int SEMESTER_COUNT = 8;

  private int[] requirements;
  private int numCategories;
  private int[] initialRequirements;

  private Set<Node> courses;
  private Set<Node> taken;
  private int currSemester;
  private List<List<Node>> path;

  public Pathway(int[] reqs, Set<Node> courseSet) {
    requirements = reqs;
    numCategories = reqs.length;
    initialRequirements = Arrays.copyOf(reqs, numCategories);
    courses = courseSet;
    path = new ArrayList<List<Node>>();
  }

  public List<List<Node>> getPath() {
    return path;
  }

  public void makePathway(Set<Node> coursesTaken, int risingSemester) {
    currSemester = risingSemester;
    Set nextSet = new HashSet<Node>();

    taken = coursesTaken;
    for (Node course : taken) {
      int category = course.getCategory();
      if (requirements[category] == 0) {
        continue;
      }
      requirements[category] -= 1;
      Node next = course.getNext();
      if (next != null) {
        nextSet.add(next);
      }
    }

    while (this.requirementsLeft()) {
      List<Node> thisSemester = new ArrayList<Node>();
      Set<Node> sources = this.getAvailableCourses();

      // Take "next" courses if available, up to SEMESTER_SIZE courses
      Set<Node> mustTakes = Sets.intersection(nextSet, sources);
      int c = 0;
      for (Node course : mustTakes) {
        if (c < SEMESTER_SIZE) {
          thisSemester.add(course);
          taken.add(course);
          nextSet.remove(course);
          c++;
        }
      }

      // Group sources by category
      List<Node>[] coursesByCat = new List[numCategories];
      for (int i = 0; i < numCategories; i++) {
        coursesByCat[i] = new ArrayList<Node>();
      }
      for (Node source : sources) {
        coursesByCat[source.getCategory()].add(source);
      }

//      System.out.println("currSemester: " + Integer.toString(currSemester));
      int[] coursesToTake = this.numCoursesToTake(coursesByCat,
          SEMESTER_SIZE - thisSemester.size(), false);
//      System.out.println(Arrays.toString(coursesToTake));

      for (int i = 0; i < numCategories; i++) {
        if (coursesToTake[i] == 0) {
          continue;
        }

        int numCourses = coursesToTake[i];
        requirements[i] -= numCourses;
        List<Node> catCourses = coursesByCat[i];

        /**
         * Add weights/priorities to courses
         * Factors to consider:
         * courseRating & class_size --> Laplace's rule of succession
         * average avg_hrs, average max_hrs --> avg / max (higher ratio is better)
         */
        List<Double> weights = new ArrayList<Double>();
        for (Node course : catCourses) {
          // Rating priority
          int classSize = course.getClassSize();
          int ratingAsNum = (int) Math.round((course.getRating() / 5.0) * classSize);
          double trueRating = ((double) (ratingAsNum + 1)) / (classSize + 2);

          // Avg vs. max hours priority
          double avgOverMax = course.getAvgHrs() / course.getMaxHrs();

          // Rating > hrs
          double priority = (0.75 * trueRating) + (0.25 * avgOverMax);
          weights.add(priority);
        }

        // Approaches to weighted random selection/shuffle:
        // Collections.shuffle() (no weights) --> O(nlogn), n is # of courses in category
        // Weighted Randomized Ordering --> O(W * n)
        // Random Interval Selection/Linear Scan (possible duplicates) --> O(n * numCourses)
        // Random Binary Search (possible duplicates) --> O(logn)
        // Fast Weighted Shuffle --> O(nlogn)

        Node[] shuffledCatCourses = new Node[catCourses.size()];
        List<Double> running = new ArrayList<Double>();
        double acc = 0;
        for (double weight : weights) {
          acc += weight;
          running.add(acc);
        }
        for (int j = 0; j < catCourses.size(); j++) {
          double target = Math.random() * running.get(running.size() - 1);
          int index = Collections.binarySearch(running, target);
          if (index < 0) {
            index *= -1;
            index--;
          }
          shuffledCatCourses[j] = catCourses.get(index);
          for (int k = index; k < running.size(); k++) {
            running.set(k, running.get(k) - weights.get(index));
          }
        }

//        for (Node course : shuffledCatCourses) {
//          thisSemester.add(course);
//          taken.add(course);
//          Node next = course.getNext();
//          if (next != null) {
//            nextSet.add(next);
//          }
//        }

        for (int j = 0; j < numCourses; j++) {
          thisSemester.add(shuffledCatCourses[j]);
          taken.add(shuffledCatCourses[j]);
          Node next = shuffledCatCourses[j].getNext();
          if (next != null) {
            nextSet.add(next);
          }
        }
      }
      path.add(thisSemester);
      currSemester++;
    }
  }

  private boolean requirementsLeft() {
    int zeroCount = 0;
    for (int i = 0; i < numCategories; i++) {
      if (requirements[i] <= 0) {
        zeroCount++;
      }
    }
    return !(zeroCount == numCategories);
  }

  private Set<Node> getAvailableCourses() {
    Set<Node> sources = new HashSet<Node>();
    int sem = currSemester % 2;
    for (Node course : courses) {
      // continue  if course is not offered this semester
      if (!course.getSemestersOffered().contains(sem)) {
        continue;
      }
        /**IFECHI YOU NEED TO ERROR CHECK ALL CALLS TO getPrereqs ARE NOT EQUAL TO NULL
         * -NAT <3 <3
         * (THESE ARE GENTLE CAPS JUST WANNA MAKE SURE YOU SEE THIS)
         *
         */
      // remove incoming edges from node
      Set<Set<Node>> satisfied = new HashSet<Set<Node>>();
      for (Set<Node> s : course.getPrereqs()) {
        for (Node c : taken) {
          if (s.contains(c)) {
            satisfied.add(s);
            break;
          }
        }
      }

      for (Set<Node> s : satisfied) {
        course.removePrereq(s);
      }

      if (course.getPrereqs().size() == 0 && !taken.contains(course)) {
        sources.add(course);
      }
    }
    return sources;
  }

  /**
   * How to choose # of courses to take in a category
   *
   * Factors to consider:
   * - SEMESTER_COUNT & currSemester
   * - aggressive vs. laid-back
   * - # of courses available in categories, # of requirements left in categories
   * - taking many courses from one category at the same time (esp. for advanced courses)
   * - finishing one category at a time vs taking courses from many categories
   *
   * Biases:
   * - Take classes in lower categories first
   * - Take classes in categories that have more requirements left & finish categories
   *   with only a couple of courses
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
    double[] reqsFrac = new double[numCategories]; // how "done" we are with requirements
    double reqsFracAvg = 0;
    for (int i = 0; i < numCategories; i++) {
      reqsFrac[i] = ((double) (initialRequirements[i] - requirements[i])) / initialRequirements[i];
      reqsFracAvg += (reqsFrac[i] / ((double) numCategories));
    }

    double maxFrac = 0.625;
    double lag = semFrac - reqsFracAvg;
    if (aggressive) {
      lag += (2.0 / SEMESTER_COUNT);
    }

    if (lag <= (-1.0 / SEMESTER_COUNT)) { // ahead by 2 semesters
      maxFrac -= (1.0 / SEMESTER_SIZE);
    } else if (lag <= 0) { // ahead by 1 semester
      maxFrac = 0.625;
    } else if (lag <= (1.0 / SEMESTER_COUNT)) { // on track
      maxFrac = 0.625;
    } else { // behind
      maxFrac += (1.0 / SEMESTER_SIZE);
    }

    int numCourses = (int) Math.ceil(maxFrac * max);

//    System.out.println("reqsAhead: " + Arrays.toString(reqsAhead));
//    System.out.println("semFrac: " + Double.toString(semFrac));
//    System.out.println(Arrays.toString(reqsFrac));
//    System.out.println("reqsFracAvg: " + Double.toString(reqsFracAvg));
//    System.out.println("lag: " + Double.toString(lag));
//    System.out.println("maxFrac: " + Double.toString(maxFrac));
//    System.out.println("numCourses: " + Integer.toString(numCourses));

    numCourses = Math.min(numCourses, count);

//    System.out.println("numCourses after Math.min: " + Integer.toString(numCourses));

    for (int i = 0; i < numCategories; i++) {
      if (numCourses == 0) {
        break;
      }
      if (coursesByCat[i].size() == 0 || requirements[i] == 0) { // if no available courses or finished req
        res[i] = 0;
        continue;
      }
      if (reqsFrac[i] + (1.0 / initialRequirements[i]) == 1) { // if need 1 more course, take it
        res[i] = 1;
        numCourses -= 1;
      } else {
        if (reqsAhead[i] >= numCourses) {
          res[i] = 1;
          numCourses -= 1;
        } else if (reqsAhead[i] < numCourses && reqsAhead[i] > 0) {
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

}
