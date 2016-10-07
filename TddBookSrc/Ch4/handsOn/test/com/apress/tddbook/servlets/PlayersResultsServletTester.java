package com.apress.tddbook.servlets;

import org.easymock.MockControl;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Node;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import com.apress.tddbook.PoolData;
import com.apress.tddbook.PoolResults;
import com.apress.tddbook.PlayerResultsData;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.meterware.servletunit.InvocationContext;
import com.meterware.httpunit.*;
import com.meterware.httpunit.javascript.JavaScript;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Vector;

public class PlayersResultsServletTester extends TestCase
{
    private MockControl poolDataMockcontrol;

    public PlayersResultsServletTester(String s)
    {
        super(s);
    }

    public void setUp()
    {
        poolDataMockcontrol = MockControl.createControl(PoolData.class);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(PlayersResultsServletTester.class);
        return suite;
    }

    // Test to make sure that the servlet can return the pool results for a given date
    public void testGetPoolResults()
    {
        //Get mock object
        PoolData mockPoolData = (PoolData) poolDataMockcontrol.getMock();
        //Train the mock object
        mockPoolData.calcPoolResults("2003-09-18");
        poolDataMockcontrol.setReturnValue(createPoolResults());
        //Training over set the mock to replay trained behavior when called
        poolDataMockcontrol.replay();

        ServletRunner sr = new ServletRunner();
        sr.registerServlet("PlayerResultsServlet", PlayerResultsServlet.class.getName());
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new PostMethodWebRequest("http://localhost/PlayerResultsServlet");
        request.setParameter("poolDate", "2003-09-18");

        try
        {
            InvocationContext ic = sc.newInvocation(request);
            PlayerResultsServlet playerResultsServlet = (PlayerResultsServlet) ic.getServlet();
            assertNull("A session already exists", ic.getRequest().getSession(false));
            HttpServletRequest presultsServletRequest = ic.getRequest();
            HttpSession servletSession = presultsServletRequest.getSession();
            servletSession.setAttribute("PoolData", mockPoolData);
            PoolResults results = playerResultsServlet.getResults(presultsServletRequest);

            //Make sure the results match the expected value
            assertEquals("ScottM", results.winningPlayersName);
            Vector resultsList = results.playerResultsList;
            PlayerResultsData playerResults = (PlayerResultsData) resultsList.elementAt(0);
            assertEquals("EddyC", playerResults.playerName);
            assertEquals(9, playerResults.correctPicks);

            playerResults = (PlayerResultsData) resultsList.elementAt(1);
            assertEquals("ScottM", playerResults.playerName);
            assertEquals(9, playerResults.correctPicks);

            playerResults = (PlayerResultsData) resultsList.elementAt(2);
            assertEquals("JenC", playerResults.playerName);
            assertEquals(5, playerResults.correctPicks);
        }
        catch (Exception e)
        {
            fail("Error testing PlayerResultsServlet Exception is " + e);
            e.printStackTrace();
        }
    }

    /**
     * Test to make sure that the JSP page displays the pool results properly
     */
    public void testPoolResultPageJSP()
    {
        ServletRunner sr = new ServletRunner();
        sr.registerServlet("JSPDispatcherServlet", JSPDispatcherServlet.class.getName());
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new PostMethodWebRequest("http://localhost/JSPDispatcherServlet");
        request.setParameter("poolDate", "2003-09-18");
        request.setParameter("JSPPage", "/Ch4/handsOn/src/com/apress/tddbook/jsps//ResultPage.jsp");
        try
        {
            InvocationContext ic = sc.newInvocation(request);
            JSPDispatcherServlet jspDispServlet = (JSPDispatcherServlet) ic.getServlet();
            assertNull("A session already exists", ic.getRequest().getSession(false));
            HttpServletRequest jspDispRequest = ic.getRequest();
            HttpSession servletSession = jspDispRequest.getSession();
            servletSession.setAttribute("poolResults", createPoolResults());
            jspDispServlet.doGet(jspDispRequest, ic.getResponse());
            WebResponse response = sc.getResponse(ic);


            assertNotNull("No response received", response);

            WebTable resultsTable = response.getTables()[0];
            assertEquals(2, resultsTable.getColumnCount());
            assertEquals(4, resultsTable.getRowCount());
            assertEquals("ScottM", resultsTable.getTableCell(2, 0).asText());
            assertEquals("9", resultsTable.getTableCell(2, 1).asText());
        }
        catch (Exception e)
        {
            System.out.println("Error getting reponse Exception is " + e);
            e.printStackTrace();
            fail("Error getting reponse Exception is " + e);
        }
    }

    public void testDisplayPoolResultsWithRealJSP()
    {
        //Get mock object
        PoolData mockPoolData = (PoolData) poolDataMockcontrol.getMock();
        //Train the mock object
        mockPoolData.calcPoolResults("2003-09-18");
        poolDataMockcontrol.setReturnValue(createPoolResults());
        //Training over set the mock to replay trained behavior when called
        poolDataMockcontrol.replay();

        ServletRunner sr = new ServletRunner();
        sr.registerServlet("PlayerResultsServlet", PlayerResultsServlet.class.getName());
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new PostMethodWebRequest("http://localhost/PlayerResultsServlet");
        request.setParameter("poolDate", "2003-09-18");
        try
        {
            InvocationContext ic = sc.newInvocation(request);
            PlayerResultsServlet playerResultsServlet = (PlayerResultsServlet) ic.getServlet();
            assertNull("A session already exists", ic.getRequest().getSession(false));
            HttpServletRequest presultsServletRequest = ic.getRequest();
            HttpSession servletSession = presultsServletRequest.getSession();
            servletSession.setAttribute("PoolData", mockPoolData);
            playerResultsServlet.doGet(presultsServletRequest, ic.getResponse());
            WebResponse response = sc.getResponse(ic);

            assertNotNull("No response received", response);

            WebTable resultsTable = response.getTables()[0];
            assertEquals(2, resultsTable.getColumnCount());
            assertEquals(4, resultsTable.getRowCount());
            assertEquals("ScottM", resultsTable.getTableCell(2, 0).asText());
            assertEquals("9", resultsTable.getTableCell(2, 1).asText());
        }
        catch (Exception e)
        {
            fail("Error getting reponse Exception is " + e);
            e.printStackTrace();
        }
    }

    /**
     * Creates a set of Pool results to be used in the tests
     *
     * @return
     */
    private PoolResults createPoolResults()
    {
        Vector resultsList = new Vector();
        PlayerResultsData playerResults = new PlayerResultsData("EddyC", 9);
        resultsList.addElement(playerResults);

        playerResults = new PlayerResultsData("ScottM", 9);
        resultsList.addElement(playerResults);

        playerResults = new PlayerResultsData("JenC", 5);
        resultsList.addElement(playerResults);

        PoolResults results = new PoolResults("2003-09-18", "ScottM", resultsList);
        return (results);
    }

    public static final void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }
}
