package edu.brown.cs.student.main;

import java.util.List;
import java.util.Set;

/**
 * Course class the the db and gui uses.
 */

public class Course {
  private String name;
  private String id;
  private List<Course> prereqs;
  private String professor;
  private double courseRating;
  private double maxHrs;
  private double avgHrs;
  private Set<Integer> semestersOffered;
  private Course next;
  private int category;
  private String CRurl;
  private int classSize;
  
  /**
   * Course constructor.
   */
  public Course(String courseID) {
    id = courseID;
  }
  
  public double getAvgHrs() {
    return avgHrs;
  }
  
  public void setAvgHrs(double avgHrs) {
    this.avgHrs = avgHrs;
  }
  
  public int getClassSize() {
    return classSize;
  }
  
  public void setClassSize(int classSize) {
    this.classSize = classSize;
  }
  
  public String getCRurl() {
    return CRurl;
  }
  
  public void setCRurl(String CRurl) {
    this.CRurl = CRurl;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public List<Course> getPrereqs() {
    return prereqs;
  }
  
  public void setPrereqs(List<Course> prereqs) {
    this.prereqs = prereqs;
  }
  
  public String getProfessor() {
    return professor;
  }
  
  public void setProfessor(String professor) {
    this.professor = professor;
  }
  
  public double getCourseRating() {
    return courseRating;
  }
  
  public void setCourseRating(double courseRating) {
    this.courseRating = courseRating;
  }
  
  public double getMaxHrs() {
    return maxHrs;
  }
  
  public void setMaxHrs(double maxHrs) {
    this.maxHrs = maxHrs;
  }
  
  public Set<Integer> getSemestersOffered() {
    return semestersOffered;
  }
  
  public void setSemestersOffered(Set<Integer> semestersOffered) {
    this.semestersOffered = semestersOffered;
  }
  
  public Course getNext() {
    return next;
  }
  
  public void setNext(Course next) {
    this.next = next;
  }
  
  public int getCategory() {
    return category;
  }
  
  public void setCategory(int category) {
    this.category = category;
  }
}
