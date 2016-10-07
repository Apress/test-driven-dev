package com.apress.tddbook.gui;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JListMouseEventData;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import com.apress.tddbook.gui.ejbmocks.FBPoolServer;
import com.apress.tddbook.gui.ejbmocks.FBPoolServerStub;
import com.apress.tddbook.FootballPool;


public class AdminGUITester extends JFCTestCase
{
    private JFCTestHelper helper;
    private FBPoolServer m_fbPoolServer;
    private static boolean guiStarted = false;

    public AdminGUITester(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        if (!guiStarted)
        {
            // start AdminGUI
            String[] args = null;
            AdminMain.main(args);
            guiStarted = true;
        }
        //Start listening for window open events
        helper = new JFCTestHelper();
        m_fbPoolServer = new FBPoolServerStub();
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
    public void testDisplayPoolList()
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
    public void testDisplayGames()
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

        FootballPool fbPool = m_fbPoolServer.getPoolInfo("2004-09-17");
        assertEquals("1", gameNum);
        assertEquals(fbPool.getHomeTeam(0), homeTeam);
        assertEquals(fbPool.getAwayTeam(0), awayTeam);

        listEvent = new JListMouseEventData(this, guiPoolJList, 0, 1);
        helper.enterClickAndLeave(listEvent);
        gameNum = (String) gameTable.getValueAt(0, 0);
        homeTeam = (String) gameTable.getValueAt(0, 1);
        awayTeam = (String) gameTable.getValueAt(0, 2);

        fbPool = m_fbPoolServer.getPoolInfo("2004-09-10");
        assertEquals("1", gameNum);
        assertEquals(fbPool.getHomeTeam(0), homeTeam);
        assertEquals(fbPool.getAwayTeam(0), awayTeam);

        listEvent = new JListMouseEventData(this, guiPoolJList, 2, 1);
        helper.enterClickAndLeave(listEvent);
        gameNum = (String) gameTable.getValueAt(0, 0);
        homeTeam = (String) gameTable.getValueAt(0, 1);
        awayTeam = (String) gameTable.getValueAt(0, 2);

        fbPool = m_fbPoolServer.getPoolInfo("2004-09-24");
        assertEquals("1", gameNum);
        assertEquals(fbPool.getHomeTeam(0), homeTeam);
        assertEquals(fbPool.getAwayTeam(0), awayTeam);
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /** Sets up the suite*/
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AdminGUITester.class);
        return suite;
    }

    public static final void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }
}
