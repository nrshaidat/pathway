package edu.brown.cs.student.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;
import edu.brown.cs.student.pathway.Node;

/**
 * Database class that handles sql queries to the sql database.
 * @author nrshaida (Natalie Rshaidat)
 */
public class Database implements DatabaseInterface {
  private static final int COURSE = 13; //courses table number of columns
  private static final int SSLINK = 12;
  private static final int COURSECODEIDX = 0;
  private static final int COURSENAMEIDX = 1;
  private static final int PREREQIDX = 2;
  private static final int SEMESTERIDX = 3;
  private static final int PROFESSORIDX = 4;
  private static final int COURSERATINGIDX = 5;
  private static final int AVGHRSIDX = 6;
  private static final int MAXHRSIDX = 7;
  private static final int CLASSSIZEIDX = 9;
  private static final double DEFAULTRATING = 3.5;
  private static final double DEFAULTAVGHRS = 8;
  private static final double DEFAULTMAXHRS = 14;
  private static final int DEFAULTCLASSSIZE = 40;
  private static Connection conn;

  /**
   * Database constructor.
   *
   * @param filename for the sql database to connect to
   * @author nrshaida (Natalie Rshaidat)
   */
  public Database(String filename) {
    if (!this.connectDB(filename)) {
      conn = null;
    }
  }

  /**
   * hasConnection checks if the database could connect.
   *
   * @return a boolean if the database was able to connect
   * @author nrshaida (Natalie Rshaidat)
   */
  @Override
  public boolean hasConnection() {
    return conn != null;
  }

  /**
   * checkConcentration checks if the concentration and its rules are in the database and that the
   * number of categories lines up with both tables.
   *
   * @param concentrationName the name of the concentration in lower case and without spaces
   *                          with ba/bs on the end
   * @return a boolean if the database has the accurate concentration data from the db
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Override
  public boolean checkConcentration(String concentrationName) throws SQLException {
    //checks that the tables exist and they have the correct column names and types
    String concentrationRules = concentrationName + "_rules";
    if (!this.checkTableExists(concentrationName)) {
      return false;
    } else { //table exists now check if columns are accurate
      if (!this.checkConcentrationColNames(concentrationName)) {
        return false;
      }
    }
    if (!this.checkTableExists(concentrationRules)) {
      return false;
    } else { //table exists not check if columns are accurate
      if (!this.checkConcentrationRulesColNames(concentrationRules)) {
        return false;
      }
    }
    // check that the number of categories and number of credits from each category lines up with
    // the concentration table's categories and the number of options availible for each category
    PreparedStatement prep = null;
    ResultSet rs = null;
    try {
      //query the concentrationName table to populate its array of
      String strQuery = "SELECT category, COUNT(*) AS 'available_courses' " + " FROM $tableName "
          + " GROUP BY category " + " ORDER BY category ASC ";
      String query = strQuery.replace("$tableName", concentrationName);
      prep = conn.prepareStatement(query);
      rs = prep.executeQuery();
      List<Integer> reqsAvailable = new ArrayList<>();
      while (rs.next()) {
        int categoryNum = Integer.parseInt(rs.getString("category"));
        int numAvailable = Integer.parseInt(rs.getString("available_courses"));
        if (categoryNum < 0) {
          return false;
        } else {
          reqsAvailable.add(categoryNum, numAvailable);
        }
      }
      List<Integer> reqs = this.getRequirements(concentrationRules);
      int numReqs = reqs.size();
      int numAvail = reqsAvailable.size();
      if (numReqs != numAvail) {
        return false;
      } else {
        for (int category = 0; category < numReqs; category++) {
          int numberCoursesAvailable = reqsAvailable.get(category);
          int numberCoursesNeeded = reqs.get(category);
          if (numberCoursesAvailable < numberCoursesNeeded) {
            return false;
          }
        }
      }
    } catch (SQLException e) {
      return false;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (prep != null) {
        prep.close();
      }
    }
    return true;
  }

  /**
   * connectDB connect to the database.
   *
   * @param filename to connect to
   * @return boolean representing if it could connect to the database file
   * @author nrshaida (Natalie Rshaidat)
   */
  private boolean connectDB(String filename) {
    try {
      File tempFile = new File(filename);
      if (tempFile.exists()) {
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:" + filename;
        conn = DriverManager.getConnection(urlToDB);
        Statement stat = conn.createStatement();
        stat.executeUpdate("PRAGMA foreign_keys=ON;");
        return true;
      } else {
        return false;
      }
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("ERROR: Could not connect to the database!");
      return false;
    }
  }

