----------------------------------------------------------------
Instructions for running the examples code and handsOn exercises
----------------------------------------------------------------

System Requirement
------------------
In order to compile and run the code, besides the files contained in this zip file,
you will also need to have installed Java JDK 1.4 or greater and Apache's Ant. You
also have to make sure you have the environmental variables JAVA_HOME and ANT_HOME 
defined.The JAVA_HOME directory should point to the directory where Java is installed
and ANT_HOME should point to the directory where Ant is installed. You must also
make sure that Ant is configured so that it can run the JUnit task.  See the
Ant documentation for details on how to configure Ant to run JUnit.

To run the code for Chapter 6 you will also need to have HSQL, JBoss and
Tomcat installed.The code for Chapter 6 should work with other databases and/or 
application servers so if you want to run it against other database or application
servers then you will have to change the build file accordingly


Code Organization
------------------
The code is organized by chapter.  There is a top-level directory for each chapter 
in the book in which source code is discussed. Underneath each chapter directory
are the bookSource directory, which contains all the test and source code for that 
chapter of the book, and the handsOn directory, which contains all the test and 
source code for all the hands-on exercises for that chapter of the book. Each of 
these directories has two subdirectories:test and src. The test directory contains 
all the test code, and the src directory contains all the actual source code.
The CH7 directory has only the bookSource subdirectory since this chapter did not
contain any handsOn exercises.

I've included two other top-level directories: DB_Scripts and dependencies. The
DB_Scripts directory contains some scripts and data files that can be used to set 
up and run the database for the Chapter 6 code. The dependencies directory is used 
to hold the dependent libraries needed to compile and run the examples. If you 
have downloaded the zip file that contains all the dependent libraries then the
dependencies directory should contain all the required jars.  If you have downloaded
the zip file that contains only the source code then you will have to populate
the dependencies directory will the required jars.  See the table in Appendix A
of the book for a list of all the required jar files.


Using the Tasks in the Build Files to Run the Code
--------------------------------------------------
There are build files for each chapter as well as a main build file which can 
be used to run some of the tasks for the other build files.

The main build file (build.xml) has the following main tasks:

   * dist - This task runs the dist task for each of the chapter build files.
   
   * dist_handsOn - This task runs the dist_handsOn task for each of the 
                     chapter build files
                     
   * clean - removes all the files created by the build.
   
   * unit-test-report - This task generate a report for all the tests run. This
                         task depends on the dist task so running this task
                         also runs all the tests for all the chapters. The report
                         is put into the testdata directory
                         
   * unit-test-report_handsOn - This task generate a report for all the tests run. 
                                 This task depends on the dist_handsOn task so running  
                                 this task also runs all the tests for all the chapters 
                                 for the handsOn exercises.The report is put into the 
                                 testdata directory.
   
The dist and dist_handsOn tasks do not run the tests for Chapter 6 since these tests 
require a running database and/or Web server and/or application server. The
tests for Chapter 6 can only be run using the task in the Chapter 6 build file 
(CH6-build.xml). The task for this build file are described in the Chapter 6 section.



Running the Chapter 3 Code
---------------------------

The example and handsOn code for chapter three can be run using the Chapter 3 
build file (Ch3-build.xml).  This file has the following main tasks:

   * dist - This task runs all the tests for this chapter.
   
   * dist_handsOn - This task runs all the tests for the handsOn exercises.
   
   * run-footballpool-tests - This task runs all the tests of the FootBallPoolTester.
   
   * run-playerpick-tests - This task runs all the tests of the PlayerPickTester.
   
   * run-postResults-tests - This task runs the tests of the PostResultsTester. 
                             This tester was develop as part of the handsOn exercise.
                             
                             
                             
Running the Chapter 4 Code
---------------------------

The example and handsOn code for chapter four can be run using the Chapter 4 
build file (Ch4-build.xml).  This file has the following main tasks:

   * dist - This task runs all the tests for this chapter.
   
   * dist_handsOn - This task runs all the tests for the handsOn exercises.
   
   * run-playerpick-jsp-tests - This task runs all the tests of the PlayerPickJSPTester.
   
   * run-playerpick-servlet-tests - This task runs all the tests of the 
                                     PlayerPickServletTester
                                     
   * run-postResults-tests - This task runs the tests of the PostResultsTester. 
                             This tester was develop as part of the handsOn exercise.   
                             
NOTE: On some systems when I run the ServletUnit tests that test JSPs I got failures 
because of some classpath issues between Ant and JUnit which I was not always able to resolve.  
Because of this I have provided a bat file which can be used in place of the Ch4-build.xml file
to run all the tests. The bat file takes one argument which is the name of the ant task that 
you want to run. To run the bat file you will have to edit it so it contains the correct
location for the jarDir, lib and toolsJar variables.


Running the Chapter 5 Code
---------------------------

