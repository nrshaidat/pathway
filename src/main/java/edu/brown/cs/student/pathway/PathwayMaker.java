package edu.brown.cs.student.pathway;

import java.util.List;
import java.util.Set;

/**
 * A class to test multithreading in generating multiple pathways for a concentration request.
 */
public class PathwayMaker {
  private int[] reqs;
  private Set<Node> courseSet, coursesTaken;
  private int risingSemester;
  private boolean aggressive;
  private class PathwayThread extends Thread {
    private int[] reqsClone;
    private Set<Node> courseSetClone, coursesTakenClone;
    private String workload;
    private Pathway pathway;

    /* Right now, the problem is the threads conflict because they're modifying the same
    reqs, courseSet, and coursesTaken objects --> need to clone each one and pass clones to
    each thread --> need to override clone() for Node
     */
    PathwayThread(String workload) {
      this.reqsClone = reqs.clone();
      this.courseSetClone = courseSet; // need to deep clone somehow
      this.coursesTakenClone = coursesTaken; // need to deep clone
      this.workload = workload;
      this.pathway = new Pathway(reqsClone, courseSetClone);
    }
    @Override
    public void run() {
      pathway.makePathway(coursesTakenClone, risingSemester, aggressive, workload);
    }
    public void printPathway() {
      System.out.println("Workload: " + this.workload);
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
  }

  public PathwayMaker(int[] reqs, Set<Node> courseSet, Set<Node> coursesTaken, int risingSemester,
                      boolean aggressive) {
    this.reqs = reqs;
    this.courseSet = courseSet;
    this.coursesTaken = coursesTaken;
    this.risingSemester = risingSemester;
    this.aggressive = aggressive;
  }

  /**
   * A method to create three pathway threads, each of which makes its own pathway where one is
   * "lo", one is "med", and one is "hi".
   */
  public void makePathways() {
    PathwayThread loPath = new PathwayThread("lo");
    //PathwayThread medPath = new PathwayThread("med");
    //PathwayThread hiPath = new PathwayThread("hi");

    loPath.start();
    //medPath.start();
    //hiPath.start();
    try {
      loPath.join();
      //medPath.join();
      //hiPath.join();
      loPath.printPathway();
      //medPath.printPathway();
      //hiPath.printPathway();
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }
  }
}
