package edu.brown.cs.student.pathway.ScrapingTest;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.pathway.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Critical Review Scraping. We consider all edge cases for valid classes, classes with
 * missing information in CR, as well as classes that aren't listed in Critical Review. To handle the
 * last two cases, we use default average hour, max hour, class size, and class rating values.
 */
public class CriticalReviewScrapingTest {
  private Database db;

  /**
   * Instantiates the database and connects it to the correct DB.
   */
  @Before
  public void setUp(){
    String file = "data/coursesDB.db";
    db = new Database(file);
  }

  /**
   * Tears down by setting the database to null.
   */
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

    this.setUp();
    //Tests valid courses with all four params filled
    Node apma350 = db.getCourseData("APMA 0350");
    assertTrue(apma350.getAvgHrs() == 4.66);
    assertTrue(apma350.getMaxHrs() == 7.84);
    assertTrue(apma350.getRating() == 4.44);
    assertTrue(apma350.getClassSize() == 133);

    Node krea0100 = db.getCourseData("KREA 0100");
    assertTrue(krea0100.getAvgHrs() == 4.00);
    assertTrue(krea0100.getMaxHrs() == 6.67);
    assertTrue(krea0100.getRating() == 4.77);
    assertTrue(krea0100.getClassSize() == 14);


    //TODO: Fix Stack OverFlow Error?? VISA 1520 not working
//    Node visa1520 = db.getCourseData("VISA 1520");
//    assertTrue(visa1520.getAvgHrs() == 4.75);
//    assertTrue(visa1520.getMaxHrs() == 8.40);
//    assertTrue(visa1520.getRating() == 4.7);
//    assertTrue(visa1520.getClassSize() == 17);

    Node biol1290 = db.getCourseData("BIOL 1290");
    assertTrue(biol1290.getAvgHrs() == 5.54);
    assertTrue(biol1290.getMaxHrs() == 12.45);
    assertTrue(biol1290.getRating() == 4.61);
    assertTrue(biol1290.getClassSize() == 31);
//
    Node educ0860 = db.getCourseData("EDUC 0860");
    assertTrue(educ0860.getAvgHrs() == 2.46);
    assertTrue(educ0860.getMaxHrs() == 5.4);
    assertTrue(educ0860.getRating() == 4.08);
    assertTrue(educ0860.getClassSize() == 152);

    this.tearDown();

  }

  /**
   * Tests courses with fields with "N/A" in CR. Testing this makes sure that Nodes are created
   * correctly regardless if there is missing hour data or not. We use a set of default values if
   * CR doesn't contain data.
   */
  @Test
  public void testMissingData() {
    this.setUp();

    double defaultAvgHrs = 8;
    double defaultMaxHrs = 14;
    double defaultRating = 3.5;

    Node biol420 = db.getCourseData("BIOL 0420");
    assertTrue(biol420.getAvgHrs() == defaultAvgHrs);
    assertTrue(biol420.getMaxHrs() == defaultMaxHrs);
    assertTrue(biol420.getRating() == 4.28);
    assertTrue(biol420.getClassSize() == 91);

    Node afri1210 = db.getCourseData("AFRI 1210");
    assertTrue(afri1210.getAvgHrs() == defaultAvgHrs);
    assertTrue(afri1210.getMaxHrs() == defaultMaxHrs);
    assertTrue(afri1210.getRating() == 4.52);
    assertTrue(afri1210.getClassSize() == 6);

    Node egyt1340 = db.getCourseData("EGYT 1340");
    assertTrue(egyt1340.getAvgHrs() == defaultAvgHrs);
    assertTrue(egyt1340.getMaxHrs() == defaultMaxHrs);
    assertTrue(egyt1340.getRating() == 4.99);
    assertTrue(egyt1340.getClassSize() == 6);

    Node pols1390 = db.getCourseData("POLS 1390");
    assertTrue(pols1390.getAvgHrs() == defaultAvgHrs);
    assertTrue(pols1390.getMaxHrs() == defaultMaxHrs);
    assertTrue(pols1390.getRating() == 3.76);
    assertTrue(pols1390.getClassSize() == 7);

    Node biol1540 = db.getCourseData("BIOL 1540");
    assertTrue(biol1540.getAvgHrs() == defaultAvgHrs);
    assertTrue(biol1540.getMaxHrs() == defaultMaxHrs);
    assertTrue(biol1540.getRating() == 4.61);
    assertTrue(biol1540.getClassSize() == 19);

    //Classes that are missing avg Hours, max Hours, and rating
    Node mcm0150 = db.getCourseData("MCM 0150");
    assertTrue(mcm0150.getAvgHrs() == defaultAvgHrs);
    assertTrue(mcm0150.getMaxHrs() == defaultMaxHrs);
    assertTrue(mcm0150.getRating() == defaultRating);
    assertTrue(mcm0150.getClassSize() == 50);

    Node math1970 = db.getCourseData("MATH 1970");
    assertTrue(math1970.getAvgHrs() == defaultAvgHrs);
    assertTrue(math1970.getMaxHrs() == defaultMaxHrs);
    assertTrue(math1970.getRating() == defaultRating);
    assertTrue(math1970.getClassSize() == 4);

    this.tearDown();

  }

  /**
   * Tests the courses that CR has no data for. Some courses don't use Critical Review, so we
   * account for that by using default values, which are listed below.
   */
  @Test
  public void testNotCR() {

    this.setUp();

    double defaultAvgHrs = 8;
    double defaultMaxHrs = 14;
    double defaultRating = 3.5;
    double defaultClassSize = 40;

    //Classes that have empty values in the database
    Node litr0999 = db.getCourseData("LITR 0999");
    assertTrue(litr0999.getAvgHrs() == defaultAvgHrs);
    assertTrue(litr0999.getMaxHrs() == defaultMaxHrs);
    assertTrue(litr0999.getRating() == defaultRating);
    assertTrue(litr0999.getClassSize() == defaultClassSize);

    Node fren1970 = db.getCourseData("FREN 1970");
    assertTrue(fren1970.getAvgHrs() == defaultAvgHrs);
    assertTrue(fren1970.getMaxHrs() == defaultMaxHrs);
    assertTrue(fren1970.getRating() == defaultRating);
    assertTrue(fren1970.getClassSize() == defaultClassSize);

    Node pols0010 = db.getCourseData("POLS 0010");
    assertTrue(pols0010.getAvgHrs() == defaultAvgHrs);
    assertTrue(pols0010.getMaxHrs() == defaultMaxHrs);
    assertTrue(pols0010.getRating() == defaultRating);
    assertTrue(pols0010.getClassSize() == defaultClassSize);

    Node engl1993 = db.getCourseData("ENGL 1993");
    assertTrue(engl1993.getAvgHrs() == defaultAvgHrs);
    assertTrue(engl1993.getMaxHrs() == defaultMaxHrs);
    assertTrue(engl1993.getRating() == defaultRating);
    assertTrue(engl1993.getClassSize() == defaultClassSize);

    Node pols1100 = db.getCourseData("POLS 1100");
    assertTrue(pols1100.getAvgHrs() == defaultAvgHrs);
    assertTrue(pols1100.getMaxHrs() == defaultMaxHrs);
    assertTrue(pols1100.getRating() == defaultRating);
    assertTrue(pols1100.getClassSize() == defaultClassSize);

    this.tearDown();

  }

}
