package edu.brown.cs.student.pathway;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.lang.Math;

public class Pathway {
  private int[] requirements;
  private Set<Node> courses;

  private Set<Node> taken;
  public int currSemester;
  private List<List<Node>> path;

  public Pathway(int[] reqs, Set<Node> courseSet) {
    requirements = reqs;
    courses = courseSet;
    path = new ArrayList<List<Node>>();
    taken = new HashSet<Node>();
    currSemester = 0;
  }

  public List<List<Node>> getPath() {
    return path;
  }

  public void makePathway() {
    Set nextSet = new HashSet<Node>(); // nextSet is for "next" courses / courses in a sequence
    while (this.requirementsLeft()) {
      currSemester++;
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
      for (int i = 0; i < requirements.length; i++) {
        // Skip if we've satisfied this category
        if (requirements[i] == 0) {
          continue;
        }
        // First, get courses in this category
        List<Node> catCourses = new ArrayList<Node>();
        for (Node source : sources) {
          if (source.getCategory() == i) {
            catCourses.add(source);
          }
        }
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
