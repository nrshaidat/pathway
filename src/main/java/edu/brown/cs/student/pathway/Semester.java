package edu.brown.cs.student.pathway;

import java.util.List;

/**
 * The Semester Class contains the courses a student takes in a specific
 * semester (made in the makePathway method). Additional courses info
 * is in the Semester class that allows Pathway customization in the GUI.
 */

/**
 * Notes on swapping:
 *
 * If I got a Pathway and wanted to edit it, how would I change it?
 * - Swap with course of same category
 *   -> What if they swap a course that is a next or has a next?
 *   -> What if they swap a course that is a prereq for another course in pathway?
 *   -> What if the swap can't happen that semester?
 *   -> Can we honor workload preference if they swap?
 *
 * - Move course to a different semester (take AI in semester 5 instead of 3)
 *  -> What if they try to move the course to a semester when it's not offered?
 *  -> What if they try to move the course to a semester when it's not a source?
 *
 * - Add a desired course
 *  -> What if they want to add a course to a semester when it's not offered?
 *
 * - Users CANNOT just remove a course (won't satisfy reqs)
 */
public class Semester {
  private List<Node> courses;
  private int semNumber;
  // coursesByCat is the sources this semNumber partitioned by category.
  // This will be used for editing Pathways in the GUI:
  // - can add any course available that sem [info in node]
  // - can move course to any sem it's offered as long as it's a source
  //   [info in semester]
  // - can swap courses of equivalent categories [info in semester]
  private List<Node>[] coursesByCat;
  private int numCategories;

  /**
   * Instantiates a new Semester.
   *
   * @param sem    semester number (1, 2, ...)
   * @param taking courses that the student will take based on the results of the pathway
   */
  public Semester(int sem, List<Node> taking) {
    semNumber = sem;
    courses = taking; // courses generated by algo, before edits
  }

  /**
   * getSemester gets the semester's integer representation.
   *
   * @return the semester as an integer
   */
  public int getSemester() {
    return semNumber;
  }

  /**
   * getCourses gets the courses that will be taken for this semester.
   *
   * @return the courses that will taken in this semester
   */
  public List<Node> getCourses() {
    return courses;
  }

  /**
   * addCourse adds a course to this semester.
   *
   * @param course the course to add
   */
  public void addCourse(Node course) {
    courses.add(course);
  }

  /**
   * removeCourse removes a course if the user wants to switch it out for another course.
   *
   * @param course the course
   */
  public void removeCourse(Node course) {
    courses.remove(course);
  }

  /**
   * getCourseEquivalents gets the course equivalents for the given course i.e. courses that are
   * offered in that same semester and satisfy the same requirements within the concentration.
   *
   * @param course the course
   * @return the course equivalents
   */
  public List<Node> getCourseEquivalents(Node course) {
    return coursesByCat[course.getCategory()];
  }

  /**
   * getAllCoursesAvailableThisSemester gets all courses available this semester as a list where
   * the category is the index and the values are the courses that can be taken in this semester
   * that satisfy the category.
   *
   * @return the list of all courses by category that can be taken in this semester
   */
  public List<Node>[] getAllCoursesAvailableThisSemester() {
    return coursesByCat;
  }

}
