package edu.brown.cs.student.pathway.PathwayTest;

import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import edu.brown.cs.student.pathway.Semester;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.main.Database;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;
import com.google.gson.Gson;

/**
 * Testing the Pathway algorithm, with data from the db.
 */
public class PathwayWithDatabaseTest {
  private DatabaseCache cache;
  private ImmutableMap<String, Range<Double>> workloads; // hours ranges for lo, med and hi workloads

  @Before
  public void setUp() throws SQLException {
    cache = new DatabaseCache(new Database("data/coursesDB.db"), false);
    workloads = ImmutableMap.of("lo", Range.closedOpen(1.0, 25.0),
        "med", Range.closedOpen(25.0, 40.0),
        "hi", Range.closedOpen(40.0, 80.0));
  }

  /**
   * This method is separate from validPathSatisfiesPreferences() because
   * the printing can slow down compiling and it makes looking at terminal
   * command history inconvenient. You can comment out the pathwayPrinter()
   * calls when compiling for speed, and can keep it in when you want to see
   * what the algo outputs independent of the front-end.
   * @param path path to print
   * @param concentration concentration header
   */
  public void pathwayPrinter(List<Semester> path, String concentration) {
    System.out.println(concentration);
    System.out.println("----");
    for (Semester list : path) {
      System.out.println("Semester: " + list.getSemnumber());
      for (Node course : list.getCourses()) {
        System.out.println(course.getId() + ": " + course.getName());
      }
      System.out.println();
    }
  }

  public void validPathSatisfiesPreferences(List<Semester> path, String workload) {
    // Iterate Through Schedule to Ensure that the Semester is Valid and that we Satisfy Preference
    for (Semester list : path) {
      List<Node> courses = list.getCourses();
      assertTrue(list.getSemnumber() <= 12); // limit to number of semesters
      assertTrue(courses.size() <= 4); // limit to number of courses taken in one semester
      double avgHrs = 0.0;
      for (Node course : courses) {
        avgHrs += course.getAvgHrs();
      }
      assertTrue(workloads.get(workload).contains(avgHrs) || // we are either exactly satisfy
          (workloads.get(workload).upperEndpoint() + 10) >= avgHrs ||  // workload preference, or we are
          (workloads.get(workload).lowerEndpoint() - 15) <= avgHrs);   // off by one class
    }
  }

  @Test
  public void computerScienceBaConcentrationTest() throws SQLException {
    String tableName = "computerscienceba";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "med";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Computer Science B.A.");
  }

  @Test
  public void computationalBiologyBaConcentrationTest() throws SQLException {
    String tableName = "computationalbiologyba";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "med";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Computational Biology B.A.");
  }


  @Test
  public void computationalBiologyAppliedMathematicsAndStatisticsTrackBsWithTakenConcentrationTest()
      throws SQLException {
    String tableName = "computationalbiologyappliedmathematicsandstatisticstrackbs";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);

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
    String workload = "med";
    pathwayMaker.makePathway(taken, 2, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Computational Biology Applied Math & Statistics Track B.S.");
  }

  @Test
  public void computationalBiologyComputerScienceTrackBsConcentrationTest() throws SQLException {
    String tableName = "computationalbiologycomputersciencetrackbs";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "med";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Computational Biology Computer Science Track B.S.");
  }


  @Test
  public void chemistryBaConcentrationTest() throws SQLException {
    String tableName = "chemistryba";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "med";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Chemistry B.A.");
  }

  @Test
  public void cognitiveNeuroscienceBaConcentrationTest() throws SQLException {
    String tableName = "cognitiveneuroscienceba";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "med";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Cognitive Neuroscience B.A.");
  }

  @Test
  public void cognitiveNeuroscienceBsConcentrationTest() throws SQLException {
    String tableName = "cognitiveneurosciencebs";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "med";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Cognitive Neuroscience B.S.");
  }

  @Test
  public void artHistoryBaConcentrationTest() throws SQLException {
    String tableName = "historyofartandarchitectureba";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "med";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "History of Art and Architecture B.A.");
  }

  @Test
  public void businessEconomicsBaConcentrationTest() throws SQLException {
    String tableName = "businesseconomicsba";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "med";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Business Economics B.A.");
  }

  @Test
  public void economicsBaConcentrationTest() throws SQLException {
    String tableName = "economicsba";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "med";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Economics B.A.");
  }

  @Test
  public void cognitiveNeuroscienceBaHighWorkloadConcentrationTest() throws SQLException {
    String tableName = "cognitiveneuroscienceba";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "hi";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Cognitive Neuroscience B.A.");
  }

  @Test
  public void economicsBaLowWorkloadConcentrationTest() throws SQLException {
    String tableName = "economicsba";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    String workload = "lo";
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();
    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Economics B.A.");
  }

  @Test
  public void computationalBiologyBaAggressiveVsNotAggressiveConcentrationTest() throws SQLException {
    String tableName = "computationalbiologyba";
    List<Integer> reqsTmp = cache.getRequirements(tableName + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tableName);

    int[] reqs1 = Arrays.copyOf(reqs, reqs.length);
    Gson gson = new Gson();
    Set<Node> deepCopy = new HashSet<>();
    for (Node c : courseSet) {
      Node deep = gson.fromJson(gson.toJson(c), Node.class);
      deepCopy.add(deep);
    }

    String workload = "med";
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    pathwayMaker.makePathway(new HashSet<>(), 1, false, workload);
    List<Semester> path = pathwayMaker.getPath();

    Pathway pathwayMaker1 = new Pathway(reqs1, deepCopy);
    pathwayMaker1.makePathway(new HashSet<>(), 1, true, workload);
    List<Semester> path1 = pathwayMaker1.getPath();

    this.validPathSatisfiesPreferences(path, workload);
    //this.pathwayPrinter(path, "Computational Biology B.A.");
    this.validPathSatisfiesPreferences(path1, workload);
    //this.pathwayPrinter(path1, "Computational Biology B.A. (Aggressive)");
    assertTrue(path1.size() <= path.size());
  }

}

