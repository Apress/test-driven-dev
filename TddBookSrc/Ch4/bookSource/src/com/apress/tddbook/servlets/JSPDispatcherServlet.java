package com.apress.tddbook.servlets;

import com.apress.tddbook.FootballPool;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

public class JSPDispatcherServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException
        {
            response.setContentType("text/html");
            String jspPage = request.getParameter("JSPPage");
            System.out.println("JSP Page is " + jspPage);
            System.out.println("Players Name is " + request.getParameter("playersName"));
            System.out.println("WeekNumber is " + request.getParameter("weekNum"));
            request.setAttribute("openPool", createFbPool());
            FootballPool fbPool = (FootballPool)request.getAttribute("openPool");
            System.out.println("Football Pool is " + fbPool);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspPage);
            //dispatcher.forward(request, response);
            dispatcher.include(request, response);
            return;
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException
        {
            doGet(request, response);
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
}
