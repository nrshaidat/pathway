package edu.brown.cs.student.pathway.ScrapingTest;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.pathway.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * A class to test the results of scraping CAB by looking at the database. It ensures each
 * column provided by CAB or SS is well-formed (using regex in the case of prereqs). It also
 * tests a number of specific classes and known edge cases.
 */
public class CABScrapingTest {
  private Database db;

  @Before
  public void setUp() {
    this.db = new Database("data/coursesDB.db");
  }

  @After
  public void tearDown() {
    this.db = null;
  }

  /**
   * Check if all course_id's are in the form of 2-4 capital letters, a space, and 4 numbers w/
   * an optional letter (e.g. CSCI 1450 or BE 1500 or BEO 1930B or VISA 1210G).
   */
  @Test
  public void testValidCourseCodes() {
    List<String> allCodes = getAllCourseInfo("course_id");
    for (String code: allCodes) {
      assertTrue(code.matches("[A-Z][A-Z][A-Z]?[A-Z]? \\d\\d\\d\\d[A-Z]?"));
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

  @Test
  public void testValidSemesters() {
    List<String> allSems = getAllCourseInfo("semester_offered");
    for (String s: allSems) {
      assertTrue(s.equals("0") || s.equals("1") || s.equals("2"));
    }
  }

  @Test
  public void testValidProfs() {
    List<String> allProfs = getAllCourseInfo("professor");
    for (String p: allProfs) {
      assertTrue(!p.isBlank());
    }
  }

  @Test
  public void testSpecificClasses() {
    setUp();

    /* Test all CAB attributes of a random class */
    Node afri1020c = db.getCourseData("AFRI 1020C");
    assertEquals(afri1020c.getId(), "AFRI 1020C");
    assertEquals(afri1020c.getName(), "The Afro-Luso-Brazilian Triangle");
    assertTrue(afri1020c.getPrereqs().isEmpty());
    assertEquals(afri1020c.getProfessor(), "A. Dzidzienyo");
    assertEquals(afri1020c.getSemestersOffered(), new HashSet<>(Arrays.asList(0)));

    /* Test class offered in fall only */
    Node biol0170 = db.getCourseData("BIOL 0170");
    assertEquals(biol0170.getSemestersOffered(), new HashSet<>(Arrays.asList(1)));

    /* Test class offered in fall and spring */
    Node econ1720 = db.getCourseData("ECON 1720");
    assertEquals(econ1720.getSemestersOffered(), new HashSet<>(Arrays.asList(0, 1)));

    /* Test course with one prereq */
    Node biol0280 = db.getCourseData("BIOL 0280");
    Node chem0350 = db.getCourseData("CHEM 0350");
    List<Set<Node>> biol0280PR = new ArrayList<>();
    biol0280PR.add(new HashSet<>(Arrays.asList(chem0350)));
    assertEquals(biol0280.getPrereqs(), biol0280PR);

    /* Test course with one OR prereq */
    Node clps1495 = db.getCourseData("CLPS 1495");
    Node clps0040 = db.getCourseData("CLPS 0040");
    Node clps0400 = db.getCourseData("CLPS 0400");
    List<Set<Node>> clps1495PR = new ArrayList<>();
    clps1495PR.add(new HashSet<>(Arrays.asList(clps0040, clps0400)));
    assertEquals(clps1495.getPrereqs(), clps1495PR);

    /* Test course with several OR prereqs */
    Node csci1730 = db.getCourseData("CSCI 1730");
    Node csci0160 = db.getCourseData("CSCI 0160");
    Node csci0180 = db.getCourseData("CSCI 0180");
    Node csci0190 = db.getCourseData("CSCI 0190");
    List<Set<Node>> csci1730PR = new ArrayList<>();
    csci1730PR.add(new HashSet<>(Arrays.asList(csci0160, csci0180, csci0190)));
    assertEquals(csci1730.getPrereqs(), csci1730PR);

    /* Test course with one AND prereq */
    Node arab0300 = db.getCourseData("ARAB 0300");
    Node arab0100 = db.getCourseData("ARAB 0100");
    Node arab0200 = db.getCourseData("ARAB 0200");
    List<Set<Node>> arab0300PR = new ArrayList<>();
    arab0300PR.add(new HashSet<>(Arrays.asList(arab0100)));
    arab0300PR.add(new HashSet<>(Arrays.asList(arab0200)));
    assertEquals(arab0300.getPrereqs(), arab0300PR);

    /* Test course with multiple AND and OR prereqs */
    Node phys1600 = db.getCourseData("PHYS 1600");
    String phys1600PR = "PHYS 0070=PHYS 0050,PHYS 0160=PHYS 0060,PHYS 0470=ENGN 0510,MATH 0180=" +
        "MATH 0200=MATH 0350";
    assertEquals(phys1600.getPrereqs(), db.parsePrereqs(phys1600PR));

    /* EDGE CASES */

    /* Test BIOL courses with an "Expected:" field in text but no prereqs area */
    Node biol0190s = db.getCourseData("BIOL 0190S");
    assertTrue(biol0190s.getPrereqs().isEmpty());
    Node biol0420 = db.getCourseData("BIOL 0420");
    assertEquals(biol0420.getPrereqs(), db.parsePrereqs("BIOL 0200,MATH 0090"));

    /* Test FREN course that was causing weird number error */
    Node fren1410t = db.getCourseData("FREN 1410T");
    assertEquals(fren1410t.getPrereqs(), db.parsePrereqs("FREN 0600"));

    /* Test MATH course, as math classes used "Prerequisites:" field in text w/o prereqs area */
    Node math1410 = db.getCourseData("MATH 1410");
    assertEquals(math1410.getPrereqs(), db.parsePrereqs("MATH 0520=MATH 0540"));

    /* Test ECON course */
    Node econ1510 = db.getCourseData("ECON 1510");
    assertEquals(econ1510.getPrereqs(), db.parsePrereqs("ECON 1110=ECON 1130,ECON 1629=ECON 1620="
        + "ECON 1630=CSCI 1450=APMA 1655=APMA 1650"));

    /* Test APMA course */
    Node apma1650 = db.getCourseData("APMA 1650");
    assertEquals(apma1650.getPrereqs(), db.parsePrereqs("MATH 0100=MATH 0180=MATH 0190=MATH 0200="
        + "MATH 0350"));

    tearDown();
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