  /**
   * checkDBFormat checks that database's format.
   *
   * @return a boolean that represents if the database is of the correct format
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Override
  public boolean checkCoursesTable() throws SQLException {
    return checkTableExists("courses") && checkCoursesColNames();
  }

  /**
   * checkTableNames checks for the table names.
   *
   * @param tableName name of the table to check
   * @return a boolean representing if the table names are of the correct format
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  public boolean checkTableExists(String tableName) throws SQLException {
    ResultSet rs = null;
    try {
      DatabaseMetaData dbmd = conn.getMetaData();
      String[] types = {"TABLE"};
      rs = dbmd.getTables(null, null, "%", types);
      List<String> tableNames = new ArrayList<>();
      while (rs.next()) {
        tableNames.add(rs.getString("TABLE_NAME"));
      }
      if (tableNames.isEmpty()) {
        return false;
      }
      if (!tableNames.contains(tableName)) {
        return false;
      }
    } catch (SQLException e) {
      return false;
    } finally {
      if (rs != null) {
        rs.close();
      }
    }
    return true;
  }

  /**
   * checkCoursesColNames checks that the columns names and types for the courses table are
   * accuracte.
   *
   * @param concentrationName concentration name in lowercase and no spaces
   * @return a boolean representing if the way table column names are of the correct format
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  public boolean checkConcentrationColNames(String concentrationName) throws SQLException {
    ResultSet rs = null;
    try {
      DatabaseMetaData metadata = conn.getMetaData();
      rs = metadata.getColumns(null, null, concentrationName, null);
      List<String> names = new ArrayList<>();
      while (rs.next()) {
        String name = rs.getString("COLUMN_NAME");
        String type = rs.getString("TYPE_NAME");
        names.add(name);
        if (!(type.equals("TEXT"))) {
          return false;
        }
      }
      if (names.size() != 3) {
        return false;
      }
      if (!names.get(0).equals("course_id")) {
        return false;
      }
      if (!names.get(1).equals("next")) {
        return false;
      }
      if (!names.get(2).equals("category")) {
        return false;
      }
    } catch (SQLException e) {
      return false;
    } finally {
      if (rs != null) {
        rs.close();
      }
    }
    return true;
  }

  /**
   * checkCoursesColNames checks that the columns names and types for the courses table are
   * accuracte.
   *
   * @param concentrationNameRules concentration name for rules table
   * @return a boolean representing if the way table column names are of the correct format
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  public boolean checkConcentrationRulesColNames(String concentrationNameRules)
      throws SQLException {
    ResultSet rs = null;
    try {
      DatabaseMetaData metadata = conn.getMetaData();
      rs = metadata.getColumns(null, null, concentrationNameRules, null);
      List<String> names = new ArrayList<>();
      while (rs.next()) {
        String name = rs.getString("COLUMN_NAME");
        String type = rs.getString("TYPE_NAME");
        names.add(name);
        if (!(type.equals("TEXT"))) {
          return false;
        }
      }
      if (names.size() != 2) {
        return false;
      }
      if (!names.get(0).equals("category")) {
        return false;
      }
      if (!names.get(1).equals("num_credits")) {
        return false;
      }
    } catch (SQLException e) {
      return false;
    } finally {
      if (rs != null) {
        rs.close();
      }
    }
    return true;
  }

  /**
   * checkCoursesColNames checks that the columns names and types for the courses table are
   * accuracte.
   *
   * @return a boolean representing if the way table column names are of the correct format
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  public boolean checkCoursesColNames() throws SQLException {
    ResultSet rs = null;
    try {
      DatabaseMetaData metadata = conn.getMetaData();
      rs = metadata.getColumns(null, null, "courses", null);
      List<String> names = new ArrayList<>();
      while (rs.next()) {
        String name = rs.getString("COLUMN_NAME");
        String type = rs.getString("TYPE_NAME");
        names.add(name);
        if (!(type.equals("TEXT"))) {
          return false;
        }
      }
      if (names.size() != COURSE) {
        return false;
      }
      if (!names.get(COURSECODEIDX).equals("course_id")) {
        return false;
      }
      if (!names.get(COURSENAMEIDX).equals("course_name")) {
        return false;
      }
      if (!names.get(PREREQIDX).equals("prereqs")) {
        return false;
      }
      if (!names.get(SEMESTERIDX).equals("semester_offered")) {
        return false;
      }
      if (!names.get(PROFESSORIDX).equals("professor")) {
        return false;
      }
      if (!names.get(COURSERATINGIDX).equals("courseRating")) {
        return false;
      }
      if (!names.get(AVGHRSIDX).equals("avg_hrs")) {
        return false;
      }
      if (!names.get(MAXHRSIDX).equals("max_hrs")) {
        return false;
      }
      if (!names.get(CLASSSIZEIDX).equals("class_size")) {
        return false;
      }
      if (!names.get(SSLINK).equals("ss_link")) {
        return false;
      }
    } catch (SQLException e) {
      return false;
    } finally {
      if (rs != null) {
        rs.close();
      }
    }
    return true;
  }

  /**
   * isEmpty checks if the database has data and returns a boolean, returning true if it has data
   * and false if it does not have data in its tables.
   *
   * @return boolean representing if table is empty of not
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Override
  public boolean isEmptyCourses() throws SQLException {
    PreparedStatement prep = null;
    ResultSet rs = null;
    int num;
    try {
      prep = conn.prepareStatement("SELECT COUNT(*) AS 'num' " + "FROM courses ");
      rs = prep.executeQuery();
      num = rs.getInt("num");
    } catch (SQLException e) {
      return true;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (prep != null) {
        prep.close();
      }
    }
    return num == 0;
  }

  /**
   * getCourseData gets a reference to a Node object with all of its field filled except next and
   * category since that is concentration specific.
   *
   * @param courseID course id such as CSCI 0320
   * @return Node object with everything filled in except category and next
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Override
  public Node getCourseData(String courseID) throws SQLException {
    PreparedStatement prep = null;
    ResultSet rs = null;
    Node newCourse = new Node(courseID);
    try {
      prep = conn.prepareStatement(" SELECT * " + " FROM courses " + " WHERE course_id = ? ");
      prep.setString(1, courseID);
      rs = prep.executeQuery();
      String name = rs.getString("course_name");
      newCourse.setName(name);
      String prereq = rs.getString("prereqs");
      if (!prereq.equals("")) { //has prereqs so parse it and set it
        List<Set<Node>> prereqList = this.parsePrereqs(prereq);
        newCourse.setPrereqs(prereqList);
      }
      String sem = rs.getString("semester_offered");
      newCourse.setSemesters(this.parseSemesterOffered(sem));
      newCourse.setProfessor(rs.getString("professor"));
      String courseRating = rs.getString("courseRating");
      if (!courseRating.equals("")) { //has a courseRating
        newCourse.setRating(Double.parseDouble(courseRating));
      } else { //set default
        newCourse.setRating(DEFAULTRATING);
      }
      String avgHrs = rs.getString("avg_hrs");
      if (!avgHrs.equals("")) { // has avghrs value
        newCourse.setAvgHrs(Double.parseDouble(avgHrs));
      } else { //set default
        newCourse.setAvgHrs(DEFAULTAVGHRS);
      }
      String maxHrs = rs.getString("max_hrs");
      if (!maxHrs.equals("")) { // has maxhrs value
        newCourse.setMaxHrs(Double.parseDouble(maxHrs));
      } else { //set default
        newCourse.setMaxHrs(DEFAULTMAXHRS);
      }
      String classSize = rs.getString("class_size");
      if (!classSize.equals("")) { // has class size value set
        newCourse.setClassSize(Integer.parseInt(classSize));
      } else { //set default
        newCourse.setClassSize(DEFAULTCLASSSIZE);
      }
      String cabLink = rs.getString("ss_link");
      newCourse.setSsurl(cabLink);
    } catch (SQLException e) {
      return null;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (prep != null) {
        prep.close();
      }
    }
    return newCourse;
  }

  /**
   * getConcentrationCourses gets the courses for the concentration in the sql database. It calls on
   * the getCourseData for each course id in the concentration.
   *
   * @param tableName the concentrationName table name to search for
   * @return a set of courses all populated with category and next populated
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Override
  public Set<Node> getConcentrationCourses(String tableName) throws SQLException {
    PreparedStatement prep = null;
    ResultSet rs = null;
    Set<Node> courseSet = new HashSet<>();
    try {
      String strQuery = " SELECT * " + " FROM $tableName " + " ORDER BY category ASC ";
      String query = strQuery.replace("$tableName", tableName);
      prep = conn.prepareStatement(query);
      rs = prep.executeQuery();
      while (rs.next()) {
        int category = Integer.parseInt(rs.getString("category"));
        String nextID = rs.getString("next");
        String courseID = rs.getString("course_id");
        Node tmp = new Node(courseID, nextID, category);
        courseSet.add(tmp);
      }
    } catch (SQLException e) {
      return null;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (prep != null) {
        prep.close();
      }
    }
    return courseSet;
  }

  /**
   * parsePrereqs parses the comma separated (AND) prereqs and the equal sign (OR) seperated
   * prereqs.
   *
   * @param prereqs the string of courseID's
   * @return a list of prereqs course objects
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  public List<Set<Node>> parsePrereqs(String prereqs) throws SQLException {
    String[] parsedLine = prereqs.split(","); //split on , (AND)
    List<Set<Node>> courseList = new ArrayList<>();
    for (String courseID : parsedLine) { //loop thru each required prereqs (AND)
      String[] parsedOrs = courseID.split("="); //split each requirement on = (OR)
      Set<Node> courseSet = new HashSet<>();
      if (parsedOrs.length == 1) { //no ors in prereqs
        Node tmp = this.getCourseData(parsedOrs[0]);
        if (tmp != null) {
          courseSet.add(tmp);
        }
      } else if (parsedOrs.length > 1) { //has or courses
        for (String courseIDOr : parsedOrs) {
          Node equivCourse = this.getCourseData(courseIDOr);
          if (equivCourse != null) {
            courseSet.add(equivCourse);
          }
        }
      }
      if (!courseSet.isEmpty()) {
        courseList.add(courseSet);
      }
    }
    return courseList;
  }

  /**
   * parseSemesterOffered parses the int into a set of 0 and/or 1.
   *
   * @param sem the string of semester_offered 2 is both semesters, 1 is fall, and 0 is spring
   * @return set of the semesters offered
   * @author nrshaida (Natalie Rshaidat)
   */
  public Set<Integer> parseSemesterOffered(String sem) {
    if (sem == null || sem.length() < 1) {
      return null;
    } else {
      int semOff = Integer.parseInt(sem);
      Set<Integer> semset = new HashSet<>();
      if (semOff == 2) {
        semset.add(0);
        semset.add(1);
      } else if (semOff == 1) {
        semset.add(1);
      } else if (semOff == 0) {
        semset.add(0);
      }
      return semset;
    }
  }

