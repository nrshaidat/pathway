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
  private int[] reqs;
  private Set<Node> courseSet;
  private String concentration;
  private int risingSem;

  /**
   * PathwayProgram constructor.
   */
  public PathwayProgram() throws SQLException {
    String file = "data/coursesDB.db";
    Database realDB = new Database(file); // real database that handles sql queries
    cache = new DatabaseCache(realDB);
    concentrationMap = cache.getConcentrationsMap();
    concentration = null;
    risingSem = 1;
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

  public void makePathways(String con, Set<Node> taken, int sem, boolean aggressive)
      throws SQLException {
    concentration = con;
    risingSem = sem;
    List<Integer> reqsTmp = cache.getRequirements(concentration + "_rules");
    reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    courseSet = cache.getConcentrationCourses(concentration);
    PathwayMaker pm = new PathwayMaker(concentration, reqs, new HashSet<Node>(), risingSem);
    path1 = pm.getPath1();
    path2 = pm.getPath2();
    path3 = pm.getPath3();
  }
  public boolean isSet() {
    return concentration == null;
  }

  public String getConcentration() {
    return concentration;
  }

  public void makeNewPathway() {
    PathwayMaker pm = new PathwayMaker(concentration, reqs, new HashSet<Node>(), risingSem);
    path1 = pm.getPath1();
    path2 = pm.getPath2();
    path3 = pm.getPath3();

  }

  public List<String> getConcentrationsList() {
    List<String> keyList = new ArrayList<String>(this.concentrationMap.keySet());
    return keyList;
  }
}
