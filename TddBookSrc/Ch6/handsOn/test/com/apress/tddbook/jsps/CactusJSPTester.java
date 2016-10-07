/*
 *
 */
package com.apress.tddbook.jsps;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import com.apress.tddbook.servlets.JSPDispatcherServlet;
import com.apress.tddbook.FootballPool;
import com.meterware.httpunit.WebResponse;




public class CactusJSPTester extends ServletTestCase
{

    public void beginPlayerPickJSP(WebRequest webRequest)
    {
        webRequest.addParameter("JSPPage", "/PickPage.jsp");
        System.out.println("Setting JSP Page");
    }
    public void testPlayerPickJSP() throws Exception
    {
        System.out.println("Starting Test");
        JSPDispatcherServlet servlet = new JSPDispatcherServlet();
        servlet.init(config);
        request.setAttribute("openPool", createFbPool());
        servlet.doPost(request, response);
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

    public void endPlayerPickJSP( WebResponse response) throws Exception
    {
       assertTrue(response.isHTML());

        assertEquals("tables", 1, response.getTables().length);
        assertEquals("columns", 3,
            response.getTables()[0].getColumnCount());
        assertEquals("rows", 15,
            response.getTables()[0].getRowCount());
    }
}
