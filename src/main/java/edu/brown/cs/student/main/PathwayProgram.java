package edu.brown.cs.student.main;

import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import edu.brown.cs.student.pathway.Semester;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * PathwayProgram.
 */
public class PathwayProgram {
  private DatabaseInterface cache;
  private Map<String, String> concentrationMap;
  private List<String> concentrationsList;
  private List<Semester> path1;
  private List<Semester> path2;
  private List<Semester> path3;
  private Pathway p1;
  private Pathway p2;
  private Pathway p3;

  /**
   * PathwayProgram constructor.
   */
  public PathwayProgram() throws SQLException {
    String file = "data/coursesDB.db";
    Database realDB = new Database(file); // real database that handles sql queries
    cache = new DatabaseCache(realDB);
    concentrationMap = cache.getConcentrationsMap();
  }

  public DatabaseInterface getCache() {
    return cache;
  }

  public List<Semester> getPath1() {
    path1 = p1.getPath();
    return path1;
  }

  public List<Semester> getPath2() {
    path2 = p2.getPath();
    return path2;
  }

  public List<Semester> getPath3() {
    path3 = p3.getPath();
    return path3;
  }

  public Map<String, String> getConcentrationMap() {
    return concentrationMap;
  }

  public void makePathway(String concentration, Set<Node> taken, int risingSem,
                                   boolean aggressive) throws SQLException {
    List<Integer> reqsTmp = cache.getRequirements(concentration + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(concentration);
    Pathway pathway1 = new Pathway(reqs, courseSet);
    pathway1.makePathway(new HashSet<Node>(), risingSem, aggressive, "lo");
    path1 = pathway1.getPath();
    p1 = pathway1;
    Pathway pathway2 = new Pathway(reqs, courseSet);
    pathway2.makePathway(new HashSet<Node>(), risingSem, aggressive, "med");
    path2 = pathway2.getPath();
    p2 = pathway2;
    Pathway pathway3 = new Pathway(reqs, courseSet);
    pathway3.makePathway(new HashSet<Node>(), risingSem, aggressive, "hi");
    path3 = pathway3.getPath();
    p3 = pathway3;
  }

  public List<String> getConcentrationsList() {
    List<String> keyList = new ArrayList<String>(this.concentrationMap.keySet());
    return keyList;
  }
}
