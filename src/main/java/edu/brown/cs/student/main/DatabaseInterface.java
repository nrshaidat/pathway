package edu.brown.cs.student.main;

import edu.brown.cs.student.pathway.Node;

import java.util.List;
import java.util.Set;

/**
 * DatabaseInterface that the sql database and the cache database implement.
 */

public interface DatabaseInterface {
  /**
   * isEmpty checks if the database has data and returns a boolean representing true if it has
   * data and false if it does not have data in its tables.
   *
   * @return boolean representing if the way table is empty of not
   */
  boolean isEmpty();
  
  /**
   * getCourseData gets a reference to a course object with all of its field filled execpt next
   * and category since that concentration specfic.
   * @param courseID the course id such as CSCI0320
   * @return course object instance with everything filled in execpt category and next
   */
  Node getCourseData(String courseID);
  
  /**
   * getRequirements gets the requirements for the concentration.
   *
   * @param tableName the concentrationNameReqs table name to search for
   * @return an int array where the index is the category and the value at that index is the
   * number of courses needed to fulfill the requirement
   */
  List<Integer> getRequirements(String tableName);
  
  /**
   * getConcentrationCourses gets the courses for the concentration in the sql database it calls
   * on the getCourseData for each course in the concentration.
   *
   * @param tableName the concentrationName table name to search for
   * @return a set of courses all populated with category and next populated
   */
  Set<Node> getConcentrationCourses(String tableName);
  
  /**
   * hasConnection checks if the database could connect.
   * @return a boolean if the database was able to connect
   */
  boolean hasConnection();
  
}
