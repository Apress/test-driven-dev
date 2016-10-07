package com.apress.tddbook;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.meterware.httpunit.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * HttpUnit test to test that the web server login works properly
 */
public class HomePageTest extends TestCase {


    /**
     * default constructor - just calls super();
     */
    public HomePageTest(String s) {
        super(s);
    }

    public void setUp() throws Exception
    {
        //Load Database with test data
            Class driverClass = Class.forName("org.hsqldb.jdbcDriver");
            Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");
            IDatabaseConnection dbConn = new DatabaseConnection(jdbcConnection);
            String dbFileDir = System.getProperty("DBDataFileDir");
            IDataSet dataSet = new FlatXmlDataSet(new FileInputStream(dbFileDir + "\\datasetEJB1.xml"));
            DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
    }
    /**
     * Tests to check whether a browser can login to the Football Pool site and get the home page
     */
    public void testHomePage() throws Exception {
        WebConversation conversation = new WebConversation();
        conversation.setAuthorization("tom", "tom");
        WebRequest request = new GetMethodWebRequest("http://localhost:8080/FootBallPool/HomePageServlet?playersName=Tom&poolDate=2004-09-18");

        //Get response
        WebResponse response = conversation.getResponse(request);
        Document welcomeDoc = response.getDOM();
        NodeList h1NodeList = welcomeDoc.getElementsByTagName("h1");
        //Check to make sure Title contains Players name
        if (h1NodeList.getLength() > 0) {
            Node h1Node = h1NodeList.item(0);
            Node textNode = h1Node.getFirstChild();
            String h1NodeStr = textNode.getNodeValue();
            assertEquals("Welcome to the Football Pool Tom", h1NodeStr);
        } else {
            fail("Error getting home page Excepion");
        }
        //Check tables for the correct data
        //Get Game table
        WebTable gameTable = response.getTableStartingWith("This Weeks Games");
        if (gameTable != null) {
            //Check Row count
            int rowCount = gameTable.getRowCount();
            assertEquals(rowCount, 15);
            // Check each entry
            String gameStr = gameTable.getCellAsText(1, 0);
            assertEquals("Tampa Bay at Tennessee", gameStr);
            //Need to add more tests for rest of the games

        } else {
            fail("Error getting Game Table");
        }
        //Get Player Results table
        WebTable playerResultsTable = response.getTableStartingWith("Players Name");
        if (gameTable != null) {
            //Check Row count
            int rowCount = playerResultsTable.getRowCount();
            int columnCount = playerResultsTable.getColumnCount();
            assertEquals(rowCount, 7);
            assertEquals(columnCount, 2);
            // Check each entry
            String nameStr = playerResultsTable.getCellAsText(1, 0);
            assertEquals("Matt Carol", nameStr);
            String pickNum = playerResultsTable.getCellAsText(1, 1);
            assertEquals("12", pickNum);
            //Need to add more tests for rest the players

        } else {
            fail("Error getting Player Result Table");
        }
        //Get Players Pick table
        WebTable playersPickTable = response.getTableStartingWith("Week");
        if (gameTable != null) {
            //Check Row count
            int rowCount = playersPickTable.getRowCount();
            int columnCount = playersPickTable.getColumnCount();
            assertEquals(rowCount, 6);
            assertEquals(columnCount, 2);
            // Check each entry
            String weekStr = playersPickTable.getCellAsText(1, 0);
            assertEquals("Week1", weekStr);
            String pickNum = playersPickTable.getCellAsText(1, 1);
            assertEquals("9", pickNum);
            //Need to add more tests for rest of the week

        } else {
            fail("Error getting Players Pick Table");
        }
        //Check to make sure the correct links are shown based on the pool status
        String responseStr = response.getText();
        int index = responseStr.indexOf("Pool Status");
        if (index < 0) {
            fail("Unable to find Pool Status");
        }
        String poolStatus = responseStr.substring(index + 12, index + 16);
        System.out.println("Pool Status is " + poolStatus);

        //This link should always be present
        WebLink teamResultsLink = response.getLinkWith("View Team");
        if (teamResultsLink == null) {
            fail("View Past Team Results Link not present");
        }
        // These links may our may not be present based on the pool status
        WebLink makePickLink = response.getLinkWith("Make your Picks");
        WebLink pickSummaryLink = response.getLinkWith("View Pick Summary");
        if (poolStatus.equalsIgnoreCase("Open")) {
            //if pool is open then make pick link should be present but not the pick summary link
            assertEquals((pickSummaryLink == null), true);
            assertEquals((makePickLink != null), true);
        } else if (poolStatus.equalsIgnoreCase("Closed")) {
            //if pool is closed then make pick link should not be present but not the pick summary link should
            assertEquals((pickSummaryLink != null), true);
            assertEquals((makePickLink == null), true);
        }


    }

    public static Test suite() {
        TestSuite suite = new TestSuite(HomePageTest.class);
        return suite;
    }

    public static final void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
