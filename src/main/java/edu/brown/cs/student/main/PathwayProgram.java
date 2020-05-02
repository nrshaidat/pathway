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
   * sets the default values for user signing, sets information about each pathway like the
   * average weekly hours of a course in the pathway, number of courses, and number of semesters
   * in the pathway.
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

  /**
   * Gets the year list to display in GUI.
   *
   * @return the year list aka Spring, Fall
   */
  public static List<String> getYearList() {
    return yearList;
  }

  /**
   * Gets grade list for the GUI to display.
   *
   * @return the grade list aka Freshmen, Junior, etc
   */
  public static List<String> getGradeList() {
    return gradeList;
  }

  /**
   * Sets path 1.
   *
   * @param path1 the path 1
   */
  public void setPath1(List<Semester> path1) {
    this.path1 = path1;
    this.setPathStats1();
  }

  /**
   * Sets path 2.
   *
   * @param path2 the path 2
   */
  public void setPath2(List<Semester> path2) {
    this.path2 = path2;
    this.setPathStats2();
  }

  /**
   * Sets path 3.
   *
   * @param path3 the path 3
   */
  public void setPath3(List<Semester> path3) {
    this.path3 = path3;
    this.setPathStats3();
  }

  /**
   * Parse taken set.
   *
   * @param coursestaken the coursestaken
   * @return the set
   */
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

  /**
   * Gets avgavghrs 1 path.
   *
   * @return the avgavghrs 1 path
   */
// Getter and setter methods for Apache Spark
  public int getAvgavghrs1path() {
    return ((int) avgavghrs1path);
  }

  /**
   * Gets avgavghrs 2 path.
   *
   * @return the avgavghrs 2 path
   */
  public int getAvgavghrs2path() {
    return ((int) avgavghrs2path);
  }

  /**
   * Gets avgavghrs 3 path.
   *
   * @return the avgavghrs 3 path
   */
  public int getAvgavghrs3path() {
    return ((int) avgavghrs3path);
  }

  /**
   * Gets totalnumcourses 1.
   *
   * @return the totalnumcourses 1
   */
  public int getTotalnumcourses1() {
    return totalnumcourses1;
  }

  /**
   * Gets totalnumcourses 2.
   *
   * @return the totalnumcourses 2
   */
  public int getTotalnumcourses2() {
    return totalnumcourses2;
  }

  /**
   * Gets totalnumcourses 3.
   *
   * @return the totalnumcourses 3
   */
  public int getTotalnumcourses3() {
    return totalnumcourses3;
  }

  /**
   * Gets numsemesters 1.
   *
   * @return the numsemesters 1
   */
  public int getNumsemesters1() {
    numsemesters1 = this.path1.size();
    return numsemesters1;
  }

  /**
   * Gets numsemesters 2.
   *
   * @return the numsemesters 2
   */
  public int getNumsemesters2() {
    numsemesters2 = this.path2.size();
    return numsemesters2;
  }

  /**
   * Gets numsemesters 3.
   *
   * @return the numsemesters 3
   */
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
   * Setter method to set the concentration name.
   *
   * @param concentrationName A String representing the user-chosen concentration.
   * @throws SQLException the sql exception
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
    this.setPath1(path1);

    Pathway pathway2 = new Pathway(reqs2, courseSet);
    pathway2.makePathway(taken, sem, aggressive, "med");
    path2 = pathway2.getPath();
    this.setPath2(path2);

    Pathway pathway3 = new Pathway(reqs3, courseSet);
    pathway3.makePathway(taken, sem, aggressive, "hi");
    path3 = pathway3.getPath();
    this.setPath3(path3);

    setPathUniques();
  }

  /**
   * Parse grade level int.
   *
   * @param gradeL    the grade level from the gui aka Freshmen, Junior, etc
   * @param semesterL the semester level from the gui aka Fall or Spring
   * @return the int
   */
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
   * A method to return if paths have neen made.
   *
   * @return a boolean checking if pathways have been made.
   */
  public boolean isSet() {
    return this.getPath1() != null;
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
   * This method sets the statistics of Pathway1, the average workload per course, the number of
   * courses, and the number of semesters in the pathway.
   * It calcualtes the average weekly hours of each class in the pathway. After
   * generating this information, these parameters can be used in the front-end to
   * display a semester summary.
   */
  public void setPathStats1() {
    totalnumcourses1 = 0;
    avgavghrs1path = 0.0;
    for (Semester sem : getPath1()) {
      sem.setStats();
      totalnumcourses1 += sem.getCourses().size();
      avgavghrs1path += sem.getAvghrs();
    }
    avgavghrs1path = Math.round(avgavghrs1path / totalnumcourses1);
  }

  /**
   * This method sets the statistics of Pathway2, the average workload per course, the number of
   * courses, and the number of semesters in the pathway.
   * It calcualtes the average weekly hours of each class in the pathway. After
   * generating this information, these parameters can be used in the front-end to
   * display a semester summary.
   */
  public void setPathStats2() {
    totalnumcourses2 = 0;
    avgavghrs2path = 0.0;
    for (Semester sem : getPath2()) {
      sem.setStats();
      totalnumcourses2 += sem.getCourses().size();
      avgavghrs2path += sem.getAvghrs();
    }
    avgavghrs2path = Math.round(avgavghrs2path / totalnumcourses2);
  }

  /**
   * This method sets the statistics of Pathway3, the average workload per course, the number of
   * courses, and the number of semesters in the pathway.
   * It calcualtes the average weekly hours of each class in the pathway. After
   * generating this information, these parameters can be used in the front-end to
   * display a semester summary.
   */
  public void setPathStats3() {
    totalnumcourses3 = 0;
    avgavghrs3path = 0.0;
    for (Semester sem : getPath3()) {
      sem.setStats();
      totalnumcourses3 += sem.getCourses().size();
      avgavghrs3path += sem.getAvghrs();
    }
    avgavghrs3path = Math.round(avgavghrs3path / totalnumcourses3);
  }

  /**
   * Sets path unique courses.
   */
  public void setPathUniques() {
    if (this.isSet()) {
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
    } else {
      path1Uniques = new ArrayList<>();
      path2Uniques = new ArrayList<>();
      path3Uniques = new ArrayList<>();
    }
  }

  /**
   * Gets pathway path 1 unique courses.
   *
   * @return the path 1 unique courses as a list of course IDs
   */
  public List<String> getPath1Uniques() {
    if (path1Uniques != null) {
      return path1Uniques.size() > 3 ? path1Uniques.subList(0, 3) : path1Uniques;
    } else {
      return null;
    }
  }

  /**
   * Gets pathway path 2 unique courses.
   *
   * @return the path 2 unique courses as a list of course IDs
   */
  public List<String> getPath2Uniques() {
    if (path2Uniques != null) {
      return path2Uniques.size() > 3 ? path2Uniques.subList(0, 3) : path2Uniques;
    } else {
      return null;
    }
  }

  /**
   * Gets path 3 unique courses.
   *
   * @return the path 3 unique courses as a list of course IDs
   */
  public List<String> getPath3Uniques() {
    if (path3Uniques != null) {
      return path3Uniques.size() > 3 ? path3Uniques.subList(0, 3) : path3Uniques;
    } else {
      return null;
    }
  }
}
