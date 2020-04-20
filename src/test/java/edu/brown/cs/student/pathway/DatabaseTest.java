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
    assertEquals(1, val);
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
   * Tests the getCourseData method returns the correct course data for valid input
   * the current db.
   */
//  @Test
//  public void validGetCourseData() {
//    setUp();
//    //course that has everything filled in the db
//    Node math10 = realDB.getCourseData("MATH 0100");
//    //course with no prereqs
//    Node math9 = realDB.getCourseData("MATH 0090");
//    assertEquals("MATH 0100", math10.getId());
//    assertEquals("Introductory Calculus, Part II", math10.getName());
//    assertEquals(1, math10.getPrereqs().size());
//    assertTrue(math10.getPrereqs().get(0).contains(math9));
//    assertTrue(3.79==math10.getRating());
//    assertTrue(3.60 == math10.getAvgHrs());
//    assertTrue(6.40 == math10.getMaxHrs());
//    assertTrue(27 == math10.getClassSize());
//    assertTrue(math10.getSemestersOffered().contains(0));
//    assertTrue(math10.getSemestersOffered().contains(1));
//
//    //math90
//    assertEquals("MATH 0090", math9.getId());
//    assertEquals("Introductory Calculus, Part I", math9.getName());
//    assertEquals(0, math9.getPrereqs().size());
//    assertTrue(3.99 == math9.getRating());
//    assertTrue(3.83 == math9.getAvgHrs());
//    assertTrue(4.92 == math9.getMaxHrs());
//    assertTrue(21 == math9.getClassSize());
//    assertTrue(math9.getSemestersOffered().contains(0));
//    assertTrue(math9.getSemestersOffered().contains(1));
//    //course with no prereqs and no critical review data in db
//    Node afri = realDB.getCourseData("AFRI 0980");
//    assertEquals("AFRI 0980", afri.getId());
//    assertEquals("Fela Kuti: African Freedom from Afrobeat to Afrobeats", afri.getName());
//    assertEquals(0, afri.getPrereqs().size());
//    assertTrue(afri.getRating()==0.0);
//    assertTrue(afri.getAvgHrs()==0.0);
//    assertTrue(afri.getMaxHrs()==0.0);
//    assertTrue(afri.getClassSize()==0);
//    assertTrue(afri.getSemestersOffered().contains(1));
//    //course with only class size only from critical review
//    Node mcm = realDB.getCourseData("MCM 0150");
//    assertEquals("MCM 0150", mcm.getId());
//    assertEquals("Text/Media/Culture: Theories of Modern Culture and Media", mcm.getName());
//    assertEquals(0, afri.getPrereqs().size());
//    assertTrue(mcm.getRating()==0.0);
//    assertTrue(mcm.getAvgHrs()==0.0);
//    assertTrue(mcm.getMaxHrs()==0.0);
//    assertTrue(50 == mcm.getClassSize());
//    assertTrue(mcm.getSemestersOffered().contains(1));
//    //course with only course rating and class size
//    Node anth = realDB.getCourseData("ANTH 1250");
//    assertEquals("ANTH 1250", anth.getId());
//    assertEquals("Film and Anthropology: Identity and Images of Indian Societies", anth.getName());
//    assertEquals(0, anth.getPrereqs().size());
//    assertTrue(4.24 == anth.getRating());
//    assertTrue(anth.getAvgHrs()==0.0);
//    assertTrue(anth.getMaxHrs()==0.0);
//    assertTrue(28 == anth.getClassSize());
//    assertTrue(anth.getSemestersOffered().contains(0));
//    tearDown();
//  }

  /**
   * Tests the getCourseData method returns null for invalid input.
   */
  @Test
  public void invalidGetCourseData() {
    setUp();
    String invalid0 = "PERIODT 101";
    assertNull(realDB.getCourseData(invalid0));
    assertNull(realDB.getCourseData(null));
    tearDown();
  }

  /**
   * Tests the getConcentrationCourses method returns the correct course data
   * the current db.
   */
  @Test
  public void validGetConcentrationData() {
    setUp();
    String validCon = "computationalbiologyba";
    Set<Node> comp = realDB.getConcentrationCourses(validCon);
    assertEquals(18,comp.size());
    Map<String, Node> mapy = this.covertDict(comp);
    Node math10 = realDB.getCourseData("MATH 0100");
    assertEquals(math10,mapy.get("MATH 0090").getNext());
    assertEquals(0, mapy.get("MATH 0090").getCategory());
    assertEquals(1, mapy.get("BIOL 0200").getCategory());
    assertNull(mapy.get("BIOL 0200").getNext());
    assertEquals(2, mapy.get("BIOL 0470").getCategory());
    assertNull(mapy.get("BIOL 0470").getNext());
    assertEquals(3, mapy.get("BIOL 0280").getCategory());
    assertNull(mapy.get("BIOL 0280").getNext());
    assertEquals(3, mapy.get("BIOL 0500").getCategory());
    assertNull(mapy.get("BIOL 0500").getNext());
    assertEquals(4, mapy.get("CHEM 0330").getCategory());
    assertNull(mapy.get("CHEM 0330").getNext());
    assertEquals(4, mapy.get("CHEM 0350").getCategory());
    assertNull(mapy.get("CHEM 0350").getNext());
    Node cs16 = realDB.getCourseData("CSCI 0160");
    assertEquals(cs16, mapy.get("CSCI 0150").getNext());
    assertEquals(5, mapy.get("CSCI 0150").getCategory());
    Node cs18 = realDB.getCourseData("CSCI 0180");
    assertEquals(cs18, mapy.get("CSCI 0170").getNext());
    assertEquals(5, mapy.get("CSCI 0170").getCategory());
    assertNull(mapy.get("CSCI 0190").getNext());
    assertEquals(5, mapy.get("CSCI 0190").getCategory());
    assertNull(mapy.get("APMA 1650").getNext());
    assertEquals(6, mapy.get("APMA 1650").getCategory());
    assertNull(mapy.get("CSCI 1450").getNext());
    assertEquals(6, mapy.get("CSCI 1450").getCategory());
    assertNull(mapy.get("MATH 1610").getNext());
    assertEquals(6, mapy.get("MATH 1610").getCategory());
    assertNull(mapy.get("CSCI 1810").getNext());
    assertEquals(7, mapy.get("CSCI 1810").getCategory());
    assertNull(mapy.get("APMA 1080").getNext());
    assertEquals(7, mapy.get("APMA 1080").getCategory());
    assertNull(mapy.get("CSCI 1420").getNext());
    assertEquals(8, mapy.get("CSCI 1420").getCategory());
    assertNull(mapy.get("APMA 1690").getNext());
    assertEquals(8, mapy.get("APMA 1690").getCategory());
    assertNull(mapy.get("APMA 1660").getNext());
    assertEquals(8, mapy.get("APMA 1660").getCategory());
    tearDown();
  }

  /**
   * Tests the getConcentrationCourses method returns the null for an invalid table name in
   * the current db.
   */
  @Test
  public void invalidGetConcentrationData() {
    setUp();
    String invalidCon = "PERIODT CONCENTRATION";
    assertNull(realDB.getConcentrationCourses(invalidCon));

    tearDown();
  }


  /**
   * CovertDict is a helper method that converts the set of nodes to a dictionary for
   * testing getConcentrationCourses.
   *
   * @param cp the cp
   * @return the map
   */
  public Map<String, Node> covertDict(Set<Node> cp) {
    Map<String, Node> mapy = new HashMap<>();
    for (Node n : cp) {
      mapy.put(n.getId(), n);
    }
    return mapy;
  }
}
