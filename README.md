# cs0320 Term Project 2020: Pathway

**Team Members:**
Members: Melissa Cui, Natalie Rshaidat, Ifechi Ilozor, Nicholas Keirstead 

## Known bugs:
None
## Design details:
- Main Package:
  -Database Design:
  -GUI Design
- REPL Package:
- Pathway Package   
- SQL Database Design:

## Runtime/Space optimizations:


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
	
## Checkstyle appeals:

None.

## Partner division of labor:

    Natalie:
        -Database and its unit tests
        -Integrating CAB scraping to main sql database
        -Manually double checking, fixing inaccurate prereqs, and entering missing prereqs for all courses in MATH, APMA, BIOL, CSCI.
        -Figured shibboleth authentication for all scraping
        -Cleaning CAB parsing data (removing nulls, extra commas, numbers), debugging cab scraping, and 
	figuring out CAB/self service scraping 

	Mel:
        -Critical review scraping
        -Login setup 
        -Concentration table in the database
        -Connecting spark to java 
        -REPL
        -Manually double checking, fixing inaccurate prereqs, and entering missing prereqs for all courses in ECON
    
	Ifechi:
        -Pathway package
        -Pathway algorithm
        -Optimizations in the algorithm 
        -Shuffling and weights addition to the courses
        -Junit testing for pathway
        
    Nick:
        -Courses at Brown / Self Service scraping
        -Manually fixing prereqs for various departments
        -Unit testing CAB database outputs
		

#### Member: Melissa Cui
##### Strengths
- Writing comments and System/JUnit tests
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

##### Weaknesses: 
- Takes longer to do non-stimulating tasks
- Unit Tests & Commenting 
- CheckStyle bugs 
- Did not take CS15


#### Member: Ifechi Ilozor
##### Strengths
- Modular Code
- Testing before Integrating 
- Front End 
- Math & Statistics 

##### Weaknesses: 
- Commenting code fully
- SQL statements 
- Package Structure 
- Workloads accross weeks can vary


#### Member: Nicholas Keirstead
##### Strengths
- Java & IntelliJ Features 
- Optimizing Code 
- Debugging 
- Writing and Communication with Team members

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

