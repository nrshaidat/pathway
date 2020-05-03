package edu.brown.cs.student.pathway.PathwayTest;


import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.main.DatabaseInterface;
import edu.brown.cs.student.main.PathwayProgram;
import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Semester;
import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * PathwayProgramTest.
 */
public class PathwayProgramTest {
  /**
   * The pathway program instance.
   */
  PathwayProgram pp;
  DatabaseInterface cache;


  /**
   * Sets up pathway program for testing.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    pp = new PathwayProgram("brown");
    cache = new DatabaseCache(new Database("data/coursesDB.db"));
  }

  /**
   * Sets up pathway program and static pathways for stats testing.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUpStats() throws Exception {
    pp = new PathwayProgram("brown");
    cache = new DatabaseCache(new Database("data/coursesDB.db"));
    //path 1
    List<Semester> p1 = new ArrayList<>();
    //fall 1
    List<Node> tak1 = new ArrayList<>();
    tak1.add(cache.getCourseData("MATH 0090"));
    tak1.add(cache.getCourseData("CSCI 0150"));
    Semester fall1 = new Semester(1, tak1);
    p1.add(fall1);
    //spring 2
    List<Node> tak2 = new ArrayList<>();
    tak2.add(cache.getCourseData("MATH 0100"));
    tak2.add(cache.getCourseData("CSCI 0160"));
    Semester spring2 = new Semester(2, tak2);
    p1.add(spring2);
    //path 2
    List<Semester> p2 = new ArrayList<>();
    //fall 1
    List<Node> tak12 = new ArrayList<>();
    tak12.add(cache.getCourseData("MATH 0090"));
    tak12.add(cache.getCourseData("CSCI 0170"));
    Semester fall12 = new Semester(1, tak12);
    p2.add(fall12);
    //spring 2
    List<Node> tak22 = new ArrayList<>();
    tak22.add(cache.getCourseData("MATH 0100"));
    tak22.add(cache.getCourseData("CSCI 0180"));
    tak22.add(cache.getCourseData("CSCI 0220"));
    Semester spring22 = new Semester(2, tak22);
    p2.add(spring22);
    //path 3
    //path 2
    List<Semester> p3 = new ArrayList<>();
    //fall 1
    List<Node> tak13 = new ArrayList<>();
    tak13.add(cache.getCourseData("CSCI 0190"));
    Semester fall13 = new Semester(1, tak13);
    p3.add(fall13);
    //spring 2
    List<Node> tak23 = new ArrayList<>();
    tak23.add(cache.getCourseData("MATH 0100"));
    Semester spring23 = new Semester(2, tak23);
    p3.add(spring23);
    //fall 2
    List<Node> tak32 = new ArrayList<>();
    tak32.add(cache.getCourseData("CSCI 1450"));
    Semester fall32 = new Semester(3, tak32);
    p3.add(fall32);
    pp.setPath1(p1);
    pp.setPath2(p2);
    pp.setPath3(p3);
  }

  /**
   * Another method to set up pathways for unique testing where there are more than 3 unique
   * courses.
   * @throws Exception An exception
   */
  @Before
  public void setUpStats2() throws Exception {
    pp = new PathwayProgram("brown");
    cache = new DatabaseCache(new Database("data/coursesDB.db"));
    //path 1
    List<Semester> p1 = new ArrayList<>();
    //fall 1
    List<Node> tak1 = new ArrayList<>();
    tak1.add(cache.getCourseData("MATH 0090"));
    tak1.add(cache.getCourseData("CSCI 0150"));
    Semester fall1 = new Semester(1, tak1);
    p1.add(fall1);
    //spring 2
    List<Node> tak2 = new ArrayList<>();
    tak2.add(cache.getCourseData("MATH 0100"));
    tak2.add(cache.getCourseData("CSCI 0160"));
    Semester spring2 = new Semester(2, tak2);
    p1.add(spring2);
    //fall 2
    List<Node> tak3 = new ArrayList<>();
    tak3.add(cache.getCourseData("MATH 0180"));
    tak3.add(cache.getCourseData("CSCI 1230"));
    Semester fall2 = new Semester(3, tak3);
    p1.add(fall2);


    //path 2
    List<Semester> p2 = new ArrayList<>();
    //fall 1
    List<Node> tak12 = new ArrayList<>();
    tak12.add(cache.getCourseData("MATH 0090"));
    tak12.add(cache.getCourseData("CSCI 0170"));
    Semester fall12 = new Semester(1, tak12);
    p2.add(fall12);
    //spring 2
    List<Node> tak22 = new ArrayList<>();
    tak22.add(cache.getCourseData("MATH 0100"));
    tak22.add(cache.getCourseData("CSCI 0180"));
    tak22.add(cache.getCourseData("CSCI 0220"));
    Semester spring22 = new Semester(2, tak22);
    p2.add(spring22);
    //path 3
    List<Semester> p3 = new ArrayList<>();
    //fall 1
    List<Node> tak13 = new ArrayList<>();
    tak13.add(cache.getCourseData("CSCI 0190"));
    Semester fall13 = new Semester(1, tak13);
    p3.add(fall13);
    //spring 2
    List<Node> tak23 = new ArrayList<>();
    tak23.add(cache.getCourseData("MATH 0100"));
    tak23.add(cache.getCourseData("CSCI 0320"));
    Semester spring23 = new Semester(2, tak23);
    p3.add(spring23);
    //fall 2
    List<Node> tak32 = new ArrayList<>();
    tak32.add(cache.getCourseData("APMA 1650"));
    Semester fall32 = new Semester(3, tak32);
    p3.add(fall32);
    pp.setPath1(p1);
    pp.setPath2(p2);
    pp.setPath3(p3);
  }

