package edu.brown.cs.student.main;

import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import edu.brown.cs.student.pathway.Semester;
import com.google.common.collect.Sets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * PathwayProgram handles integrating the Pathway class, the main class aka user input, and the
 * database cache.
 * @author nrshaida (Natalie Rshaidat)
 */
public class PathwayProgram {
  private static final int MAGICNUM = 7;
  private DatabaseInterface cache;
  private Map<String, String> concentrationMap; //map of concentration GUI names to table names
  private List<String> concentrationsList;
  private List<Semester> path1;
  private List<Semester> path2;
  private List<Semester> path3;
  private List<Node> path1Uniques;
  private List<Node> path2Uniques;
  private List<Node> path3Uniques;
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
  private static List<String> yearList = new ArrayList<>() {{
      add("Fall");
      add("Spring");
    }};
  private static List<String> gradeList = new ArrayList<>() {{
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
   * @param univ string representing the first word in the unversity name ie brown or cornell
   * @throws SQLException for problems with querying db.
   * @author nrshaida (Natalie Rshaidat)
   */
  public PathwayProgram(String univ) throws SQLException {
    String file;
    univ = univ.toLowerCase();
    if (univ.equals("cornell")) {
      file = "data/cornellcoursesDB.db";
    } else {
      file = "data/coursesDB.db";
    }
    Database realDB = new Database(file); // real database that handles sql queries
    cache = new DatabaseCache(realDB);
    concentrationMap = cache.getConcentrationsMap();
    concentrationsList = new ArrayList<>(this.concentrationMap.keySet());
    courseList = cache.getAllCourseIDs();
    if (univ.equals("cornell")) {
      this.setConcentrationName("Economics B.A.");
    } else {
      this.setConcentrationName("Computer Science B.A.");
    }
  }

  /**
   * Gets course data.
   *
   * @param id the id
   * @return the course data
   * @throws SQLException the sql exception
   * @author iilozor (Ifechi Ilozor)
   */
  public Node getCourseData(String id) throws SQLException {
    return cache.getCourseData(id);
  }

  /**
   * Gets the year list to display in GUI.
   *
   * @return the year list aka Spring, Fall
   * @author nrshaida (Natalie Rshaidat)
   */
  public static List<String> getYearList() {
    return yearList;
  }

  /**
   * Gets grade list for the GUI to display.
   *
   * @return the grade list aka Freshmen, Junior, etc
   * @author nrshaida (Natalie Rshaidat)
   */
  public static List<String> getGradeList() {
    return gradeList;
  }

  /**
   * Sets path 1.
   *
   * @param path1 the path 1
   * @author nrshaida (Natalie Rshaidat)
   */
  public void setPath1(List<Semester> path1) {
    this.path1 = path1;
    this.setPathStats1();
  }

  /**
   * Sets path 2.
   *
   * @param path2 the path 2
   * @author nrshaida (Natalie Rshaidat)
   */
  public void setPath2(List<Semester> path2) {
    this.path2 = path2;
    this.setPathStats2();
  }

  /**
   * Sets path 3.
   *
   * @param path3 the path 3
   * @author nrshaida (Natalie Rshaidat)
   */
  public void setPath3(List<Semester> path3) {
    this.path3 = path3;
    this.setPathStats3();
  }

  /**
   * Parse taken set.
   *
   * @param coursestaken the coursestaken
   * @param con          concentration in GUI form
   * @return the set
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat) and mcui5 (Melissa Cui)
   */
  public Set<Node> parseTaken(String coursestaken, String con) throws SQLException {
    this.setConcentrationName(con);
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
   * @throws SQLException sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  public List<String> getCourseList() throws SQLException {
    courseList = cache.getAllCourseIDs();
    return courseList;
  }

  /**
   * Gets avgavghrs for path 1.
   *
   * @return the avgavghrs for path 1
   * @author nrshaida (Natalie Rshaidat)
   */
// Getter and setter methods for Apache Spark
  public int getAvgavghrs1path() {
    return (int) avgavghrs1path;
  }

  /**
   * Gets avgavghrs for path 2.
   *
   * @return the avgavghrs for path 2
   * @author nrshaida (Natalie Rshaidat)
   */
  public int getAvgavghrs2path() {
    return (int) avgavghrs2path;
  }

  /**
   * Gets avgavghrs for path 3.
   *
   * @return the avgavghrs for path 3
   * @author nrshaida (Natalie Rshaidat)
   */
  public int getAvgavghrs3path() {
    return (int) avgavghrs3path;
  }

  /**
   * Gets totalnumcourses for path 1.
   *
   * @return the totalnumcourses for path1
   * @author nrshaida (Natalie Rshaidat)
   */
  public int getTotalnumcourses1() {
    return totalnumcourses1;
  }

  /**
   * Gets totalnumcourses for path 2.
   *
   * @return the totalnumcourses for path 2
   * @author nrshaida (Natalie Rshaidat)
   */
  public int getTotalnumcourses2() {
    return totalnumcourses2;
  }

  /**
   * Gets totalnumcourses for path 3.
   *
   * @return the totalnumcourses for path 3
   * @author nrshaida (Natalie Rshaidat)
   */
  public int getTotalnumcourses3() {
    return totalnumcourses3;
  }

  /**
   * Gets numsemesters for path 1.
   *
   * @return the numsemesters for path 1
   * @author nrshaida (Natalie Rshaidat)
   */
  public int getNumsemesters1() {
    if (path1 != null) {
      numsemesters1 = this.path1.size();
      return numsemesters1;
    } else {
      return 0;
    }
  }

  /**
   * Gets numsemesters for path 2.
   *
   * @return the numsemesters for path 2
   * @author nrshaida (Natalie Rshaidat)
   */
  public int getNumsemesters2() {
    if (path2 != null) {
      numsemesters2 = this.path2.size();
      return numsemesters2;
    } else {
      return 0;
    }
  }

  /**
   * Gets numsemesters for path 3.
   *
   * @return the numsemesters for path 3
   * @author nrshaida (Natalie Rshaidat)
   */
  public int getNumsemesters3() {
    if (path3 != null) {
      numsemesters3 = this.path3.size();
      return numsemesters3;
    } else {
      return 0;
    }
  }

  /**
   * Getter method that gets the first pathway, Pathway 1 aka the low workload pathway.
   *
   * @return a List of Semesters for the first pathway.
   * @author nrshaida (Natalie Rshaidat)
   */
  public List<Semester> getPath1() {
    return path1;
  }

  /**
   * Getter method that gets the second pathway, Pathway 2 aka the medium workload pathway.
   *
   * @return a List of Semesters for the second pathway.
   * @author nrshaida (Natalie Rshaidat)
   */
  public List<Semester> getPath2() {
    return path2;
  }

  /**
   * Getter method that gets the third pathway, Pathway 3 aka the high workload pathway.
   *
   * @return a List of Semesters for the third pathway.
   * @author nrshaida (Natalie Rshaidat)
   */
  public List<Semester> getPath3() {
    return path3;
  }

  /**
   * Returns a Map of the concentrations where the key is GUI name for the concentration and the
   * value is the sql table name for that concentration.
   *
   * @return a Map of key and value String pairs.
   * @throws SQLException sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  public Map<String, String> getConcentrationMap() throws SQLException {
    concentrationMap = cache.getConcentrationsMap();
    return concentrationMap;
  }

  /**
   * Returns a Set of nodes representing the courseSet for the current or last used concentration.
   *
   * @return a Node Set.
   * @author nrshaida (Natalie Rshaidat)
   */
  public Set<Node> getCourseSet() {
    return courseSet;
  }

  /**
   * A setter method that sets the concentration and its course set in this class to the
   * user-specified value.
   *
   * @param concentration the specified concentration as its table name as a String.
   * @throws SQLException if there are problems accessing the database.
   * @author nrshaida (Natalie Rshaidat)
   */
  public void setConcentration(String concentration) throws SQLException {
    this.concentration = concentration;
    this.courseSet = cache.getConcentrationCourses(concentration);
  }

  /**
   * Returns the concentration name aka GUI name that the user has specified.
   *
   * @return a String representing the concentration Name.
   * @author nrshaida (Natalie Rshaidat)
   */
  public String getConcentrationName() {
    return concentrationName;
  }

  /**
   * Setter method to set the concentration GUI name and the concentration table name.
   *
   * @param concentrationName A String representing the user-chosen concentration.
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
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
   * @author nrshaida (Natalie Rshaidat) and iilozor (Ifechi Ilozor) and nkeirste (Nick)
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
    List<Semester> p1 = pathway1.getPath();

    Pathway pathway2 = new Pathway(reqs2, courseSet);
    pathway2.makePathway(taken, sem, aggressive, "med");
    List<Semester> p2 = pathway2.getPath();

    Pathway pathway3 = new Pathway(reqs3, courseSet);
    pathway3.makePathway(taken, sem, aggressive, "hi");
    List<Semester> p3 = pathway3.getPath();

    double p1Hrs = getPathAvgHrs(p1);
    double p2Hrs = getPathAvgHrs(p2);
    double p3Hrs = getPathAvgHrs(p3);

    /* Check avg hours for paths 1, 2, 3 and reorder if needed so that path 1 actually has the
    lowest avg hours, path 2 has middle, path 3 has highest. */
    List<Map.Entry<List<Semester>, Double>> pathHours = new ArrayList<>();
    pathHours.add(Map.entry(p1, p1Hrs));
    pathHours.add(Map.entry(p2, p2Hrs));
    pathHours.add(Map.entry(p3, p3Hrs));
    Collections.sort(pathHours, new Comparator<Map.Entry<List<Semester>, Double>>() {
      @Override
      public int compare(Map.Entry<List<Semester>, Double> e1,
                         Map.Entry<List<Semester>, Double> e2) {
        return e1.getValue().compareTo(e2.getValue());
      }
    });
    /* Reset stats for each path, because had to calculate avgHrs to sort pathways */
    for (Map.Entry<List<Semester>, Double> e: pathHours) {
      for (Semester s : e.getKey()) {
        s.resetStats();
      }
    }
    this.setPath1(pathHours.get(0).getKey());
    this.setPath2(pathHours.get(1).getKey());
    this.setPath3(pathHours.get(2).getKey());

    this.setPathUniques();
  }

  /**
   * Parse grade level int.
   *
   * @param gradeL    the grade level from the gui aka Freshmen, Junior, etc
   * @param semesterL the semester level from the gui aka Fall or Spring
   * @return the int representing the semester level
   * @author mcui5 (Melissa Cui)
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
   * A method to return if paths have been made.
   *
   * @return a boolean checking if pathways have been made.
   * @author nrshaida (Natalie Rshaidat)
   */
  public boolean isSet() {
    return this.getPath1() != null;
  }

  /**
   * Returns the last concentration used for when the user returns back to the page showing all
   * three pathways from each pathway page.
   *
   * @return a String representing the concentration sql table name.
   * @author nrshaida (Natalie Rshaidat)
   */
  public String getConcentration() {
    return concentration;
  }

  /**
   * Returns the concentration list, a list of strings for use in the GUI.
   *
   * @return a List of Strings
   * @author nrshaida (Natalie Rshaidat)
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
   * @author nrshaida (Natalie Rshaidat)
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
   * @author nrshaida (Natalie Rshaidat)
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
   * @author nrshaida (Natalie Rshaidat)
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
   * A method that calculates the average number of hours in a pathway per semester. This is used
   * in makePathways to get the workload of each pathway and sort them, ensuring path1 is the
   * lowest hours, path 2 is the medium, path 3 is the highest.
   * @param path The pathway, a list of semesters
   * @return The average number of hours per semester for path
   * @author nkeirste (Nick Keirstead)
   */
  private double getPathAvgHrs(List<Semester> path) {
    double avgHrs = 0.0;
    int numCourses = 0;
    for (Semester sem : path) {
      sem.setStats();
      avgHrs += sem.getAvghrs();
      numCourses += sem.getCourses().size();
    }
    return Math.round(avgHrs / numCourses);
  }

  /**
   * Sets path unique courses.
   * @author nkeirste (Nick) and nrshaida (Natalie Rshaidat)
   * @throws SQLException sql exception
   */
  public void setPathUniques() throws SQLException {
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
        path1Uniques.add(cache.getCourseData(n.getId()));
      }
      for (Node n : path2UniqueNodes) {
        path2Uniques.add(cache.getCourseData(n.getId()));
      }
      for (Node n : path3UniqueNodes) {
        path3Uniques.add(cache.getCourseData(n.getId()));
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
   * @return the path 1 unique courses as a list of nodes
   * @author nrshaida (Natalie Rshaidat) and nkeirste (Nick)
   */
  public List<Node> getPath1Uniques() {
    if (path1Uniques != null) {
      return path1Uniques.size() > 3 ? path1Uniques.subList(0, 3) : path1Uniques;
    } else {
      return null;
    }
  }

  /**
   * Gets pathway path 2 unique courses.
   *
   * @return the path 2 unique courses as a list of nodes
   * @author nrshaida (Natalie Rshaidat) and nkeirste (Nick)
   */
  public List<Node> getPath2Uniques() {
    if (path2Uniques != null) {
      return path2Uniques.size() > 3 ? path2Uniques.subList(0, 3) : path2Uniques;
    } else {
      return null;
    }
  }

  /**
   * Gets path 3 unique courses.
   *
   * @return the path 3 unique courses as a list of nodes
   * @author nrshaida (Natalie Rshaidat) and nkeirste (Nick)
   */
  public List<Node> getPath3Uniques() {
    if (path3Uniques != null) {
      return path3Uniques.size() > 3 ? path3Uniques.subList(0, 3) : path3Uniques;
    } else {
      return null;
    }
  }
}
