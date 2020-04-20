package edu.brown.cs.student.pathway.ScrapingTest;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.pathway.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Critical Review Scraping
 */
public class CriticalReviewScrapingTest {
  private Database db;

  @Before
  public void setUp(){
    String file = "data/coursesDB.db";
    db = new Database(file);
  }

  @After
  public void tearDown() {
    db = null;
  }


  /**
   * Uses getCourseData() to test created Nodes against the actual Critical Review output.
   * Focuses on results from scraping Critical Review, for valid courses with filled values for
   * all four parameters (Avg Hours, Max Hours, Course Rating, Class Size).
   */
  @Test
  public void testValidCourses() {
    Node apma350 = db.getCourseData("APMA 0350");
    assertTrue(apma350.getAvgHrs() == 4.66);
    assertTrue(apma350.getMaxHrs() == 7.84);
    assertTrue(apma350.getRating() == 4.44);
    assertTrue(apma350.getClassSize() == 133);


  }


  /**
   * Tests that courses that are marked "N/A" in Critical Review for Missing Hours. Some courses
   * in Critical Review don't have enough respondents, so they can't get an average or maximum
   * number of hours. Testing this makes sure that Nodes are created correctly regardless if
   * there is missing hour data or not. We use a set of default values if CR doesn't contain data.
   */
  @Test
  public void testMissingAvgMaxHours() {
    this.setUp();

    double defaultAvgHrs = 8;
    double defaultMaxHrs = 14;
    double defaultRating = 3.5;
    double defaultClassSize = 40;

    Node biol420 = db.getCourseData("BIOL 0420");
    assertTrue(biol420.getAvgHrs() == defaultAvgHrs);
    assertTrue(biol420.getMaxHrs() == defaultMaxHrs);
    assertTrue(biol420.getRating() == 4.28);
    assertTrue(biol420.getClassSize() == 4.24);


    Node afri1210 = db.getCourseData("AFRI 1210");
    assertTrue(afri1210.getAvgHrs() == defaultAvgHrs);
    assertTrue(afri1210.getMaxHrs() == defaultMaxHrs);
    assertTrue(afri1210.getRating() == 4.52);
    assertTrue(afri1210.getClassSize() == 4.6);


    Node egyt1340 = db.getCourseData("EGYT 1340");
    assertTrue(egyt1340.getAvgHrs() == defaultAvgHrs);
    assertTrue(egyt1340.getMaxHrs() == defaultMaxHrs);
    assertTrue(egyt1340.getRating() == 4.52);
    assertTrue(egyt1340.getClassSize() == 4.6);

    this.tearDown();

  }


  /**
   * Tests the courses that CR has no data for. Some courses don't use Critical Review, so we
   * account for that by using defaults.
   */
  @Test
  public void testNotCR() {

    double defaultAvgHrs = 8;
    double defaultMaxHrs = 14;
    double defaultRating = 3.5;
    double defaultClassSize = 40;



  }




}
