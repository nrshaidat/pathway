package edu.brown.cs.student.repl;

import java.util.List;

/**
 * The Command Interface. We define Commands
 * in the CommandManager and the REPL calls execute.
 */
public interface Command {

  /**
   * Execute function.
   * @param commandData the user input from the repl
   * @return an array of output
   */
  String[] execute(List<String> commandData);

}
