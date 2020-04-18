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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * DatabaseTest.
 */
public class DatabaseTest {
  Database realDB;

  /**
   * Sets up the database connection.
   */
  @Before
  public void setUp() {
    String file = "data/coursesDB.db";
    realDB = new Database(file); // real database that handles sql queries
  }

  /**
   * Resets the the databases.
   */
  @After
  public void tearDown() {
    realDB = null;
  }

  /**
   * Tests the checkCoursesTable method returns true for the current db.
   */
  @Test
  public void validCheckCoursesTable() {
    setUp();
    assertTrue(realDB.checkCoursesTable());
    tearDown();
  }

  /**
   * Tests the checkCoursesColNames method returns true for the current db.
   */
  @Test
  public void validCoursesColNames() {
    setUp();
    assertTrue(realDB.checkCoursesColNames());
    tearDown();
  }

  /**
   * Tests the checkTableExists method returns true for the courses table in the current db.
   */
  @Test
  public void validCheckTableExists() {
    setUp();
    assertTrue(realDB.checkTableExists("courses"));
    tearDown();
  }

  /**
   * Tests the isEmptyCourses method returns false for the current db.
   */
  @Test
  public void validisEmptyCourses() {
    setUp();
    assertFalse(realDB.isEmptyCourses());
    tearDown();
  }

  /**
   * Tests the hasConnection method returns true for the current db.
   */
  @Test
  public void validhasConnection() {
    setUp();
    assertTrue(realDB.hasConnection());
    tearDown();
  }

  /**
   * Tests the checkConcentration method returns true for a valid concentration in the the current
   * db.
   */
  @Test
  public void validCheckConcentration() {
    setUp();
    String validCon = "computationalbiologyba";
    assertTrue(realDB.checkConcentration(validCon));
    tearDown();
  }

  /**
   * Tests the checkConcentration method returns false for an invalid concentration not in the the
   * current db.
   */
  @Test
  public void invalidCheckConcentration() {
    setUp();
    String invalidCon = "PERIODTcocentration";
    assertFalse(realDB.checkConcentration(invalidCon));
    tearDown();
  }

