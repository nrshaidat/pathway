package edu.brown.cs.student.pathway.PathwayTest;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.main.DatabaseInterface;
import edu.brown.cs.student.main.PathwayProgram;
import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Semester;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * PathwayProgramTest.
 * @author nrshaida (Natalie Rshaidat)
 */
public class PathwayProgramTest {
  /**
   * The pathway program instance.
   * @author nrshaida (Natalie Rshaidat)
   */
  static PathwayProgram pp;
  static DatabaseInterface cache;
  static DatabaseInterface cachec;

  @BeforeClass
  public static void setUpClass() throws SQLException {
    //executed only once, before the first test
    cache = new DatabaseCache(new Database("data/coursesDB.db"));
    cachec = new DatabaseCache(new Database("data/cornellcoursesDB.db"));
    pp = new PathwayProgram("brown");
  }
  /**
   * Sets up pathway program for testing.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Before
  public void setUp() throws Exception {
    pp = new PathwayProgram("brown");
  }

  /**
   * Sets up pathway program for testing for cornell db.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Before
  public void setUpCornell() throws Exception {
    pp = new PathwayProgram("cornell");
  }

  /**
   * Sets up pathway program and static pathways for stats testing.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Before
  public void setUpStats() throws Exception {
    //pp = new PathwayProgram("brown");
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
   *
   * @throws Exception An exception
   * @author nkirstead (Nick)
   */
  @Before
  public void setUpStats2() throws Exception {
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
   * @author nrshaida (Natalie Rshaidat)
   */
  @Before
  public void setUpStats3() throws Exception {
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
   * @author nrshaida (Natalie Rshaidat)
   */
  @After
  public void tearDown() {
    pp = null;
  }

  /**
   * Tests all the defaults are set on first run.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void initialValsNull() throws Exception {
    setUp();
    assertNull(pp.getPath1());
    assertNull(pp.getPath2());
    assertNull(pp.getPath3());
    assertNull(pp.getPath1Uniques());
    assertNull(pp.getPath2Uniques());
    assertNull(pp.getPath3Uniques());
    assertEquals("Computer Science B.A.", pp.getConcentrationName());
    assertFalse(pp.isSet());
    assertEquals(0, pp.getTotalnumcourses1());
    assertEquals(0, pp.getTotalnumcourses2());
    assertEquals(0, pp.getTotalnumcourses3());
    assertEquals(0, pp.getNumsemesters1());
    assertEquals(0, pp.getNumsemesters2());
    assertEquals(0, pp.getNumsemesters3());
    assertEquals(0, pp.getAvgavghrs1path());
    assertEquals(0, pp.getAvgavghrs2path());
    assertEquals(0, pp.getAvgavghrs3path());
    assertEquals("computerscienceba", pp.getConcentration());
    pp.setPathUniques();
    assertEquals(0, pp.getPath1Uniques().size());
    assertEquals(0, pp.getPath2Uniques().size());
    assertEquals(0, pp.getPath3Uniques().size());
  }

  /**
   * Tests course list.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getCourseList() throws Exception {
    List<String> cl = pp.getCourseList();
    List<String> cl2 = cache.getAllCourseIDs();
    assertEquals(cl2, cl);
  }

  /**
   * Tests getavgavghrspath.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getAvgavghrspath() throws Exception {
    setUpStats();
    assertEquals(7, pp.getAvgavghrs1path());
    assertEquals(7, pp.getAvgavghrs2path());
    assertEquals(7, pp.getAvgavghrs3path());
  }

  /**
   * Tests gettotalnumcourses for all paths.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getTotalnumcourses() throws Exception {
    setUpStats();
    assertEquals(4, pp.getTotalnumcourses1());
    assertEquals(5, pp.getTotalnumcourses2());
    assertEquals(3, pp.getTotalnumcourses3());
  }


  /**
   * Tests getNumsemesters for all paths.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getNumsemesters() throws Exception {
    setUpStats();
    assertEquals(2, pp.getNumsemesters1());
    assertEquals(2, pp.getNumsemesters2());
    assertEquals(3, pp.getNumsemesters3());
  }


  /**
   * Tests getPath for all paths.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getPath() throws Exception {
    //test setting new
    pp.makePathways("Computer Science B.A.", new HashSet<>(), 1, false);
    assertNotNull(pp.getPath1());
    assertNotNull(pp.getPath2());
    assertNotNull(pp.getPath3());
    assertNotNull(pp.getPath1Uniques());
    assertNotNull(pp.getPath2Uniques());
    assertNotNull(pp.getPath3Uniques());
  }


  /**
   * Tests getConcentrationMap.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getConcentrationMap() throws Exception {
    Map<String, String> mapy = pp.getConcentrationMap();
    Map<String, String> mapycache = cache.getConcentrationsMap();
    assertEquals(mapycache, mapy);
  }


  /**
   * Tests setConcentration, getConcentration, and getCourseSet since setConcentration sets the
   * last two.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void setConcentration() throws Exception {
    pp.setConcentration("cognitiveneuroscienceba");
    assertEquals("cognitiveneuroscienceba", pp.getConcentration());
    Set<Node> courses = cache.getConcentrationCourses("cognitiveneuroscienceba");
    assertEquals(courses, pp.getCourseSet());
  }


  /**
   * Tests  valid input for setConcentrationName.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void validsetConcentrationName() throws Exception {
    pp.setConcentrationName("Cognitive Neuroscience B.A.");
    assertEquals("Cognitive Neuroscience B.A.", pp.getConcentrationName());
  }

  /**
   * Tests invalid input for setConcentrationName.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void invalidsetConcentrationName() throws Exception {
    pp.setConcentrationName("Cognitive Neuroscience B.T.");
    assertEquals("Computer Science B.A.", pp.getConcentrationName());
  }


  /**
   * Tests isSet.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void isSet() throws Exception {
    pp.makePathways("Computer Science B.A.", new HashSet<>(), 1, false);
    assertTrue(pp.isSet());
  }


  /**
   * Tests getConcentrationsList.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getConcentrationsList() throws Exception {
    assertEquals(new ArrayList<>(cache.getConcentrationsMap().keySet()),
        pp.getConcentrationsList());
  }

  /**
   * Tests setPathStats for all pathways.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void setPathStats() throws Exception {
    pp.makePathways("Computer Science B.A.", new HashSet<>(), 1, false);
    assertTrue(pp.getTotalnumcourses1() > 0);
    assertTrue(pp.getTotalnumcourses2() > 0);
    assertTrue(pp.getTotalnumcourses3() > 0);
    assertTrue(pp.getNumsemesters1() > 0);
    assertTrue(pp.getNumsemesters2() > 0);
    assertTrue(pp.getNumsemesters3() > 0);
    assertTrue(pp.getAvgavghrs1path() > 0);
    assertTrue(pp.getAvgavghrs2path() > 0);
    assertTrue(pp.getAvgavghrs3path() > 0);
  }


  /**
   * Tests less than or equal to 3 unique courses case getPathUniques.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getPathUniques() throws Exception {
    setUpStats();
    pp.setPathUniques();
    List<Node> path1u = new ArrayList<>();
    path1u.add(cache.getCourseData("CSCI 0150"));
    path1u.add(cache.getCourseData("CSCI 0160"));
    assertTrue(pp.getPath1Uniques().containsAll(path1u));
    assertEquals(2, pp.getPath1Uniques().size());
    List<Node> path2u = new ArrayList<>();
    path2u.add(cache.getCourseData("CSCI 0170"));
    path2u.add(cache.getCourseData("CSCI 0220"));
    path2u.add(cache.getCourseData("CSCI 0180"));
    assertTrue(pp.getPath2Uniques().containsAll(path2u));
    assertEquals(3, pp.getPath2Uniques().size());
    List<Node> path3u = new ArrayList<>();
    path3u.add(cache.getCourseData("CSCI 0190"));
    path3u.add(cache.getCourseData("CSCI 1450"));
    assertTrue(pp.getPath3Uniques().containsAll(path3u));
    assertEquals(2, pp.getPath3Uniques().size());
  }

  /**
   * Tests no 3 unique courses case getPathUniques.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getPathNoUniques() throws Exception {
    setUpStats3();
    pp.setPathUniques();
    assertEquals(0, pp.getPath1Uniques().size());
    assertEquals(0, pp.getPath2Uniques().size());
    assertEquals(0, pp.getPath3Uniques().size());
  }

  /**
   * Tests getPathUniques on more than 3 unique courses .
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat) && nkirstead (Nick)
   */
  @Test
  public void getPathUniques2() throws Exception {
    setUpStats2();
    pp.setPathUniques();
    /* Path 1 should actually have 4 uniques (cs 15, cs 15, math 180, and cs 1230), but
    getPath1Uniques should cut it off at 3.
     */
    List<Node> path1u = new ArrayList<>();
    path1u.add(cache.getCourseData("CSCI 0150"));
    path1u.add(cache.getCourseData("CSCI 0160"));
    path1u.add(cache.getCourseData("MATH 0180"));
    path1u.add(cache.getCourseData("CSCI 1230"));
    assertTrue(path1u.containsAll(pp.getPath1Uniques()));
    assertEquals(3, pp.getPath1Uniques().size());
    List<Node> path2u = new ArrayList<>();
    path2u.add(cache.getCourseData("CSCI 0170"));
    path2u.add(cache.getCourseData("CSCI 0220"));
    path2u.add(cache.getCourseData("CSCI 0180"));
    assertTrue(pp.getPath2Uniques().containsAll(path2u));
    assertEquals(3, pp.getPath2Uniques().size());
    List<Node> path3u = new ArrayList<>();
    path3u.add(cache.getCourseData("CSCI 0190"));
    path3u.add(cache.getCourseData("CSCI 0320"));
    path3u.add(cache.getCourseData("APMA 1650"));
    assertTrue(pp.getPath3Uniques().containsAll(path3u));
    assertEquals(3, pp.getPath3Uniques().size());
  }

  /**
   * Tests defaults are correctly set with cornell university.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void differentUni() throws Exception {
    setUpCornell();
    assertEquals(1, pp.getConcentrationMap().size());
    assertEquals(1, pp.getConcentrationsList().size());
    assertEquals("Economics B.A.", pp.getConcentrationName());
    assertEquals("economicsba", pp.getConcentration());
    assertEquals(cachec.getAllCourseIDs(), pp.getCourseList());
    assertNull(pp.getPath1());
    assertNull(pp.getPath2());
    assertNull(pp.getPath3());
    pp.makePathways("Economics B.A.", new HashSet<>(), 1, false);
    assertEquals(cachec.getConcentrationCourses("economicsba"), pp.getCourseSet());
    assertNotNull(pp.getPath1());
    assertNotNull(pp.getPath2());
    assertNotNull(pp.getPath3());
    tearDown();
  }

  /**
   * Tests parseTaken method.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void parseTaken() throws Exception {
    String con = "Computer Science B.A.";
    Set<Node> taken = new HashSet<Node>();
    assertEquals(taken, pp.parseTaken("", con));
    assertEquals(2, pp.parseTaken("CSCI 0150,MATH 0100", con).size());
    assertEquals(1, pp.parseTaken("CSCI 0150,CLPS 0010", con).size());
  }

  /**
   * Tests parseGradeLevel method.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void parseGradeLevel() throws Exception {
    assertEquals(1, pp.parseGradeLevel("Freshman", "Fall"));
    assertEquals(2, pp.parseGradeLevel("Freshman", "Spring"));
    assertEquals(3, pp.parseGradeLevel("Sophomore", "Fall"));
    assertEquals(4, pp.parseGradeLevel("Sophomore", "Spring"));
    assertEquals(5, pp.parseGradeLevel("Junior", "Fall"));
    assertEquals(6, pp.parseGradeLevel("Junior", "Spring"));
    assertEquals(7, pp.parseGradeLevel("Senior", "Fall"));
    assertEquals(8, pp.parseGradeLevel("Senior", "Spring"));
  }

  /**
   * Tests getGradeList and getYearList method.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getGradeYearList() throws Exception {
    List<String> ll = new ArrayList<>();
    ll.add("Freshman");
    ll.add("Sophomore");
    ll.add("Junior");
    ll.add("Senior");
    assertTrue(pp.getGradeList().containsAll(ll));
    List<String> l = new ArrayList<>();
    l.add("Fall");
    l.add("Spring");
    assertTrue(pp.getYearList().containsAll(l));
  }

  /**
   * Tests getCourseData method.
   *
   * @throws Exception the exception
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void getCourseData() throws Exception {
    Node cs16 = cache.getCourseData("CSCI 0160");
    assertEquals(cs16, pp.getCourseData("CSCI 0160"));
  }
}

