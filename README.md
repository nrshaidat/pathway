# cs0320 Term Project 2020: Pathway

**Team Members:**
Members: Melissa Cui, Natalie Rshaidat, Ifechi Ilozor, Nicholas Keirstead 

## Known bugs:
None
## Design details:
#### Main Package:  
 Database Design:  
 (Natalie) I used the proxy pattern to abstract away the interactions between the sql database and any class that wants data from it. It also allowed me to use the Guava cache to store all the courses in the courses table when the cache is first initialized. This not only optimized times needed to get data from sql database, but it also allowed for me to populate each course's data when I get the courses associated with a concentration and avoid having the result set opened to query the concentration table and the courses table. 
 
  * DatabaseInterface:  Contains all the methods that the Database class and DatabaseCache share. 
	
  * Database Class:  The class that connects to the sql database, error checks concentration tables since we manually inputted their values, and convert what is returned from sql queries into Nodes for the pathway class to use. 
	
  * DatabaseCache Class:  The class that has the cache of all courses and calls on the Database class to query the sql database. 
	
 -PathwayProgram class:  A class that contains all the information needed from the GUI and executes making pathways for the main class. It also serves as integrater class between the pathway class, main class, and database classes.   
 -GUI Design:  
 
#### Pathway Package:  
 Node class:  
 Semester class:  
 Pathway class:  
## Runtime/Space optimizations:
 Time optimizations were made by using the guava cache to load in all of the courses upon initialization of the databaseCache class. This optimization made queries runtime cut in half. Since there were only 1900 courses we felt it was not that immense of a burden on space compared to the runtime burden of each sql query. It takes 5 seconds on average to load in all of the courses which is done while the user is inputing their user preferences. 


## How to build and run our program:
- To build:  
	Use "mvn package" or "mvn site" (for more project details). Make sure to do the
steps in the "How to run our tests" section before building, so that JUnit tests
don't fail when building.    

- To run Pathway via terminal:  
	./run 

## How to run our tests:
	- To run unit tests: "mvn test", or using one of the build commands (mvn package)
## Tests we wrote:
	