  /**
   * Sets up pathway program and static pathways for stats testing on pathways with no unique
   * courses.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUpStats3() throws Exception {
    pp = new PathwayProgram("brown");
    cache = new DatabaseCache(new Database("data/coursesDB.db"));
    //path 1
    List<Semester> p1 = new ArrayList<>();
    //fall 1
    List<Node> tak1 = new ArrayList<>();
    tak1.add(cache.getCourseData("MATH 0090"));
    tak1.add(cache.getCourseData("CSCI 0150"));
    Semester fall1 = new Semester(1, tak1);
    p1.add(fall1);
    //spring 2
    List<Node> tak2 = new ArrayList<>();
    tak2.add(cache.getCourseData("MATH 0100"));
    tak2.add(cache.getCourseData("CSCI 0160"));
    Semester spring2 = new Semester(2, tak2);
    p1.add(spring2);
    //path 2
    List<Semester> p2 = new ArrayList<>();
    //fall 1
    List<Node> tak12 = new ArrayList<>();
    tak12.add(cache.getCourseData("MATH 0090"));
    tak12.add(cache.getCourseData("CSCI 0150"));
    Semester fall12 = new Semester(1, tak12);
    p2.add(fall12);
    //spring 2
    List<Node> tak22 = new ArrayList<>();
    tak22.add(cache.getCourseData("MATH 0100"));
    tak22.add(cache.getCourseData("CSCI 0160"));
    Semester spring22 = new Semester(2, tak22);
    p2.add(spring22);
    //path 3
    //path 2
    List<Semester> p3 = new ArrayList<>();
    //fall 1
    List<Node> tak13 = new ArrayList<>();
    tak13.add(cache.getCourseData("CSCI 0150"));
    tak13.add(cache.getCourseData("MATH 0090"));
    Semester fall13 = new Semester(1, tak13);
    p3.add(fall13);
    //spring 2
    List<Node> tak23 = new ArrayList<>();
    tak23.add(cache.getCourseData("MATH 0100"));
    tak23.add(cache.getCourseData("CSCI 0160"));
    Semester spring23 = new Semester(2, tak23);
    p3.add(spring23);
    pp.setPath1(p1);
    pp.setPath2(p2);
    pp.setPath3(p3);
  }

  /**
   * Tear down.
   *
   * @throws Exception the exception
   */
  @After
  public void tearDown() throws Exception {
    pp = null;
  }

