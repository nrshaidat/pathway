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
import java.util.ArrayList;
import java.util.List;

/**
 * Database class that handles sql queries to the sql database.
 */
public class Database implements DatabaseInterface {
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
   * hasConnection checks if the database has a connection.
   *
   * @return boolean representing if a connection was made to the database
   */
  @Override
  public boolean hasConnection() {
    return conn != null;
  }
  
  /**
   * Method to reset the connection.
   */
  public void resetConn() {
    conn = null;
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
  public boolean checkDBFormat() {
    return (checkTableNames() && checkCoursesColNames());
  }
  
  /**
   * checkTableNames checks for the table names.
   *
   * @return a boolean representing if the table names are of the correct format
   */
  public boolean checkTableNames() {
    try {
      DatabaseMetaData dbmd = conn.getMetaData();
      String[] types = {
          "TABLE"
      };
      ResultSet rs = dbmd.getTables(null, null, "%", types);
      List<String> tableNames = new ArrayList<>();
      while (rs.next()) {
        tableNames.add(rs.getString("TABLE_NAME"));
      }
      if (tableNames.isEmpty()) {
        return false;
      }
      if (!tableNames.contains("courses")) {
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
   * @return a boolean representing if the way table column names are of the correct format
   */
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
      if (names.size() != 10) {
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
      if (!names.get(8).equals("CR_link")) {
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
   * isEmpty checks if the database has data and returns a boolean representing true if it has data
   * and false if it does not have data in its tables.
   *
   * @return boolean representing if the way table is empty of not
   */
  @Override
  public boolean isEmpty() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT COUNT(*) AS 'num' " + " FROM courses ");
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
   * getCourseData gets a reference to a course object with all of its field filled execpt next and
   * category since that concentration specfic.
   *
   * @param courseID the course id such as CSCI0320
   * @return course object instance with everything filled in execpt category and next
   */
  @Override
  public Course getCourseData(String courseID) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(" SELECT * "
          + " FROM courses "
          + " WHERE course_id = ? ");
      prep.setString(1, courseID);
      ResultSet rs = prep.executeQuery();
      Course newCourse = new Course(courseID);
      String name = rs.getString("course_name");
      newCourse.setName(name);
      String prereq = rs.getString("prereqs");
      List<Course> prereqList = this.parsePrereqs(prereq);
      newCourse.setPrereqs(prereqList);
      String sem = rs.getString("semester_offered");
      Set<Integer> semesterOff = this.parseSemesterOffered(sem);
      newCourse.setSemestersOffered(semesterOff);
      String profName = rs.getString("professor");
      newCourse.setProfessor(profName);
      Double courseRating = Double.parseDouble(rs.getString("courseRating"));
      newCourse.setCourseRating(courseRating);
      Double avgHrs = Double.parseDouble(rs.getString("avg_hrs"));
      newCourse.setAvgHrs(avgHrs);
      Double maxHrs = Double.parseDouble(rs.getString("max_hrs"));
      newCourse.setMaxHrs(maxHrs);
      String CRLink = rs.getString("CR_link");
      newCourse.setCRurl(CRLink);
      Integer classSize = Integer.parseInt(rs.getString("class_size"));
      newCourse.setClassSize(classSize);
      rs.close(); // close the reading of the db
      prep.close(); // close the query
      return newCourse;
    } catch (SQLException e) {
      return null;
    }
  }
  
  /**
   * parsePrereqs parses the comma seperated prereqs.
   *
   * @param prereqs the string of courseID's
   * @return a list of prereqs course objects
   */
  List<Course> parsePrereqs(String prereqs) {
    String[] parsedLine = prereqs.split(",");
    List<Course> courseList = new ArrayList<Course>();
    Course tmp;
    for(String courseID : parsedLine) {
      tmp = this.getCourseData(courseID);
      courseList.add(tmp);
    }
    return courseList;
  }
  
  /**
   * parseSemesterOffered parses the int into a set of 1 and/or 2.
   *
   * @param sem the string of semester_offered 2 is both semesters, 1 is spring, and 0 is fall
   * @return set of the semesters offered
   */
  Set<Integer> parseSemesterOffered(String sem) {
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
  
  /**
   * getRequirements gets the requirements for the concentration.
   *
   * @param tableName the concentrationNameReqs table name to search for
   * @return an int array where the index is the category and the value at that index is the number
   *     of courses needed to fulfill the requirement
   */
  @Override
  public List<Integer> getRequirements(String tableName) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(" SELECT * "
          + " FROM ? "
          + " ORDER BY category ASC ");
      prep.setString(1, tableName);
      ResultSet rs = prep.executeQuery();
      List<Integer> reqs = new ArrayList<Integer>();
      while(rs.next()){
        Integer category = Integer.parseInt(rs.getString("category"));
        Integer numCredits = Integer.parseInt(rs.getString("num_credits"));
        reqs.add(category, numCredits);
      }
      rs.close(); // close the reading of the db
      prep.close(); // close the query
      return reqs;
    } catch (SQLException e) {
      return null;
    }
  }
  
  /**
   * getConcentrationCourses gets the courses for the concentration in the sql database it calls on
   * the getCourseData for each course in the concentration.
   *
   * @param tableName the concentrationName table name to search for
   * @return a set of courses all populated with category and next populated
   */
  @Override
  public Set<Course> getConcentrationCourses(String tableName) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(" SELECT * " + " FROM ? " + " ORDER BY category ASC ");
      prep.setString(1, tableName);
      ResultSet rs = prep.executeQuery();
      Set<Course> courseSet = new HashSet<Course>();
      while (rs.next()) {
        Integer category = Integer.parseInt(rs.getString("category"));
        String courseID = rs.getString("course_id");
        String nextID = rs.getString("next");
        Course tmp = this.getCourseData(courseID);
        if (nextID.length() > 0){
          Course next = this.getCourseData(nextID);
          tmp.setNext(next);
        }
        tmp.setCategory(category);
        courseSet.add(tmp);
      }
      rs.close(); // close the reading of the db
      prep.close(); // close the query
      return courseSet;
    } catch (SQLException e) {
      return null;
    }
  }
}