## Data files:
	-coursesDB.db: Brown Courses Database
		-courses table: contains all courses from Fall 2020 and Spring 2020
			-course_id: Deptartment code +space+ course number  (CSCI 0150) from CAB
			-course_name: Name of the course (Intro to Object Oriented Programming) from CAB
			-prereqs: courseID's of the prereqs for this course where a ',' represents AND and '=' represents OR from CAB
			-semester_offered: 0 is spring, 1 is fall, and 2 is both semesters from CAB
			-professor: the course's professor from CAB
			-courseRating: the most recent course rating from critical review (0-5)
			-avg_hrs: the most recent record of average hours listed on critical review (hrs)
			-max_hrs: the most recent record of maximum hours listed on critical review (hrs)
			-CR_link: the critical review link for the course 
			-class_size: the most recent record of the class size from critical review
			-department: the 3-4 letter code of the department the course is in (CSCI 0150-> CSCI)
			-number: the course number listed after the department code in the course id (CSCI 0150-> 0150)
			-ss_link: the self service link for the course
		-concentrations table:
			-concentration_id: the table name associated with the concentration name 
			-concentration_name: the name of the concentration for displaying on the GUI
		-Each concentration has one table for its rules and one for the course options that satisfy credits for the concentration
			-Naming of concentration courses tables: concentration name in all lowercase with no spaces with ba or bs in the end to specify if the courses listed are for the bachelor of sciences or just bachelors degree.
			-Naming of concentration rules tables: same table name as teh concentrations course list but with '_rules' on the end
		-cognitiveneuroscienceba table: sample concentration courses table
			-course_id: the course id used in the courses table
			-next: course id of the next course in a sequence (CS15 would have CS16 as its next)
			-category: the category that the course is credit for
		-cognitiveneuroscienceba_rules table: sample concentration rules table
			-category: 0-20+ we organized concentration rules into categories where one has to take a specified number of courses from one category to satisfy a certain number of credits for the concentration
			-num_credits: the number of course one must take to satisfy the categories requierements 
	-cornellcoursesDB.db: Cornell Courses Database
		-courses table: contains all courses from Fall 2020 and Spring 2020
			-course_id: Deptartment code +space+ course number  (CSCI 0150) from ss_link
			-course_name: Name of the course (Intro to Object Oriented Programming) from cornell course ss_link
			-prereqs: courseID's of the prereqs for this course where a ',' represents AND and '=' represents OR from ss_link
			-semester_offered: 0 is spring, 1 is fall, and 2 is both semesters from ss_link
			-professor: the course's professor from ss_link
			-courseRating: the most recent course rating from cure reviews (0-5)
			-avg_hrs: the most recent record of average hours listed on cure reviews (hrs)
			-max_hrs: the most recent record of maximum hours listed on cure reviews (hrs)
			-CR_link: the https://www.cureviews.org link equivalent to brown's critical review
			-class_size: the most recent record of the class size from CR_link
			-department: the 3-4 letter code of the department the course is in (CSCI 0150-> CSCI)
			-number: the course number listed after the department code in the course id (CSCI 0150-> 0150)
			-ss_link: the https://classes.cornell.edu link for each course
		-concentrations table:
			-concentration_id: the table name associated with the concentration name 
			-concentration_name: the name of the concentration for displaying on the GUI
		-economicsba table: sample concentration courses table
			-course_id: the course id used in the courses table
			-next: course id of the next course in a sequence (CS15 would have CS16 as its next)
			-category: the category that the course is credit for
		-economicsba_rules table: sample concentration rules table
			-category: 0-20+ we organized concentration rules into categories where one has to take a specified number of courses from one category to satisfy a certain number of credits for the concentration
			-num_credits: the number of course one must take to satisfy the categories requierements 
	-coursesDBColErr.db:has wrong column names and number of columns for a multitude of tables for testing
	-coursesDBempty.db: has an empty courses table for testing
	-coursesDBInvalid.db: has wrong type fields in multiple tables for testing
## Checkstyle appeals:

None.

## Partner division of labor:

    Natalie:
        -Database and comprehensive unit tests
        -Integrating CAB scraping to main sql database
        -Manually double checking, fixing inaccurate prereqs, and entering missing prereqs 
         for all courses in MATH, APMA, BIOL, CSCI.
        -Figured shibboleth authentication for all scraping
        -Cleaning CAB parsing data (removing nulls, extra commas, numbers), debugging cab 
         scraping, and figuring out CAB / Self Service scraping 
        -Routing and various handling of user input
        -Styling and adding functionality to front end (navbar)
        -Extensive debugging of all parts (db, algo & frontend)
	    -Setting up login, login as guest, and sign up routing correctly
	    -Setting up cornell's extensibility 
	    -22/23 of the PathwayProgram junit tests
	    -Setting up PathwayProgram

	Mel:
        -Critical Review scraping
        -Manually double checking, fixing inaccurate prereqs, and entering missing prereqs for all courses in ECON
        -Concentrations table in the database
        -Connecting Spark to our Java code
        -Setting up the entire front end framework
        -Routing, alerting and comprehensive handling of user input
        -Styling and adding functionality to front end (login, faq, main form, 3-pathway display)
        -Transitioned styling to semantic ui to improve user experience 
	    -Setting up cornell db and integrating cornell into GUI
       
	Ifechi:
        -Developing and optimizing the fundamental makePathway algorithm
        -Layering complexity onto the algorithm: weighting courses based on different Bayesian stastical 
         measures, fast weighted shuffle using binary search, and tracking lag for pacing of pathways
        -Pathway package
        -JUnit testing for makePathway algorithm (by-hand and with the database, covering all scenarios)
        -Manually double checking, fixing inaccurate prereqs, and entering missing prereqs 
         for all courses in CHEM, PHYS, HIAA.
        -Solving deep copy of the course set to make multiple unique and valid pathways
        -mypath.ftl & mypath.css
        -Implementing and styling customization functionality on the front end (add, move, swap),
         mapping out and handling all edge cases 
        -Implementing and styling hover to show course details
        -Styling semester boxes, arrows, navigation bar, and pathway statistics (everything responsive) 
	    -Styling nav bar
        
    Nick:
        -CAB/self service scraping
        -Manually fixing prereqs for CHEM
        -Unit testing CAB database outputs
	    -Username/password fields on sign up page, guest button in the login page
	    -1/23 junit tests on PathwayProgram Tests
		

