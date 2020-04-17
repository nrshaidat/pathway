package edu.brown.cs.student.repl;

import edu.brown.cs.student.main.DatabaseInterface;
import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.primitives.Ints.toArray;

/**
 * PathwayCommand connects the database and the pathway class together and returns a pathway.
 */
public class PathwayCommand implements Command {
  private DatabaseInterface proxyDB;
  
  /**
   * PathwayCommand constructor.
   *
   * @param proxy the proxy db
   */
  public PathwayCommand(DatabaseInterface proxy) {
    proxyDB = proxy;
  }
  
  /**
   * Execute function.
   *
   * @param commandData the user input from the repl
   * @return an array of output
   */
  @Override
  public String[] execute(List<String> commandData) {
    String concentrationName = null;
    String concentrationRules = concentrationName + "_rules";
    int[] reqs = toArray(proxyDB.getRequirements(concentrationRules));
    //NEED TO PARSE INPUT ARGS AND BUILD THE PATHWAY
    return new String[0];
  }
  
}