  /**
   * Tests course list.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getCourseList() throws Exception {
    setUp();
    List<String> cl = pp.getCourseList();
    List<String> cl2 = cache.getAllCourseIDs();
    assertEquals(cl2, cl);
    tearDown();
  }

  /**
   * Tests getavgavghrspath.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getAvgavghrspath() throws Exception {
    setUpStats();
    assertEquals(7, pp.getAvgavghrs1path());
    assertEquals(7, pp.getAvgavghrs2path());
    assertEquals(7, pp.getAvgavghrs3path());
    tearDown();
  }

  /**
   * Tests gettotalnumcourses.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getTotalnumcourses() throws Exception {
    setUpStats();
    assertEquals(4, pp.getTotalnumcourses1());
    assertEquals(5, pp.getTotalnumcourses2());
    assertEquals(3, pp.getTotalnumcourses3());
    tearDown();
  }


  /**
   * Tests getNumsemesters.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getNumsemesters() throws Exception {
    setUpStats();
    assertEquals(2, pp.getNumsemesters1());
    assertEquals(2, pp.getNumsemesters2());
    assertEquals(3, pp.getNumsemesters3());
    tearDown();
  }


  /**
   * Tests getPath.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getPath() throws Exception {
    setUp();
    //test default
    assertNull(pp.getPath1());
    assertNull(pp.getPath2());
    assertNull(pp.getPath3());
    assertNull(pp.getPath1Uniques());
    assertNull(pp.getPath2Uniques());
    assertNull(pp.getPath3Uniques());
    //test setting new
    pp.makePathways("Computer Science B.A.", new HashSet<>(), 1, false);
    assertNotNull(pp.getPath1());
    assertNotNull(pp.getPath2());
    assertNotNull(pp.getPath3());
    assertNotNull(pp.getPath1Uniques());
    assertNotNull(pp.getPath2Uniques());
    assertNotNull(pp.getPath3Uniques());
    tearDown();
  }


  /**
   * Tests getConcentrationMap.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getConcentrationMap() throws Exception {
    setUp();
    Map<String, String> mapy = pp.getConcentrationMap();
    Map<String, String> mapycache = cache.getConcentrationsMap();
    assertEquals(mapycache, mapy);
    tearDown();
  }


  /**
   * Tests setConcentration, getConcentration, and getCourseSet since setConcentration sets the
   * last two.
   *
   * @throws Exception the exception
   */
  //@Test
  public void setConcentration() throws Exception {
    setUp();
    pp.setConcentration("cognitiveneuroscienceba");
    assertEquals("cognitiveneuroscienceba", pp.getConcentration());
    Set<Node> courses = cache.getConcentrationCourses("cognitiveneuroscienceba");
    assertEquals(courses, pp.getCourseSet());
    tearDown();
  }

  /**
   * Tests getConcentrationName returns default concentration when concentration is not set.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getConcentrationName() throws Exception {
    setUp();
    assertEquals("Computer Science B.A.", pp.getConcentrationName());
    tearDown();
  }

  /**
   * Tests  valid input for setConcentrationName.
   *
   * @throws Exception the exception
   */
  //@Test
  public void validsetConcentrationName() throws Exception {
    setUp();
    pp.setConcentrationName("Cognitive Neuroscience B.A.");
    assertEquals("Cognitive Neuroscience B.A.", pp.getConcentrationName());
    tearDown();
  }

  /**
   * Tests invalid input for setConcentrationName.
   *
   * @throws Exception the exception
   */
  //@Test
  public void invalidsetConcentrationName() throws Exception {
    setUp();
    pp.setConcentrationName("Cognitive Neuroscience B.T.");
    assertEquals("Computer Science B.A.", pp.getConcentrationName());
    tearDown();
  }


  /**
   * Tests isSet.
   *
   * @throws Exception the exception
   */
  //@Test
  public void isSet() throws Exception {
    setUp();
    assertFalse(pp.isSet());
    pp.makePathways("Computer Science B.A.", new HashSet<>(), 1, false);
    assertTrue(pp.isSet());
    tearDown();
  }

