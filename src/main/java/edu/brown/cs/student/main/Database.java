package edu.brown.cs.student.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import edu.brown.cs.student.pathway.Node;

/**
 * Database class that handles sql queries to the sql database.
 */
public class Database implements DatabaseInterface {
  public static final int COURSE = 13;
  private static Connection conn;

  /**
   * Database constructor.
   *
   * @param filename for the sql database to connect to
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
   *         with ba/bs on the end
   * @return a boolean if the database has the accurate concentration data from the db
   */
  @Override
  public boolean checkConcentration(String concentrationName) {
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
    PreparedStatement prep;
    try {
      //query the concentrationName table to populate its array of
      String strQuery = "SELECT category, COUNT(*) AS 'available_courses' " + " FROM $tableName "
          + " GROUP BY category " + " ORDER BY category ASC ";
      String query = strQuery.replace("$tableName", concentrationName);
      prep = conn.prepareStatement(query);
      ResultSet rs = prep.executeQuery();
      List<Integer> reqsAvailable = new ArrayList<Integer>();
      while (rs.next()) {
        Integer categoryNum = Integer.parseInt(rs.getString("category"));
        Integer numAvailable = Integer.parseInt(rs.getString("available_courses"));
        if (categoryNum < 0) {
          return false;
        } else {
          reqsAvailable.add(categoryNum, numAvailable);
        }
      }
      rs.close(); // close the reading of the db
      prep.close(); // close the query
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
    }
    return true;
  }

  /**
   * connectDB connect to the database.
   *
   * @param filename to connect to
   * @return boolean representing if it could connect to the database file
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
   */
  @Override
  public boolean checkCoursesTable() {
    return (checkTableExists("courses") && checkCoursesColNames());
  }

  /**
   * checkTableNames checks for the table names.
   *
   * @param tableName name of the table to check
   * @return a boolean representing if the table names are of the correct format
   */
  public boolean checkTableExists(String tableName) {
    try {
      DatabaseMetaData dbmd = conn.getMetaData();
      String[] types = {"TABLE"};
      ResultSet rs = dbmd.getTables(null, null, "%", types);
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
    }
    return true;
  }

