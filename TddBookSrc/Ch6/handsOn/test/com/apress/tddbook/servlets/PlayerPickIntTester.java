/*
 *
 */
package com.apress.tddbook.servlets;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.DatabaseUnitException;
import com.apress.tddbook.servlets.JSPDispatcherServlet;
import com.apress.tddbook.FootballPool;
import com.apress.tddbook.PlayersPicks;
import com.apress.tddbook.PoolDatabase;
import com.apress.tddbook.PoolDatabaseImpl;
import com.meterware.httpunit.WebResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class PlayerPickIntTester extends ServletTestCase
{

    public PlayerPickIntTester(String s)
    {
        super(s);
    }

    //Set up the database with default values for the test
    public void setUp() throws ClassNotFoundException, SQLException, IOException, DatabaseUnitException
    {
        Class driverClass = Class.forName("org.hsqldb.jdbcDriver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");
        IDatabaseConnection dbConn = new DatabaseConnection(jdbcConnection);
        String dbFileDir = System.getProperty("DBDataFileDir");        
        IDataSet dataSet = new FlatXmlDataSet(new FileInputStream(dbFileDir + "\\dataset1.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
    }

    public void beginGetOpenPool(WebRequest webRequest)
    {
        webRequest.addParameter("username", "TimmyC");
    }

    public void testGetOpenPool() throws Exception
    {
        System.out.println("Starting Get Open Pool Test");
        PlayerPickServlet servlet = new PlayerPickServlet();
        //Set up the init params needed to initialize the database
        config.setInitParameter("dbLocation", "jdbc:hsqldb:hsql://localhost");
        config.setInitParameter("dbUser", "sa");
        config.setInitParameter("dbPass", "");
        servlet.init(config);
        servlet.doPost(request, response);
    }

    public void endGetOpenPool(WebResponse response) throws Exception
    {
        System.out.println("Ending Get Open Pool");
        assertTrue(response.isHTML());

        assertEquals("tables", 1, response.getTables().length);
        assertEquals("columns", 3,
                response.getTables()[0].getColumnCount());
        assertEquals("rows", 15,
                response.getTables()[0].getRowCount());
    }

    /**
     * Test to make sure that a player can make a set of picks correctly
     *
     */
    public void beginMakePlayerPicks(WebRequest webRequest)
    {
        webRequest.addParameter("username", "TimmyC");
        webRequest.addParameter("action", "makePicks");
        webRequest.addParameter("poolDate", "2003-09-17");
        webRequest.addParameter("game_1_pick", "Green Bay");
        webRequest.addParameter("game_2_pick", "Houston");
        webRequest.addParameter("game_3_pick", "Indianapolis");
        webRequest.addParameter("game_4_pick", "NY Giants");
        webRequest.addParameter("game_5_pick", "Chicago");
        webRequest.addParameter("game_6_pick", "Cleveland");
        webRequest.addParameter("game_7_pick", "Dallas");
        webRequest.addParameter("game_8_pick", "Washington");
        webRequest.addParameter("game_9_pick", "Jacksonville");
        webRequest.addParameter("game_10_pick", "Pittsburgh");
        webRequest.addParameter("game_11_pick", "Buffalo");
        webRequest.addParameter("game_12_pick", "Baltimore");
        webRequest.addParameter("game_13_pick", "San Francisco");
        webRequest.addParameter("game_14_pick", "Atlanta");
    }


    public void testMakePlayerPicks() throws Exception
    {
        System.out.println("Starting Test Make Players Picks");
        PlayerPickServlet servlet = new PlayerPickServlet();
        //Set up the init params needed to initialize the database
        config.setInitParameter("dbLocation", "jdbc:hsqldb:hsql://localhost");
        config.setInitParameter("dbUser", "sa");
        config.setInitParameter("dbPass", "");
        servlet.init(config);
        servlet.doPost(request, response);
        System.out.println("Ending Test");
    }

    public void endMakePlayerPicks(WebResponse response) throws Exception
    {
        System.out.println("Ending Make Players Picks");
        PoolDatabase poolDB = new PoolDatabaseImpl("jdbc:hsqldb:hsql://localhost", "sa", "");
        System.out.println("Got poolDB");
        PlayersPicks playerPicks = poolDB.getPlayersPicks("TimmyC", "2003-09-17");
        System.out.println("Got picks");
        assertEquals("Green Bay", playerPicks.getPickedTeam(0));
        assertEquals("Houston", playerPicks.getPickedTeam(1));
        assertEquals("Indianapolis", playerPicks.getPickedTeam(2));
        assertEquals("NY Giants", playerPicks.getPickedTeam(3));
        assertEquals("Chicago", playerPicks.getPickedTeam(4));
        assertEquals("Cleveland", playerPicks.getPickedTeam(5));
        assertEquals("Dallas", playerPicks.getPickedTeam(6));
        assertEquals("Washington", playerPicks.getPickedTeam(7));
        assertEquals("Jacksonville", playerPicks.getPickedTeam(8));
        assertEquals("Pittsburgh", playerPicks.getPickedTeam(9));
        assertEquals("Buffalo", playerPicks.getPickedTeam(10));
        assertEquals("Baltimore", playerPicks.getPickedTeam(11));
        assertEquals("San Francisco", playerPicks.getPickedTeam(12));
        assertEquals("Atlanta", playerPicks.getPickedTeam(13));
    }
}