  /**
   * Tests getConcentration on default value.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getConcentration() throws Exception {
    setUp();
    assertEquals("computerscienceba", pp.getConcentration());
    tearDown();
  }

  /**
   * Tests getConcentrationsList.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getConcentrationsList() throws Exception {
    setUp();
    assertEquals(new ArrayList<>(cache.getConcentrationsMap().keySet()),
        pp.getConcentrationsList());
    tearDown();
  }

  /**
   * Tests setPathStats for all pathways.
   *
   * @throws Exception the exception
   */
  //@Test
  public void setPathStats() throws Exception {
    setUp();
    assertEquals(0,pp.getTotalnumcourses1());
    assertEquals(0,pp.getTotalnumcourses2());
    assertEquals(0,pp.getTotalnumcourses3());
    assertEquals(0,pp.getNumsemesters1());
    assertEquals(0,pp.getNumsemesters2());
    assertEquals(0,pp.getNumsemesters3());
    assertEquals(0,pp.getAvgavghrs1path());
    assertEquals(0,pp.getAvgavghrs2path());
    assertEquals(0,pp.getAvgavghrs3path());
    pp.makePathways("Computer Science B.A.", new HashSet<>(), 1, false);
    assertTrue(pp.getTotalnumcourses1()>0);
    assertTrue(pp.getTotalnumcourses2()>0);
    assertTrue(pp.getTotalnumcourses3()>0);
    assertTrue(pp.getNumsemesters1()>0);
    assertTrue(pp.getNumsemesters2()>0);
    assertTrue(pp.getNumsemesters3()>0);
    assertTrue(pp.getAvgavghrs1path()>0);
    assertTrue(pp.getAvgavghrs2path()>0);
    assertTrue(pp.getAvgavghrs3path()>0);
    tearDown();
  }

  /**
   * Tests setPathUniques.
   *
   * @throws Exception the exception
   */
  //@Test
  public void setPathUniques() throws Exception {
    setUp();
    pp.setPathUniques();
    assertEquals(0, pp.getPath1Uniques().size());
    assertEquals(0, pp.getPath2Uniques().size());
    assertEquals(0, pp.getPath3Uniques().size());
    tearDown();
  }

  /**
   * Tests less than or equal to 3 unique courses case getPathUniques.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getPathUniques() throws Exception {
    setUpStats();
    pp.setPathUniques();
    List<String> path1u = new ArrayList<>();
    path1u.add("CSCI 0150");
    path1u.add("CSCI 0160");
    assertTrue(pp.getPath1Uniques().containsAll(path1u));
    assertEquals(2, pp.getPath1Uniques().size());
    List<String> path2u = new ArrayList<>();
    path2u.add("CSCI 0170");
    path2u.add("CSCI 0220");
    path2u.add("CSCI 0180");
    assertTrue(pp.getPath2Uniques().containsAll(path2u));
    assertEquals(3, pp.getPath2Uniques().size());
    List<String> path3u = new ArrayList<>();
    path3u.add("CSCI 0190");
    path3u.add("CSCI 1450");
    assertTrue(pp.getPath3Uniques().containsAll(path3u));
    assertEquals(2, pp.getPath3Uniques().size());
    tearDown();
  }

  /**
   * Tests no 3 unique courses case getPathUniques.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getPathNoUniques() throws Exception {
    setUpStats3();
    pp.setPathUniques();
    assertEquals(0, pp.getPath1Uniques().size());
    assertEquals(0, pp.getPath2Uniques().size());
    assertEquals(0, pp.getPath3Uniques().size());
    tearDown();
  }

  /**
   * Tests getPathUniques on more than 3 unique courses .
   *
   * @throws Exception the exception
   */
  //@Test
  public void getPathUniques2() throws Exception {
    setUpStats2();
    pp.setPathUniques();
    /* Path 1 should actually have 4 uniques (cs 15, cs 15, math 180, and cs 1230), but
    getPath1Uniques should cut it off at 3.
     */
    List<String> u1 = new ArrayList<>(Arrays.asList("CSCI 0150", "CSCI 0160", "MATH 0180", "CSCI 1230"));
    assertTrue(u1.containsAll(pp.getPath1Uniques()) && pp.getPath1Uniques().size() == 3);
    List<String> u2 = new ArrayList<>(Arrays.asList("CSCI 0170", "CSCI 0180", "CSCI 0220"));
    assertTrue(pp.getPath2Uniques().containsAll(u2) && u2.containsAll(pp.getPath2Uniques()));
    List<String> u3 = new ArrayList<>(Arrays.asList("CSCI 0190", "CSCI 0320", "APMA 1650"));
    assertTrue(pp.getPath3Uniques().containsAll(u3) && u3.containsAll(pp.getPath3Uniques()));
    tearDown();
  }

  //@Test
  public void testSetOps() {
    /* Add tests for Sets.union, Sets.difference on Nodes */
  }
}

