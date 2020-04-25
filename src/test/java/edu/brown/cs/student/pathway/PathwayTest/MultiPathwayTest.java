package edu.brown.cs.student.pathway.PathwayTest;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.PathwayMaker;
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
  @Test
  public void testThreePathways() throws SQLException {
    String tablename = "computerscienceba";
    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();
    Set<Node> courseSet = cache.getConcentrationCourses(tablename);

    PathwayMaker pm = new PathwayMaker(reqs, courseSet, new HashSet<>(), 1,
        false);
    pm.makePathways();
  }
}
