package edu.brown.cs.student.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.*;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Pathway;
import edu.brown.cs.student.pathway.Semester;
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
  private static Database db;

  /**
   * The initial method called when execution begins.
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
    db = new Database("data/coursesDB.db");
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
    Spark.post("/generate", new myPathHandler(), freeMarker);
    Spark.get("/faqs", new faqHandler(), freeMarker);
    Spark.get("/signup", new signUpHandler(), freeMarker);
    Spark.post("/mypath", new pathLandingHandler(), freeMarker);
    Spark.get("/mypath/:id", new pathwayHandler(), freeMarker);

  }

  private static class loginHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      //TODO: Implement some sort of uname/password check (put on hold to work on more important stuff)
//      QueryParamsMap qm = req.queryMap();
//      System.out.println("=========================================");
//      System.out.println(qm.hasKey("username"));
//      System.out.println(qm.hasKey("uname"));
//      System.out.println("=========================================");
      String status = "";

      Map<String, Object> variables = ImmutableMap.of("title", "Pathway", "loginStatus", status, "username", "");
      return new ModelAndView(variables, "main.ftl");
    }

  }

  private static class myPathHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {

      List<String> concentrationList = db.getConcentrations();

      Map<String, Object> variables = ImmutableMap.of("title", "Pathway",
              "results", "", "courseList", concentrationList);
      return new ModelAndView(variables, "generate.ftl");
    }

  }



  private static class pathLandingHandler implements TemplateViewRoute {

    public void pathwayPrinter(List<Semester> path) {
      for (Semester list : path) {
        System.out.println("Semester: " + list.getSemester());
        for (Node course : list.getCourses()) {
          System.out.println(course.getId() + ": " + course.getName());
        }
        System.out.println();
      }
    }

    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {

      QueryParamsMap qm = req.queryMap();
      String concentration = qm.value("concentration");
      //TODO: Have error checks if the user enters in the wrong type for any of the number fields!!!

      String concentration_id = db.getConcentrationID(qm.value("concentration"));
      String semester_level = qm.value("semester");
      Double workload = Double.parseDouble(qm.value("workload"));
      String workloadLevel = "";
      boolean aggressive = false;

      if (qm.value("aggressive") != null) {
        aggressive = true;
      }
      if (workload > 40) {
        workloadLevel = "hi";
      } else if (workload < 30) {
        workloadLevel = "lo";
      } else {
        workloadLevel = "med";
      }

      DatabaseCache cache = new DatabaseCache(new Database("data/coursesDB.db"));
      List<Integer> reqsTmp = cache.getRequirements(concentration_id + "_rules");
      int[] reqs = reqsTmp.stream().mapToInt(i -> i).toArray();
      Set<Node> courseSet = cache.getConcentrationCourses(concentration_id);
      Pathway pathwayMaker = new Pathway(reqs, courseSet);
      pathwayMaker.makePathway(new HashSet<Node>(), 1, aggressive, "med");
      System.out.println("Computational Biology Applied Math & Statistics Track B.S.");
      System.out.println("----");
      this.pathwayPrinter(pathwayMaker.getPath());

      String display = "Pathways generated for the concentration: " + concentration;

      Map<String, Object> variables = ImmutableMap.of("title", "Pathway",
              "content", display);
      return new ModelAndView(variables, "pathway.ftl");
    }
  }

  /**
   * List<Integer> reqsTmp = cache.getRequirements(tablename + "_rules");
   *     int[] reqs = reqsTmp.stream().mapToInt(i->i).toArray();
   *
   *     PathwayMaker pm = new PathwayMaker(tablename, reqs, new HashSet<>(), 1);
   *     pm.makePathways();
   */

  private static class pathwayHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      QueryParamsMap qm = req.queryMap();

      String pathNum = req.params(":id");

      System.out.println("====================-=");
      System.out.println(pathNum);
      System.out.println("====================-=");

      Map<String, Object> variables = ImmutableMap.of("title", "My Path", "id", pathNum);
      return new ModelAndView(variables, "mypath.ftl");

    }
  }

  private static class signUpHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {
      Map<String, Object> variables = ImmutableMap.of("title", "Pathway Sign Up",
              "results", "");
      return new ModelAndView(variables, "signup.ftl");

    }
  }

  private static class faqHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title", "Pathway FAQs", "results", "");
      return new ModelAndView(variables, "faqs.ftl");
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
