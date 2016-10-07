/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook.ejb;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import com.apress.tddbook.HomePageTest;
import com.apress.tddbook.FootballPool;

import java.util.Vector;
import java.util.Hashtable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.FileInputStream;

import org.dbunit.DatabaseTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.DatabaseConnection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.CreateException;

public class FBPoolServerTest extends DatabaseTestCase
{


    public FBPoolServerTest(String s)
    {
        super(s);
    }

    protected IDatabaseConnection getConnection() throws Exception
    {
        Class driverClass = Class.forName("org.hsqldb.jdbcDriver");
        String dblocation = System.getProperty("DBLocation");
        //Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:1701", "sa", "");
        Connection jdbcConnection = DriverManager.getConnection(dblocation, "sa", "");
        return new DatabaseConnection(jdbcConnection);
    }

    protected IDataSet getDataSet() throws Exception
    {
        String dbFileDir = System.getProperty("DBDataFileDir");
        return new FlatXmlDataSet(new FileInputStream(dbFileDir + "\\datasetEJB1.xml"));
    }

    /**
     * Gets a handle to the EJB
     *
     * @return
     */
    private FBPoolServer getEJB() throws NamingException, RemoteException, CreateException, SQLException, ClassNotFoundException
    {
        String ejbTestType = System.getProperty("EJBTestType");
        if (ejbTestType.equals("Stub"))
        {
            FBPoolServer fbPoolServer = new FBPoolServerEJB(System.getProperty("DBLocation"));
            return(fbPoolServer);
        }
        else
        {
            FBPoolServerHome fbPoolHome = null;
            Hashtable env = new Hashtable();

            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            env.put(Context.PROVIDER_URL, "localhost:1099");
            env.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
            Context ctx = new InitialContext(env);
            System.out.println("Before lookup of FBPoolHome");
            fbPoolHome = (FBPoolServerHome) javax.rmi.PortableRemoteObject.narrow(ctx.lookup("FBPoolServerEJB"), FBPoolServerHome.class);
            System.out.println("Before create on FBPoolServerHome");
            return (fbPoolHome.create());
        }
    }

    /**
     * Test the ability to get the pool info for a specific pool
     */
    public void testGetPoolInfo() throws RemoteException, SQLException, ClassNotFoundException, NamingException, CreateException
    {
        FBPoolServer fbPoolServer = getEJB();
        FootballPool pool = fbPoolServer.getPoolInfo("2004-09-11");       
        assertEquals("Kansas City", pool.getAwayTeam(0));
        assertEquals("Green Bay", pool.getHomeTeam(0));
        assertEquals("Houston", pool.getAwayTeam(1));
        assertEquals("Tennessee", pool.getHomeTeam(1));
        assertEquals("Carolina", pool.getAwayTeam(2));
        assertEquals("Indianapolis", pool.getHomeTeam(2));
        assertEquals("NY Giants", pool.getAwayTeam(3));
        assertEquals("New England", pool.getHomeTeam(3));
        assertEquals("Chicago", pool.getAwayTeam(4));
        assertEquals("New Orleans", pool.getHomeTeam(4));
        assertEquals("Oakland", pool.getAwayTeam(5));
        assertEquals("Cleveland", pool.getHomeTeam(5));
        assertEquals("Philadelphia", pool.getAwayTeam(6));
        assertEquals("Dallas", pool.getHomeTeam(6));
        assertEquals("Tampa Bay", pool.getAwayTeam(7));
        assertEquals("Washington", pool.getHomeTeam(7));
        assertEquals("Miami", pool.getAwayTeam(8));
        assertEquals("Jacksonville", pool.getHomeTeam(8));
        assertEquals("Pittsburgh", pool.getAwayTeam(9));
        assertEquals("Denver", pool.getHomeTeam(9));
        assertEquals("Buffalo", pool.getAwayTeam(10));
        assertEquals("NY Jets", pool.getHomeTeam(10));
        assertEquals("Baltimore", pool.getAwayTeam(11));
        assertEquals("Arizona", pool.getHomeTeam(11));
        assertEquals("San Francisco", pool.getAwayTeam(12));
        assertEquals("Seattle", pool.getHomeTeam(12));
        assertEquals("Atlanta", pool.getAwayTeam(13));
        assertEquals("St. Louis", pool.getHomeTeam(13));

        pool = fbPoolServer.getPoolInfo("2004-09-18");
        assertEquals("Kansas City", pool.getAwayTeam(7));
        assertEquals("Green Bay", pool.getHomeTeam(1));
        assertEquals("Houston", pool.getAwayTeam(6));
        assertEquals("Tennessee", pool.getHomeTeam(0));
        assertEquals("Carolina", pool.getAwayTeam(5));
        assertEquals("Indianapolis", pool.getHomeTeam(3));
        assertEquals("NY Giants", pool.getAwayTeam(4));
        assertEquals("New England", pool.getHomeTeam(2));
        assertEquals("Chicago", pool.getAwayTeam(3));
        assertEquals("New Orleans", pool.getHomeTeam(5));
        assertEquals("Oakland", pool.getAwayTeam(2));
        assertEquals("Cleveland", pool.getHomeTeam(4));
        assertEquals("Philadelphia", pool.getAwayTeam(1));
        assertEquals("Dallas", pool.getHomeTeam(7));
        assertEquals("Tampa Bay", pool.getAwayTeam(0));
        assertEquals("Washington", pool.getHomeTeam(6));
        assertEquals("Miami", pool.getAwayTeam(13));
        assertEquals("Jacksonville", pool.getHomeTeam(9));
        assertEquals("Pittsburgh", pool.getAwayTeam(12));
        assertEquals("Denver", pool.getHomeTeam(8));
        assertEquals("Buffalo", pool.getAwayTeam(11));
        assertEquals("NY Jets", pool.getHomeTeam(11));
        assertEquals("Baltimore", pool.getAwayTeam(10));
        assertEquals("Arizona", pool.getHomeTeam(10));
        assertEquals("San Francisco", pool.getAwayTeam(9));
        assertEquals("Seattle", pool.getHomeTeam(13));
        assertEquals("Atlanta", pool.getAwayTeam(8));
        assertEquals("St. Louis", pool.getHomeTeam(12));

        pool = fbPoolServer.getPoolInfo("2004-09-25");
        assertEquals("Kansas City", pool.getAwayTeam(3));
        assertEquals("Green Bay", pool.getHomeTeam(13));
        assertEquals("Houston", pool.getAwayTeam(4));
        assertEquals("Tennessee", pool.getHomeTeam(12));
        assertEquals("Carolina", pool.getAwayTeam(5));
        assertEquals("Indianapolis", pool.getHomeTeam(11));
        assertEquals("NY Giants", pool.getAwayTeam(6));
        assertEquals("New England", pool.getHomeTeam(10));
        assertEquals("Chicago", pool.getAwayTeam(7));
        assertEquals("New Orleans", pool.getHomeTeam(9));
        assertEquals("Oakland", pool.getAwayTeam(8));
        assertEquals("Cleveland", pool.getHomeTeam(8));
        assertEquals("Philadelphia", pool.getAwayTeam(9));
        assertEquals("Dallas", pool.getHomeTeam(7));
        assertEquals("Tampa Bay", pool.getAwayTeam(10));
        assertEquals("Washington", pool.getHomeTeam(6));
        assertEquals("Miami", pool.getAwayTeam(11));
        assertEquals("Jacksonville", pool.getHomeTeam(5));
        assertEquals("Pittsburgh", pool.getAwayTeam(12));
        assertEquals("Denver", pool.getHomeTeam(4));
        assertEquals("Buffalo", pool.getAwayTeam(13));
        assertEquals("NY Jets", pool.getHomeTeam(3));
        assertEquals("Baltimore", pool.getAwayTeam(0));
        assertEquals("Arizona", pool.getHomeTeam(2));
        assertEquals("San Francisco", pool.getAwayTeam(1));
        assertEquals("Seattle", pool.getHomeTeam(1));
        assertEquals("Atlanta", pool.getAwayTeam(2));
        assertEquals("St. Louis", pool.getHomeTeam(0));
    }

