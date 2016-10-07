package com.apress.tddbook.servlets;

import com.apress.tddbook.*;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebForm;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.easymock.MockControl;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Vector;
import java.sql.SQLException;

public class PlayerPickServletTester extends TestCase
{
    private MockControl poolDataMockcontrol;

    public PlayerPickServletTester(String s)
    {
        super(s);
    }

    public void testDisplayPlayerPicks()
    {
        //Create PoolData object
        PoolData poolData = new PoolDataImpl();
        //Set
        try
        {
            PoolDatabase poolDB = new PoolDatabaseImpl("jdbc:hsqldb:hsql://localhost:1701", "sa", "");
            poolData.set_poolDB(poolDB);
        }
        catch (Exception e)
        {
            System.out.println("Error creating PoolDatabase object Exception is " + e);
            e.printStackTrace();
        }


        ServletRunner sr = new ServletRunner();
        sr.registerServlet("PlayerPickServlet", PlayerPickServlet.class.getName());
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new PostMethodWebRequest("http://localhost/PlayerPickServlet");
        request.setParameter("username", "TimmyC");
        try
        {
            InvocationContext ic = sc.newInvocation(request);
            PlayerPickServlet ppickServlet = (PlayerPickServlet) ic.getServlet();
            assertNull("A session already exists", ic.getRequest().getSession(false));
            HttpServletRequest ppickServletRequest = ic.getRequest();
            HttpSession servletSession = ppickServletRequest.getSession();
            servletSession.setAttribute("PoolData", poolData);
            FootballPool openPool = ppickServlet.getOpenPool(ppickServletRequest);

            assertEquals("Kansas City", openPool.getAwayTeam(0));
            assertEquals("Green Bay", openPool.getHomeTeam(0));
            assertEquals("Houston", openPool.getAwayTeam(1));
            assertEquals("Tennessee", openPool.getHomeTeam(1));
            assertEquals("Carolina", openPool.getAwayTeam(2));
            assertEquals("Indianapolis", openPool.getHomeTeam(2));
            assertEquals("NY Giants", openPool.getAwayTeam(3));
            assertEquals("New England", openPool.getHomeTeam(3));
            assertEquals("Chicago", openPool.getAwayTeam(4));
            assertEquals("New Orleans", openPool.getHomeTeam(4));
            assertEquals("Oakland", openPool.getAwayTeam(5));
            assertEquals("Cleveland", openPool.getHomeTeam(5));
            assertEquals("Philadelphia", openPool.getAwayTeam(6));
            assertEquals("Dallas", openPool.getHomeTeam(6));
            assertEquals("Tampa Bay", openPool.getAwayTeam(7));
            assertEquals("Washington", openPool.getHomeTeam(7));
            assertEquals("Miami", openPool.getAwayTeam(8));
            assertEquals("Jacksonville", openPool.getHomeTeam(8));
            assertEquals("Pittsburgh", openPool.getAwayTeam(9));
            assertEquals("Denver", openPool.getHomeTeam(9));
            assertEquals("Buffalo", openPool.getAwayTeam(10));
            assertEquals("NY Jets", openPool.getHomeTeam(10));
            assertEquals("Baltimore", openPool.getAwayTeam(11));
            assertEquals("Arizona", openPool.getHomeTeam(11));
            assertEquals("San Francisco", openPool.getAwayTeam(12));
            assertEquals("Seattle", openPool.getHomeTeam(12));
            assertEquals("Atlanta", openPool.getAwayTeam(13));
            assertEquals("St. Louis", openPool.getHomeTeam(13));

            poolDataMockcontrol.verify();
        }
        catch (Exception e)
        {
            fail("Error testing DIsplayPlayerPickServlet Exception is " + e);
            e.printStackTrace();
        }
    }

