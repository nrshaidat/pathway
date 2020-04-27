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
  private List<Semester> path1;
  private List<Semester> path2;
  private List<Semester> path3;

  public List<Semester> getPath1() {
    return path1;
  }

  public List<Semester> getPath2() {
    return path2;
  }

  public List<Semester> getPath3() {
    return path3;
  }

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
      this.coursesTakenClone = copyCoursesTaken(coursesTaken);
      //System.out.println(coursesTaken);
      System.out.println(coursesTakenClone);
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
        System.out.println("Semester: " + list.getSemnumber());
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
    private Set<Node> copyCoursesTaken(Set<Node> ct) {
      Set<Node> cp = new HashSet<>();
      for (Node n: ct) {
        if (n == null) {
          System.out.println("here");
        }
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
      Node cp = new Node(n.getId());
      cp.setNext(copyNode(n.getNext()));
      cp.setName(n.getName());
      cp.setAvgHrs(n.getAvgHrs());
      cp.setCategory(n.getCategory());
      cp.setClassSize(n.getClassSize());
      cp.setMaxHrs(n.getMaxHrs());
      cp.setPrereqs(n.getPrereqs());
      cp.setProfessor(n.getProfessor());
      cp.setRating(n.getRating());
      cp.setSemesters(n.getSemestersOffered());
      return cp;
    }
  }

  public PathwayMaker(String concentrationName, int[] reqs, Set<Node> coursesTaken,
                      int risingSemester) {
    this.concentrationName = concentrationName;
    this.reqs = reqs;
    this.coursesTaken = coursesTaken;
    this.risingSemester = risingSemester;
    makePathways();
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
    path1 = loPath.pathway.getPath();
    path2 = medPath.pathway.getPath();
    path3 = hiPath.pathway.getPath();
  }
}
