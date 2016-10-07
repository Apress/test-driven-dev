
package com.apress.tddbook.gui;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JListMouseEventData;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.*;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ejb.CreateException;
import java.awt.*;
import java.util.Vector;
import java.util.Hashtable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.FileInputStream;
import java.rmi.RemoteException;


import com.apress.tddbook.FootballPool;
import com.apress.tddbook.CommonTestProps;
import com.apress.tddbook.gui.ejbmocks.FBPoolServerStub;
import com.apress.tddbook.ejb.FBPoolServerHome;
import com.apress.tddbook.ejb.FBPoolServer;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;


public class AdminGUITester extends JFCTestCase
{
    private JFCTestHelper helper;
    private FBPoolServer m_fbPoolServer;
    private static boolean guiStarted = false;
    private boolean useStub = false;

    public AdminGUITester(String name)
    {
        super(name);
        CommonTestProps testProps = new CommonTestProps("C:\\Java\\Projects\\TddBook\\src\\Ch7\\com\\apress\\tddbook\\test.properties");
        useStub = new Boolean(testProps.getProperty("Use_Stub")).booleanValue();
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        if (!useStub)
        {
            //Load Database with test data
            Class driverClass = Class.forName("org.hsqldb.jdbcDriver");
            Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:1701", "sa", "");
            IDatabaseConnection dbConn = new DatabaseConnection(jdbcConnection);
            IDataSet dataSet = new FlatXmlDataSet(new FileInputStream("C:\\Java\\Projects\\TddBook\\DB_Scripts\\datasetEJB1.xml"));
            DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
        }

        //Start GUI
        if (!guiStarted)
        {
            // start AdminGUI
            String[] args = new String[3];
            if (useStub)
            {
                args = null;
            }
            else
            {
                args[0] = "org.jnp.interfaces.NamingContextFactory";
                args[1] = "localhost:1099";
                args[2] = "org.jboss.naming:org.jnp.interfaces";
            }

            AdminMain.main(args);
            guiStarted = true;
        }
        //Start listening for window open events
        helper = new JFCTestHelper();
        if (useStub)
        {
            m_fbPoolServer = new FBPoolServerStub();
        }
        else
        {
            m_fbPoolServer = getEJB();
        }
    }

    /**
     * Tests that the main window was displayed
     */
    public void testMainWindow()
    {
        Window appWindow = helper.getWindow("Football Pool Administrator");
        assertNotNull("Unable to get main window", appWindow);
        JList poolList = (JList) helper.findComponent(JList.class, appWindow, 0);
        assertNotNull("Unable to find pool list", poolList);
        JTable gameTable = (JTable) helper.findComponent(JTable.class, appWindow, 0);
        assertNotNull("Unable to find gameTable", gameTable);
        JButton closePoolButton = (JButton) helper.findComponent(JButton.class, appWindow, 2);
        assertNotNull("Unable to find Close Pool button", closePoolButton);
        assertEquals("Close Pool", closePoolButton.getText());
        JButton newPoolButton = (JButton) helper.findNamedComponent(JButton.class, "NewPoolButton", appWindow, 0);
        assertNotNull("Unable to find New Pool button", poolList);
        assertEquals("New Pool", newPoolButton.getText());
    }

    /**
     * Tests that the GUI can correctly display a pool List
     */
    public void testDisplayPoolList() throws RemoteException
    {
        //Get Pool List
        Window appWindow = helper.getWindow("Football Pool Administrator");
        assertNotNull("Unable to get main window", appWindow);
        JList guiPoolJList = (JList) helper.findComponent(JList.class, appWindow, 0);
        assertNotNull("Unable to find pool list", guiPoolJList);
        //Determine if list is the right size and contains the correct
        Vector serverPoolList = m_fbPoolServer.getPoolList();
        int poolSize = serverPoolList.size();
        assertEquals(poolSize, guiPoolJList.getModel().getSize());
        String guiPoolName = (String) guiPoolJList.getModel().getElementAt(0);
        String serverPoolName = (String) serverPoolList.elementAt(0);
        assertEquals(serverPoolName, guiPoolName);
        guiPoolName = (String) guiPoolJList.getModel().getElementAt(1);
        serverPoolName = (String) serverPoolList.elementAt(1);
        assertEquals(serverPoolName, guiPoolName);
        guiPoolName = (String) guiPoolJList.getModel().getElementAt(2);
        serverPoolName = (String) serverPoolList.elementAt(2);
        assertEquals(serverPoolName, guiPoolName);

    }

    /**
     * Selects Week 2 in the pool list and checks to make sure that the correct games are displayed
     */
    public void testDisplayGames() throws RemoteException
    {
        //Get Pool List and game table
        Window appWindow = helper.getWindow("Football Pool Administrator");
        JList guiPoolJList = (JList) helper.findComponent(JList.class, appWindow, 0);
        JTable gameTable = (JTable) helper.findComponent(JTable.class, appWindow, 0);
        //Select the second entry in the pool list
        JListMouseEventData listEvent = new JListMouseEventData(this, guiPoolJList, 1, 1);
        helper.enterClickAndLeave(listEvent);
        String gameNum = (String) gameTable.getValueAt(0, 0);
        String homeTeam = (String) gameTable.getValueAt(0, 1);
        String awayTeam = (String) gameTable.getValueAt(0, 2);

        FootballPool fbPool = m_fbPoolServer.getPoolInfo("2004-09-18");
        assertEquals("1", gameNum);
        assertEquals(fbPool.getHomeTeam(0), homeTeam);
        assertEquals(fbPool.getAwayTeam(0), awayTeam);

        listEvent = new JListMouseEventData(this, guiPoolJList, 0, 1);
        helper.enterClickAndLeave(listEvent);
        gameNum = (String) gameTable.getValueAt(0, 0);
        homeTeam = (String) gameTable.getValueAt(0, 1);
        awayTeam = (String) gameTable.getValueAt(0, 2);

        fbPool = m_fbPoolServer.getPoolInfo("2004-09-11");
        assertEquals("1", gameNum);
        assertEquals(fbPool.getHomeTeam(0), homeTeam);
        assertEquals(fbPool.getAwayTeam(0), awayTeam);

        listEvent = new JListMouseEventData(this, guiPoolJList, 2, 1);
        helper.enterClickAndLeave(listEvent);
        gameNum = (String) gameTable.getValueAt(0, 0);
        homeTeam = (String) gameTable.getValueAt(0, 1);
        awayTeam = (String) gameTable.getValueAt(0, 2);

        fbPool = m_fbPoolServer.getPoolInfo("2004-09-25");
        assertEquals("1", gameNum);
        assertEquals(fbPool.getHomeTeam(0), homeTeam);
        assertEquals(fbPool.getAwayTeam(0), awayTeam);
    }

    private FBPoolServer getEJB() throws NamingException, RemoteException, CreateException
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

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Sets up the suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AdminGUITester.class);
        return suite;
    }

    public static final void main(String[] args)
    {

        junit.textui.TestRunner.run(suite());

        //AdminMain.exitGUI();
    }
}