  /**
   * getRequirements gets the requirements for the concentration.
   *
   * @param tableName the concentrationNameReqs table name to search for
   * @return an int array where the index is the category and the value at that index is the number
   * of courses needed to fulfill the requirement
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Override
  public List<Integer> getRequirements(String tableName) throws SQLException {
    PreparedStatement prep = null;
    ResultSet rs = null;
    List<Integer> reqs = new ArrayList<>();
    try {
      String strQuery =
          " SELECT * " + " FROM $tableName " + " ORDER BY cast(category as unsigned) ASC ";
      String query = strQuery.replace("$tableName", tableName);
      prep = conn.prepareStatement(query);
      rs = prep.executeQuery();
      while (rs.next()) {
        int category = Integer.parseInt(rs.getString("category"));
        int numCredits = Integer.parseInt(rs.getString("num_credits"));
        if (category < 0) {
          return null;
        } else {
          reqs.add(category, numCredits);
        }
      }
    } catch (SQLException e) {
      return null;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (prep != null) {
        prep.close();
      }
    }
    return reqs;
  }

  /**
   * getConcentrationsMap gets the concentrations gui names mapped to their sql table names in the
   * sql database for use in the GUI.
   *
   * @return a list of concentration names
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Override
  public Map<String, String> getConcentrationsMap() throws SQLException {

    PreparedStatement prep = null;
    ResultSet rs = null;
    Map<String, String> concentrationMap = new HashMap<>();
    try {
      prep = conn.prepareStatement("SELECT * FROM concentrations ORDER BY concentration_name ASC ");
      rs = prep.executeQuery();
      while (rs.next()) {
        concentrationMap.put(rs.getString("concentration_name"), rs.getString("concentration_id"));
      }
      rs.close();
      prep.close();
      return concentrationMap;
    } catch (SQLException e) {
      return null;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (prep != null) {
        prep.close();
      }
    }

  }


  /**
   * hasLoop checks that all the courses in the courses db don't have a loop between prereqs
   * pointing to each other.
   *
   * @return a list of concentration names
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  public boolean hasLoop() throws SQLException {
    PreparedStatement prep = null;
    ResultSet rs = null;
    try {
      prep = conn.prepareStatement("SELECT course_id " + " FROM courses");
      rs = prep.executeQuery();
      while (rs.next()) {
        this.getCourseData(rs.getString("course_id"));
      }
    } catch (SQLException e) {
      return false;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (prep != null) {
        prep.close();
      }
    }
    return true;
  }

  /**
   * getAllCourseIDs gets all the course's id's for the cache database to populate the cache with.
   *
   * @return a list of concentration names
   * @throws SQLException the sql exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Override
  public List<String> getAllCourseIDs() throws SQLException {
    PreparedStatement prep = null;
    ResultSet rs = null;
    List<String> courseIDs = new ArrayList<>();
    try {
      prep = conn.prepareStatement("SELECT course_id " + " FROM courses ORDER BY course_id asc");
      rs = prep.executeQuery();
      while (rs.next()) {
        courseIDs.add(rs.getString("course_id"));
      }
      rs.close();
      prep.close();
      return courseIDs;
    } catch (SQLException e) {
      return courseIDs;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (prep != null) {
        prep.close();
      }
    }
  }
}
