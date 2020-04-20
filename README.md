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

- To run Maps via terminal:  
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
        -Fixing ALL of Nick's bugs in cab scraping
        -Manually double checking, fixing inaccurate prereqs, and entering missing prereqs for all courses in MATH, APMA, BIOL, CSCI.
        -Giving Nick a step by step guide to cab scraping 
        -Figured shibboleth authentication for all scraping
        -Cleaning up Nicks data (removing NULLs, extra commas etc)

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

**Project Idea(s):** 
### Idea 1
### Portr
Problem: When guests are at a hotel, they can't request things unless they are in their room next to a hotel cable phone. In order to optimize guest requests and use of employee's time, We will create an online web-app with a guest user frontend and a hotel employee backend, with a central algorithm to optimize employee-guest interactions. Given a data on guest location, staff information, employee experience and request importance, we will calculate the best way to handle a particular guest request and have it display on a real-time user interface so managers can allocate who does what. 



Features: 
- Algorithms: Prioritize guest requests by importance, location, and time to completion in a queue, then allocate particular requests to employees by weighting a given employee's experience, location, and specialization/skill set. We would need to store information about employees and guests by using a static employee database and dynamic caching (). 
  
- Front-End: Simple guest user interface for guests to make requests, details, request type and specify location for delivery/completion. A log-in portal would differentiate guests and managers that lets them see current requests and delegate requests to an employee. 

Challenges: 
- Security would be very important, because we need to hold both the guest data and employee information, which if leaked could cause a big problem. 
- Difficulties testing if the algorithms work and is optimizing requests correctly, disconnect between algorithmic accuracy and customer service needs
- Getting accurate location data between employees and guests
- Implementing multiple hotels; we need to make sure that our code is very generic and can implement different shift times, hotel sizes, employee numbers, specifications, etc. 
- Different languages (How do we parse information that is inputted in a different language?)

TA Approval: Approved - good idea and I think the queue algorithm will be interesting. I hope you can user test this somehow!


### Idea 2
### cloutalytics

Problem: Companies, social media managers, and advertising companies spend a lot of time figuring out what social media posts are most effective and gain traction among followers. We will create a data analytics platform based on social media sites like Instagram to provide insight on page engagements and recommend best types of content that pertain to their product and personal branding. 

Features: 
- Nicely visualized data analytics including measures like: likes, comments, shares, views, engagement, reach, and impressions. We could analyze best times and days to post given a audience, as well as doing basic sentiment analysis on a particular post, perhaps using a random sample of comments. 
- Recommendations for best content labels / hashtags to use, as well as locations to post from. Competitor analysis to compare competitor performance against yours. 

Challenges: 
- Handling the amount of data from a social media platform, because there are a lot of posts, likes, and user engagements that we would need to keep track of in a database. 
- Security issues could arise if a company does not want to volunteer data so easiily. 
- Recommendation would be difficult to implement because we would need to understand what posts are considered "good" and viable for a particular company in a sector. 

TA Approval: Approved - make sure to differentiate yourself from the tools already built into instagram, and algorithm could also focus on identifying competitors and successful tactics on their end.

### Idea 3
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

**Note:** You do not need to resubmit ideas.


**Mentor TA:** _Put your mentor TA's name and email here once you're assigned one!_

## Meetings
_On your first meeting with your mentor TA, you should plan dates for at least the following meetings:_

**Specs, Mockup, and Design Meeting:** _(Schedule for on or before March 13)_

**4-Way Checkpoint:** _(Schedule for on or before April 23)_

**Adversary Checkpoint:** _(Schedule for on or before April 29 once you are assigned an adversary TA)_

