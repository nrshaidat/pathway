package edu.brown.cs.student.pathway;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.lang.Math;

public class Pathway {
  /**
   * NOTE: these instance variables should be taken from
   * the respective instance of the University class.
   */
  private static final int SEMESTER_SIZE = 4;
  private static final int SEMESTER_COUNT = 8;

  private int[] requirements;
  private Set<Node> courses;
  private Set<Node> taken;
  private int currSemester;
  private List<List<Node>> path;

  public Pathway(int[] reqs, Set<Node> courseSet) {
    requirements = reqs;
    courses = courseSet;
    path = new ArrayList<List<Node>>();
  }

  public List<List<Node>> getPath() {
    return path;
  }

  public void makePathway(Set<Node> coursesTaken, int risingSemester) {
    // Initialize currSemester, nextSet and taken
    currSemester = risingSemester;
    Set nextSet = new HashSet<Node>();

    taken = coursesTaken;
    for (Node course : taken) {
      requirements[course.getCategory()] -= 1;
      Node next = course.getNext();
      if (next != null) {
        nextSet.add(next);
      }
    }

    while (this.requirementsLeft()) {
      // Set up new semester
      List<Node> thisSemester = new ArrayList<Node>();
      // Get available courses / sources in the DAG
      Set<Node> sources = this.getAvailableCourses();

      // Take "next" courses if available
      Set<Node> mustTakes = Sets.intersection(nextSet, sources);
      for (Node course : mustTakes) {
        thisSemester.add(course);
        taken.add(course);
        nextSet.remove(course);
      }

      // Group courses by category
      List<Node>[] coursesByCat = new List[requirements.length];
      for (int i = 0; i < requirements.length; i++) {
        coursesByCat[i] = new ArrayList<Node>();
      }

      for (Node source : sources) {
        coursesByCat[source.getCategory()].add(source);
      }

      for (int i = 0; i < requirements.length; i++) {
        // Skip if we've satisfied this category
        if (requirements[i] == 0) {
          continue;
        }
        // Get courses in this category
        List<Node> catCourses = coursesByCat[i];
        // Skip if no available courses in this category
        if (catCourses.size() == 0) {
          continue;
        }

        /**
         * TODO: make this dynamic
         */
        // Choose number of courses to take
        int numCoursesTmp = Math.min(catCourses.size(), requirements[i]);
        int numCourses = Math.min(4 - thisSemester.size(), numCoursesTmp);
        requirements[i] -= numCourses;

        /**
         * TODO: add weights/priorities
         */
        Collections.shuffle(catCourses);
        for (int j = 0; j < numCourses; j++) {
          thisSemester.add(catCourses.get(j));
          taken.add(catCourses.get(j));

          Node next = catCourses.get(j).getNext();
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
    for (int i = 0; i < requirements.length; i++) {
      if (requirements[i] == 0) {
        zeroCount++;
      }
    }
    return !(zeroCount == requirements.length);
  }

  private Set<Node> getAvailableCourses() {
    Set<Node> sources = new HashSet<Node>();
    int sem = currSemester % 2;
    for (Node course : courses) {
      if (!course.getSemestersOffered().contains(sem)) {
        continue;
      }

      Set<Set<Node>> satisfied = new HashSet<Set<Node>>();
      for (Set<Node> s : course.getPrereqs()) {
        for (Node c : taken) {
          if (s.contains(c)) {
            satisfied.add(s);
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

}