    /**
     * Tests the ability to get a list of the available pools
     */
    public void testGetPoolList()
    {
        try
        {
            FBPoolServer fbPoolServer = getEJB();
            Vector poolList = fbPoolServer.getPoolList();
            assertEquals(poolList.size(), 3);
            String poolDate = (String) poolList.elementAt(0);
            assertEquals("2004-09-11", poolDate);
            poolDate = (String) poolList.elementAt(1);
            assertEquals("2004-09-18", poolDate);
            poolDate = (String) poolList.elementAt(2);
            assertEquals("2004-09-25", poolDate);
        }
        catch (Exception e)
        {
            System.out.println("Error getting getting Pool List.  Exception is " + e);
            e.printStackTrace();
            fail("Error getting getting Pool List.  Exception is " + e);
        }
    }

    public void testGetPoolStatus()
    {
        try
        {
            FBPoolServer fbPoolServer = getEJB();
            String poolStatus = fbPoolServer.getStatus("2004-09-11");
            assertEquals("Closed", poolStatus);
            poolStatus = fbPoolServer.getStatus("2004-09-18");
            assertEquals("Open", poolStatus);
            poolStatus = fbPoolServer.getStatus("2004-09-25");
            assertEquals("Created", poolStatus);
        }
        catch (Exception e)
        {
            System.out.println("Error getting getting Pool Status.  Exception is " + e);
            e.printStackTrace();
            fail("Error getting getting Pool Status.  Exception is " + e);
        }
    }

    public void testOpenPool()
    {
        try
        {
            FBPoolServer fbPoolServer = getEJB();

            String poolStatus = fbPoolServer.getStatus("2004-09-25");
            assertEquals("Created", poolStatus);
            fbPoolServer.openPool("2004-09-25");
            poolStatus = fbPoolServer.getStatus("2004-09-25");
            assertEquals("Open", poolStatus);
        }
        catch (Exception e)
        {
            System.out.println("Error opening Pool.  Exception is " + e);
            e.printStackTrace();
            fail("Error opening Pool.  Exception is " + e);
        }
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(FBPoolServerTest.class);
        return suite;
    }

    public static final void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }
}
