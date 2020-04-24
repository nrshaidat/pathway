package edu.brown.cs.student.pathway.PathwayTest;

import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import edu.brown.cs.student.pathway.Semester;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.main.Database;


public class PathwayWithDatabaseTest {
  private DatabaseCache cache;

  @Before
  public void setUp() {
    cache = new DatabaseCache(new Database("data/coursesDB.db"));
  }

  public void pathwayPrinter(List<Semester> path) {
    System.out.println("----");
    for(Semester list : path) {
      System.out.println("Semester: " + list.getSemester());
      for (Node course : list.getCourses()) {
        System.out.println(course.getId() + ": " + course.getCategory());
        //System.out.println(course.getId() + ": " + course.getName());
      }
      System.out.println();
    }
  }

  @Test
  public void csConcentrationTest() {
    String tablename = "computerscienceba";
    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();

    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
    this.pathwayPrinter(pathwayMaker.getPath());
  }


  @Test
  public void computationalbiologyConcentrationTest() {
    String tablename = "computationalbiologyba";
    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();

    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
    this.pathwayPrinter(pathwayMaker.getPath());
  }

//  @Test
//  public void chemistryConcentrationTest() {
//    String tablename = "chemistryba";
//    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
//    int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();
//
//    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
//    Pathway pathwayMaker = new Pathway(reqs, courseSet);
//    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
//    this.pathwayPrinter(pathwayMaker.getPath());
//  }

  @Test
  public void cognitiveneuroscienceConcentrationTest() {
    String tablename = "cognitiveneuroscienceba";
    List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
    int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();

    Set<Node> courseSet = cache.getConcentrationCourses(tablename);
    Pathway pathwayMaker = new Pathway(reqs, courseSet);
    pathwayMaker.makePathway(new HashSet<Node>(), 1, false, "med");
    this.pathwayPrinter(pathwayMaker.getPath());
  }

}
