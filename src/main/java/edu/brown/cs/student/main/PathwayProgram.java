package edu.brown.cs.student.main;

import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import edu.brown.cs.student.pathway.Semester;
import com.google.common.collect.Sets;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.lang.Math;

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
  private List<String> path1Uniques;
  private List<String> path2Uniques;
  private List<String> path3Uniques;
  private int totalnumcourses1;
  private int totalnumcourses2;
  private int totalnumcourses3;
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
  private int[] reqs2;
  private int[] reqs3;
  private Set<Node> courseSet;
  private String concentration;
  private String concentrationName;
  private int risingSem;


  //TODO Rising Sem and other variables not used; Resolve
  private List<String> courseList;

  /**
   * The PathwayProgram constructor that sets all of the necessary variables. It
   * sets the dummy values for the number of semesters,
   * and information about each semester like the average and maxmimum number of hours.
   *
   * @throws SQLException for problems with querying db.
   */
  public PathwayProgram() throws SQLException {
    String file = "data/coursesDB.db";
    Database realDB = new Database(file); // real database that handles sql queries
    cache = new DatabaseCache(realDB);
    concentrationMap = cache.getConcentrationsMap();
    concentrationsList = new ArrayList<>(this.concentrationMap.keySet());
    risingSem = 1;
    courseList = cache.getAllCourseIDs();
  }

  /**
   * Returns a list of Strings representing the courseList.
   *
   * @return a list of Strings.
   */
  public List<String> getCourseList() {
    return courseList;
  }

  // Getter and setter methods for Apache Spark
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

  public int getAvgavghrs1sem() {
    avgavghrs1sem = Math.round(totalavg1 / numsemesters1);
    return ((int) avgavghrs1sem);
  }

  public int getAvgavghrs2sem() {
    avgavghrs2sem = Math.round(totalavg2 / numsemesters2);
    return ((int) avgavghrs2sem);
  }

  public int getAvgavghrs3sem() {
    avgavghrs3sem = Math.round(totalavg3 / numsemesters3);
    return ((int) avgavghrs3sem);
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
    avgrating1path = (totalrating1 / totalnumcourses1);
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

  public int getAvgavghrs1path() {
    avgavghrs1path = Math.round(totalavg1 / totalnumcourses1);
    return ((int) avgavghrs1path);
  }

  public int getAvgavghrs2path() {
    avgavghrs2path = Math.round(totalavg2 / totalnumcourses2);
    return ((int) avgavghrs2path);
  }

  public int getAvgavghrs3path() {
    avgavghrs3path = Math.round(totalavg3 / totalnumcourses3);
    return ((int) avgavghrs3path);
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

  /**
   * Getter method that gets the first pathway, Pathway 1.
   *
   * @return a List of Semesters for the first pathway.
   */
  public List<Semester> getPath1() {
    return path1;
  }

  /**
   * Getter method that gets the second pathway, Pathway 2.
   *
   * @return a List of Semesters for the second pathway.
   */
  public List<Semester> getPath2() {
    return path2;
  }

  /**
   * Getter method that gets the third pathway, Pathway 3.
   *
   * @return a List of Semesters for the third pathway.
   */
  public List<Semester> getPath3() {
    return path3;
  }

  /**
   * Returns a Map of the concentration.
   *
   * @return a Map of key and value String pairs.
   */
  public Map<String, String> getConcentrationMap() {
    return concentrationMap;
  }

  /**
   * Returns a Set of nodes representing the courseSet.
   *
   * @return a Node Set.
   */
  public Set<Node> getCourseSet() {
    return courseSet;
  }

  /**
   * A setter method that sets the concentration in this class to the user-specified value.
   *
   * @param concentration the specified concentration as a String.
   * @throws SQLException if there are problems accessing the database.
   */
  public void setConcentration(String concentration) throws SQLException {
    this.concentration = concentration;
    courseSet = cache.getConcentrationCourses(concentration);
  }

  /**
   * Returns the concentration name that the user has specified.
   *
   * @return a String representing the concentration Name.
   */
  public String getConcentrationName() {
    return concentrationName;
  }

  /**
   * Getter method to set the concentration name.
   *
   * @param concentrationName A String representing the user-chosen concentration.
   */
  public void setConcentrationName(String concentrationName) {
    this.concentrationName = concentrationName;
  }

  /**
   * Creates a clone of sets. This method is used to create copies of the set of
   * taken courses to create pathways 2 and 3.
   *
   * @param original The original set of nodes. (taken)
   * @param <T>      A generic type T.
   * @return Returns a copy of the original set of nodes.
   */
  public static <T> Set<T> clone(Set<T> original) {
    Set<T> copy = original.stream().collect(Collectors.toSet());
    return copy;
  }

  /**
   * The makePathway method takes in params to create a pathway given user input. It
   * uses the concentration String, the set of courses a student has gotten credit for,
   * their rising semester number, and whether they prefer a faster pathway or not. After
   * generating three pathways, this information is displayed on the GUI.
   *
   * @param con        String representing the user choice of concentration.
   * @param taken      The set of nodes representing the courses a user has obtained credit for.
   *                   In the front end, if left empty this set of nodes is length 0.
   * @param sem        The rising semester of a student.
   * @param aggressive A boolean representing if a student wants to complete their
   *                   specified concentration as fast as possible (in the least number
   *                   of semesters).
   * @throws SQLException if there are errors with querying the database.
   */
  public void makePathways(String con, Set<Node> taken, int sem, boolean aggressive)
      throws SQLException {
    concentration = con;
    risingSem = sem;

    List<Integer> reqsTmp = cache.getRequirements(concentration + "_rules");
    reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
    reqs2 = Arrays.copyOf(reqs, reqs.length);
    reqs3 = Arrays.copyOf(reqs, reqs.length);

    Pathway pathway1 = new Pathway(reqs, courseSet);
    pathway1.makePathway(taken, sem, aggressive, "lo");
    path1 = pathway1.getPath();
    this.setPathStats1();

    Pathway pathway2 = new Pathway(reqs2, courseSet);
    pathway2.makePathway(taken, sem, aggressive, "med");
    path2 = pathway2.getPath();
    this.setPathStats2();

    Pathway pathway3 = new Pathway(reqs3, courseSet);
    pathway3.makePathway(taken, sem, aggressive, "hi");
    path3 = pathway3.getPath();
    this.setPathStats3();

    setPathUniques();
  }

  /**
   * A method to return if the concentration has been set of not.
   *
   * @return a boolean checking if concentration is null.
   */
  public boolean isSet() {
    return concentration == null;
  }

  /**
   * Returns the last concentration used for when the user returns back to the page showing all
   * three pathways from each pathway page.
   *
   * @return a String representing the pathways generated for a certain concentration.
   */
  public String getConcentration() {
    return concentration;
  }

  /**
   * Returns the concentration list, a list of strings.
   *
   * @return a List of Strings
   */
  public List<String> getConcentrationsList() {
    return concentrationsList;
  }

  /**
   * This method sets the statistics of Pathway1, the lowest workload pathway.
   * It calcualtes the average rating, hours, and maximum hours by averaging the
   * rating, average hours and maximum hours of each class in the semester. After
   * generating this information, these parameters can be used in the front-end to
   * display a semester summary.
   */
  public void setPathStats1() {
    totalnumcourses1 = 0;
    totalavg1 = 0.0;
    totalmax1 = 0.0;
    totalrating1 = 0.0;
    avgrating1sem = 0.0;
    avgavghrs1sem = 0.0;
    avgmaxhrs1sem = 0.0;
    for (Semester sem : getPath1()) {
      sem.setStats();
      totalnumcourses1 += sem.getCourses().size();
      totalavg1 += sem.getAvghrs();
      totalmax1 += sem.getMaxhrs();
      totalrating1 += sem.getRating();
      avgrating1sem += (sem.getRating() / totalnumcourses1);
      avgavghrs1sem += (sem.getAvghrs() / totalnumcourses1);
      avgmaxhrs1sem += (sem.getMaxhrs() / totalnumcourses1);
    }
  }

  /**
   * This method sets the statistics of Pathway2, the medium workload pathway.
   * It calcualtes the average rating, hours, and maximum hours by averaging the
   * rating, average hours and maximum hours of each class in the semester. After
   * generating this information, these parameters can be used in the front-end to
   * display a semester summary.
   */
  public void setPathStats2() {
    totalnumcourses2 = 0;
    totalavg2 = 0.0;
    totalmax2 = 0.0;
    totalrating2 = 0.0;
    avgrating2sem = 0.0;
    avgavghrs2sem = 0.0;
    avgmaxhrs2sem = 0.0;
    for (Semester sem : getPath2()) {
      sem.setStats();
      totalnumcourses2 += sem.getCourses().size();
      totalavg2 += sem.getAvghrs();
      totalmax2 += sem.getMaxhrs();
      totalrating2 += sem.getRating();
      avgrating2sem += (sem.getRating() / totalnumcourses2);
      avgavghrs2sem += (sem.getAvghrs() / totalnumcourses2);
      avgmaxhrs2sem += (sem.getMaxhrs() / totalnumcourses2);
    }
  }

  /**
   * This method sets the statistics of Pathway3, the most workload intensive pathway.
   * It calcualtes the average rating, hours, and maximum hours by averaging the
   * rating, average hours and maximum hours of each class in the semester. After
   * generating this information, these parameters can be used in the front-end to
   * display a semester summary.
   */
  public void setPathStats3() {
    totalnumcourses3 = 0;
    totalavg3 = 0.0;
    totalmax3 = 0.0;
    totalrating3 = 0.0;
    avgrating3sem = 0.0;
    avgavghrs3sem = 0.0;
    avgmaxhrs3sem = 0.0;
    for (Semester sem : getPath3()) {
      sem.setStats();
      totalnumcourses3 += sem.getCourses().size();
      totalavg3 += sem.getAvghrs();
      totalmax3 += sem.getMaxhrs();
      totalrating3 += sem.getRating();
      avgrating3sem += (sem.getRating() / totalnumcourses3);
      avgavghrs3sem += (sem.getAvghrs() / totalnumcourses3);
      avgmaxhrs3sem += (sem.getMaxhrs() / totalnumcourses3);
    }
  }

  public void setPathUniques() {
    Set<Node> path1Courses = new HashSet<>();
    for (Semester s: path1) {
      path1Courses.addAll(s.getCourses());
    }
    Set<Node> path2Courses = new HashSet<>();
    for (Semester s: path2) {
      path2Courses.addAll(s.getCourses());
    }
    Set<Node> path3Courses = new HashSet<>();
    for (Semester s: path3) {
      path3Courses.addAll(s.getCourses());
    }
    Set<Node> path1UniqueNodes = Sets.difference(path1Courses, Sets.union(path2Courses, path3Courses));
    Set<Node> path2UniqueNodes = Sets.difference(path2Courses, Sets.union(path1Courses, path3Courses));
    Set<Node> path3UniqueNodes = Sets.difference(path3Courses, Sets.union(path1Courses, path2Courses));
    path1Uniques = new ArrayList<>();
    path2Uniques = new ArrayList<>();
    path3Uniques = new ArrayList<>();
    for (Node n: path1UniqueNodes) {
      path1Uniques.add(n.getId());
    }
    for (Node n: path2UniqueNodes) {
      path2Uniques.add(n.getId());
    }
    for (Node n: path3UniqueNodes) {
      path3Uniques.add(n.getId());
    }
  }
  public List<String> getPath1Uniques() {
    return path1Uniques.size() > 3 ? path1Uniques.subList(0, 3) : path1Uniques;
  }
  public List<String> getPath2Uniques() {
    return path2Uniques.size() > 3 ? path2Uniques.subList(0, 3) : path2Uniques;
  }
  public List<String> getPath3Uniques() {
    return path3Uniques.size() > 3 ? path3Uniques.subList(0, 3) : path3Uniques;
  }
}
