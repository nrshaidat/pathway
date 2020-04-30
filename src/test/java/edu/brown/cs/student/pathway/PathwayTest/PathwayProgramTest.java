package edu.brown.cs.student.pathway.PathwayTest;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.main.DatabaseInterface;
import edu.brown.cs.student.main.PathwayProgram;
import org.junit.After;
import org.junit.Before;

import java.sql.SQLException;

/**
 * PathwayProgramTest.
 */
public class PathwayProgramTest {
  PathwayProgram pp;

  /**
   * Sets up the database connection.
   */
  @Before
  public void setUp() throws SQLException {
    pp =new PathwayProgram();
  }

  /**
   * Resets the the databases.
   */
  @After
  public void tearDown() {
    pp = null;
  }
}
