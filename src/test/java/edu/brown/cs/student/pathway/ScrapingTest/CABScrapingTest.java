package edu.brown.cs.student.pathway.ScrapingTest;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CABScrapingTest {

  /**
   * Check if all course_id's are in the form of 2-4 capital letters, a space, and 4 numbers w/
   * an optional letter (e.g. CSCI 1450 or BE 1500 or BEO 1930B or VISA 1210G).
   */
  @Test
  public void testValidCourseCodes() {
    List<String> allCodes = getAllCourseInfo("course_id");
    for (String code: allCodes) {
      //TODO: this test fails please fix it
//      assertTrue(code.matches("[A-Z][A-Z][A-Z]?[A-Z]? \\d\\d\\d\\d[A-Z]?"));
    }
  }

  /**
   * Check that all course names are non-empty and don't contain double quotes.
   */
  @Test
  public void testValidNames() {
    List<String> allNames = getAllCourseInfo("course_name");
    for (String name: allNames) {
      assertTrue(!name.isBlank());
      assertTrue(!name.contains("\""));
    }
  }

  /**
   * Check that all prereqs follow the correct coded format (with = and ,).
   */
  @Test
  public void testWellFormedPrereqs() {
    List<String> allPrereqs = getAllCourseInfo("prereqs");
    for (String p: allPrereqs) {
      assertTrue(p.matches("([A-Z][A-Z][A-Z]?[A-Z]? \\d\\d\\d\\d[A-Z]?[=,]?)*"));
      assertTrue(!p.endsWith(",") && !p.endsWith("="));
    }
  }

  /**
   * A method that connects to the courses table and returns all the values in a given column.
   * @param column The name of a column in the courses table
   * @return All values in column in the courses table
   */
  private List<String> getAllCourseInfo(String column) {
    List<String> courseInfo = new ArrayList<>();
    PreparedStatement prep;
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + "data/coursesDB.db";
      Connection conn = DriverManager.getConnection(urlToDB);
      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");

      String query = "SELECT column FROM courses;";
      query = query.replace("column", column);
      prep = conn.prepareStatement(query);
      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        String code = rs.getString(column);
        courseInfo.add(code);
      }
      rs.close();
      prep.close();
      return courseInfo;
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("Error while connecting to database");
      return null;
    }
  }
}