    public void testDisplayPlayerPicksWithRealJSP()
    {
        //Get mock object
        PoolData mockPoolData = (PoolData) poolDataMockcontrol.getMock();
        //Train the mock object
        mockPoolData.getOpenPool();
        //Create return value for mock method
        FootballPool fbPool = createFbPool();
        poolDataMockcontrol.setReturnValue(fbPool);
        poolDataMockcontrol.replay();
        ServletRunner sr = new ServletRunner();
        sr.registerServlet("PlayerPickServlet", PlayerPickServlet.class.getName());
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new PostMethodWebRequest("http://localhost/PlayerPickServlet");
        request.setParameter("playersName", "TimmyC");
        request.setParameter("weekNum", "6");
        request.setParameter("JSPPage", "/PickPage.jsp");
        try
        {
            InvocationContext ic = sc.newInvocation(request);
            PlayerPickServlet ppickServlet = (PlayerPickServlet) ic.getServlet();
            assertNull("A session already exists", ic.getRequest().getSession(false));
            HttpServletRequest ppickServletRequest = ic.getRequest();
            HttpSession servletSession = ppickServletRequest.getSession();
            servletSession.setAttribute("PoolData", mockPoolData);
            ppickServlet.doGet(ppickServletRequest, ic.getResponse());
            WebResponse response = sc.getResponse(ic);

            Document pickPageDoc = response.getDOM();
            NodeList h2NodeList = pickPageDoc.getElementsByTagName("h2");
            if (h2NodeList.getLength() > 0)
            {
                Node h2Node = h2NodeList.item(0);
                Node childNode = h2Node.getFirstChild();
                String h2NodeStr = childNode.getNodeValue();
                assertEquals("TimmyC's Picksheet for Week 6", h2NodeStr);
            }
            else
            {
                fail("Can't find h2 element");
            }
            assertNotNull("No response received", response);
            assertEquals("tables", 1, response.getTables().length);
            assertEquals("columns", 3,
                    response.getTables()[0].getColumnCount());
            assertEquals("rows", 15,
                    response.getTables()[0].getRowCount());
            WebForm form = response.getForms()[0];
            assertEquals("", form.getParameterValue("MNTotal"));
            assertEquals("", form.getParameterValue("email"));
        }
        catch (Exception e)
        {
            fail("Error getting reponse Exception is " + e);
            e.printStackTrace();
        }
    }

