package edu.brown.cs.student.pathway;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class PathwayTest {

  public void pathwayPrinter(List<List<Node>> path) {
    int sem = 1;
    for(List<Node> list : path) {
      System.out.println("Semester: " + Integer.toString(sem));
      for (Node course : list) {
        System.out.println(course.getId() + ": " + course.getName());
      }
      System.out.println();
      sem++;
    }
  }

  @Test
  public void csConcentrationSmallTest() {
    Node cs15Node = new Node("csci0150", "Introduction to Object-Oriented Programming", 0);
    Node cs16Node = new Node("csci0160", "Introduction to Algorithms and Data Structures", 0);
    Node cs17Node = new Node("csci0170", "Computer Science: An Integrated Introduction", 0);
    Node cs18Node = new Node("csci0180", "Computer Science: An Integrated Introduction II", 0);
    Node cs19Node = new Node("csci0190", "Accelerated Introduction to Computer Science", 0);
    Node cs22Node = new Node("csci0220", "Discrete Structures and Probability", 1);
    Node cs1010Node = new Node("csci1010", "Theory of Computation", 1);
    Node cs33Node = new Node("csci0330", "Introduction to Computer Systems", 1);
    Node cs32Node = new Node("csci0320", "Introduction to Software Engineering", 1);
    Node cs1660Node = new Node("csci1660", "Introduction to Computer Systems Security", 2);

    cs15Node.setNext(cs16Node);
    cs17Node.setNext(cs18Node);

    cs15Node.setSemesters(new HashSet<Integer>(Arrays.asList(1))); // 1 is fall
    cs17Node.setSemesters(new HashSet<Integer>(Arrays.asList(1)));
    cs19Node.setSemesters(new HashSet<Integer>(Arrays.asList(1)));
    cs1010Node.setSemesters(new HashSet<Integer>(Arrays.asList(1)));
    cs33Node.setSemesters(new HashSet<Integer>(Arrays.asList(1)));
    cs16Node.setSemesters(new HashSet<Integer>(Arrays.asList(0))); // 0 is spring
    cs18Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs22Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs32Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs1660Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));

    cs16Node.addPrereq(new HashSet<Node>(Arrays.asList(cs15Node)));
    cs18Node.addPrereq(new HashSet<Node>(Arrays.asList(cs17Node)));
    cs1010Node.addPrereq(new HashSet<Node>(Arrays.asList(cs22Node)));
    cs33Node.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));
    cs32Node.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));
    cs1660Node.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));
    cs1660Node.addPrereq(new HashSet<Node>(Arrays.asList(cs33Node)));

    Set<Node> courseSet = new HashSet<Node>(Arrays.asList(cs15Node, cs16Node, cs17Node, cs18Node, cs19Node,
        cs22Node, cs1010Node, cs33Node, cs32Node, cs1660Node));

    int[] reqs = {
                    1, // 1 sequence from category 0
                    3, // 3 courses from category 1
                    1  // 1 course from category 2
                 };

    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    pathwayMaker.makePathway();
    this.pathwayPrinter(pathwayMaker.getPath());
  }

  @Test
  public void csConcentrationLargerTest() {
    Node cs15Node = new Node("csci0150", "Introduction to Object-Oriented Programming", 0);
    Node cs16Node = new Node("csci0160", "Introduction to Algorithms and Data Structures", 0);
    Node cs17Node = new Node("csci0170", "Computer Science: An Integrated Introduction", 0);
    Node cs18Node = new Node("csci0180", "Computer Science: An Integrated Introduction II", 0);
    Node cs19Node = new Node("csci0190", "Accelerated Introduction to Computer Science", 0);
    Node cs22Node = new Node("csci0220", "Discrete Structures and Probability", 1);
    Node cs1010Node = new Node("csci1010", "Theory of Computation", 1);
    Node cs33Node = new Node("csci0330", "Introduction to Computer Systems", 1);
    Node cs32Node = new Node("csci0320", "Introduction to Software Engineering", 1);
    Node cs1660Node = new Node("csci1660", "Introduction to Computer Systems Security", 2);
    Node cs1380Node = new Node("csci1380", "Distributed Computer Systems", 2);
    Node cs1320Node = new Node("csci1320", "Creating Modern Web & Mobile Applications", 2);
    Node cs1950YNode = new Node("csci1950Y", "Logic For Systems", 2);
    Node cs1430Node = new Node("csci1430", "Computer Vision", 2);
    Node cs1420Node = new Node("csci1420", "Machine Learning", 2);
    Node cs1410Node = new Node("csci1410", "Artificial Intelligence", 2);
    Node cs1951ANode = new Node("csci1951A", "Data Science", 2);

    cs15Node.setNext(cs16Node);
    cs17Node.setNext(cs18Node);

    cs15Node.setSemesters(new HashSet<Integer>(Arrays.asList(1))); // 1 is fall
    cs17Node.setSemesters(new HashSet<Integer>(Arrays.asList(1)));
    cs19Node.setSemesters(new HashSet<Integer>(Arrays.asList(1)));
    cs1010Node.setSemesters(new HashSet<Integer>(Arrays.asList(1)));
    cs33Node.setSemesters(new HashSet<Integer>(Arrays.asList(1)));
    cs1410Node.setSemesters(new HashSet<Integer>(Arrays.asList(1)));
    cs16Node.setSemesters(new HashSet<Integer>(Arrays.asList(0))); // 0 is spring
    cs18Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs22Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs32Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs1660Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs1380Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs1320Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs1950YNode.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs1430Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs1420Node.setSemesters(new HashSet<Integer>(Arrays.asList(0)));
    cs1951ANode.setSemesters(new HashSet<Integer>(Arrays.asList(0)));

    Node math100Node = new Node("math0100", "Introductory Calculus, Part II", 3);
    Node math520Node = new Node("math0520", "Linear Algebra", 3);
    Node math180Node = new Node("math0180", "Intermediate Calculus", 3);
    Node apma1650Node = new Node("apma1650", "Statistical Inference", 3);

    math100Node.setSemesters(new HashSet<Integer>(Arrays.asList(1, 0)));
    math520Node.setSemesters(new HashSet<Integer>(Arrays.asList(1, 0)));
    math180Node.setSemesters(new HashSet<Integer>(Arrays.asList(1, 0)));
    apma1650Node.setSemesters(new HashSet<Integer>(Arrays.asList(1, 0)));

    math520Node.addPrereq(new HashSet<Node>(Arrays.asList(math100Node)));
    math180Node.addPrereq(new HashSet<Node>(Arrays.asList(math100Node)));
    apma1650Node.addPrereq(new HashSet<Node>(Arrays.asList(math100Node)));

    cs16Node.addPrereq(new HashSet<Node>(Arrays.asList(cs15Node)));
    cs18Node.addPrereq(new HashSet<Node>(Arrays.asList(cs17Node)));
    cs1010Node.addPrereq(new HashSet<Node>(Arrays.asList(cs22Node)));
    cs33Node.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));
    cs32Node.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));
    cs1660Node.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));
    cs1660Node.addPrereq(new HashSet<Node>(Arrays.asList(cs33Node)));
    cs1380Node.addPrereq(new HashSet<Node>(Arrays.asList(cs33Node, cs32Node)));
    cs1320Node.addPrereq(new HashSet<Node>(Arrays.asList(cs33Node, cs32Node)));
    cs1950YNode.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));
    cs1410Node.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));
    cs1410Node.addPrereq(new HashSet<Node>(Arrays.asList(cs22Node, apma1650Node)));
    cs1420Node.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));
    cs1420Node.addPrereq(new HashSet<Node>(Arrays.asList(math180Node)));
    cs1430Node.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));
    cs1430Node.addPrereq(new HashSet<Node>(Arrays.asList(math520Node)));
    cs1951ANode.addPrereq(new HashSet<Node>(Arrays.asList(cs16Node, cs18Node, cs19Node)));

    Set<Node> courseSet = new HashSet<Node>(Arrays.asList(cs15Node, cs16Node, cs17Node, cs18Node, cs19Node,
        cs22Node, cs1010Node, cs33Node, cs32Node, cs1660Node, cs1380Node, cs1320Node, cs1950YNode,
        cs1410Node, cs1420Node, cs1430Node, cs1951ANode, math100Node, math520Node, math180Node, apma1650Node));

    int[] reqs = {1, 3, 6, 2};

    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    pathwayMaker.makePathway();
    this.pathwayPrinter(pathwayMaker.getPath());
  }

}