  /**
   * checkCoursesColNames checks that the columns names and types for the courses table are
   * accuracte.
   *
   * @param concentrationName concentration name in lowercase and no spaces
   * @return a boolean representing if the way table column names are of the correct format
   */
  public boolean checkConcentrationColNames(String concentrationName) {
    try {
      DatabaseMetaData metadata = conn.getMetaData();
      ResultSet resultSet = metadata.getColumns(null, null, concentrationName, null);
      List<String> names = new ArrayList<>();
      while (resultSet.next()) {
        String name = resultSet.getString("COLUMN_NAME");
        String type = resultSet.getString("TYPE_NAME");
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
      resultSet.close();
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

  /**
   * checkCoursesColNames checks that the columns names and types for the courses table are
   * accuracte.
   *
   * @param concentrationNameRules concentration name for rules table
   * @return a boolean representing if the way table column names are of the correct format
   */
  public boolean checkConcentrationRulesColNames(String concentrationNameRules) {
    try {
      DatabaseMetaData metadata = conn.getMetaData();
      ResultSet resultSet = metadata.getColumns(null, null, concentrationNameRules, null);
      List<String> names = new ArrayList<>();
      while (resultSet.next()) {
        String name = resultSet.getString("COLUMN_NAME");
        String type = resultSet.getString("TYPE_NAME");
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
      resultSet.close();
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

  /**
   * checkCoursesColNames checks that the columns names and types for the courses table are
   * accuracte.
   *
   * @return a boolean representing if the way table column names are of the correct format
   */
  @SuppressWarnings("checkstyle:MagicNumber")
  public boolean checkCoursesColNames() {
    try {
      DatabaseMetaData metadata = conn.getMetaData();
      ResultSet resultSet = metadata.getColumns(null, null, "courses", null);
      List<String> names = new ArrayList<>();
      while (resultSet.next()) {
        String name = resultSet.getString("COLUMN_NAME");
        String type = resultSet.getString("TYPE_NAME");
        names.add(name);
        if (!(type.equals("TEXT"))) {
          return false;
        }
      }
      if (names.size() != COURSE) {
        return false;
      }
      if (!names.get(0).equals("course_id")) {
        return false;
      }
      if (!names.get(1).equals("course_name")) {
        return false;
      }
      if (!names.get(2).equals("prereqs")) {
        return false;
      }
      if (!names.get(3).equals("semester_offered")) {
        return false;
      }
      if (!names.get(4).equals("professor")) {
        return false;
      }
      if (!names.get(5).equals("courseRating")) {
        return false;
      }
      if (!names.get(6).equals("avg_hrs")) {
        return false;
      }
      if (!names.get(7).equals("max_hrs")) {
        return false;
      }
      if (!names.get(9).equals("class_size")) {
        return false;
      }
      resultSet.close();
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

  /**
   * isEmpty checks if the database has data and returns a boolean, returning true if it has data
   * and false if it does not have data in its tables.
   *
   * @return boolean representing if table is empty of not
   */
  @Override
  public boolean isEmptyCourses() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT COUNT(*) AS 'num' " + "FROM courses ");
      ResultSet rs = prep.executeQuery();
      int num = rs.getInt("num");
      rs.close();
      prep.close();
      return num == 0;
    } catch (SQLException e) {
      return true;
    }
  }

  /**
   * getCourseData gets a reference to a Node object with all of its field filled except next and
   * category since that is concentration specific.
   *
   * @param courseID course id such as CSCI 0320
   * @return Node object with everything filled in except category and next
   */
  @Override
  public Node getCourseData(String courseID) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(" SELECT * " + " FROM courses " + " WHERE course_id = ?");
      prep.setString(1, courseID);
      ResultSet rs = prep.executeQuery();
      Node newCourse = new Node(courseID);
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
        newCourse.setRating(3.5);
      }
      String avgHrs = rs.getString("avg_hrs");
      if (!avgHrs.equals("")) { // has avghrs value
        newCourse.setAvgHrs(Double.parseDouble(avgHrs));
      } else { //set default
        newCourse.setAvgHrs(8);
      }
      String maxHrs = rs.getString("max_hrs");
      if (!maxHrs.equals("")) { // has maxhrs value
        newCourse.setMaxHrs(Double.parseDouble(maxHrs));
      } else { //set default
        newCourse.setMaxHrs(14);
      }
      String classSize = rs.getString("class_size");
      if (!classSize.equals("")) { // has class size value set
        newCourse.setClassSize(Integer.parseInt(classSize));
      } else { //set default
        newCourse.setClassSize(40);
      }
      rs.close(); // close the reading of the db
      prep.close(); // close the query
      return newCourse;
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * parsePrereqs parses the comma separated prereqs.
   *
   * @param prereqs the string of courseID's
   * @return a list of prereqs course objects
   */
  public List<Set<Node>> parsePrereqs(String prereqs) {
    String[] parsedLine = prereqs.split(","); //split on , (AND)
    List<Set<Node>> courseList = new ArrayList<>();
    for (String courseID : parsedLine) { //loop thru each required prereqs (AND)
      String[] parsedOrs = courseID.split("="); //split each requirment on = (OR)
      Set<Node> courseSet = new HashSet<Node>();
      if (parsedOrs.length == 1) { //no ors in prereqs
        Node tmp = this.getCourseData(parsedOrs[0]);
        courseSet.add(tmp);
      } else if (parsedOrs.length > 1) { //has or courses
        for (String courseIDOr : parsedOrs) {
          Node equivCourse = this.getCourseData(courseIDOr);
          courseSet.add(equivCourse);
        }
      }
      courseList.add(courseSet);
    }
    return courseList;
  }

  /**
   * parseSemesterOffered parses the int into a set of 0 and/or 1.
   *
   * @param sem the string of semester_offered 2 is both semesters, 1 is fall, and 0 is spring
   * @return set of the semesters offered
   */
  public Set<Integer> parseSemesterOffered(String sem) {
    if (sem == null || sem.length() < 1) {
      return null;
    } else {
      Integer semOff = Integer.parseInt(sem);
      Set<Integer> semset = new HashSet<Integer>();
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
   */
  @Override
  public List<Integer> getRequirements(String tableName) {
    PreparedStatement prep;
    try {
      String strQuery = " SELECT * " + "FROM $tableName " + " ORDER BY category ASC ";
      String query = strQuery.replace("$tableName", tableName);
      prep = conn.prepareStatement(query);
      ResultSet rs = prep.executeQuery();
      List<Integer> reqs = new ArrayList<>();
      int n = rs.getFetchSize();

      while (rs.next()) {
        int category = Integer.parseInt(rs.getString("category"));
        int numCredits = Integer.parseInt(rs.getString("num_credits"));
        if (category < 0) {
          return null;
        } else {
          reqs.add(category, numCredits);
        }
      }
      rs.close(); // close the reading of the db
      prep.close(); // close the query
      return reqs;
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * getConcentrationCourses gets the courses for the concentration in the sql database. It calls on
   * the getCourseData for each course id in the concentration.
   *
   * @param tableName the concentrationName table name to search for
   * @return a set of courses all populated with category and next populated
   */
  @Override
  public Set<Node> getConcentrationCourses(String tableName) {
    PreparedStatement prep;
    try {
      String strQuery = " SELECT * " + " FROM $tableName " + " ORDER BY category ASC ";
      String query = strQuery.replace("$tableName", tableName);
      prep = conn.prepareStatement(query);
      ResultSet rs = prep.executeQuery();
      Set<Node> courseSet = new HashSet<Node>();
      while (rs.next()) {
        Integer category = Integer.parseInt(rs.getString("category"));
        String nextID = rs.getString("next");
        String courseID = rs.getString("course_id");
        Node tmp = this.getCourseData(courseID);
        if (tmp == null) { // course is not offered anymore so don't add it
          continue;
        } else { // course is in our courses table and is offered
          if (nextID.length() > 0) {
            Node next = this.getCourseData(nextID);
            next.setCategory(category);
            tmp.setNext(next);
            courseSet.add(next);
          }
          tmp.setCategory(category);
          courseSet.add(tmp);
        }

      }
      rs.close(); // close the reading of the db
      prep.close(); // close the query
      return courseSet;
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * getConcentrations gets the concentrations in the sql database for use in the GUI.
   *
   * @return a list of concentration names
   */
  public List<String> getConcentrations() throws SQLException {
    PreparedStatement prep = null;
    ResultSet rs = null;
    List<String> courseList = new ArrayList<>();

    try {
      prep = conn.prepareStatement("SELECT concentration_name "
          + " FROM concentrations "
          + " ORDER BY concentration_name ASC ");
      rs = prep.executeQuery();
      while (rs.next()) {
        courseList.add(rs.getString("concentration_name"));
      }

    } catch (SQLException e){
      return null;
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (prep != null) {
        prep.close();
      }
    }

    return courseList;

  }

  /**
   * getConcentrations gets the concentrations in the sql database for use in the GUI.
   *
   * @return a list of concentration names
   */
  public boolean hasLoop() {
    PreparedStatement prep = null;
    ResultSet rs = null;
    try {
      prep = conn.prepareStatement("SELECT course_id "
          + " FROM courses");
      rs = prep.executeQuery();
      while (rs.next()) {
        this.getCourseData(rs.getString("course_id"));
      }
      rs.close();
      prep.close();
      return true;
    } catch (SQLException e) {
      return false;
    }

  }

}
