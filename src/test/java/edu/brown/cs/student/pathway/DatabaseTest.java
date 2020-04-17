package edu.brown.cs.student.pathway;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import edu.brown.cs.student.main.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
  public void validCheckCoursesTableName() {
    setUp();
    assertTrue(realDB.checkCoursesTable());
    tearDown();
  }
  
  /**
   * Tests the checkCoursesColNames method returns true for the current db.
   */
  @Test
  public void validCoursesColName() {
    setUp();
    assertTrue(realDB.checkCoursesColNames());
    tearDown();
  }
  
  /**
   * Tests the checkDBFormat method returns true for the current db.
   */
  @Test
  public void validCheckDBFormat() {
    setUp();
    assertTrue(realDB.checkTableExists("courses"));
    tearDown();
  }
  
  /**
   * Tests the isEmpty method returns false for the current db.
   */
  @Test
  public void validisEmpty() {
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
  
  
}
