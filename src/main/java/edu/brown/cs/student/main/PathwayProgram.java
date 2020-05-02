package edu.brown.cs.student.main;

import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import edu.brown.cs.student.pathway.Semester;
import com.google.common.collect.Sets;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * PathwayProgram.
 */
public class PathwayProgram {
  private static final int MAGICNUM = 7;
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
  private double avgavghrs1path;
  private double avgavghrs2path;
  private double avgavghrs3path;
  private double avgmaxhrs1path;
  private double avgmaxhrs2path;
  private double avgmaxhrs3path;
  private double avgrating1path;
  private double avgrating2path;
  private double avgrating3path;
  private int numsemesters1;
  private int numsemesters2;
  private int numsemesters3;
  private int[] reqs;
  private int[] reqs2;
  private int[] reqs3;
  private Set<Node> courseSet;
  private String concentration;
  private String concentrationName;
  private static List<String> yearList = new ArrayList<String>() {{
      add("Fall");
      add("Spring");
    }};
  private static List<String> gradeList = new ArrayList<String>() {{
      add("Freshman");
      add("Sophomore");
      add("Junior");
      add("Senior");
    }};
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
    courseList = cache.getAllCourseIDs();
    this.setConcentrationName("Computer Science B.A.");
  }
  public static List<String> getYearList() {
    return yearList;
  }
  public static List<String> getGradeList() {
    return gradeList;
  }

  public void setPath1(List<Semester> path1) {
    this.path1 = path1;
  }

  public void setPath2(List<Semester> path2) {
    this.path2 = path2;
  }

  public void setPath3(List<Semester> path3) {
    this.path3 = path3;
  }

  public Set<Node> parseTaken(String coursestaken) {
    String[] cList = coursestaken.split(",");
    Set<Node> taken = new HashSet<>();
    for (int i = 0; i < cList.length; i++) {
      for (Node c : this.getCourseSet()) {
        if (c.getId().equals(cList[i])) {
          taken.add(c);
        }
      }
    }
    return taken;
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
  public int getAvgavghrs1path() {
    return ((int) avgavghrs1path);
  }

  public int getAvgavghrs2path() {
    return ((int) avgavghrs2path);
  }

  public int getAvgavghrs3path() {
    return ((int) avgavghrs3path);
  }

  public int getAvgmaxhrs1path() {
    return ((int) avgmaxhrs1path);
  }

  public int getAvgmaxhrs2path() {
    return ((int) avgmaxhrs2path);
  }

  public int getAvgmaxhrs3path() {
    return ((int) avgmaxhrs3path);
  }

  public double getAvgrating1path() {
    return avgrating1path;
  }

  public double getAvgrating2path() {
    return avgrating2path;
  }

  public double getAvgrating3path() {
    return avgrating3path;
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
   * Returns a Set of nodes representing the courseSet for the current or last used concentration.
   *
   * @return a Node Set.
   */
  public Set<Node> getCourseSet() {
    return courseSet;
  }

  /**
   * A setter method that sets the concentration in this class to the user-specified value.
   *
   * @param concentration the specified concentration as its table name as a String.
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
  public void setConcentrationName(String concentrationName) throws SQLException {
    if (this.concentrationMap.containsKey(concentrationName)) {
      this.concentrationName = concentrationName;
      this.setConcentration(this.getConcentrationMap().get(concentrationName));
    }
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
    this.setConcentrationName(con);
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

  public static int parseGradeLevel(String gradeL, String semesterL) {
    int semester;
    if (gradeL.equals("Freshman")) {
      semester = 1;
    } else if (gradeL.equals("Sophomore")) {
      semester = 3;
    } else if (gradeL.equals("Junior")) {
      semester = 5;
    } else {
      semester = MAGICNUM;
    }
    if (semesterL.equals("Spring")) {
      semester += 1;
    }
    return semester;
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
    avgavghrs1path = 0.0;
    avgmaxhrs1path = 0.0;
    avgrating1path = 0.0;
    for (Semester sem : getPath1()) {
      sem.setStats();
      totalnumcourses1 += sem.getCourses().size();
      avgavghrs1path += sem.getAvghrs();
      avgmaxhrs1path += sem.getMaxhrs();
      avgrating1path += sem.getRating();
    }
    avgavghrs1path = Math.round(avgavghrs1path / totalnumcourses1);
    avgmaxhrs1path = Math.round(avgmaxhrs1path / totalnumcourses1);
    avgrating1path = avgrating1path / totalnumcourses1;

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
    avgavghrs2path = 0.0;
    avgmaxhrs2path = 0.0;
    avgrating2path = 0.0;
    for (Semester sem : getPath2()) {
      sem.setStats();
      totalnumcourses2 += sem.getCourses().size();
      avgavghrs2path += sem.getAvghrs();
      avgmaxhrs2path += sem.getMaxhrs();
      avgrating2path += sem.getRating();
    }
    avgavghrs2path = Math.round(avgavghrs2path / totalnumcourses2);
    avgmaxhrs2path = Math.round(avgmaxhrs2path / totalnumcourses2);
    avgrating2path = avgrating2path / totalnumcourses2;
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
    avgavghrs3path = 0.0;
    avgmaxhrs3path = 0.0;
    avgrating3path = 0.0;
    for (Semester sem : getPath3()) {
      sem.setStats();
      totalnumcourses3 += sem.getCourses().size();
      avgavghrs3path += sem.getAvghrs();
      avgmaxhrs3path += sem.getMaxhrs();
      avgrating3path += sem.getRating();
    }
    avgavghrs3path = Math.round(avgavghrs3path / totalnumcourses3);
    avgmaxhrs3path = Math.round(avgmaxhrs3path / totalnumcourses3);
    avgrating3path = avgrating3path / totalnumcourses3;
  }

  public void setPathUniques() {
    Set<Node> path1Courses = new HashSet<>();
    for (Semester s : path1) {
      path1Courses.addAll(s.getCourses());
    }
    Set<Node> path2Courses = new HashSet<>();
    for (Semester s : path2) {
      path2Courses.addAll(s.getCourses());
    }
    Set<Node> path3Courses = new HashSet<>();
    for (Semester s : path3) {
      path3Courses.addAll(s.getCourses());
    }
    Set<Node> path1UniqueNodes =
        Sets.difference(path1Courses, Sets.union(path2Courses, path3Courses));
    Set<Node> path2UniqueNodes =
        Sets.difference(path2Courses, Sets.union(path1Courses, path3Courses));
    Set<Node> path3UniqueNodes =
        Sets.difference(path3Courses, Sets.union(path1Courses, path2Courses));
    path1Uniques = new ArrayList<>();
    path2Uniques = new ArrayList<>();
    path3Uniques = new ArrayList<>();
    for (Node n : path1UniqueNodes) {
      path1Uniques.add(n.getId());
    }
    for (Node n : path2UniqueNodes) {
      path2Uniques.add(n.getId());
    }
    for (Node n : path3UniqueNodes) {
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
