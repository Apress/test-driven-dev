package com.apress.tddbook.jsps;

import com.apress.tddbook.FootballPool;
import com.apress.tddbook.Game;
import com.apress.tddbook.PlayersPicks;
import com.apress.tddbook.PoolData;
import com.apress.tddbook.servlets.PlayerPickServlet;
import com.apress.tddbook.servlets.JSPDispatcherServlet;
import com.meterware.httpunit.*;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.meterware.servletunit.InvocationContext;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


public class PlayerPickJSPTester extends TestCase
{


    public PlayerPickJSPTester(String s)
    {
        super(s);
    }

    public void testDisplayPlayerPicks()
    {
        ServletRunner sr = new ServletRunner();
        sr.registerServlet("JSPDispatcherServlet", JSPDispatcherServlet.class.getName());
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new PostMethodWebRequest("http://localhost/JSPDispatcherServlet");
        request.setParameter("playersName", "TimmyC");
        request.setParameter("weekNum", "6");
        request.setParameter("JSPPage", "/Ch4/bookSource/src/com/apress/tddbook/jsps/PickPage.jsp");
        try
        {
            InvocationContext ic = sc.newInvocation(request);
            JSPDispatcherServlet jspDispServlet = (JSPDispatcherServlet) ic.getServlet();
            assertNull("A session already exists", ic.getRequest().getSession(false));
            HttpServletRequest jspDispRequest = ic.getRequest();
            HttpSession servletSession = jspDispRequest.getSession();
            jspDispRequest.setAttribute("openPool", createFbPool());
            jspDispServlet.doGet(jspDispRequest, ic.getResponse());
            WebResponse response = sc.getResponse(ic);

            Document pickPageDoc = response.getDOM();
            NodeList h2NodeList = pickPageDoc.getElementsByTagName("h2");
            if (h2NodeList.getLength() > 0)
            {
                Node h2Node = h2NodeList.item(0);
                Node childNode = h2Node.getFirstChild();
                String h2NodeStr = childNode.getNodeValue();
                System.out.println("***" + h2NodeStr + "***");
                assertEquals(true, h2NodeStr.startsWith("TimmyC's Picksheet for Week 6"));
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
            System.out.println("Error getting reponse Exception is " + e);
            e.printStackTrace();
            fail("Error getting reponse Exception is " + e);
        }
    }


    private FootballPool createFbPool()
    {
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

    public static Test suite()
    {
        TestSuite suite = new TestSuite(PlayerPickJSPTester.class);
        return suite;
    }


    public static final void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }
}
