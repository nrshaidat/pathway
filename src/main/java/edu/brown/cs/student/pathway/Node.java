package edu.brown.cs.student.pathway;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

/**
 * The Node Class represents a course that 1) fulfills a concentration requirement or
 * 2) satisfies a prerequisite for a course that fulfills a concentration requirement and
 * 3) is still offered at Brown and has data on CAB. The Database is the only class
 * that instantiates Node and populates its attributes for the Pathway class and GUI.
 */
public class Node {
  private String id;
  private String name;
  private int category;
  private String professor;
  private List<Set<Node>> prereqs;
  private Node next;
  private String nextID; // used only for the cache to get the info from the result set
  private Set<Integer> semestersOffered;
  private double avgHrs;
  private double maxHrs;
  private double rating;
  private int classSize;
  private String ssurl;
  private int sem;

  /**
   * Instantiates a new Node.
   *
   * @param courseID the course ID w/o its course name
   */
  public Node(String courseID) {
    id = courseID;
    prereqs = new ArrayList<>();
    semestersOffered = new HashSet<>();
  }

  /**
   * This constructor is only for the brute force tests.
   *
   * @param courseID       course id
   * @param courseCategory course category
   */
  public Node(String courseID, int courseCategory) {
    id = courseID;
    category = courseCategory;
    prereqs = new ArrayList<>();
    semestersOffered = new HashSet<>();
  }

  /**
   * Instantiates a new Node. Meant to be used only in the database to store the values
   * returned from the resultSet in getConcentrationCourses in the Database class, so the cache
   * can use that information and the course data in the cache to make new nodes with all the
   * concentration specific info. This was needed to avoid having multiple result sets open at
   * the same time.
   *
   * @param courseID the course ID w/out its course name so just the department code space and
   *                 the course number
   * @param nextid   the course ID of the next course in a sequence if none exists then it is an
   *                 empty string
   * @param cat      the category associated with the course
   * @author nrshaida (Natalie Rshaidat)
   */
  public Node(String courseID, String nextid, int cat) {
    id = courseID;
    nextID = nextid;
    category = cat;
  }

