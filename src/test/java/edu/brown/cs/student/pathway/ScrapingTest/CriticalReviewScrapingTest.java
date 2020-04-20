package edu.brown.cs.student.pathway.ScrapingTest;

import edu.brown.cs.student.main.Database;
import org.junit.After;
import org.junit.Before;

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
}
