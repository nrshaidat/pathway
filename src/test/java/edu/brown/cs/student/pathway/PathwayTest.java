package edu.brown.cs.student.pathway;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Testing the main algorithm, independent of the db.
 */
public class PathwayTest {
  private Set<Node> csCourseSetSmall;
  private Set<Node> csCourseSetLarger;
  private Set<Node> csTakenSetSmall;

  public void pathwayPrinter(List<List<Node>> path, int risingSemester) {
    int sem = risingSemester;
    System.out.println("----");
    for(List<Node> list : path) {
      System.out.println("Semester: " + Integer.toString(sem));
      for (Node course : list) {
        System.out.println(course.getId() + ": " + course.getName());
      }
      System.out.println();
      sem++;
    }
  }

//  @Test
//  public void csConcentrationSmallTest() {
//    int[] reqs = {
//                    1, // 1 sequence from category 0
//                    3, // 3 courses from category 1
//                    1  // 1 course from category 2
//                 };
//    Pathway pathwayMaker = new Pathway(reqs, csCourseSetSmall);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1);
//    this.pathwayPrinter(pathwayMaker.getPath(), 1);
//  }

  @Test
  public void csConcentrationLargerTest() {
    int[] reqs = {1, 3, 6, 2};
    Pathway pathwayMaker = new Pathway(reqs, csCourseSetLarger);
    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "lo");
    this.pathwayPrinter(pathwayMaker.getPath(), 1);
  }

  @Test
  public void csConcentrationLargerWithTakenTest() {
    int[] reqs = {1, 3, 6, 2};
    Pathway pathwayMaker = new Pathway(reqs, csCourseSetLarger);
    pathwayMaker.makePathway(csTakenSetSmall, 2, false, "lo");
    this.pathwayPrinter(pathwayMaker.getPath(), 2);
  }

  @Before
  public void setUp() {
    Node cs15Node = new Node("csci0150", "Introduction to Object-Oriented Programming", 0);
    Node cs16Node = new Node("csci0160", "Introduction to Algorithms and Data Structures", 0);
    Node cs17Node = new Node("csci0170", "Computer Science: An Integrated Introduction", 0);
    Node cs18Node = new Node("csci0180", "Computer Science: An Integrated Introduction II", 0);
    Node cs19Node = new Node("csci0190", "Accelerated Introduction to Computer Science", 0);
    Node cs22Node = new Node("csci0220", "Discrete Structures and Probability", 1);
    Node cs1010Node = new Node("csci1010", "Theory of Computation", 1);
    Node cs33Node = new Node("csci0330", "Introduction to Computer Systems", 1);
    Node cs32Node = new Node("csci0320", "Introduction to Software Engineering", 1);
    Node cs1320Node = new Node("csci1320", "Creating Modern Web & Mobile Applications", 2);
    Node cs1380Node = new Node("csci1380", "Distributed Computer Systems", 2);
    Node cs1410Node = new Node("csci1410", "Artificial Intelligence", 2);
    Node cs1420Node = new Node("csci1420", "Machine Learning", 2);
    Node cs1430Node = new Node("csci1430", "Computer Vision", 2);
    Node cs1660Node = new Node("csci1660", "Introduction to Computer Systems Security", 2);
    Node cs1950YNode = new Node("csci1950Y", "Logic For Systems", 2);
    Node cs1951ANode = new Node("csci1951A", "Data Science", 2);

    cs15Node.setAvgHrs(11.59);
    cs15Node.setMaxHrs(20.74);
    cs15Node.setRating(4.21);
    cs15Node.setClassSize(390);
    cs16Node.setAvgHrs(9.03);
    cs16Node.setMaxHrs(17.46);
    cs16Node.setRating(3.92);
    cs16Node.setClassSize(275);

    cs17Node.setAvgHrs(9.91);
    cs17Node.setMaxHrs(19.58);
    cs17Node.setRating(4.05);
    cs17Node.setClassSize(181);
    cs18Node.setAvgHrs(10.09);
    cs18Node.setMaxHrs(22.34);
    cs18Node.setRating(4.08);
    cs18Node.setClassSize(182);

    cs19Node.setAvgHrs(9.04);
    cs19Node.setMaxHrs(15.00);
    cs19Node.setRating(4.22);
    cs19Node.setClassSize(76);

    cs22Node.setAvgHrs(7.73);
    cs22Node.setMaxHrs(12.31);
    cs22Node.setRating(4.25);
    cs22Node.setClassSize(290);
    cs1010Node.setAvgHrs(9.31);
    cs1010Node.setMaxHrs(14.00);
    cs1010Node.setRating(4.06);
    cs1010Node.setClassSize(53);

    cs32Node.setAvgHrs(19.21);
    cs32Node.setMaxHrs(32.15);
    cs32Node.setRating(3.97);
    cs32Node.setClassSize(140);
    cs33Node.setAvgHrs(13.79);
    cs33Node.setMaxHrs(22.24);
    cs33Node.setRating(4.01);
    cs33Node.setClassSize(323);

    cs1320Node.setAvgHrs(8.96);
    cs1320Node.setMaxHrs(18.00);
    cs1320Node.setRating(3.16);
    cs1320Node.setClassSize(101);

    cs1380Node.setAvgHrs(13.4);
    cs1380Node.setMaxHrs(25.05);
    cs1380Node.setRating(3.58);
    cs1380Node.setClassSize(98);

    cs1410Node.setAvgHrs(7.92);
    cs1410Node.setMaxHrs(14.38);
    cs1410Node.setRating(3.86);
    cs1410Node.setClassSize(196);

    cs1420Node.setAvgHrs(8.53);
    cs1420Node.setMaxHrs(14.47);
    cs1420Node.setRating(3.7);
    cs1420Node.setClassSize(271);

    cs1430Node.setAvgHrs(8.15);
    cs1430Node.setMaxHrs(13.15);
    cs1430Node.setRating(4.19);
    cs1430Node.setClassSize(181);

    cs1660Node.setAvgHrs(8.13);
    cs1660Node.setMaxHrs(14.53);
    cs1660Node.setRating(3.98);
    cs1660Node.setClassSize(89);

    cs1950YNode.setAvgHrs(5.86);
    cs1950YNode.setMaxHrs(10.36);
    cs1950YNode.setRating(3.86);
    cs1950YNode.setClassSize(139);

    cs1951ANode.setAvgHrs(8.37);
    cs1951ANode.setMaxHrs(17.28);
    cs1951ANode.setRating(3.5);
    cs1951ANode.setClassSize(198);

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

    math100Node.setAvgHrs(3.6);
    math100Node.setMaxHrs(6.4);
    math100Node.setRating(3.79);
    math100Node.setClassSize(27);

    math520Node.setAvgHrs(5.5);
    math520Node.setMaxHrs(8.63);
    math520Node.setRating(3.41);
    math520Node.setClassSize(41);

    math180Node.setAvgHrs(3.63);
    math180Node.setMaxHrs(6.5);
    math180Node.setRating(3.5);
    math180Node.setClassSize(16);

    apma1650Node.setAvgHrs(6.13);
    apma1650Node.setMaxHrs(10.63);
    apma1650Node.setRating(4.01);
    apma1650Node.setClassSize(278);

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

    csCourseSetLarger = new HashSet<Node>(Arrays.asList(cs15Node, cs16Node, cs17Node, cs18Node, cs19Node,
        cs22Node, cs1010Node, cs33Node, cs32Node, cs1660Node, cs1380Node, cs1320Node, cs1950YNode,
        cs1410Node, cs1420Node, cs1430Node, cs1951ANode, math100Node, math520Node, math180Node, apma1650Node));
    csCourseSetSmall = new HashSet<Node>(Arrays.asList(cs15Node, cs16Node, cs17Node, cs18Node, cs19Node,
        cs22Node, cs1010Node, cs33Node, cs32Node, cs1660Node));
    csTakenSetSmall = new HashSet<Node>(Arrays.asList(math100Node, math520Node, cs15Node));
  }

}
