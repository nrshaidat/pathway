package edu.brown.cs.student.main;

import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import edu.brown.cs.student.pathway.Semester;

import java.sql.SQLException;
import java.util.ArrayList;
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
  private List<Semester> path;

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

  public List<Semester> getPath() {
    return path;
  }

  public Map<String, String> getConcentrationMap() {
    return concentrationMap;
  }

  public void makePathway(String concentration, Set<Node> taken, int risingSem,
                                   boolean aggressive, String workload) throws SQLException {
    List<Integer> reqsTmp = cache.getRequirements(concentration + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(concentration);
    Pathway pathway = new Pathway(reqs, courseSet);
    pathway.makePathway(taken, risingSem, aggressive, workload);
    path = pathway.getPath();
  }

  public List<String> getConcentrationsList() {
    List<String> keyList = new ArrayList<String>(this.concentrationMap.keySet());
    return keyList;
  }
}