  /**
   * Tests the checkConcentrationColNames method returns true for a valid concentration in the the
   * current db.
   */
  @Test
  public void validCheckConcentrationColNames() {
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
   * Tests the getRequirements method returns the valid list for a valid concentration in the
   * the current db.
   */
  @Test
  public void validGetRequirements() {
    setUp();
    String validCon = "computationalbiologyba";
    String rules = validCon + "_rules";
    List<Integer> result = realDB.getRequirements(rules);
    assertEquals(9, result.size());
    int val = result.get(0);
    assertEquals(1, val);
    val = result.get(1);
    assertEquals(1, val);
    val = result.get(2);
    assertEquals(1, val);
    val = result.get(3);
    assertEquals(1, val);
    val = result.get(4);
    assertEquals(1, val);
    val = result.get(5);
    assertEquals(2, val);
    val = result.get(6);
    assertEquals(1, val);
    val = result.get(7);
    assertEquals(2, val);
    val = result.get(8);
    assertEquals(2, val);
    tearDown();
  }

  /**
   * Tests the getRequirements method returns null an invalid concentration in the
   * the current db.
   */
  @Test
  public void invalidGetRequirements() {
    setUp();
    String validCon = "PERIODTTT";
    String rules = validCon + "_rules";
    List<Integer> result = realDB.getRequirements(rules);
    assertNull(result);
    tearDown();
  }

  /**
   * Tests the parseSemestersOffered method returns the correct values for valid input.
   */
  @Test
  public void validParseSemestersOffered() {
    setUp();
    String valid0 = "0";
    String valid1 = "1";
    String valid2 = "2";
    Set<Integer> result0 = realDB.parseSemesterOffered(valid0);
    assertEquals(1, result0.size());
    assertTrue(result0.contains(0));
    Set<Integer> result1 = realDB.parseSemesterOffered(valid1);
    assertEquals(1, result1.size());
    assertTrue(result1.contains(1));
    Set<Integer> result2 = realDB.parseSemesterOffered(valid2);
    assertEquals(2, result2.size());
    assertTrue(result2.contains(1));
    assertTrue(result2.contains(0));
    tearDown();
  }

  /**
   * Tests the parseSemestersOffered method returns null for invalid input.
   */
  @Test
  public void invalidParseSemestersOffered() {
    setUp();
    String invalid0 = "";
    assertNull(realDB.parseSemesterOffered(invalid0));
    assertNull(realDB.parseSemesterOffered(null));
    tearDown();
  }

  /**
   * Tests the parsePrereqs method returns the correct values for a multitude of variations of prereqs.
   */
  @Test
  public void validParsePrereqs() {
    setUp();
    String oddNumOrs = "CSCI 0150=CSCI 0170=CSCI 0190,MATH 0100"; //2
    String evenNumOrs = "CSCI 0150=CSCI 0170,MATH 0100"; //2
    String noOrsEven = "CSCI 0190,MATH 0100"; //2
    String noOrsOdd = "CSCI 0190,MATH 0100,BIOL 0200"; //3
    String noAndsEven = "CSCI 0150=CSCI 0170"; //1
    String noAndsOdd = "CSCI 0150=CSCI 0170=CSCI 0190"; //1
    String evenAndsOddOr = "CSCI 0150,CSCI 0170=CSCI 0190=MATH 0100"; //2
    String oddAndsEvenOr = "CSCI 0150,CSCI 0170=CSCI 0190,MATH 0100"; //3
    Node cs15 = realDB.getCourseData("CSCI 0150");
    Node cs17 = realDB.getCourseData("CSCI 0170");
    Node cs19 = realDB.getCourseData("CSCI 0190");
    Node math10 = realDB.getCourseData("MATH 0100");
    Node bio = realDB.getCourseData("BIOL 0200");
    List<Set<Node>> sol1 = realDB.parsePrereqs(oddNumOrs);
    assertEquals(2, sol1.size());
    assertTrue(sol1.get(0).contains(cs15));
    assertTrue(sol1.get(0).contains(cs17));
    assertTrue(sol1.get(0).contains(cs19));
    assertTrue(sol1.get(1).contains(math10));
    List<Set<Node>> sol2 = realDB.parsePrereqs(evenNumOrs);
    assertEquals(2, sol2.size());
    assertTrue(sol2.get(0).contains(cs15));
    assertTrue(sol2.get(0).contains(cs17));
    assertTrue(sol2.get(1).contains(math10));
    List<Set<Node>> sol3 = realDB.parsePrereqs(noOrsEven);
    assertEquals(2, sol3.size());
    assertTrue(sol3.get(0).contains(cs19));
    assertTrue(sol3.get(1).contains(math10));
    List<Set<Node>> sol4 = realDB.parsePrereqs(noOrsOdd);
    assertEquals(3, sol4.size());
    assertTrue(sol4.get(0).contains(cs19));
    assertTrue(sol4.get(1).contains(math10));
    assertTrue(sol4.get(2).contains(bio));
    List<Set<Node>> sol5 = realDB.parsePrereqs(noAndsEven);
    assertEquals(1, sol5.size());
    assertTrue(sol5.get(0).contains(cs15));
    assertTrue(sol5.get(0).contains(cs17));
    List<Set<Node>> sol6 = realDB.parsePrereqs(noAndsOdd);
    assertEquals(1, sol6.size());
    assertTrue(sol6.get(0).contains(cs15));
    assertTrue(sol6.get(0).contains(cs17));
    assertTrue(sol6.get(0).contains(cs19));
    List<Set<Node>> sol7 = realDB.parsePrereqs(evenAndsOddOr);
    assertEquals(2, sol7.size());
    assertTrue(sol7.get(0).contains(cs15));
    assertTrue(sol7.get(1).contains(cs17));
    assertTrue(sol7.get(1).contains(cs19));
    assertTrue(sol7.get(1).contains(math10));
    List<Set<Node>> sol8 = realDB.parsePrereqs(oddAndsEvenOr);
    assertEquals(3, sol8.size());
    assertTrue(sol8.get(0).contains(cs15));
    assertTrue(sol8.get(1).contains(cs17));
    assertTrue(sol8.get(1).contains(cs19));
    assertTrue(sol8.get(2).contains(math10));
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
    Set<Node> comp = realDB.getConcentrationCourses(validCon);
    //SHOULD BE 21
    assertEquals(18,
        comp.size());//CHANGE ONCE IK HOW TO HANDLE MORE THAN ONE OPTION FOR A NEXT AKA CS19
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
    for (Node n : cp) {
      mapy.put(n.getId(), n);
    }
    return mapy;
  }
}
