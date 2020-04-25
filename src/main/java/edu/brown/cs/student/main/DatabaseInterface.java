package edu.brown.cs.student.main;

import edu.brown.cs.student.pathway.Node;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * DatabaseInterface that the sql database and the cache database implement.
 */
public interface DatabaseInterface {
  /**
   * isEmptyCourses checks if the database has data for the courses table and returns a boolean,
   * returning true if it has data and false if it does not have data in its tables.
   *
   * @return boolean representing if table is empty of not
   * @throws SQLException the sql exception
   */
  boolean isEmptyCourses() throws SQLException;

  /**
   * getCourseData gets a reference to a Node object with all of its field filled except next and
   * category since that is concentration specific.
   *
   * @param courseID course id such as CSCI 0320
   * @return Node object with everything filled in except category and next
   * @throws SQLException the sql exception
   */
  Node getCourseData(String courseID) throws SQLException;

  /**
   * getRequirements gets the requirements for the concentration.
   *
   * @param tableName the concentrationNameReqs table name to search for
   * @return an int array where the index is the category and the value at that index is the number
   * of courses needed to fulfill the requirement
   * @throws SQLException the sql exception
   */
  List<Integer> getRequirements(String tableName) throws SQLException;

  /**
   * getConcentrationCourses gets the courses for the concentration in the sql database. It calls on
   * the getCourseData for each course id in the concentration.
   *
   * @param tableName the concentrationName table name to search for
   * @return a set of courses all populated with category and next populated
   * @throws SQLException the sql exception
   */
  Set<Node> getConcentrationCourses(String tableName) throws SQLException;

  /**
   * hasConnection checks if the database could connect.
   *
   * @return a boolean if the database was able to connect
   */
  boolean hasConnection();

  /**
   * checkConcentration checks if the concentration and its rules are in the database and that the
   * number of categories lines up with both tables.
   *
   * @param concentrationName the name of the concentration in lower case and without spaces
   *                          with ba/bs on the end
   * @return a boolean if the database has the accurate concentration data from the db
   * @throws SQLException the sql exception
   */
  boolean checkConcentration(String concentrationName) throws SQLException;

  /**
   * checkCoursesTable checks if the courses table format is correct.
   *
   * @return a boolean if the database has the courses data from the db
   * @throws SQLException the sql exception
   */
  boolean checkCoursesTable() throws SQLException;

  /**
   * getAllCourseIDs gets all course ids from the courses table to load each course into the cache.
   *
   * @return the all course i ds
   * @throws SQLException the sql exception
   */
  List<String> getAllCourseIDs() throws SQLException;
}
