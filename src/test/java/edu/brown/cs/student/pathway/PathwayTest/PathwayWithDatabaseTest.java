package edu.brown.cs.student.pathway.PathwayTest;

import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import edu.brown.cs.student.pathway.Semester;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.main.Database;

public class PathwayWithDatabaseTest {
  private DatabaseCache cache;

  @Before
  public void setUp() throws SQLException {
    cache = new DatabaseCache(new Database("data/coursesDB.db"));
  }

  public void pathwayPrinter(List<Semester> path) {
    for (Semester list : path) {
      System.out.println("Semester: " + list.getSemnumber());
      for (Node course : list.getCourses()) {
        System.out.println(course.getId() + ": " + course.getName());
      }
      System.out.println();
    }
  }


//  @Test
//  public void csConcentrationTest() throws SQLException {
//    String tablename = "computerscienceba";
//    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
//    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
//    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
//    Pathway pathwayMaker = new Pathway(reqs, courseSet);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
//    System.out.println("Computer Science B.A.");
//    System.out.println("----");
//    this.pathwayPrinter(pathwayMaker.getPath());
//  }
//
//
//  @Test
//  public void computationalbiologybaConcentrationTest() throws SQLException {
//    String tablename = "computationalbiologyba";
//    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
//    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
//    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
//    Pathway pathwayMaker = new Pathway(reqs, courseSet);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
//    System.out.println("Computational Biology B.A.");
//    System.out.println("----");
//    this.pathwayPrinter(pathwayMaker.getPath());
//  }
//
  //@Test
  public void computationalbiologyappliedmathematicsandstatisticstrackbsConcentrationTest() throws SQLException {
    String tablename = "computationalbiologyappliedmathematicsandstatisticstrackbs";
    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tablename);

    Set<Node> taken = new HashSet<>();
    for (Node c : courseSet) {
      if (c.getId().equals("CSCI 0170")) {
        taken.add(c);
      }
      if (c.getId().equals("MATH 0100")) {
        taken.add(c);
      }
    }

    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    pathwayMaker.makePathway(taken, 2, false, "med");
    System.out.println("Computational Biology Applied Math & Statistics Track B.S.");
    System.out.println("----");
    this.pathwayPrinter(pathwayMaker.getPath());
  }
//
//  @Test
//  public void computationalbiologycomputersciencetrackbsConcentrationTest() throws SQLException {
//    String tablename = "computationalbiologycomputersciencetrackbs";
//    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
//    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
//    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
//    Pathway pathwayMaker = new Pathway(reqs, courseSet);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
//    System.out.println("Computational Biology Computer Science Track B.S.");
//    System.out.println("----");
//    this.pathwayPrinter(pathwayMaker.getPath());
//  }
//
//
//  @Test
//  public void chemistrybaConcentrationTest() throws SQLException {
//    String tablename = "chemistryba";
//    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
//    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
//    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
//    Pathway pathwayMaker = new Pathway(reqs, courseSet);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
//    System.out.println("Chemistry B.A.");
//    System.out.println("----");
//    this.pathwayPrinter(pathwayMaker.getPath());
//  }
//
//  @Test
//  public void cognitiveneurosciencebaConcentrationTest() throws SQLException {
//    String tablename = "cognitiveneuroscienceba";
//    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
//    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
//    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
//    Pathway pathwayMaker = new Pathway(reqs, courseSet);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
//    System.out.println("Cognitive Neuroscience B.A.");
//    System.out.println("----");
//    this.pathwayPrinter(pathwayMaker.getPath());
//  }
//
//  @Test
//  public void cognitiveneurosciencebsConcentrationTest() throws SQLException {
//    String tablename = "cognitiveneurosciencebs";
//    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
//    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
//    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
//    Pathway pathwayMaker = new Pathway(reqs, courseSet);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
//    System.out.println("Cognitive Neuroscience B.S.");
//    System.out.println("----");
//    this.pathwayPrinter(pathwayMaker.getPath());
//  }
//
//  @Test
//  public void arthistoryConcentrationTest() throws SQLException {
//    String tablename = "historyofartandarchitectureba";
//    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
//    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
//    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
//    Pathway pathwayMaker = new Pathway(reqs, courseSet);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
//    System.out.println("History of Art and Architecture B.A.");
//    System.out.println("----");
//    this.pathwayPrinter(pathwayMaker.getPath());
//  }
//
//  @Test
//  public void bussinessEconomicsbaConcentrationTest() throws SQLException {
//    String tablename = "businesseconomicsba";
//    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
//    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
//    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
//    Pathway pathwayMaker = new Pathway(reqs, courseSet);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
//    System.out.println("Business Economics B.A.");
//    System.out.println("----");
//    this.pathwayPrinter(pathwayMaker.getPath());
//  }
//
//  @Test
//  public void economicsbaConcentrationTest() throws SQLException {
//    String tablename = "economicsba";
//    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
//    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
//    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
//    Pathway pathwayMaker = new Pathway(reqs, courseSet);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
//    System.out.println("Economics B.A.");
//    System.out.println("----");
//    this.pathwayPrinter(pathwayMaker.getPath());
//  }

}

