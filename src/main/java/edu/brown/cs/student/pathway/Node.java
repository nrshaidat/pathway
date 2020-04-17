package edu.brown.cs.student.pathway;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Node {
  private String id;
  private String name;
  private int category;
  private String professor;
  private List<Set<Node>> prereqs;
  private Node next;
  private Set<Integer> semestersOffered;
  private double avgHrs;
  private double maxHrs;
  private double rating;
  private int classSize;
  private double priority;

  /**
   * Note: this constructor should be removed later on.
   * @param i id
   * @param n name
   * @param cat category
   */
  public Node(String i, String n, int cat) {
    id = i;
    name = n;
    category = cat;
    prereqs = new ArrayList<Set<Node>>();
    semestersOffered = new HashSet<Integer>();
    priority = 0;
  }

  public Node(String i) {
    id = i;
    prereqs = new ArrayList<Set<Node>>();
    semestersOffered = new HashSet<Integer>();
    priority = 0;
  }

  public String getId() {
    return id;
  }

  public void setPriority(double pri) {
    priority = pri;
  }

  public double getPriority() {
    return priority;
  }
  
  public void setName(String n) {
    name = n;
  }

  public String getName() {
    return name;
  }
  
  public void setCategory(int cat) {
    category = cat;
  }

  public int getCategory() {
    return category;
  }

  public void setPrereqs(List<Set<Node>> prerequisites) {
    prereqs = prerequisites;
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

  public void setProfessor(String prof) {
    professor = prof;
  }

  public String getProfessor() {
    return professor;
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
