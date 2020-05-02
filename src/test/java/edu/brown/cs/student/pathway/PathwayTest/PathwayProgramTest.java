package edu.brown.cs.student.pathway.PathwayTest;


import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.main.DatabaseInterface;
import edu.brown.cs.student.main.PathwayProgram;
import edu.brown.cs.student.pathway.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
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
   * Tests getavgavghrssem.
   *
   * @throws Exception the exception
   */
  //@Test
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

