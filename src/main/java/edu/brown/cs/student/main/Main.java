package edu.brown.cs.student.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.student.pathway.Node;
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
  private static final int HIGH = 40;
  private static final int LOW = 30;
  private static PathwayProgram pathwayProgram;
  private static List<Semester> pathway1;
  private static List<Semester> pathway2;
  private static List<Semester> pathway3;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) throws SQLException {
    new Main(args).run();
  }

  private String[] args;


  private Main(String[] args) {
    this.args = args;
  }

  /**
   * Runs application on port. Launches gui if indicated. Starts the REPL.
   */
  private void run() throws SQLException {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    // Launch gui
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }
    pathwayProgram = new PathwayProgram();

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
    Spark.get("/login", new LoginHandler(), freeMarker);
    Spark.post("/generate", new MyPathHandler(), freeMarker);
    Spark.get("/faqs", new FaqHandler(), freeMarker);
    Spark.get("/signup", new SignUpHandler(), freeMarker);
    Spark.post("/mypath", new PathLandingHandler(), freeMarker);
    Spark.get("/mypath/:id", new PathwayHandler(), freeMarker);

  }

  private static class LoginHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String status = "";
      Map<String, Object> variables =
          ImmutableMap.of("title", "Pathway", "loginStatus", status, "username", "");
      return new ModelAndView(variables, "main.ftl");
    }

  }

  private static class MyPathHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {

      List<String> concentrationList = pathwayProgram.getConcentrationsList();

      Map<String, Object> variables =
          ImmutableMap.of("title", "Pathway", "results", "", "courseList", concentrationList);
      return new ModelAndView(variables, "generate.ftl");
    }

  }

  private static class PathLandingHandler implements TemplateViewRoute {

    public void pathwayPrinter(List<Semester> path) {
      for (Semester list : path) {
        System.out.println("Semester: " + list.getSemnumber());
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
      //Have error checks if the user enters in the wrong type for any of the number fields
      String concentrationId = pathwayProgram.getConcentrationMap().get(qm.value("concentration"));
      String semesterLevel = qm.value("semester");
      Double workload = Double.parseDouble(qm.value("workload"));
      String workloadLevel = "";
      boolean aggressive = false;
      if (qm.value("aggressive") != null) {
        aggressive = true;
      }

      if (workload > HIGH) {
        workloadLevel = "hi";
      } else if (workload < LOW) {
        workloadLevel = "lo";
      } else {
        workloadLevel = "med";
      }
      pathwayProgram.makePathways(concentrationId, new HashSet<Node>(), 1, aggressive);
      String display = "Pathways generated for the concentration: " + concentration;
      pathwayPrinter(pathwayProgram.getPath1());
      pathwayPrinter(pathwayProgram.getPath2());
      pathwayPrinter(pathwayProgram.getPath3());
      List<Object> concentrationList = new ArrayList<>();
      List<Object> titles = new ArrayList<>();
      titles.add("Pathway");
      concentrationList.add(display);
      pathway1 = pathwayProgram.getPath1();
      pathway2 = pathwayProgram.getPath2();
      pathway3 = pathwayProgram.getPath3();

      Map<String, Object> variables = ImmutableMap
              .of("content", concentrationList, "results1", "Pathway 1", "results2", "Pathway 2", "results3",
                      "Pathway 3");

//      Map<String, List<? extends Object>> variables = ImmutableMap
//          .of("content", concentrationList, "results1", result1, "results2", result2, "results3",
//              result3);
      return new ModelAndView(variables, "pathway.ftl");
    }
  }

  private static class PathwayHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String pathNum = req.params(":id");
      Integer num = Integer.parseInt(pathNum);
      List<Semester> path;
      switch (num) {
        case 1:
          path = pathway1;
//          path = pathwayProgram.getPath1();
          break;
        case 2:
          path = pathway2;
//          path = pathwayProgram.getPath2();
          break;
        case 3:
          path = pathway3;
//          path = pathwayProgram.getPath3();
          break;
        default:
          path = pathway1;
//          path = pathwayProgram.getPath1();
          break;
      }
      List<String> pathnumlst = new ArrayList<>();
      pathnumlst.add(pathNum);
      System.out.println("====================-=");
      System.out.println(pathNum);
      System.out.println("====================-=");
      Map<String, List<? extends Object>> variables =
          ImmutableMap.of("id", pathnumlst, "results", path);
      return new ModelAndView(variables, "mypath.ftl");

    }
  }

  private static class SignUpHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {
      Map<String, Object> variables = ImmutableMap.of("title", "Pathway Sign Up", "results", "");
      return new ModelAndView(variables, "signup.ftl");
    }
  }

  private static class FaqHandler implements TemplateViewRoute {
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
