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
        System.out.println("Starting Test");
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
        assertTrue(response.isHTML());

        assertEquals("tables", 1, response.getTables().length);
        assertEquals("columns", 3,
                response.getTables()[0].getColumnCount());
        assertEquals("rows", 15,
                response.getTables()[0].getRowCount());
    }
}
