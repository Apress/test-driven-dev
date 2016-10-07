package com.apress.tddbook.gui;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JListMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.*;
import java.awt.*;
import java.util.List;
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

    /**
     * Tests to make sure that the New Pool dialog can be displayed
     */
    public void testNewPoolDialog()
    {
        Window appWindow = helper.getWindow("Football Pool Administrator");
        assertNotNull("Unable to get main window", appWindow);
        JButton newPoolButton = (JButton) helper.findNamedComponent(JButton.class, "NewPoolButton", appWindow, 0);
        assertNotNull("Unable to find New Pool button", newPoolButton);
        helper.enterClickAndLeave(new MouseEventData(this, newPoolButton));
        //Get dialog box
        List dialogList = helper.getShowingDialogs();
        //There should only be one dialog box showing
        JDialog dialog = (JDialog) dialogList.get(0);

        //Check the dialog to make sure it contains the expected components
        JList teamList = (JList) helper.findComponent(JList.class, dialog, 0);
        assertNotNull("Unable to find team list", teamList);
        JTable gameTable = (JTable) helper.findComponent(JTable.class, dialog, 0);
        assertNotNull("Unable to find gameTable", gameTable);
        JButton addHomeGameButton = (JButton) helper.findNamedComponent(JButton.class, "addHomeTeamButton", dialog, 0);
        System.out.println("Button 1 is " + addHomeGameButton.getText());
        assertNotNull("Unable to find addHomeGame Button", addHomeGameButton);
        dialog.dispose();
    }

    /**
     * Tests to make sure that the Team Name List in the New Pool dialog contains the correct list of teams
     */
    public void testNewPoolDialogPopulateTeamNameList()
    {
        JDialog dialog = null;
        try
        {
            Window appWindow = helper.getWindow("Football Pool Administrator");
            assertNotNull("Unable to get main window", appWindow);
            JButton newPoolButton = (JButton) helper.findNamedComponent(JButton.class, "NewPoolButton", appWindow, 0);
            assertNotNull("Unable to find New Pool button", newPoolButton);
            helper.enterClickAndLeave(new MouseEventData(this, newPoolButton));
            //Get dialog box
            List dialogList = helper.getShowingDialogs();
            //There should only be one dialog box showing
            dialog = (JDialog) dialogList.get(0);

            //Check the team name list to make sure it is the right size and contains the correct team names
            JList teamList = (JList) helper.findComponent(JList.class, dialog, 0);
            assertNotNull("Unable to find team list", teamList);
            ListModel model = teamList.getModel();
            assertEquals(30, model.getSize());
            assertEquals("NY Giants", model.getElementAt(0));
            assertEquals("Green Bay", model.getElementAt(1));
            // ...
        }
                // No matter if we pass or fail the test we need to close the dialog
                // so that other tests can bring up a fresh dialog box
                // if we don't do this then the other tests might fail
        finally
        {
            if (dialog != null)
            {
                dialog.dispose();
            }
        }

    }

    /**
     * Tests to make sure that a user can add a new game in the New Pool dialog
     */
    public void testNewPoolDialogAddGame()
    {
        JDialog dialog = null;
        try
        {
            Window appWindow = helper.getWindow("Football Pool Administrator");
            assertNotNull("Unable to get main window", appWindow);
            JButton newPoolButton = (JButton) helper.findNamedComponent(JButton.class, "NewPoolButton", appWindow, 0);
            assertNotNull("Unable to find New Pool button", newPoolButton);
            helper.enterClickAndLeave(new MouseEventData(this, newPoolButton));
            //Get dialog box
            List dialogList = helper.getShowingDialogs();
            //There should only be one dialog box showing
            dialog = (JDialog) dialogList.get(0);

            //Press the Add Home Team and Add Away team button then check the game table to make sure the game was added
            JList teamList = (JList) helper.findComponent(JList.class, dialog, 0);
            assertNotNull("Unable to find team list", teamList);
            JTable gameTable = (JTable) helper.findComponent(JTable.class, dialog, 0);
            assertNotNull("Unable to find gameTable", gameTable);
            JButton addHomeGameButton = (JButton) helper.findNamedComponent(JButton.class, "addHomeTeamButton", dialog, 0);
            System.out.println("Button 1 is " + addHomeGameButton.getText());
            assertNotNull("Unable to find addHomeGame Button", addHomeGameButton);
            JButton addAwayGameButton = (JButton) helper.findNamedComponent(JButton.class, "addAwayTeamButton", dialog, 0);
            System.out.println("Button 2 is " + addAwayGameButton.getText());
            assertNotNull("Unable to find addAwayGame Button", addAwayGameButton);

            //Select home team
            teamList.setSelectedIndex(1);
            //Press Add Home Team button
            helper.enterClickAndLeave(new MouseEventData(this, addHomeGameButton));
            awtSleep(5000);
            //Select away team
            teamList.setSelectedIndex(4);
            //Press Add Away Team button
            helper.enterClickAndLeave(new MouseEventData(this, addAwayGameButton));
            //Check game table to make sure game was added
            awtSleep(5000);
            int rowCount = gameTable.getRowCount();
            assertEquals("Green Bay", gameTable.getValueAt(rowCount - 1, 0));
            assertEquals("Chicago", gameTable.getValueAt(rowCount - 1, 1));
        }
                // No matter if we pass or fail the test we need to close the dialog
                // so that other tests can bring up a fresh dialog box
                // if we don't do this then the other tests might fail
        finally
        {
            if (dialog != null)
            {
                dialog.dispose();
            }
        }
    }

    public void testNewPoolDialogDeleteGame()
    {
        JDialog dialog = null;
        try
        {
            Window appWindow = helper.getWindow("Football Pool Administrator");
            assertNotNull("Unable to get main window", appWindow);
            JButton newPoolButton = (JButton) helper.findNamedComponent(JButton.class, "NewPoolButton", appWindow, 0);
            assertNotNull("Unable to find New Pool button", newPoolButton);
            helper.enterClickAndLeave(new MouseEventData(this, newPoolButton));
            //Get dialog box
            List dialogList = helper.getShowingDialogs();
            //There should only be one dialog box showing
            dialog = (JDialog) dialogList.get(0);

            //Press the Add then check the game table to make sure the game was added
            // After the add delete the game and make sure the game table is empty
            JList teamList = (JList) helper.findComponent(JList.class, dialog, 0);
            assertNotNull("Unable to find team list", teamList);
            JTable gameTable = (JTable) helper.findComponent(JTable.class, dialog, 0);
            assertNotNull("Unable to find gameTable", gameTable);
            JButton addHomeTeamButton = (JButton) helper.findNamedComponent(JButton.class, "addHomeTeamButton", dialog, 0);
            assertNotNull("Unable to find addHomeGame Button", addHomeTeamButton);
            JButton addAwayTeamButton = (JButton) helper.findNamedComponent(JButton.class, "addAwayTeamButton", dialog, 0);
            assertNotNull("Unable to find addAwayGame Button", addAwayTeamButton);
            JButton deleteGameButton = (JButton) helper.findNamedComponent(JButton.class, "delGameButton", dialog, 0);
            assertNotNull("Unable to find delete Game Button", addAwayTeamButton);

            //Select home team
            teamList.setSelectedIndex(1);
            //Press Add Home Team button
            helper.enterClickAndLeave(new MouseEventData(this, addHomeTeamButton));
            awtSleep(5000);
            //Select away team
            teamList.setSelectedIndex(4);
            //Press Add Away Team button
            helper.enterClickAndLeave(new MouseEventData(this, addAwayTeamButton));
            //Check game table to make sure game was added
            awtSleep(5000);
            int rowCount = gameTable.getRowCount();
            assertEquals("Green Bay", gameTable.getValueAt(rowCount - 1, 0));
            assertEquals("Chicago", gameTable.getValueAt(rowCount - 1, 1));

            //Select the first cell of the table
            helper.enterClickAndLeave(new JTableMouseEventData(this, gameTable, 0, 0, 1));
            //Press Delete Game button
            helper.enterClickAndLeave(new MouseEventData(this, deleteGameButton));
            //Check game table to make sure game was delete
            awtSleep(5000);
            rowCount = gameTable.getRowCount();
            assertEquals(0, rowCount);
        }
                // No matter if we pass or fail the test we need to close the dialog
                // so that other tests can bring up a fresh dialog box
                // if we don't do this then the other tests might fail
        finally
        {
            if (dialog != null)
            {
                dialog.dispose();
            }
        }
    }

    /**
     * Test to make sure use can correctly set tie break game
     */
    public void testNewPoolDialogSetTieBreakGame()
    {

        JDialog dialog = null;
        try
        {
            Window appWindow = helper.getWindow("Football Pool Administrator");
            assertNotNull("Unable to get main window", appWindow);
            JButton newPoolButton = (JButton) helper.findNamedComponent(JButton.class, "NewPoolButton", appWindow, 0);
            assertNotNull("Unable to find New Pool button", newPoolButton);
            helper.enterClickAndLeave(new MouseEventData(this, newPoolButton));
            //Get dialog box
            List dialogList = helper.getShowingDialogs();
            //There should only be one dialog box showing
            dialog = (JDialog) dialogList.get(0);

            //Press the Add then check the game table to make sure the game was added
            // After the add delete the game and make sure the game table is empty
            JList teamList = (JList) helper.findComponent(JList.class, dialog, 0);
            assertNotNull("Unable to find team list", teamList);
            JTable gameTable = (JTable) helper.findComponent(JTable.class, dialog, 0);
            assertNotNull("Unable to find gameTable", gameTable);
            JButton addHomeTeamButton = (JButton) helper.findNamedComponent(JButton.class, "addHomeTeamButton", dialog, 0);
            assertNotNull("Unable to find addHomeGame Button", addHomeTeamButton);
            JButton addAwayTeamButton = (JButton) helper.findNamedComponent(JButton.class, "addAwayTeamButton", dialog, 0);
            assertNotNull("Unable to find addAwayGame Button", addAwayTeamButton);
            JButton setTieBreakButton = (JButton) helper.findNamedComponent(JButton.class, "setTieBreakButton", dialog, 0);
            assertNotNull("Unable to find set tie break Game Button", addAwayTeamButton);

            //Add some games
            teamList.setSelectedIndex(1);
            helper.enterClickAndLeave(new MouseEventData(this, addHomeTeamButton));
            awtSleep(5000);
            teamList.setSelectedIndex(4);
            helper.enterClickAndLeave(new MouseEventData(this, addAwayTeamButton));
            awtSleep(5000);

            teamList.setSelectedIndex(3);
            helper.enterClickAndLeave(new MouseEventData(this, addHomeTeamButton));
            awtSleep(5000);
            teamList.setSelectedIndex(5);
            helper.enterClickAndLeave(new MouseEventData(this, addAwayTeamButton));
            awtSleep(5000);

            teamList.setSelectedIndex(7);
            helper.enterClickAndLeave(new MouseEventData(this, addHomeTeamButton));
            awtSleep(5000);
            teamList.setSelectedIndex(9);
            helper.enterClickAndLeave(new MouseEventData(this, addAwayTeamButton));
            awtSleep(5000);

            int rowCount = gameTable.getRowCount();
            assertEquals(3, rowCount);
            //Select the first cell of the table
            helper.enterClickAndLeave(new JTableMouseEventData(this, gameTable, 1, 0, 1));
            //Press Delete Game button
            helper.enterClickAndLeave(new MouseEventData(this, setTieBreakButton));
            awtSleep(5000);
            //Check to make sure that the tie break game was set. The tie break game will have an asterisk after them
            assertEquals("Oakland *", gameTable.getValueAt(1, 0));
            assertEquals("Miami *", gameTable.getValueAt(1, 1));
        }
                // No matter if we pass or fail the test we need to close the dialog
                // so that other tests can bring up a fresh dialog box
                // if we don't do this then the other tests might fail
        finally
        {
            if (dialog != null)
            {
                dialog.dispose();
            }
        }
    }

    /**
     * Test to make sure use can correctly save a new pool once it have been created
     */
    public void testNewPoolDialogSaveNewPool()
    {
        JDialog dialog = null;
        try
        {
            Window appWindow = helper.getWindow("Football Pool Administrator");
            assertNotNull("Unable to get main window", appWindow);
            JButton newPoolButton = (JButton) helper.findNamedComponent(JButton.class, "NewPoolButton", appWindow, 0);
            assertNotNull("Unable to find New Pool button", newPoolButton);
            helper.enterClickAndLeave(new MouseEventData(this, newPoolButton));
            //Get dialog box
            List dialogList = helper.getShowingDialogs();
            //There should only be one dialog box showing
            dialog = (JDialog) dialogList.get(0);

            //Press the Add then check the game table to make sure the game was added
            // After the add delete the game and make sure the game table is empty
            JList teamList = (JList) helper.findComponent(JList.class, dialog, 0);
            assertNotNull("Unable to find team list", teamList);
            JTable gameTable = (JTable) helper.findComponent(JTable.class, dialog, 0);
            assertNotNull("Unable to find gameTable", gameTable);
            JButton addHomeTeamButton = (JButton) helper.findNamedComponent(JButton.class, "addHomeTeamButton", dialog, 0);
            assertNotNull("Unable to find addHomeGame Button", addHomeTeamButton);
            JButton addAwayTeamButton = (JButton) helper.findNamedComponent(JButton.class, "addAwayTeamButton", dialog, 0);
            assertNotNull("Unable to find addAwayGame Button", addAwayTeamButton);
            JButton okButton = (JButton) helper.findNamedComponent(JButton.class, "okButton", dialog, 0);
            assertNotNull("Unable to find OK Button", addAwayTeamButton);

            //Add some games
            teamList.setSelectedIndex(1);
            helper.enterClickAndLeave(new MouseEventData(this, addHomeTeamButton));
            awtSleep(5000);
            teamList.setSelectedIndex(4);
            helper.enterClickAndLeave(new MouseEventData(this, addAwayTeamButton));
            awtSleep(5000);

            teamList.setSelectedIndex(3);
            helper.enterClickAndLeave(new MouseEventData(this, addHomeTeamButton));
            awtSleep(5000);
            teamList.setSelectedIndex(5);
            helper.enterClickAndLeave(new MouseEventData(this, addAwayTeamButton));
            awtSleep(5000);

            teamList.setSelectedIndex(7);
            helper.enterClickAndLeave(new MouseEventData(this, addHomeTeamButton));
            awtSleep(5000);
            teamList.setSelectedIndex(9);
            helper.enterClickAndLeave(new MouseEventData(this, addAwayTeamButton));
            awtSleep(5000);

            int rowCount = gameTable.getRowCount();
            assertEquals(3, rowCount);
            helper.enterClickAndLeave(new MouseEventData(this, okButton));
            awtSleep(5000);
        }
                // No matter if we pass or fail the test we need to close the dialog
                // so that other tests can bring up a fresh dialog box
                // if we don't do this then the other tests might fail
        finally
        {
            if (dialog != null)
            {
                dialog.dispose();
            }
        }
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
        //TestSuite suite = new TestSuite();
        //suite.addTest(new AdminGUITester("testNewPoolDialog"));
        //suite.addTest(new AdminGUITester("testNewPoolDialogPopulateTeamNameList"));
        //suite.addTest(new AdminGUITester("testNewPoolDialogAddGame"));
        TestSuite suite = new TestSuite(AdminGUITester.class);
        return suite;
    }

    public static final void main(String[] args)
    {

        junit.textui.TestRunner.run(suite());

        //AdminMain.exitGUI();
    }
}
