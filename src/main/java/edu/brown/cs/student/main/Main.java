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
import java.util.Set;
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
  private static PathwayProgram pathwayProgram;
  private String[] args;
  private static final List<String> UNI = new ArrayList<>();
  private static String uniName;
  private static String uniNameShort;
  private static boolean firstLogin = true;
  private static boolean cornell = false;

  /**
   * Main is called when execution begins.
   *
   * @param args An array of command line arguments
   * @throws SQLException If encountering a SQL Exception
   */
  public static void main(String[] args) throws SQLException {
    new Main(args).run();
    UNI.add("Brown University");
    UNI.add("Cornell University");
  }

  private Main(String[] args) {
    this.args = args;
  }

  /**
   * Runs application on port. Launches gui if indicated.
   */
  private void run() throws SQLException {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);
    runSparkServer((int) options.valueOf("port"));

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

  /**
   * Sets up the Spark server, with the 5 unique pages we have right now.
   *
   * @param port Default port is 4567.
   */
  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());
    FreeMarkerEngine freeMarker = createEngine();
    // Setup Spark Routes
    Spark.get("/home", new HomeHandler(), freeMarker);
    Spark.post("/signin", new LoginHandler(), freeMarker);
    Spark.get("/login", new LoginHandler(), freeMarker);
    Spark.post("/generate", new GenerateHandler(), freeMarker);
    Spark.get("/faqs", new FaqHandler(), freeMarker);
    Spark.get("/signup", new SignUpHandler(), freeMarker);
    Spark.post("/mypath", new PathLandingHandler(), freeMarker);
    Spark.get("/mypath/:id", new PathwayHandler(), freeMarker);

  }

  /**
   * Home Handler lets the user choose the University they're attending. We currently support
   * Brown University and Cornell University.
   */
  private static class HomeHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("universityList", UNI);
      return new ModelAndView(variables, "home.ftl");
    }
  }

  /**
   * LoginHandler handles the main landing page, logging in at /login. On the main login
   * page, the user has the option to log in as a user which gives them their already
   * chosen pathway. They can also choose to create an account or login as a guest,
   * which directly takes them to the generate page.
   */
  private static class LoginHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {
      QueryParamsMap qm = req.queryMap();
      uniName = qm.value("university");
      if (uniName == null) {
        uniName = "Brown University";
        cornell = false;
      } else if (uniName.equals("Brown University")) {
        cornell = false;
      } else {
        cornell = true;
      }
      uniNameShort = uniName.split(" ")[0];
      pathwayProgram = new PathwayProgram(cornell);

      Map<String, Object> variables =
          ImmutableMap.of("uniName", uniName, "uniNameShort", uniNameShort);
      return new ModelAndView(variables, "main.ftl");
    }

  }

  /**
   * GenerateHandler handles the /generate page. The generate page is where the user adds
   * necessary information in order to run Pathway. The ftl file uses variables from the
   * database, concentrationList and courseList. These two variables are used for the dropdowns
   * so the user can select a concentration and courses they've received credit for. We
   * originally had the user input a number of hours for their workload, but decided on
   * generating three pathways with differing workloads. Thus, by removing some options
   * from the user choice, we are able to make our program more clear and intuitive.
   */
  private static class GenerateHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {
      List<String> gradeList = pathwayProgram.getGradeList();
      List<String> yearList = pathwayProgram.getYearList();
      Map<String, Object> variables = ImmutableMap
          .of("uniNameShort", uniNameShort, "concentrationList",
              pathwayProgram.getConcentrationsList(), "gradeList", gradeList, "yearList", yearList,
              "courseList", pathwayProgram.getCourseList());
      return new ModelAndView(variables, "generate.ftl");
    }

  }

  /**
   * PathLandingHandler handles the landing page for the display of the user's three pathways.
   * After successfully submitting the pathway information and generating a pathway, a user
   * is shown three pathways: a low workload, medium workload, and high workload pathway. The
   * user then can individually click on each pathway to modify the contents of each semester
   * by adding, swapping, or removing a course.
   */
  private static class PathLandingHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {
      QueryParamsMap qm = req.queryMap();
      String concentration; //gui name
      String gradeLev = qm.value("grade");
      String semesterLev = qm.value("year");
      if (gradeLev == null || semesterLev == null) {
        concentration = pathwayProgram.getConcentrationName();
        if (!pathwayProgram.isSet()) {
          //user defaults when signing in
          pathwayProgram.makePathways(concentration, new HashSet<>(), 1, false);
        }
      } else { //new user or guest user
        concentration = qm.value("concentration");
        int semester = PathwayProgram.parseGradeLevel(gradeLev, semesterLev);
        boolean aggressive = false;
        if (qm.value("aggressive") != null) {
          aggressive = true;
        }
        String coursestaken = qm.value("results");
        Set<Node> taken = pathwayProgram.parseTaken(coursestaken);
        pathwayProgram.makePathways(concentration, taken, semester, aggressive);
      }
      String display = "Pathways generated for the concentration: " + concentration;
      List<Semester> pathway1 = pathwayProgram.getPath1();
      List<Semester> pathway2 = pathwayProgram.getPath2();
      List<Semester> pathway3 = pathwayProgram.getPath3();
      List<String> uniques1 = pathwayProgram.getPath1Uniques();
      List<String> uniques2 = pathwayProgram.getPath2Uniques();
      List<String> uniques3 = pathwayProgram.getPath3Uniques();
      Map<String, Object> variables =
          ImmutableMap.<String, Object>builder().put("header", display).put("results1", pathway1)
              .put("results2", pathway2).put("results3", pathway3).put("stats", pathwayProgram)
              .put("uniques1", uniques1).put("uniques2", uniques2).put("uniques3", uniques3)
              .build();
      return new ModelAndView(variables, "pathway.ftl");
    }
  }

  /**
   * PathwayHandler handles the individual pathways generated. This is the page
   * that the user clicks after shown their three pathways on the landing page.
   * On this page, the user can modify their pathway by using one of three
   * functionality: the add, remove, or swap of courses. They can also see information
   * about each pathway like the number of semesters, courses, and average hours.
   */
  private static class PathwayHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {
      String pathNum = req.params(":id");
      int num = Integer.parseInt(pathNum);
      List<Semester> path;
      switch (num) {
        case 2:
          path = pathwayProgram.getPath2();
          break;
        case 3:
          path = pathwayProgram.getPath3();
          break;
        default:
          path = pathwayProgram.getPath1();
          break;
      }
      List<String> pathnumlst = new ArrayList<>();
      pathnumlst.add(pathNum);
      List<String> allCourses = pathwayProgram.getCourseList();
      List<String> addCoursesFall = new ArrayList<>();
      List<String> addCoursesSpring = new ArrayList<>();

      for (String id : allCourses) {
        Node course = pathwayProgram.getCourseData(id);
        if (course.getSemestersOffered().contains(1)) {
          addCoursesFall.add(id);
        }
        if (course.getSemestersOffered().contains(0)) {
          addCoursesSpring.add(id);
        }
      }

      List<String> pathCourses = new ArrayList<>();
      for (Semester s : path) {
        for (Node c : s.getCourses()) {
          pathCourses.add(c.getId());
        }
      }

      addCoursesFall.removeAll(pathCourses);
      addCoursesSpring.removeAll(pathCourses);

      Map<String, Object> variables =
          ImmutableMap.<String, Object>builder().put("id", pathnumlst).put("results", path)
              .put("courseList", allCourses).put("stats", pathwayProgram)
              .put("fallAdd", addCoursesFall).put("springAdd", addCoursesSpring).build();

      return new ModelAndView(variables, "mypath.ftl");

    }
  }

  /**
   * SignUp Handler handles the sign up page, where the user hasn't used the pathway program before.
   * After the user enters their name, username and password, they are directed to the generate page
   * similar to a user hwo signed in as a guest. Note that since we don't implement any database for
   * the user information, this is currently a dummy sign in, which means that we don't actually use
   * the information the user enters for anything.
   */
  private static class SignUpHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables =
          ImmutableMap.of("title", "Pathway Sign Up", "uniName", uniName);
      return new ModelAndView(variables, "signup.ftl");
    }
  }

  /**
   * FaqHandler handles the /faq page. The FAQ Page returns a Spark POST list of commonly
   * asked questions about Pathway. We obtained the questions from user research and demo-ing to
   * other people we knew.
   */
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
