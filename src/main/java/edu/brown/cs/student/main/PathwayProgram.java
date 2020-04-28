package edu.brown.cs.student.main;

import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
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
  private Map<String, String> concentrationMap; //map of concentration GUI names to table names
  private List<String> concentrationsList;
  private List<Semester> path1;
  private List<Semester> path2;
  private List<Semester> path3;
  private int totalnumcourses1;
  private int totalnumcourses2;
  private int totalnumcourses3;
  private int totalflexcourses1;
  private int totalflexcourses2;
  private int totalflexcourses3;
  private double totalavg1;
  private double totalavg2;
  private double totalavg3;
  private double totalmax1;
  private double totalmax2;
  private double totalmax3;
  private double totalrating1;
  private double totalrating2;
  private double totalrating3;
  private double avgrating1sem;
  private double avgrating2sem;
  private double avgrating3sem;
  private double avgavghrs1sem;
  private double avgavghrs2sem;
  private double avgavghrs3sem;
  private double avgmaxhrs1sem;
  private double avgmaxhrs2sem;
  private double avgmaxhrs3sem;
  private double avgrating1path;
  private double avgrating2path;
  private double avgrating3path;
  private double avgavghrs1path;
  private double avgavghrs2path;
  private double avgavghrs3path;
  private double avgmaxhrs1path;
  private double avgmaxhrs2path;
  private double avgmaxhrs3path;
  private int numsemesters1;
  private int numsemesters2;
  private int numsemesters3;
  private int[] reqs;
  private Set<Node> courseSet;
  private String concentration;
  private String concentrationName;
  private int risingSem;
  private List<String> courseList;

  /**
   * PathwayProgram constructor.
   */
  public PathwayProgram() throws SQLException {
    String file = "data/coursesDB.db";
    Database realDB = new Database(file); // real database that handles sql queries
    cache = new DatabaseCache(realDB);
    concentrationMap = cache.getConcentrationsMap();
    concentrationsList = new ArrayList<String>(this.concentrationMap.keySet());
    risingSem = 1;
    courseList=cache.getAllCourseIDs();
    numsemesters1 = 0;
    numsemesters2 = 0;
    numsemesters3 = 0;
    totalnumcourses1 = 0;
    totalnumcourses2 = 0;
    totalnumcourses3 = 0;
    totalflexcourses1 = 0;
    totalflexcourses2 = 0;
    totalflexcourses3 = 0;
    totalavg1 = 0.0;
    totalavg2 = 0.0;
    totalavg3 = 0.0;
    totalmax1 = 0.0;
    totalmax2 = 0.0;
    totalmax3 = 0.0;
    totalrating1 = 0.0;
    totalrating2 = 0.0;
    totalrating3 = 0.0;
    avgrating1sem = 0.0;
    avgrating2sem = 0.0;
    avgrating3sem = 0.0;
    avgavghrs1sem = 0.0;
    avgavghrs2sem = 0.0;
    avgavghrs3sem = 0.0;
    avgmaxhrs1sem = 0.0;
    avgmaxhrs2sem = 0.0;
    avgmaxhrs3sem = 0.0;
    avgrating1path = 0.0;
    avgrating2path = 0.0;
    avgrating3path = 0.0;
    avgavghrs1path = 0.0;
    avgavghrs2path = 0.0;
    avgavghrs3path = 0.0;
    avgmaxhrs1path = 0.0;
    avgmaxhrs2path = 0.0;
    avgmaxhrs3path = 0.0;
  }

  public double getAvgrating1sem() {
    avgrating1sem = (avgrating1sem / numsemesters1);
    return avgrating1sem;
  }

  public double getAvgrating2sem() {
    avgrating2sem = (avgrating1sem / numsemesters2);
    return avgrating2sem;
  }

  public double getAvgrating3sem() {
    avgrating3sem = (avgrating3sem / numsemesters3);
    return avgrating3sem;
  }

  public double getAvgavghrs1sem() {
    avgavghrs1sem = (totalavg1 / numsemesters1);
    return avgavghrs1sem;
  }

  public double getAvgavghrs2sem() {
    avgavghrs2sem = (totalavg2 / numsemesters2);
    return avgavghrs2sem;
  }

  public double getAvgavghrs3sem() {
    avgavghrs3sem = (totalavg3 / numsemesters3);
    return avgavghrs3sem;
  }

  public double getAvgmaxhrs1sem() {
    avgmaxhrs1sem = (avgmaxhrs1sem / numsemesters1);
    return avgmaxhrs1sem;
  }

  public double getAvgmaxhrs2sem() {
    avgmaxhrs2sem = (avgmaxhrs2sem / numsemesters2);
    return avgmaxhrs2sem;
  }

  public double getAvgmaxhrs3sem() {
    avgmaxhrs3sem = (avgmaxhrs3sem / numsemesters3);
    return avgmaxhrs3sem;
  }

  public double getAvgrating1path() {
    avgrating1path = (totalrating1/totalnumcourses1);
    return avgrating1path;
  }

  public double getAvgrating2path() {
    avgrating2path = (totalrating2 / totalnumcourses2);
    return avgrating2path;
  }

  public double getAvgrating3path() {
    avgrating3path = (totalrating3 / totalnumcourses3);
    return avgrating3path;
  }

  public double getAvgavghrs1path() {
    avgavghrs1path = (totalavg1 / totalnumcourses1);
    return avgavghrs1path;
  }

  public double getAvgavghrs2path() {
    avgavghrs2path = (totalavg2 / totalnumcourses2);
    return avgavghrs2path;
  }

  public double getAvgavghrs3path() {
    avgavghrs3path = (totalavg3 / totalnumcourses3);
    return avgavghrs3path;
  }

  public double getAvgmaxhrs1path() {
    avgmaxhrs1path = (totalmax1 / totalnumcourses1);
    return avgmaxhrs1path;
  }

  public double getAvgmaxhrs2path() {
    avgmaxhrs2path = (totalmax2 / totalnumcourses2);
    return avgmaxhrs2path;
  }

  public double getAvgmaxhrs3path() {
    avgmaxhrs3path = (totalmax3 / totalnumcourses3);
    return avgmaxhrs3path;
  }

  public int getTotalnumcourses1() {
    return totalnumcourses1;
  }

  public int getTotalnumcourses2() {
    return totalnumcourses2;
  }

  public int getTotalnumcourses3() {
    return totalnumcourses3;
  }

  public int getTotalflexcourses1() {
    return totalflexcourses1;
  }

  public int getTotalflexcourses2() {
    return totalflexcourses2;
  }

  public int getTotalflexcourses3() {
    return totalflexcourses3;
  }

  public double getTotalavg1() {
    return totalavg1;
  }

  public double getTotalavg2() {
    return totalavg2;
  }

  public double getTotalavg3() {
    return totalavg3;
  }

  public double getTotalmax1() {
    return totalmax1;
  }

  public double getTotalmax2() {
    return totalmax2;
  }

  public double getTotalmax3() {
    return totalmax3;
  }

  public double getTotalrating1() {
    return totalrating1;
  }

  public double getTotalrating2() {
    return totalrating2;
  }

  public double getTotalrating3() {
    return totalrating3;
  }


  public int getNumsemesters1() {
    numsemesters1 = this.path1.size();
    return numsemesters1;
  }

  public int getNumsemesters2() {
    numsemesters2 = this.path2.size();
    return numsemesters2;
  }

  public int getNumsemesters3() {
    numsemesters3 = this.path3.size();
    return numsemesters3;
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

  public Set<Node> getCourseSet() {
    return courseSet;
  }

  public void setConcentration(String concentration) throws SQLException {
    this.concentration = concentration;
    courseSet = cache.getConcentrationCourses(concentration);
  }

  public String getConcentrationName() {
    return concentrationName;
  }

  public void setConcentrationName(String concentrationName) {
    this.concentrationName = concentrationName;
  }

  public void makePathways(String con, Set<Node> taken, int sem, boolean aggressive)
      throws SQLException {
    concentration = con;
    risingSem = sem;
    List<Integer> reqsTmp = cache.getRequirements(concentration + "_rules");
    reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    Pathway pathway = new Pathway(reqs, courseSet);
    pathway.makePathway(taken, sem, aggressive, "lo");
    path1 = pathway.getPath();
    this.setPathStats1();
    pathway.makePathway(taken, sem, aggressive, "med");
    path2 = pathway.getPath();
    this.setPathStats2();
    pathway.makePathway(taken, sem, aggressive, "hi");
    path3 = pathway.getPath();
    this.setPathStats3();
  }
  public boolean isSet() {
    return concentration == null;
  }

  public String getConcentration() {
    return concentration;
  }

  public List<String> getConcentrationsList() {
    return concentrationsList;
  }
  public void setPathStats1() {
    totalnumcourses1 = 0;
    totalflexcourses1 = 0;
    totalavg1 = 0.0;
    totalmax1 = 0.0;
    totalrating1 = 0.0;
    avgrating1sem = 0.0;
    avgavghrs1sem = 0.0;
    avgmaxhrs1sem = 0.0;
    for(Semester sem: getPath1()) {
      sem.setStats();
      totalnumcourses1 += sem.getCourses().size();
      totalflexcourses1 += sem.getNumflex();
      totalavg1 += sem.getAvghrs();
      totalmax1 += sem.getMaxhrs();
      totalrating1 += sem.getRating();
      avgrating1sem += (sem.getRating()/ totalnumcourses1);
      avgavghrs1sem += (sem.getAvghrs()/ totalnumcourses1);
      avgmaxhrs1sem += (sem.getMaxhrs()/ totalnumcourses1);
    }
  }

  public void setPathStats2() {
    totalnumcourses2 = 0;
    totalflexcourses2 = 0;
    totalavg2 = 0.0;
    totalmax2 = 0.0;
    totalrating2 = 0.0;
    avgrating2sem = 0.0;
    avgavghrs2sem = 0.0;
    avgmaxhrs2sem = 0.0;
    for (Semester sem : getPath2()) {
      sem.setStats();
      totalnumcourses2 += sem.getCourses().size();
      totalflexcourses2 += sem.getNumflex();
      totalavg2 += sem.getAvghrs();
      totalmax2 += sem.getMaxhrs();
      totalrating2 += sem.getRating();
      avgrating2sem += (sem.getRating() / totalnumcourses2);
      avgavghrs2sem += (sem.getAvghrs() / totalnumcourses2);
      avgmaxhrs2sem += (sem.getMaxhrs() / totalnumcourses2);
    }
  }

  public void setPathStats3() {
    totalnumcourses3 = 0;
    totalflexcourses3 = 0;
    totalavg3 = 0.0;
    totalmax3 = 0.0;
    totalrating3 = 0.0;
    avgrating3sem = 0.0;
    avgavghrs3sem = 0.0;
    avgmaxhrs3sem = 0.0;
    for (Semester sem : getPath3()) {
      sem.setStats();
      totalnumcourses3 += sem.getCourses().size();
      totalflexcourses3 += sem.getNumflex();
      totalavg3 += sem.getAvghrs();
      totalmax3 += sem.getMaxhrs();
      totalrating3 += sem.getRating();
      avgrating3sem += (sem.getRating() / totalnumcourses3);
      avgavghrs3sem += (sem.getAvghrs() / totalnumcourses3);
      avgmaxhrs3sem += (sem.getMaxhrs() / totalnumcourses3);
    }
  }
}