The example and handsOn code for chapter five can be run using the Chapter 5 
build file (Ch5-build.xml).  This file has the following main tasks:

   * dist - This task runs all the tests for this chapter.
   
   * dist_handsOn - This task runs all the tests for the handsOn exercises.
   
   * run-jfcunit-tests - This task runs all the JFCUnit tests of the AdminGUITester.
   
   * run-jemmy-tests - This task runs all the Jemmy tests of the AdminGUIJemmyTester.
   
   * run-fbpoolserver-tests - This task runs all the tests of the FBPoolServerTest.
   
   * run-jfcunit-tests_handsOn - This task runs all the JFCUnit tests of the AdminGUITester
                                  this includes addition tests added as a result of 
                                  the handsOn exercises.
   
   

Running the Chapter 6 Code
---------------------------

The example and handsOn code for chapter 6 can be run using the Chapter 6 
build file (Ch6-build.xml).  

The tests in chapter 6 are different than the other tests because they are
integration tests and require a running database and/or Web server and/or 
application server. Therefore before each test is run you will have to 
make sure the environment is setup correctly.  

The first thing you need to do is to setup the database. The DB_Scripts
directory contains two bat files that can be used to run the HSQL
server and HSQL manager (HSQLDB_Server.bat, HSQLDB_Manager.bat).  To use 
these scripts you will have to edit them so that they have the correct 
location of the HSQL jar and Database directory.  Once this is done start
the HSQL server using the HSQLDB_Server.bat file. Then start the HSQL
manager using the HSQLDB_Manager.bat.  When the manager starts choose
"HSQL Database Engine Server" for type and for the other fields use the
default values.  Once the manager starts open and execute the create-db.sql 
script.  This will create all the tables needed to run the examples. 
If you don't use the default setting for the database location, username
and password you may have update the code to match your settings

The EJB and GUI tests assume that JBoss has the HSQL database configured in
client-server mode so before you run these tests you need to make sure that 
HSQL is properly configured on JBoss. You will also need to execute the
create-db.sql script to create the database tables.

Before running any of the tests you should update the values of the tomcat.dir,
jboss-deploy.dir, and jboss-clientjar.dir in the Ch6-build.xml file to make sure 
they match your environment. 

This Chapter 6 build file has the following main tasks:

   * dist - This task builds all the jar files EJBs, and war files needed 
            to run the tests but it does not actually run any tests.
            
   * dist_handsOn - This task builds all the jar files EJBs, and war files needed 
                     to run the tests for the handsOn exercises but it does not 
                     actually run any tests.
                     
   * deployToTomcat - Deploys the FootballPool.war file to the Tomcat directory.
   
   * deployToTomcat_handsOn - Deploys the FootballPool.war created from the handsOn
                               directory to the Tomcat directory.
                               
   * deployEjbToJBoss - Deploys the EJB to the JBoss deployment directory.
   
   * run-poolDB-tests - Runs the PoolDatabaseDBTester tests.  This requires that the 
                         HSQL database server be running. Used the HSQLDB_Server.bat
                         to start the HSQL database server
                         
   * run-poolDB-tests_handsOn - Runs the PoolDatabaseDBTester handsOn tests.  This requires 
                                 that the HSQL database server be running. Used the 
                                 HSQLDB_Server.bat to start the HSQL database server
                                 
   * run-ejbstub-tests - Runs the FBPoolServerTest tests locally against the EJB. 
                          This requires that the HSQL database server be running. 
                          Used the HSQLDB_Server.bat to start the HSQL database server.
                          
   * run-ejb-tests - Runs the FBPoolServerTest tests against the EJB on the real. 
                     application server This requires that ejbjar be deployed and
                     that the application server be up and running.
                     
   * runPlayerPickTests - This will run the PlayerPickIntTester Cactus tests. This 
                           test requires that the HSQL database server be running.
                           Used the HSQLDB_Server.bat to start the HSQL database 
                           server. If you want to have this test use a different
                           web or application server you will have to update the
                           containerset attribute of the task.
                           
   * runPlayerPickTests_handsOn - This will run the PlayerPickIntTester Cactus tests for
                                   the handsOn exercises This test requires that the 
                                   HSQL database server be running.Used the HSQLDB_Server.bat
                                   to start the HSQL database server. This test will
                                   build and deploy the FootballPool.war file to the
                                   tomcat.dir. If you want to have this test use a
                                   different web or application server you will have
                                   to update the he containerset attribute of the task.
          
                                   
   * run-homePageServlet-tests - This runs HomePageTest HttpUnit tests. Before this test is run
                                 you need to initialize the database to a known state. To do this
                                 start the HSQL database server then run the HSQL database manager
                                 Make sure you choose the "HSQL Database Engine Server" for type.
                                 After the manager starts select the Restore option on the Tools 
                                 menu and load the HttpTestData file. You can close the HSQL
                                 database manager but leave the HSQL database server running.
                                 This test also requires that you have deployed the war file
                                 to the Tomcat directory and that you have Tomcat running.
                                 
                                 
   * run-admin-gui-tests - This runs the AdminGuiTester using the real application server.
                            This requires that ejbjar be deployed and that the application 
                            server be up and running.


Errors Comments and Questions
-----------------------------
I have tried to make the example code error free and easy to run. If you find any problems 
with the it or have questions about how to run it please send me a message with as much
detail as possible about the problem so I can update the code or readme file to fix 
the problem.  I can be reached at thomashammell@apress.com.


