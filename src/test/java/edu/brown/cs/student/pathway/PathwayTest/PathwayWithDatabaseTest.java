package edu.brown.cs.student.pathway.PathwayTest;

import edu.brown.cs.student.main.DatabaseInterface;
import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import edu.brown.cs.student.pathway.Semester;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.main.Database;

import javax.xml.crypto.Data;

import static org.junit.Assert.assertTrue;


public class PathwayWithDatabaseTest {
  private DatabaseInterface cache;
  private Database realDB;

  @Before
  public void setUp() {
    realDB = new Database("data/coursesDB.db");
    cache = new DatabaseCache(realDB);
  }

  public void pathwayPrinter(List<Semester> path) {
    System.out.println("----");
    for(Semester list : path) {
      System.out.println("Semester: " + list.getSemester());
      for (Node course : list.getCourses()) {
        System.out.println(course.getId() + ": " + course.getName());
      }
      System.out.println();
    }
  }

  @Test
  public void csConcentrationTest() throws SQLException {
    String tablename = "computerscienceba";
    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();

    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
    this.pathwayPrinter(pathwayMaker.getPath());
  }


  @Test
  public void businesseconomicsConcentrationTest() throws SQLException {
    String tablename = "businesseconomicsba";
    assertTrue(cache.checkConcentration(tablename));
    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();

    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
    Set<Node> courseSet2 = realDB.getConcentrationCourses(tablename);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
    this.pathwayPrinter(pathwayMaker.getPath());
  }

}