    /**
     * Tests to make sure the servlet can take a set of player picks and process them so that they get stored
     */
    public void testMakePlayerPicks()
    {
        try
        {
            //Get mock object
            PoolData mockPoolData = (PoolData) poolDataMockcontrol.getMock();
            //Train the mock object
            mockPoolData.getOpenPool();
            //Create return value for mock method
            FootballPool fbPool = createFbPool();
            poolDataMockcontrol.setReturnValue(fbPool);
            mockPoolData.savePlayersPicks(null);
            poolDataMockcontrol.setMatcher(MockControl.ALWAYS_MATCHER);
            mockPoolData.getPlayersPicks("TimmyC", "9/17/2003");
            poolDataMockcontrol.setReturnValue(createPlayerPicks());
            poolDataMockcontrol.replay();
            ServletRunner sr = new ServletRunner();
            sr.registerServlet("PlayerPickServlet", PlayerPickServlet.class.getName());
            ServletUnitClient sc = sr.newClient();
            WebRequest request = new PostMethodWebRequest("http://localhost/PlayerPickServlet");
            request.setParameter("username", "TimmyC");
            request.setParameter("poolDate", "9/17/2003");
            request.setParameter("game_1_pick", "Green Bay");
            request.setParameter("game_2_pick", "Houston");
            request.setParameter("game_3_pick", "Indianapolis");
            request.setParameter("game_4_pick", "NY Giants");
            request.setParameter("game_5_pick", "Chicago");
            request.setParameter("game_6_pick", "Cleveland");
            request.setParameter("game_7_pick", "Dallas");
            request.setParameter("game_8_pick", "Washington");
            request.setParameter("game_9_pick", "Jacksonville");
            request.setParameter("game_10_pick", "Pittsburgh");
            request.setParameter("game_11_pick", "Buffalo");
            request.setParameter("game_12_pick", "Baltimore");
            request.setParameter("game_13_pick", "San Francisco");
            request.setParameter("game_14_pick", "Atlanta");

            InvocationContext ic = sc.newInvocation(request);
            PlayerPickServlet ppickServlet = (PlayerPickServlet) ic.getServlet();
            assertNull("A session already exists", ic.getRequest().getSession(false));
            HttpServletRequest ppickServletRequest = ic.getRequest();
            HttpSession servletSession = ppickServletRequest.getSession();
            servletSession.setAttribute("PoolData", mockPoolData);
            ppickServlet.storePlayersPicks(ppickServletRequest);
            PlayersPicks playerPicks = ppickServlet.getPlayerPicks(ppickServletRequest);
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
        catch (Exception e)
        {
            fail("Error testing MakePlayerPicks Exception is " + e);
            e.printStackTrace();
        }
    }

    public void testEditPlayerPicks()
    {
        try
        {
            //Get mock object
            PoolData mockPoolData = (PoolData) poolDataMockcontrol.getMock();
            //Train the mock object
            mockPoolData.getOpenPool();
            //Create return value for mock method
            FootballPool fbPool = createFbPool();
            poolDataMockcontrol.setReturnValue(fbPool);
            mockPoolData.savePlayersPicks(null);
            poolDataMockcontrol.setMatcher(MockControl.ALWAYS_MATCHER);
            mockPoolData.getPlayersPicks("TimmyC", "9/17/2003");
            poolDataMockcontrol.setReturnValue(createPlayerPicks());
            poolDataMockcontrol.replay();
            ServletRunner sr = new ServletRunner();
            sr.registerServlet("PlayerPickServlet", PlayerPickServlet.class.getName());
            ServletUnitClient sc = sr.newClient();
            WebRequest request = new PostMethodWebRequest("http://localhost/PlayerPickServlet");
            request.setParameter("username", "TimmyC");
            request.setParameter("poolDate", "9/17/2003");
            request.setParameter("game_1_pick", "Kansas City");
            request.setParameter("game_2_pick", "Houston");
            request.setParameter("game_3_pick", "Indianapolis");
            request.setParameter("game_4_pick", "NY Giants");
            request.setParameter("game_5_pick", "Chicago");
            request.setParameter("game_6_pick", "Cleveland");
            request.setParameter("game_7_pick", "Dallas");
            request.setParameter("game_8_pick", "Washington");
            request.setParameter("game_9_pick", "Miami");
            request.setParameter("game_10_pick", "Denver");
            request.setParameter("game_11_pick", "Buffalo");
            request.setParameter("game_12_pick", "Baltimore");
            request.setParameter("game_13_pick", "San Francisco");
            request.setParameter("game_14_pick", "St. Louis");

            InvocationContext ic = sc.newInvocation(request);
            PlayerPickServlet ppickServlet = (PlayerPickServlet) ic.getServlet();
            assertNull("A session already exists", ic.getRequest().getSession(false));
            HttpServletRequest ppickServletRequest = ic.getRequest();
            HttpSession servletSession = ppickServletRequest.getSession();
            servletSession.setAttribute("PoolData", mockPoolData);
            //Get players current picks
            PlayersPicks playerPicks = ppickServlet.getPlayerPicks(ppickServletRequest);
            //Check the present value of the player picks
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

            poolDataMockcontrol.reset();
            //Re Train the mock object
            mockPoolData.getOpenPool();
            fbPool = createFbPool();
            poolDataMockcontrol.setReturnValue(fbPool);
            mockPoolData.savePlayersPicks(null);
            poolDataMockcontrol.setMatcher(MockControl.ALWAYS_MATCHER);
            mockPoolData.getPlayersPicks("TimmyC", "9/17/2003");
            poolDataMockcontrol.setReturnValue(createUpdatedPlayerPicks());
            poolDataMockcontrol.replay();
            //process the updates from the request
            ppickServlet.storePlayersPicks(ppickServletRequest);
            //Get the updated player picks and check to make sure the updates were made correctly
            playerPicks = ppickServlet.getPlayerPicks(ppickServletRequest);
            assertEquals("Kansas City", playerPicks.getPickedTeam(0));
            assertEquals("Houston", playerPicks.getPickedTeam(1));
            assertEquals("Indianapolis", playerPicks.getPickedTeam(2));
            assertEquals("NY Giants", playerPicks.getPickedTeam(3));
            assertEquals("Chicago", playerPicks.getPickedTeam(4));
            assertEquals("Cleveland", playerPicks.getPickedTeam(5));
            assertEquals("Dallas", playerPicks.getPickedTeam(6));
            assertEquals("Washington", playerPicks.getPickedTeam(7));
            assertEquals("Jacksonville", playerPicks.getPickedTeam(8));
            assertEquals("Denver", playerPicks.getPickedTeam(9));
            assertEquals("Buffalo", playerPicks.getPickedTeam(10));
            assertEquals("Baltimore", playerPicks.getPickedTeam(11));
            assertEquals("San Francisco", playerPicks.getPickedTeam(12));
            assertEquals("St. Louis", playerPicks.getPickedTeam(13));

        }
        catch (Exception e)
        {
            fail("Error testing EditPlayerPicks Exception is " + e);
            e.printStackTrace();
        }
    }

    private FootballPool createFbPool()
    {
        Vector poolList = new Vector();
        FootballPool pool = new FootballPool("9/17/2003");
        pool.addGame("Kansas City", "Green Bay");
        pool.addGame("Houston", "Tennessee");
        pool.addGame("Carolina", "Indianapolis");
        pool.addGame("NY Giants", "New England");
        pool.addGame("Chicago", "New Orleans");
        pool.addGame("Oakland", "Cleveland");
        pool.addGame("Philadelphia", "Dallas");
        pool.addGame("Tampa Bay", "Washington");
        pool.addGame("Miami", "Jacksonville");
        pool.addGame("Pittsburgh", "Denver");
        pool.addGame("Buffalo", "NY Jets");
        pool.addGame("Baltimore", "Arizona");
        pool.addGame("San Francisco", "Seattle");
        pool.addGame("Atlanta", "St. Louis");
        return (pool);
    }

    private PlayersPicks createPlayerPicks()
    {
        FootballPool fbPool = createFbPool();
        Vector gameList = fbPool.getGameList();
        PlayersPicks playerPicks = new PlayersPicks("TimmyC", "9/17/2003", gameList);

        Game game = (Game) gameList.elementAt(0);
        playerPicks.makePick(0, "Green Bay");
        game = (Game) gameList.elementAt(1);
        playerPicks.makePick(1, "Houston");
        game = (Game) gameList.elementAt(2);
        playerPicks.makePick(2, "Indianapolis");
        game = (Game) gameList.elementAt(3);
        playerPicks.makePick(3, "NY Giants");
        game = (Game) gameList.elementAt(4);
        playerPicks.makePick(4, "Chicago");
        game = (Game) gameList.elementAt(5);
        playerPicks.makePick(5, "Cleveland");
        game = (Game) gameList.elementAt(6);
        playerPicks.makePick(6, "Dallas");
        game = (Game) gameList.elementAt(7);
        playerPicks.makePick(7, "Washington");
        game = (Game) gameList.elementAt(8);
        playerPicks.makePick(8, "Jacksonville");
        game = (Game) gameList.elementAt(9);
        playerPicks.makePick(9, "Pittsburgh");
        game = (Game) gameList.elementAt(10);
        playerPicks.makePick(10, "Buffalo");
        game = (Game) gameList.elementAt(11);
        playerPicks.makePick(11, "Baltimore");
        game = (Game) gameList.elementAt(12);
        playerPicks.makePick(12, "San Francisco");
        game = (Game) gameList.elementAt(13);
        playerPicks.makePick(13, "Atlanta");


        return (playerPicks);
    }

    private PlayersPicks createUpdatedPlayerPicks()
    {
        FootballPool fbPool = createFbPool();
        Vector gameList = fbPool.getGameList();
        PlayersPicks playerPicks = new PlayersPicks("TimmyC", "9/17/2003", gameList);

        Game game = (Game) gameList.elementAt(0);
        playerPicks.makePick(0, "Kansas City");
        game = (Game) gameList.elementAt(1);
        playerPicks.makePick(1, "Houston");
        game = (Game) gameList.elementAt(2);
        playerPicks.makePick(2, "Indianapolis");
        game = (Game) gameList.elementAt(3);
        playerPicks.makePick(3, "NY Giants");
        game = (Game) gameList.elementAt(4);
        playerPicks.makePick(4, "Chicago");
        game = (Game) gameList.elementAt(5);
        playerPicks.makePick(5, "Cleveland");
        game = (Game) gameList.elementAt(6);
        playerPicks.makePick(6, "Dallas");
        game = (Game) gameList.elementAt(7);
        playerPicks.makePick(7, "Washington");
        game = (Game) gameList.elementAt(8);
        playerPicks.makePick(8, "Jacksonville");
        game = (Game) gameList.elementAt(9);
        playerPicks.makePick(9, "Denver");
        game = (Game) gameList.elementAt(10);
        playerPicks.makePick(10, "Buffalo");
        game = (Game) gameList.elementAt(11);
        playerPicks.makePick(11, "Baltimore");
        game = (Game) gameList.elementAt(12);
        playerPicks.makePick(12, "San Francisco");
        game = (Game) gameList.elementAt(13);
        playerPicks.makePick(13, "St. Louis");


        return (playerPicks);
    }

    public void setUp()
    {
        poolDataMockcontrol = MockControl.createControl(PoolData.class);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(PlayerPickServletTester.class);
        return suite;
    }


    public static final void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }
}
