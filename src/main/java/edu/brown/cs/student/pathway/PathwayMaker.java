package edu.brown.cs.student.pathway;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.DatabaseCache;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

/**
 * The PathwayMaker creates three pathways for a user by using multi-threading.
 * It creates three PathwayThreads that represent each semester, then creates them using copies
 * of the data from the database, which then display three unique pathways on the front-end.
 */
public class PathwayMaker {
  private String concentrationName;
  private int[] reqs;
  private Set<Node> coursesTaken;
  private int risingSemester;
  private List<Semester> path1;
  private List<Semester> path2;
  private List<Semester> path3;

//  public List<Semester> getPath1() {
//    return path1;
//  }
//
//  public List<Semester> getPath2() {
//    return path2;
//  }
//
//  public List<Semester> getPath3() {
//    return path3;
//  }

  /**
   * The PathwayThread class is a thread-based solution to display pathways on the GUI.
   * When the user clicks the generate button on the /generate page, the PathwayThread class
   * creates three threads representing each of three pathways to display on the front end.
   * This is done by creating deep copies of the needed information from the databases, for example
   * the concentration courses requirements as well as the user's courses taken. Then, it generates
   * three unique pathways for the user to access.
   */
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
      System.out.println(coursesTakenClone);
      this.workload = workload;
      this.aggressive = new Random().nextBoolean();
      this.pathway = new Pathway(reqsClone, courseSetClone);
    }

    /**
     * The run method creates a pathway given the required parameters by calling the Pathway class.
     */
    @Override
    public void run() {
      pathway.makePathway(coursesTakenClone, risingSemester, aggressive, workload);
    }

//    /**
//     * Print Pathway is used to print out the User's pathway in the Back-end. (not sure if
//     * necessary for the GUI, commenting out for now)
//     */
//    public void printPathway() {
//      System.out.println(
//          "Concentration: " + concentrationName + ", Workload: " +
//          this.workload + ", aggressive: "
//              + this.aggressive);
//      System.out.println("---------");
//      List<Semester> path = pathway.getPath();
//      for (Semester list : path) {
//        System.out.println("Semester: " + list.getSemnumber());
//        for (Node course : list.getCourses()) {
//          System.out.println(course.getId() + ": " + course.getCategory());
//        }
//        System.out.println();
//      }
//    }

    /**
     * A method that deep copies a set of nodes by iterating through and copying each node.
     *
     * @param ct A set of nodes
     * @return A deep copy of ct
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

  /**
   * Pathway maker takes in four parameters to make pathways. This method is used to
   * make pathways to be displayed in the front end. It takes in the concentration name,
   * the requirements of a concentration, the user's semester level, and the courses the user
   * has received credit for. It then calls the makePathways method to create three pathways.
   *
   * @param concentrationName String representing the concentration name
   * @param reqs Requirements of a concentration
   * @param coursesTaken User-specified set of courses taken
   * @param risingSemester User-specified integer representing their rising Semester
   */
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
//      loPath.printPathway();
//      medPath.printPathway();
//      hiPath.printPathway();
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }
    path1 = loPath.pathway.getPath();
    path2 = medPath.pathway.getPath();
    path3 = hiPath.pathway.getPath();
  }
}
