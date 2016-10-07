@echo off

set lib=C:\Java\Projects\TddBook\TddBookSrc\dependencies
set jarDir=C:\Java\Projects\TddBook\TddBookSrc\dist\lib
set toolsJar=C:\j2sdk1.4.1_03\lib\tools.jar

echo %1

:setClassPath
set cp=%CLASSPATH%

set cp=%cp%;%lib%\/nekohtml.jar
set cp=%cp%;%lib%\/jasper-compiler.jar
set cp=%cp%;%lib%\/xmlParserAPIs.jar
set cp=%cp%;%lib%\/junit.jar
set cp=%cp%;%lib%\/servlet.jar
set cp=%cp%;%lib%\/jasper-runtime.jar
set cp=%cp%;%lib%\/easymock.jar
set cp=%cp%;%lib%\/httpunit.jar
set cp=%cp%;%lib%\/xercesImpl.jar
set cp=%cp%;%toolsJar%
set cp=%cp%;%lib%\/js.jar
set cp=%cp%;%jarDir%\/Ch4.jar
set cp=%cp%;%jarDir%\/Ch4Test.jar

:setClassPath for handsOn
set cp_handsOn=%CLASSPATH%

set cp_handsOn=%cp_handsOn%;%lib%\/nekohtml.jar
set cp_handsOn=%cp_handsOn%;%lib%\/jasper-compiler.jar
set cp_handsOn=%cp_handsOn%;%lib%\/xmlParserAPIs.jar
set cp_handsOn=%cp_handsOn%;%lib%\/junit.jar
set cp_handsOn=%cp_handsOn%;%lib%\/servlet.jar
set cp_handsOn=%cp_handsOn%;%lib%\/jasper-runtime.jar
set cp_handsOn=%cp_handsOn%;%lib%\/easymock.jar
set cp_handsOn=%cp_handsOn%;%lib%\/httpunit.jar
set cp_handsOn=%cp_handsOn%;%lib%\/xercesImpl.jar
set cp_handsOn=%cp_handsOn%;%toolsJar%
set cp_handsOn=%cp_handsOn%;%lib%\/js.jar
set cp_handsOn=%cp_handsOn%;%jarDir%\/Ch4_handsOn.jar
set cp_handsOn=%cp_handsOn%;%jarDir%\/Ch4Test_handsOn.jar

if '%1%'=='dist_handsOn' GOTO run-all-tests_handsOn
if '%1%'=='dist' GOTO run-all-tests
if '%1%'=='run-all-tests_handsOn' GOTO run-all-tests_handsOn
if '%1%'=='run-all-tests' GOTO run-all-tests
if '%1%'=='run-playerpick-jsp-tests' GOTO run-playerpick-jsp-tests
if '%1%'=='run-playerpick-servlet-tests' GOTO run-playerpick-servlet-tests
if '%1%'=='run-postResults-servlet-tests' GOTO run-postResults-servlet-tests


:run-all-tests_handsOn
java -classpath %cp_handsOn% com.apress.tddbook.Ch4TestSuite
GOTO end

:run-all-tests
java -classpath %cp% com.apress.tddbook.Ch4TestSuite
GOTO end

:run-playerpick-servlet-tests
java -classpath %cp% com.apress.tddbook.servlets.PlayerPickServletTester
GOTO end

:run-playerpick-jsp-tests
java -classpath %cp% com.apress.tddbook.jsps.PlayerPickJSPTester
GOTO end

:run-postResults-servlet-tests
java -classpath %cp_handsOn% com.apress.tddbook.servlets.PlayersResultsServletTester
GOTO end






:end
pause