  public int getSem() {
    if (this.getSemestersOffered().size() == 2) {
      return 2;
    } else if (this.getSemestersOffered().contains(0)) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   * Gets self service url.
   *
   * @return the cab url
   */
  public String getSsurl() {
    return ssurl;
  }

  /**
   * Sets ss url.
   *
   * @param ssurl the ss url
   */
  public void setSsurl(String ssurl) {
    this.ssurl = ssurl;
  }

  /**
   * getNextID gets next id as a string, this is only used for the cache database to build the
   * course set for the concentration.
   *
   * @return the next id
   */
  public String getNextID() {
    return nextID;
  }

  /**
   * Equals method that checks if two nodes are equal.
   *
   * @return a boolean if they are equal or not
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Node)) {
      return false;
    }
    Node node = (Node) o;
    return Double.compare(node.getAvgHrs(), getAvgHrs()) == 0
        && Double.compare(node.getMaxHrs(), getMaxHrs()) == 0
        && Double.compare(node.getRating(), getRating()) == 0 && getClassSize() == node
        .getClassSize() && getId().equals(node.getId()) && getName().equals(node.getName())
        && Objects.equals(getPrereqs(), node.getPrereqs()) && getSemestersOffered()
        .equals(node.getSemestersOffered());
  }

  /**
   * hashCode method gets the hashcode for the node.
   *
   * @return the node's hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }

  /**
   * getId method gets the node's id (ie CSCI 0150) and can never be null so long
   * as the course exists in the db.
   *
   * @return the course id
   */
  public String getId() {
    return id;
  }

  /**
   * setName sets the name of the course (ie Intro to Software Engineering).
   *
   * @param courseName the name of the course with its id
   */
  public void setName(String courseName) {
    name = courseName;
  }

  /**
   * getName gets the course name and is never null .
   *
   * @return the course name
   */
  public String getName() {
    return name;
  }

  /**
   * setCategory sets the concentration requierment category that the course fulfills.
   *
   * @param courseCategory the course's category
   */
  public void setCategory(int courseCategory) {
    category = courseCategory;
  }

  /**
   * getCategory gets the course's category and can never be null.
   *
   * @return the category for the course
   */
  public int getCategory() {
    return category;
  }

  /**
   * setPrereqs sets the course's prereqs if there are any and if there
   * arent any prereqs then an empty list is set as the prereqs.This method is specifically
   * used solely for the database class.
   *
   * @param prerequisites the prerequisites
   */
  public void setPrereqs(List<Set<Node>> prerequisites) {
    prereqs = prerequisites;
  }

  /**
   * addPrereq adds a required set of prereqs, where one course in a set needs to be taken to
   * satisfy that
   * prereq qualification. This is used in the pathway class.
   *
   * @param prereqSet a prereq set to add
   */
  public void addPrereq(Set<Node> prereqSet) {
    prereqs.add(prereqSet);
  }

  /**
   * removePrereq removes a prereq set when a student has fulfilled that section of the prereqs
   * .This is used in the Pathway class.
   *
   * @param prereqSet the prereq set to remove
   */
  public void removePrereq(Set<Node> prereqSet) {
    prereqs.remove(prereqSet);
  }

  /**
   * getPrereqs gets all prereqs for a course and if there aren't any prereqs then it
   * returns an empty list, so check if the size is 0 to see if a course has prereqs.
   *
   * @return all the prereqs for the course
   */
  public List<Set<Node>> getPrereqs() {
    return prereqs;
  }

  /**
   * setNext sets the next course in a sequence (CS15's next field would be CS16).
   * This is only used for mandatory sequence courses.
   *
   * @param nextCourse of the sequence
   */
  public void setNext(Node nextCourse) {
    next = nextCourse;
  }

  /**
   * getNext gets the next course in the sequence if it exists and if it doesn't it will return
   * null.
   *
   * @return the next course in a sequence or null if it doesn't exist
   */
  public Node getNext() {
    return next;
  }

  /**
   * setSemesters sets the hash set of semesters that this course is offered in, so a course that
   * is offered
   * both semesters would have a set of 1 and 0 while if a course if only offered in the fall its
   * set would have only 1
   * and a course that is only offered in the spring would only have 0.
   *
   * @param sems the semesters set to set
   */
  public void setSemesters(Set<Integer> sems) {
    semestersOffered = sems;
  }

  /**
   * getSemestersOffered gets the semesters offered for the course.
   *
   * @return the semesters offered
   */
  public Set<Integer> getSemestersOffered() {
    return semestersOffered;
  }

  /**
   * setProfessor sets the professor for the course, but we are aware that one course can have
   * many professors in a semester so to simplify things we just picked the first section's
   * professor.
   *
   * @param prof the professor to set for the course
   */
  public void setProfessor(String prof) {
    professor = prof;
  }

  /**
   * getProfessor gets the professor for the course.
   *
   * @return the professor that teaches the course
   */
  public String getProfessor() {
    return professor;
  }

  /**
   * setRating sets the course's most recent rating from critical review if it exists, but
   * if it doesnt exist then we set the default to 3.5 as it was the average course rating
   * from all courses.
   *
   * @param rate the course rating
   */
  public void setRating(double rate) {
    rating = rate;
  }

  /**
   * getRating gets the most recent course rating from critical review.
   *
   * @return the course rating
   */
  public double getRating() {
    return rating;
  }

  /**
   * setAvgHrs sets the most recent average hrs for the course from critical review if it exists,
   * but
   * if it doesnt exist then we set the default to 8 hours as it was the average average hourse
   * from all courses.
   *
   * @param avg the most recent average hours for the course from critical review
   */
  public void setAvgHrs(double avg) {
    avgHrs = avg;
  }

  /**
   * getAvgHrs gets the most recent average hrs for the course from critical review.
   *
   * @return the average hrs for the course from critical review
   */
  public double getAvgHrs() {
    return avgHrs;
  }

  /**
   * setMaxHrs sets the most recent max hourse from critical review if it exists, but
   * if it doesnt exist then we set the default to 14 hours as it was the average max
   * hours from all courses.
   *
   * @param max the most recent max hours from critical review
   */
  public void setMaxHrs(double max) {
    maxHrs = max;
  }

  /**
   * getMaxHrs gets the most recent max hrs for the course from critical review.
   *
   * @return the most recent max hrs from critical review
   */
  public double getMaxHrs() {
    return maxHrs;
  }

  /**
   * setClassSize sets the most recent class size from critical review if it exists, but
   * if it doesnt exist then we set the default to 40 as it was the average class size
   * from all courses.
   *
   * @param size the most recent class size from critical review
   */
  public void setClassSize(int size) {
    classSize = size;
  }

  /**
   * getClassSize gets the most recent class size from critical review.
   *
   * @return the most recent class size from critical review
   */
  public int getClassSize() {
    return classSize;
  }

}
