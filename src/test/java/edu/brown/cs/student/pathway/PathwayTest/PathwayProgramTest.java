package edu.brown.cs.student.pathway.PathwayTest;


import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.main.DatabaseInterface;
import edu.brown.cs.student.main.PathwayProgram;
import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Semester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.util.ArrayList;
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
    pp = new PathwayProgram();
    cache = new DatabaseCache(new Database("data/coursesDB.db"));
  }

  /**
   * Sets up pathway program and static pathways for stats testing.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUpStats() throws Exception {
    pp = new PathwayProgram();
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
    Semester spring22 = new Semester(2, tak22);
    p2.add(spring22);
    //path 3
    //path 2
    List<Semester> p3 = new ArrayList<>();
    //fall 1
    List<Node> tak13 = new ArrayList<>();
    tak13.add(cache.getCourseData("MATH 0090"));
    tak13.add(cache.getCourseData("CSCI 0190"));
    Semester fall13 = new Semester(1, tak13);
    p3.add(fall13);
    //spring 2
    List<Node> tak23 = new ArrayList<>();
    tak23.add(cache.getCourseData("MATH 0100"));
    tak23.add(cache.getCourseData("CSCI 0180"));
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
  @Test
  public void getCourseList() throws Exception {
    setUp();
    List<String> cl = pp.getCourseList();
    List<String> cl2 = cache.getAllCourseIDs();
    assertEquals(cl2, cl);
    tearDown();
  }

  /**
   * Tests getavgavghrssem.
   *
   * @throws Exception the exception
   */
  @Test
  public void getAvgavghrssem() throws Exception {
    setUp();
    pp.getAvgavghrs1sem();
    pp.getAvgavghrs2sem();
    pp.getAvgavghrs3sem();
    tearDown();
  }

  /**
   * Tests getavgavghrspath.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getAvgavghrspath() throws Exception {
    setUp();
    pp.getAvgavghrs1path();
    pp.getAvgavghrs2path();
    pp.getAvgavghrs3path();
    tearDown();
  }


  /**
   * Tests gettotalnumcourses.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getTotalnumcourses() throws Exception {
    setUp();
    pp.getTotalnumcourses1();
    pp.getTotalnumcourses2();
    pp.getTotalnumcourses3();
    tearDown();
  }


  /**
   * Tests gettotalavg.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getTotalavg() throws Exception {
    setUp();
    pp.getTotalavg1();
    pp.getTotalavg2();
    pp.getTotalavg3();
    tearDown();
  }


  /**
   * Tests getNumsemesters.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getNumsemesters() throws Exception {
    setUp();
    pp.getNumsemesters1();
    pp.getNumsemesters2();
    pp.getNumsemesters3();
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
    pp.getPath1();
    pp.getPath2();
    pp.getPath3();
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
   * Tests getConcentrationName.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getConcentrationName() throws Exception {
    setUp();
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
    assertNull(pp.getConcentrationName());
    tearDown();
  }

  /**
   * Test clone.
   *
   * @throws Exception the exception
   */
  //@Test
  public void testClone() throws Exception {
    setUp();
    tearDown();
  }

  /**
   * Tests makePathways.
   *
   * @throws Exception the exception
   */
  //@Test
  public void makePathways() throws Exception {
    setUp();
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
    tearDown();
  }

  /**
   * Tests getConcentration.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getConcentration() throws Exception {
    setUp();
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
    tearDown();
  }

  /**
   * Tests setPathStats1.
   *
   * @throws Exception the exception
   */
  //@Test
  public void setPathStats1() throws Exception {
    setUp();
    tearDown();
  }

  /**
   * Tests setPathStats2.
   *
   * @throws Exception the exception
   */
  //@Test
  public void setPathStats2() throws Exception {
    setUp();
    tearDown();
  }

  /**
   * Tests setPathStats3.
   *
   * @throws Exception the exception
   */
  //@Test
  public void setPathStats3() throws Exception {
    setUp();
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
    tearDown();
  }

  /**
   * Tests getPathUniques.
   *
   * @throws Exception the exception
   */
  //@Test
  public void getPathUniques() throws Exception {
    setUp();
    pp.getPath1Uniques();
    pp.getPath2Uniques();
    pp.getPath3Uniques();
    tearDown();
  }
}

