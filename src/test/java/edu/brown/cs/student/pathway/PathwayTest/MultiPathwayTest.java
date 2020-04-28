package edu.brown.cs.student.pathway.PathwayTest;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.PathwayMaker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class to test multithreading when creating pathways.
 */
public class MultiPathwayTest {
  private DatabaseCache cache;

  @Before
  public void setUp() throws SQLException {
    cache = new DatabaseCache(new Database("data/coursesDB.db"));
  }
  @After
  public void tearDown() {
    cache = null;
  }
  //@Test
  public void testCSPathways() throws SQLException {
    String tablename = "computerscienceba";
    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();

    PathwayMaker pm = new PathwayMaker(tablename, reqs, new HashSet<>(), 1);
    pm.makePathways();
  }
  //@Test
  public void testChemPathways() throws SQLException {
    String tablename = "chemistryba";
    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();

    PathwayMaker pm = new PathwayMaker(tablename, reqs, new HashSet<>(), 1);
    pm.makePathways();
  }
  /*@Test
  public void testCoursesTaken() throws SQLException {
    String tablename = "computerscienceba";
    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();
    Set<Node> coursesTaken = new HashSet<>();
    coursesTaken.add(cache.getCourseData("CSCI 0170"));
    coursesTaken.add(cache.getCourseData("MATH 0090"));

    PathwayMaker pm = new PathwayMaker(tablename, reqs, coursesTaken, 1);
    pm.makePathways();
  }*/
}
