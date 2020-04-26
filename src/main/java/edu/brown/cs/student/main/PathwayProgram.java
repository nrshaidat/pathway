package edu.brown.cs.student.main;

import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.PathwayMaker;
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
    return path1;
  }

  public List<Semester> getPath2() {
    return path2;
  }

  public List<Semester> getPath3() {
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
    PathwayMaker pm = new PathwayMaker(concentration, reqs, new HashSet<Node>(), risingSem);
    //Pathway pathway1 = new Pathway(reqs, courseSet);
    //pathway1.makePathway(new HashSet<Node>(), risingSem, aggressive, "lo");
    path1 = pm.getPath1();
    //Pathway pathway2 = new Pathway(reqs, courseSet);
    //pathway2.makePathway(new HashSet<Node>(), risingSem, aggressive, "med");
    path2 = pm.getPath2();
    //Pathway pathway3 = new Pathway(reqs, courseSet);
    //pathway3.makePathway(new HashSet<Node>(), risingSem, aggressive, "hi");
    path3 = pm.getPath3();
  }

  public List<String> getConcentrationsList() {
    List<String> keyList = new ArrayList<String>(this.concentrationMap.keySet());
    return keyList;
  }
}
