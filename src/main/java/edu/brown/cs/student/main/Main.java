package edu.brown.cs.student.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  /**
   * Runs application on port. Launches gui if indicated. Starts the REPL.
   */
  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    // Launch gui
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    // Process commands in a REPL

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n", templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());
    FreeMarkerEngine freeMarker = createEngine();
    // Setup Spark Routes

    Spark.get("/login", new loginHandler(), freeMarker);
    Spark.post("/mypath", new pathwayHandler(), freeMarker);

  }

  private static class loginHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title", "Pathway", "results", "");
      return new ModelAndView(variables, "main.ftl");
    }

  }

  private static class pathwayHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {

      List<String> courseList = new ArrayList<>();
      Database db = new Database("data/coursesDB.db");

      courseList = db.getConcentrations();

      Map<String, Object> variables = ImmutableMap.of("title", "Mel's Pathway", "results", "", "courseList", courseList);
      return new ModelAndView(variables, "pathway.ftl");
    }

  }

  /**
   * Displays an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}
