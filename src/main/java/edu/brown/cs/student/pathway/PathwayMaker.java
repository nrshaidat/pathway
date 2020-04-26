package edu.brown.cs.student.pathway;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.DatabaseCache;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

/**
 * A class to test multithreading in generating multiple pathways for a concentration request.
 */
public class PathwayMaker {
  private String concentrationName;
  private int[] reqs;
  private Set<Node> coursesTaken;
  private int risingSemester;

  private class PathwayThread extends Thread {
    private DatabaseCache dbc;
    private int[] reqsClone;
    private Set<Node> courseSetClone, coursesTakenClone;
    private String workload;
    private Pathway pathway;
    private boolean aggressive;

    PathwayThread(String workload) throws SQLException {
      this.dbc = new DatabaseCache(new Database("data/coursesDB.db"));
      this.reqsClone = reqs.clone();
      this.courseSetClone = dbc.getConcentrationCourses(concentrationName);
      this.coursesTakenClone = copySet(coursesTaken);
      this.workload = workload;
      this.aggressive = new Random().nextBoolean();
      this.pathway = new Pathway(reqsClone, courseSetClone);
    }

    @Override
    public void run() {
      pathway.makePathway(coursesTakenClone, risingSemester, aggressive, workload);
    }

    public void printPathway() {
      System.out.println(
          "Concentration: " + concentrationName + ", Workload: " + this.workload + ", aggressive: "
              + this.aggressive);
      System.out.println("---------");
      List<Semester> path = pathway.getPath();
      for (Semester list : path) {
        System.out.println("Semester: " + list.getSemester());
        for (Node course : list.getCourses()) {
          System.out.println(course.getId() + ": " + course.getCategory());
          //System.out.println(course.getId() + ": " + course.getName());
        }
        System.out.println();
      }
    }

    /**
     * A method that deep copies a set of nodes by iterating through and copying each node.
     *
     * @param s A set of nodes
     * @return A deep copy of s
     */
    private Set<Node> copySet(Set<Node> s) {
      Set<Node> cp = new HashSet<>();
      for (Node n : s) {
        cp.add(copyNode(n));
      }
      return cp;
    }

    /**
     * A method that deep copies a course node by retrieving its course id / data again from db.
     *
     * @param n A node
     * @return A deep copy of n
     */
    private Node copyNode(Node n) {
      return dbc.getCourseData(n.getId());
    }
  }

  public PathwayMaker(String concentrationName, int[] reqs, Set<Node> coursesTaken,
                      int risingSemester) {
    this.concentrationName = concentrationName;
    this.reqs = reqs;
    this.coursesTaken = coursesTaken;
    this.risingSemester = risingSemester;
  }

  /**
   * A method to create three pathway threads, each of which makes its own pathway where one is
   * "lo", one is "med", and one is "hi".
   */
  public void makePathways() {
    PathwayThread loPath, medPath, hiPath;
    loPath = null;
    medPath = null;
    hiPath = null;
    try {
      loPath = new PathwayThread("lo");
      medPath = new PathwayThread("med");
      hiPath = new PathwayThread("hi");
    } catch (SQLException sqe) {
      sqe.printStackTrace();
    }

    loPath.start();
    medPath.start();
    hiPath.start();
    try {
      loPath.join();
      medPath.join();
      hiPath.join();
      loPath.printPathway();
      medPath.printPathway();
      hiPath.printPathway();
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }
  }
}
