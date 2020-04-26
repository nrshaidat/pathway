package edu.brown.cs.student.repl;

import java.io.IOException;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * The REPL class. An instance of this class
 * process System.in and executes commands accordingly.
 */
public class REPL {

  private String regex;
  private Map<String, Command> manager;

  /**
   * Constructor.
   * @param commandManager map of strings to commands to execute
   * @param regEx regex to split System.in
   */
  public REPL(Map<String, Command> commandManager, String regEx) {
    manager = commandManager;
//    regex = regEx;
  }



  /**
   * Start the REPL loop.
   */
//  public void startLoop() {
//    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//    System.out.print("");
//    try {
//      String command = null;
//      // While not EOF
//      while ((command = reader.readLine()) != null) {
//        // Check if command is just whitespace
//        if (command.isEmpty() || command.equals("\r") || command.equals("\n")
//            || command.equals("\r\n")) {
//          continue;
//        }
//
//        List<String> commandArgs = this.doRegex(command);
//
//        // Exit command
//        if (commandArgs.get(0).equals("exit")) {
//          System.out.println("Exiting REPL...");
//          break;
//        }
//
//        // Show list of commands
//        if (commandArgs.get(0).equals("commands")) {
//          System.out.println("Available commands:");
//          for (String name : manager.keySet()) {
//            System.out.println(name);
//          }
//          continue;
//        }
//
//        // Execute if valid command, else ERROR:
//        if (manager.containsKey(commandArgs.get(0))) {
//          List<String> args = commandArgs.subList(1, commandArgs.size());
//          String[] out = manager.get(commandArgs.get(0)).execute(args);
//          for (String str: out) {
//            System.out.println(str);
//          }
//        } else {
//          System.out.println("ERROR: " + "Unsupported command. "
//              + "Run 'commands' to get list of commands.");
//        }
//        System.out.print("");
//      }
//      reader.close();
//    } catch (IOException e) {
//      System.out.println("ERROR: IOException thrown.");
//    }
//  }

  /**
   * Split string according to regex.
   * @param command String to split based on regex
   * @return list of split string
   */
  private List<String> doRegex(String command) {
    List<String> commandArgs = new ArrayList<String>();
    Pattern regexP = Pattern.compile(regex);
    Matcher regexMatcher = regexP.matcher(command);
    while (regexMatcher.find()) {
      commandArgs.add(regexMatcher.group());
    }
    return commandArgs;
  }

}

