package edu.brown.cs.student.pathway;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PathwayWithDatabaseTest {

  public void pathwayPrinter(List<Semester> path) {
    System.out.println("----");
    for(Semester list : path) {
      System.out.println("Semester: " + list.getSemester());
      for (Node course : list.getCourses()) {
        System.out.println(course.getId() + ": " + course.getName());
      }
      System.out.println();
    }
  }

  @Test
  public void csConcentrationTest() {

  }


  @Test
  public void mathConcentrationTest() {

  }
  
}
