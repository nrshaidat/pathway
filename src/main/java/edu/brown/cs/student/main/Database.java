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

import edu.brown.cs.student.pathway.Node;

/**
 * Database class that handles sql queries to the sql database.
 */
public class Database implements DatabaseInterface {
  private static Connection conn;
  
  /**
   * Database constructor.
   * @param filename for the sql database to connect to
   */
  public Database(String filename) {
    if (!this.connectDB(filename)) {
      conn = null;
    }
  }

  /**
   * hasConnection checks if the database could connect.
   * @return a boolean if the database was able to connect
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
   * @return a boolean that represents if the database is of the correct format
   */
  public boolean checkDBFormat() {
    return (checkTableNames() && checkCoursesColNames());
  }
  
  /**
   * checkTableNames checks for the table names.
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
   * isEmpty checks if the database has data and returns a boolean, returning
   * true if it has data and false if it does not have data in its tables.
   * @return boolean representing if table is empty of not
   */
  @Override
  public boolean isEmpty() {
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
   * getCourseData gets a reference to a Node object with all of its field filled except next
   * and category since that is concentration specific.
   * @param courseID course id such as CSCI 0320
   * @return Node object with everything filled in except category and next
   */
  @Override
  public Node getCourseData(String courseID) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(" SELECT * "
          + " FROM courses "
          + " WHERE course_id = ?");
      prep.setString(1, courseID);
      ResultSet rs = prep.executeQuery();

      Node newCourse = new Node(courseID);
      String name = rs.getString("course_name");
      newCourse.setName(name);

      // NOTE from Ifechi: we'll need to come back this. Once Nick
      // adds all course data, parsing has to be updated. You probably
      // already know this, but I'm putting this note here just in case.
      String prereq = rs.getString("prereqs");
      Set<Node> prereqList = this.parsePrereqs(prereq);
      newCourse.addPrereq(prereqList);

      String sem = rs.getString("semester_offered");
      newCourse.setSemesters(this.parseSemesterOffered(sem));
      newCourse.setProfessor(rs.getString("professor"));
      newCourse.setRating(Double.parseDouble(rs.getString("courseRating")));
      newCourse.setAvgHrs(Double.parseDouble(rs.getString("avg_hrs")));
      newCourse.setMaxHrs(Double.parseDouble(rs.getString("max_hrs")));
      newCourse.setClassSize(Integer.parseInt(rs.getString("class_size")));

      rs.close(); // close the reading of the db
      prep.close(); // close the query
      return newCourse;
    } catch (SQLException e) {
      return null;
    }
  }
  
  /**
   * parsePrereqs parses the comma separated prereqs.
   * @param prereqs the string of courseID's
   * @return a list of prereqs course objects
   */
  Set<Node> parsePrereqs(String prereqs) {
    String[] parsedLine = prereqs.split(",");
    Set<Node> courseList = new HashSet<Node>();
    for(String courseID : parsedLine) {
      Node tmp = this.getCourseData(courseID);
      courseList.add(tmp);
    }
    return courseList;
  }
  
  /**
   * parseSemesterOffered parses the int into a set of 0 and/or 1.
   * @param sem the string of semester_offered 2 is both semesters, 1 is fall, and 0 is spring
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
   * NOTE from Ifechi: may be simpler parsing if return type is int[]. Declare int[]
   * of size COUNT(*) and fill with num_credits values. I can make this change later on
   * if you're cool with it!
   *
   * getRequirements gets the requirements for the concentration.
   * @param tableName the concentrationNameReqs table name to search for
   * @return an int array where the index is the category and the value at that index is the
   * number of courses needed to fulfill the requirement
   */
  @Override
  public List<Integer> getRequirements(String tableName) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(" SELECT * "
          + "FROM ? "
          + "ORDER BY category ASC");
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
   * getConcentrationCourses gets the courses for the concentration in the sql database. It calls
   * on the getCourseData for each course id in the concentration.
   * @param tableName the concentrationName table name to search for
   * @return a set of courses all populated with category and next populated
   */
  @Override
  public Set<Node> getConcentrationCourses(String tableName) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(" SELECT * " + "FROM ? " + "ORDER BY category ASC");
      prep.setString(1, tableName);
      ResultSet rs = prep.executeQuery();

      Set<Node> courseSet = new HashSet<Node>();
      while (rs.next()) {
        Integer category = Integer.parseInt(rs.getString("category"));
        String nextID = rs.getString("next");
        String courseID = rs.getString("course_id");
        Node tmp = this.getCourseData(courseID);
        if (nextID.length() > 0){
          Node next = this.getCourseData(nextID);
          next.setCategory(category);
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
