package edu.brown.cs.student.pathway;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import edu.brown.cs.student.main.Course;

public class Node {
  private String id;
  private String name;
  private int category;
  private List<Set<Node>> prereqs;
  private Node next;
  private Set<Integer> semestersOffered;
  private double rating;
  private double avgHrs;
  private double maxHrs;
  private int classSize;

  public Node(String i, String n, int cat) {
    id = i;
    name = n;
    category = cat;
    prereqs = new ArrayList<Set<Node>>();
    semestersOffered = new HashSet<Integer>();
  }

  public Node(Course courseRef) {
    id = courseRef.getId();
    name = courseRef.getName();
    category = courseRef.getCategory();
    semestersOffered = courseRef.getSemestersOffered();
    if (courseRef.getNext() != null){
      Node nextNode = new Node(courseRef.getNext());
      next = nextNode;
    }
    if (!courseRef.getPrereqs().isEmpty()) {
      Set<Node> preqs = new HashSet<Node>();
      for (Course preq : courseRef.getPrereqs()) {
        Node prereq = new Node(preq);
        preqs.add(prereq);
      }
      this.addPrereq(preqs);
    }
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCategory() {
    return category;
  }

  public void addPrereq(Set<Node> prereqSet) {
    prereqs.add(prereqSet);
  }

  public void removePrereq(Set<Node> prereqSet) {
    prereqs.remove(prereqSet);
  }

  public List<Set<Node>> getPrereqs() {
    return prereqs;
  }

  public void setNext(Node n) {
    next = n;
  }

  public Node getNext() {
    return next;
  }

  public void setSemesters(Set<Integer> sems) {
    semestersOffered = sems;
  }

  public Set<Integer> getSemestersOffered() {
    return semestersOffered;
  }

  public void setRating(double rate) {
    rating = rate;
  }

  public double getRating() {
    return rating;
  }

  public void setAvgHrs(double avg) {
    avgHrs = avg;
  }

  public double getAvgHrs() {
    return avgHrs;
  }

  public void setMaxHrs(double max) {
    maxHrs = max;
  }

  public double getMaxHrs() {
    return maxHrs;
  }

  public void setClassSize(int size) {
    classSize = size;
  }

  public int getClassSize() {
    return classSize;
  }
}
