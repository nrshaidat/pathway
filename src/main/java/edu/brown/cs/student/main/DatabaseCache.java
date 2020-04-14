package edu.brown.cs.student.main;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.List;
import java.util.Set;

/**
 * DatabaseCache that has a course cache and calls on the real database that handles the sql
 * queries.
 */
public class DatabaseCache implements DatabaseInterface {
  private DatabaseInterface realDB;
  private static CacheLoader<String, Course> coursesLoader; //course id to course object instance
  // with all its attributes fully populated
  private static LoadingCache<String, Course> cacheLoaderCourses;
  private static final int NUMENTRIES = 800; //max cache size
  /**
   * DatabaseCache constructor.
   */
  public DatabaseCache(DatabaseInterface dB) {
    realDB = dB;
    if (dB.hasConnection()) {
      realDB = dB;
      this.init();
    } else {
      realDB = null;
    }
  }
  
  /**
   * init gets called once per run of the application and sets up the cache.
   */
  private void init() {
    coursesLoader = new CacheLoader<String, Course>() { // anonymous class: a CacheLoader
      @Override // but with an override...
      public Course load(String key) {
        return realDB.getCourseData(key);
      } // returns the value to cache for the key
    };
    CacheBuilder<Object, Object> objectObjectCacheBuilder = CacheBuilder.newBuilder();
    objectObjectCacheBuilder.maximumSize(NUMENTRIES);
    cacheLoaderCourses = objectObjectCacheBuilder.build(coursesLoader); // cap at
    // 800
    // entries
  }
  /**
   * isEmpty checks if the database has data and returns a boolean representing true if it has data
   * and false if it does not have data in its tables.
   *
   * @return boolean representing if the way table is empty of not
   */
  @Override
  public boolean isEmpty() {
    return realDB.isEmpty();
  }
  
  /**
   * getCourseData gets a reference to a course object with all of its field filled execpt next and
   * category since that concentration specfic.
   *
   * @param courseID the course id such as CSCI0320
   * @return course object instance with everything filled in execpt category and next
   */
  @Override
  public Course getCourseData(String courseID) {
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
   * @return an int array where the index is the category and the value at that index is the number
   *     of courses needed to fulfill the requirement
   */
  @Override
  public List<Integer> getRequirements(String tableName) {
    if(tableName == null){
      return null;
    } else {
      return realDB.getRequirements(tableName);
    }
  }
  
  /**
   * getConcentrationCourses gets the courses for the concentration in the sql database it calls on
   * the getCourseData for each course in the concentration.
   *
   * @param tableName the concentrationName table name to search for
   * @return a set of courses all populated with category and next populated
   */
  @Override
  public Set<Course> getConcentrationCourses(String tableName) {
    if (tableName == null) {
      return null;
    } else {
      return realDB.getConcentrationCourses(tableName);
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
}
