/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook.gui;

import com.apress.tddbook.FootballPool;
import com.apress.tddbook.ejb.FBPoolServer;
import com.apress.tddbook.gui.ejbmocks.FBPoolServerStub;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.netbeans.jemmy.operators.*;
import org.netbeans.jemmy.ClassReference;

import javax.swing.*;
import java.util.Vector;
import java.awt.*;
import java.rmi.RemoteException;


public class AdminGUIJemmyTester extends TestCase
{
    private FBPoolServer m_fbPoolServer;
    private static boolean guiStarted = false;

    public AdminGUIJemmyTester(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        if (!guiStarted)
        {
            // start AdminGUI
            new ClassReference("com.apress.tddbook.gui.AdminMain").startApplication();
            guiStarted = true;
        }
        m_fbPoolServer = new FBPoolServerStub();
    }

    /**
     * Tests that the main window was displayed
     */
    public void testMainWindow()
    {
        JFrameOperator appWindow = new JFrameOperator("Football Pool Administrator");
        JListOperator poolList = new JListOperator(appWindow);
        JTableOperator gameTable = new JTableOperator(appWindow);
        JButtonOperator closePoolButton = new JButtonOperator(appWindow, "Close Pool");
        assertEquals("Close Pool", closePoolButton.getText());
        JButtonOperator newPoolButton = new JButtonOperator(appWindow, new NameCompChooser("NewPoolButton"));
        assertEquals("New Pool", newPoolButton.getText());
    }

    /**
     * Tests that the GUI can correctly display a pool List
     */
    public void testDisplayPoolList() throws RemoteException
    {
        //Get Pool List
        JFrameOperator appWindow = new JFrameOperator("Football Pool Administrator");
        JListOperator guiPoolJList = new JListOperator(appWindow);
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
        JFrameOperator appWindow = new JFrameOperator("Football Pool Administrator");
        JListOperator guiPoolJList = new JListOperator(appWindow);
        JTableOperator gameTable = new JTableOperator(appWindow);
        //Select the second entry in the pool list
        guiPoolJList.selectItem(1);
        String gameNum = (String) gameTable.getValueAt(0, 0);
        String homeTeam = (String) gameTable.getValueAt(0, 1);
        String awayTeam = (String) gameTable.getValueAt(0, 2);

        FootballPool fbPool = m_fbPoolServer.getPoolInfo("Week2");
        assertEquals("1", gameNum);
        assertEquals(fbPool.getHomeTeam(0), homeTeam);
        assertEquals(fbPool.getAwayTeam(0), awayTeam);

        guiPoolJList.selectItem(0);
        gameNum = (String) gameTable.getValueAt(0, 0);
        homeTeam = (String) gameTable.getValueAt(0, 1);
        awayTeam = (String) gameTable.getValueAt(0, 2);

        fbPool = m_fbPoolServer.getPoolInfo("Week1");
        assertEquals("1", gameNum);
        assertEquals(fbPool.getHomeTeam(0), homeTeam);
        assertEquals(fbPool.getAwayTeam(0), awayTeam);

        guiPoolJList.selectItem(2);
        gameNum = (String) gameTable.getValueAt(0, 0);
        homeTeam = (String) gameTable.getValueAt(0, 1);
        awayTeam = (String) gameTable.getValueAt(0, 2);

        fbPool = m_fbPoolServer.getPoolInfo("Week3");
        assertEquals("1", gameNum);
        assertEquals(fbPool.getHomeTeam(0), homeTeam);
        assertEquals(fbPool.getAwayTeam(0), awayTeam);
    }

    public void testOpenPoolButton()
    {
        //Get Pool List and game table
        JFrameOperator appWindow = new JFrameOperator("Football Pool Administrator");
        JListOperator guiPoolJList = new JListOperator(appWindow);
        JLabelOperator statusLabel = new JLabelOperator(appWindow, new NameCompChooser("StatusLabel"));
        JButtonOperator openButton = new JButtonOperator(appWindow, new NameCompChooser("OpenPoolButton"));
        //Select the third entry in the pool list
        guiPoolJList.selectItem(2);
        //Check the current status of the selected pool
        assertEquals("Status:Created", statusLabel.getText());
        //Press the open button
        openButton.push();
        //Check to make sure the status was updated correctly
        assertEquals("Status:Open", statusLabel.getText());
    }

    /**
     * Tests the Open Pool button when no pool has been selected
     */
    public void testOpenPoolButtonSelectError()
    {
        //Get Pool List and game table
        JFrameOperator appWindow = new JFrameOperator("Football Pool Administrator");
        JListOperator guiPoolJList = new JListOperator(appWindow);
        JButtonOperator openButton = new JButtonOperator(appWindow, new NameCompChooser("OpenPoolButton"));
        //Clear the pool list selection
        guiPoolJList.clearSelection();
        //Press the open button
        openButton.pushNoBlock();
        //Get error dialog and check to make sure it contains the correct message
        JDialogOperator dialogBox = new JDialogOperator("Error");
        JLabelOperator messageLabel = new JLabelOperator(dialogBox);
        String message = messageLabel.getText();
        assertEquals("No Pool Selected", message);
    }

    /**
     * Tests the Open Pool button when the pool selected is already open
     */
    public void testOpenPoolButtonAlreadyOpenError()
    {
        //Get Pool List and game table
        JFrameOperator appWindow = new JFrameOperator("Football Pool Administrator");
        JListOperator guiPoolJList = new JListOperator(appWindow);
        JLabelOperator statusLabel = new JLabelOperator(appWindow, new NameCompChooser("StatusLabel"));
        JButtonOperator openButton = new JButtonOperator(appWindow, new NameCompChooser("OpenPoolButton"));
        //Select the third entry in the pool list
        guiPoolJList.selectItem(1);
        //Check the current status of the selected pool
        assertEquals("Status:Open", statusLabel.getText());
        //Press the open button
        openButton.push();
        //Get error dialog and check to make sure it contains the correct message
        JDialogOperator dialogBox = new JDialogOperator("Error");
        JLabelOperator messageLabel = new JLabelOperator(dialogBox);
        String message = messageLabel.getText();
        assertEquals("Pool Already Opened", message);

    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /** Sets up the suite*/
    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        suite.addTest(new AdminGUIJemmyTester("testOpenPoolButtonAlreadyOpenError"));
        //TestSuite suite = new TestSuite(AdminGUIJemmyTester.class);
        return suite;
    }


    private JFrameOperator getMainWindow()
    {
        return(new JFrameOperator("Football Pool Administrator"));
    }
    public static final void main(String[] args)
    {

        junit.textui.TestRunner.run(suite());

        //AdminMain.exitGUI();
    }
}
