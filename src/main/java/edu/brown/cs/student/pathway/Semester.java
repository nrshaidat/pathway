package edu.brown.cs.student.pathway;

import java.util.List;

/**
 * The Semester Class contains the courses a student takes in a specific
 * semester (made in the makePathway method). Additional courses info
 * is in the Semester class that allows Pathway customization in the GUI.
 */

/**
 * Notes on swapping:
 * <p>
 * If I got a Pathway and wanted to edit it, how would I change it?
 * - Swap with course of same category
 * - What if they swap a course that is a next or has a next?
 * - What if they swap a course that is a prereq for another course in pathway?
 * - What if the swap can't happen that semester?
 * - Can we honor workload preference if they swap?
 * <p>
 * - Move course to a different semester (take AI in semester 5 instead of 3)
 * - What if they try to move the course to a semester when it's not offered?
 * - What if they try to move the course to a semester when it's not a source?
 * <p>
 * - Add a desired course
 * - What if they want to add a course to a semester when it's not offered?
 * <p>
 * - Users CANNOT just remove a course (won't satisfy reqs)
 */
public class Semester {
  private List<Node> courses;
  private int semnumber;
  private String courseid1;
  private String courseid2;
  private String courseid3;
  private String courseid4;
  private double maxhrs;
  private double avghrs;
  private int numcourses;
  private double rating;
  private int numflex; //number of flexible courses that can be taken in both semesters
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
    semnumber = sem;
    courses = taking; // courses generated by algo, before edits
    numcourses = 0;
    maxhrs = 0.0;
    avghrs = 0.0;
    rating = 0.0;
    numflex = 0;
  }

  /**
   * Public getter method to return the maximum hours of a semester.
   *
   * @return a double representing maxhrs variable.
   */
  public double getMaxhrs() {
    return maxhrs;
  }

  /**
   * Sets the statistics for a semester like maxHrs, avghrs, and rating.
   */
  public void setStats() {
    for (Node c : this.courses) {
      maxhrs += c.getMaxHrs();
      avghrs += c.getAvgHrs();
      rating += c.getRating();
      if (c.getSemestersOffered().size() == 2) {
        numflex += 1;
      }
    }
  }

  /**
   * Getter method for the rating of a semester.
   *
   * @return a double variable, rating.
   */
  public double getRating() {
    return rating;
  }

  /**
   * Getter to return # of flexible courses that can be taken in both semesters.
   *
   * @return an integer, numflex.
   */
  public int getNumflex() {
    return numflex;
  }

  /**
   * Getter to return a double representing the average hours in a semester.
   *
   * @return double, avghrs variable.
   */
  public double getAvghrs() {
    return avghrs;
  }

  public int getNumcourses() {
    numcourses = this.courses.size();
    return numcourses;
  }

  public void setCourses() {
    for (int i = 0; i < courses.size(); i++) {
      switch (i) {
        case 0:
          courseid1 = courses.get(0).getId();
          break;
        case 1:
          courseid2 = courses.get(1).getId();
          break;
        case 2:
          courseid3 = courses.get(2).getId();
          break;
        case 3:
          courseid4 = courses.get(3).getId();
          break;
        default:
          break;
      }
    }
  }

  public String getCourseid1() {
    setCourses();
    return courseid1;
  }

  public String getCourseid2() {
    setCourses();
    return courseid2;
  }

  public String getCourseid3() {
    setCourses();
    return courseid3;
  }

  public String getCourseid4() {
    setCourses();
    return courseid4;
  }

  /**
   * getSemester gets the semester's integer representation.
   *
   * @return the semester as an integer
   */
  public int getSemnumber() {
    return semnumber;
  }

  /**
   * getCourses gets the courses that will be taken for this semester.
   *
   * @return the courses that will taken in this semester
   */
  public List<Node> getCourses() {
    return courses;
  }

//  /**
//   * addCourse adds a course to this semester.
//   *
//   * @param course the course to add
//   */
//  public void addCourse(Node course) {
//    courses.add(course);
//  }
//
//  /**
//   * removeCourse removes a course if the user wants to switch it out for another course.
//   *
//   * @param course the course
//   */
//  public void removeCourse(Node course) {
//    courses.remove(course);
//  }
//
//  /**
//   * getCourseEquivalents gets the course equivalents for the given course i.e. courses that are
//   * offered in that same semester and satisfy the same requirements within the concentration.
//   *
//   * @param course the course
//   * @return the course equivalents
//   */
//  public List<Node> getCourseEquivalents(Node course) {
//    return coursesByCat[course.getCategory()];
//  }
//
//  /**
//   * getAllCoursesAvailableThisSemester gets all courses available this semester as a list where
//   * the category is the index and the values are the courses that can be taken in this semester
//   * that satisfy the category.
//   *
//   * @return the list of all courses by category that can be taken in this semester
//   */
//  public List<Node>[] getAllCoursesAvailableThisSemester() {
//    return coursesByCat;
//  }

}
