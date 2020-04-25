package edu.brown.cs.student.main;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import edu.brown.cs.student.pathway.Node;
/* MIGHT DELETE THIS CLASS BECAUSE WE NEVER USE THE CACHE BECAUSE I HAVE TO ERROR CHECK
THAT A COURSE IS NOT NULL EACH TIME AND THE CACHE WILL JUST THROW ERRORS ON NULL'S. DUE TO
THE FACT THAT COURSES CAN BE NULL AND NULL BEING A SIGNAL THAT THE COURSE IS NOT OFFERED ANYMORE
A CACHE ISNT VERY HELPFUL.
 */

/**
 * DatabaseCache that has a course cache and calls on the real database that handles the sql
 * queries.
 */
public class DatabaseCache implements DatabaseInterface {
  private DatabaseInterface realDB;
  // course id to node with all its attributes set
  private static CacheLoader<String, Node> coursesLoader;
  private static LoadingCache<String, Node> cacheLoaderCourses;
  private static final int NUMENTRIES = 3000; // max cache size

  /**
   * DatabaseCache constructor.
   *
   * @param dB database instance
   */
  public DatabaseCache(DatabaseInterface dB) {
    if (dB.hasConnection()) {
      realDB = dB;
      this.init();
      this.loadALLCourses();
    } else {
      realDB = null;
    }
  }

  /**
   * init gets called once per run of the application and sets up the cache.
   */
  private void init() {
    coursesLoader = new CacheLoader<String, Node>() { // anonymous CacheLoader class
      @Override // but with an override...
      public Node load(String key) throws SQLException {
        return realDB.getCourseData(key);
      } // returns the value to cache for the key
    };
    CacheBuilder<Object, Object> objectObjectCacheBuilder = CacheBuilder.newBuilder();
    objectObjectCacheBuilder.maximumSize(NUMENTRIES);
    cacheLoaderCourses = objectObjectCacheBuilder.build(coursesLoader); // cap at 800 entries
  }

  /**
   * isEmpty checks if the database has data and returns a boolean, returning
   * true if it has data and false if it does not have data in its tables.
   *
   * @return boolean representing if table is empty of not
   */
  @Override
  public boolean isEmptyCourses() {
    return realDB.isEmptyCourses();
  }

  /**
   * getCourseData gets a reference to a Node object with all of its field filled except next
   * and category since that is concentration specific.
   *
   * @param courseID course id such as CSCI 0320
   * @return Node object with everything filled in except category and next
   */
  @Override
  public Node getCourseData(String courseID) {
    if (courseID == null) {
      return null;
    } else {
      return cacheLoaderCourses.getUnchecked(courseID);
    }
  }

  /**
   * getRequirements gets the requirements for the concentration.
   *
   * @param tableName the concentrationNameReqs table name to search for
   * @return an int array where the index is the category and the value at that index is the
   * number of courses needed to fulfill the requirement
   */
  @Override
  public List<Integer> getRequirements(String tableName) {
    if (tableName == null) {
      return null;
    } else {
      return realDB.getRequirements(tableName);
    }
  }

  /**
   * getConcentrationCourses gets the courses for the concentration in the sql database. It calls
   * on the getCourseData for each course id in the concentration.
   *
   * @param tableName the concentrationName table name to search for
   * @return a set of courses all populated with category and next populated
   */
  @Override
  public Set<Node> getConcentrationCourses(String tableName) throws SQLException {
    if (tableName == null) {
      return null;
    } else {
      Set<Node> courseSet = new HashSet<>();
      Set<Node> unfilledNodes = realDB.getConcentrationCourses(tableName);
      for (Node nody : unfilledNodes) {
        if (this.cacheLoaderCourses.getIfPresent(nody.getId()) == null) { // course is not offered
          // anymore so
          // don't add it
          continue;
        } else { // course is in our courses table and is offered
          Node cachetmp = this.getCourseData(nody.getId());
          Node tmp = new Node(cachetmp.getId());
          tmp.setMaxHrs(cachetmp.getMaxHrs());
          tmp.setClassSize(cachetmp.getClassSize());
          tmp.setAvgHrs(cachetmp.getAvgHrs());
          tmp.setRating(cachetmp.getRating());
          tmp.setSemesters(cachetmp.getSemestersOffered());
          tmp.setName(cachetmp.getName());
          tmp.setPrereqs(cachetmp.getPrereqs());
          tmp.setProfessor(cachetmp.getProfessor());
          if (nody.getNextID().length() > 0) {
            Node next = this.getCourseData(nody.getNextID());
            next.setCategory(nody.getCategory());
            tmp.setNext(next);
            courseSet.add(next);
          }
          tmp.setCategory(nody.getCategory());
          courseSet.add(tmp);
        }
      }
      return courseSet;
    }
  }

  /**
   * hasConnection checks if the database could connect.
   *
   * @return a boolean if the database was able to connect
   */
  @Override
  public boolean hasConnection() {
    return realDB.hasConnection();
  }

  /**
   * checkConcentration checks if the concentration and its rules are in the database and that the
   * number of categories lines up with both tables.
   *
   * @param concentrationName the name of the concentration in lower case and without spaces
   *                          with ba/bs on the end
   * @return a boolean if the database has the accurate concentration data from the db
   */
  @Override
  public boolean checkConcentration(String concentrationName) {
    return realDB.checkConcentration(concentrationName);
  }

  /**
   * checkCoursesTable checks if the courses table format is correct.
   *
   * @return a boolean if the database has the courses data from the db
   */
  @Override
  public boolean checkCoursesTable() {
    return realDB.checkCoursesTable();
  }

  @Override
  public List<String> getAllCourseIDs() {
    return realDB.getAllCourseIDs();
  }

  private void loadALLCourses() {
    List<String> courseIDS = this.getAllCourseIDs();
    for (String id : courseIDS) {
      this.getCourseData(id);
    }
  }
}