#### Member: Melissa Cui
##### Strengths
- Writing comments and system/JUnit tests
- Good logical structure when thinking about project design 
- Starts projects way ahead of time 
- Familiar with SQL, Pandas, Matplotlib
##### Weaknesses: 
- Memory and Time efficiency 
- Data structures 
- Optimizing Code 
- Not familiar with coding large projects 


#### Member: Natalie Rshaidat
##### Strengths
- UIUX, Taking Data Science 
- Profiling 
- Familiar with SQL, MATLAB
- Hard workers, starts projects early 
- Debugging 
##### Weaknesses: 
- Takes longer to do non-stimulating tasks
- Unit Tests & Commenting 
- CheckStyle bugs 
- Did not take CS15


#### Member: Ifechi Ilozor
##### Strengths
- Algorithm design & Testing
- Front End (functionality & styling)
- Math & Statistics 
- Debugging 
- Hard worker, good organizer
##### Weaknesses: 
- Commenting code fully
- Package Structure 
- Workloads across weeks can vary


#### Member: Nicholas Keirstead
##### Strengths
- Java & IntelliJ Features 
- Optimizing Code 

##### Weaknesses: 
- Time Management 
- Algorithms & Data Structures 
- Lack of experience with large group projects 
- Testing 

**Project Idea:** 

### Pathway 

Problem: Colleges have a lot of information about majors online, but it is very difficult to centralize that data without doing a lot of research. For Brown students specifically, we want to make a platform that allows students to input past classes they've taken, concentration interests, and workload preferences to help them better navigate concentration requirements and possible degrees. 

Features: 
- We will also take into account if a student wants a lighter workload, or is taking leave/study abroad for a particular semester and weight that semester with courses that require less time through scraping data from Critical Review. 
- Personal preferences will weight classes by class structure (exams, projects, or essays) as well as the department a class is in. If a student prefers a particular type of class or if they want to take classes outside of their concentration. 
- Teacher and class ratings from Critical Review, and merging a visual GUI with information from CAB including course name, course description, and professor. 
- Returns a set of possible N-Semester concentration plans, based on past courses taken and a set of requirements for a list of possible concentration, including WRIT requirements (& 30 credits). 

Challenges: 
- Scraping information from Critical Review and CAB to get data for class weights and display on the user interface 
- Not generic to other universities, only applicable for Brown Students (If we wanted to include RISD classes, we would also need to get that information as well)
- Logic on the algorithm: figuring out constraints on their preferences, requirements, workload, etc. Furthermore, there are a lot of constraints because of semesters a class is offered, time a course is offered, etc. 
- Edge cases: e.g. 0.5'ers, independent concentrators, GISPs, capstone projects, personal projects, research, recruiting scheduling, TA schedules, Sports. 

TA Approval: Approved with change - contingent on making this applicable to many universities, not just Brown

**Mentor TA:** _Julianne Rudner julianne_rudner@brown.edu_


## Design Details 


## Meetings
_On your first meeting with your mentor TA, you should plan dates for at least the following meetings:_

**Specs, Mockup, and Design Meeting:** _April 5_

**4-Way Checkpoint:** _April 20_

**Adversary Checkpoint:** _(Schedule for on or before April 29 once you are assigned an adversary TA)_

