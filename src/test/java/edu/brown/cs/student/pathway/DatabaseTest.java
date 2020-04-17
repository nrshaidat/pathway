package edu.brown.cs.student.pathway;

import edu.brown.cs.student.main.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * DatabaseTest.
 */
public class DatabaseTest {
  Database realDB;

  /**
   * Sets up the database connection.
   */
  @Before public void setUp() {
    String file = "data/coursesDB.db";
    realDB = new Database(file); // real database that handles sql queries
  }

  /**
   * Resets the the databases.
   */
  @After public void tearDown() {
    realDB = null;
  }

  /**
   * Tests the checkCoursesTable method returns true for the current db.
   */
  @Test public void validCheckCoursesTable() {
    setUp();
    assertTrue(realDB.checkCoursesTable());
    tearDown();
  }

  /**
   * Tests the checkCoursesColNames method returns true for the current db.
   */
  @Test public void validCoursesColNames() {
    setUp();
    assertTrue(realDB.checkCoursesColNames());
    tearDown();
  }

  /**
   * Tests the checkTableExists method returns true for the courses table in the current db.
   */
  @Test public void validCheckTableExists() {
    setUp();
    assertTrue(realDB.checkTableExists("courses"));
    tearDown();
  }

  /**
   * Tests the isEmptyCourses method returns false for the current db.
   */
  @Test public void validisEmptyCourses() {
    setUp();
    assertFalse(realDB.isEmptyCourses());
    tearDown();
  }

  /**
   * Tests the hasConnection method returns true for the current db.
   */
  @Test public void validhasConnection() {
    setUp();
    assertTrue(realDB.hasConnection());
    tearDown();
  }

  /**
   * Tests the checkConcentration method returns true for a valid concentration in the the current
   * db.
   */
  @Test public void validCheckConcentration() {
    setUp();
    String validCon = "computationalbiologyba";
    assertTrue(realDB.checkConcentration(validCon));
    tearDown();
  }

  /**
   * Tests the checkConcentration method returns false for an invalid concentration not in the the
   * current db.
   */
  @Test public void invalidCheckConcentration() {
    setUp();
    String invalidCon = "PERIODTcocentration";
    assertFalse(realDB.checkConcentration(invalidCon));
    tearDown();
  }

  /**
   * Tests the checkConcentrationColNames method returns true for a valid concentration in the the
   * current db.
   */
  @Test public void validCheckConcentrationColNames() {
    setUp();
    String validCon = "computationalbiologyba";
    assertTrue(realDB.checkConcentrationColNames(validCon));
    tearDown();
  }

  /**
   * Tests the checkConcentrationRulesColNames method returns true for a valid concentration in the
   * the current db.
   */
  @Test
  public void validCheckConcentrationRulesColNames() {
    setUp();
    String validCon = "computationalbiologyba";
    String rules = validCon + "_rules";
    assertTrue(realDB.checkConcentrationRulesColNames(rules));
    tearDown();
  }

  /**
   * Tests the getCourseData method returns the correct course data
   * the current db.
   */
  @Test
  public void validGetCourseData() {
    setUp();
    String validCon = "computationalbiologyba";
    //Node math = realDB.getCourseData("MATH 0100");
    Set<Node> comp = realDB.getConcentrationCourses(validCon);
    assertEquals(21,comp.size());
    Map<String, Node> mapy = this.covertDict(comp);
    Set<Node> preqs = mapy.get("MATH 0090").getNext().getPrereqs().get(0);
    Map<String, Node> pre = this.covertDict(preqs);

    assertEquals(0, mapy.get("MATH 0090").getCategory());
    assertEquals("MATH 0100", mapy.get("MATH 0090").getNext().getId());
    assertEquals(1, mapy.get("MATH 0090").getNext().getPrereqs().size());
    assertEquals(1, mapy.get("BIOL 0200").getCategory());
    assertEquals(1, mapy.get("BIOL 0200").getCategory());
    tearDown();
  }

  public Map<String, Node> covertDict(Set<Node> cp) {
      Map<String, Node> mapy = new HashMap<>();
      for(Node n:cp) {
          mapy.put(n.getId(), n);
      }
      return mapy;
  }
}
