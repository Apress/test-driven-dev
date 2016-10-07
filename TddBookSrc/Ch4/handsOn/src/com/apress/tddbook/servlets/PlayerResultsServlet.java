package com.apress.tddbook.servlets;

import com.apress.tddbook.PoolResults;
import com.apress.tddbook.PoolData;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

public class PlayerResultsServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        PoolResults poolResults = getResults(request);
        request.setAttribute("poolResults", poolResults);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Ch4/handsOn/src/com/apress/tddbook/jsps/ResultPage.jsp");
        dispatcher.include(request, response);
        return;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }
    /**
     * Returns the pool results for a particular pool given the date.  If there are no results for the specified
     * pool date or if there is no pool for that date then null is returned
     *
     * @param request
     * @return PoolResults for the specified date or null if no results or pool
     */
    public PoolResults getResults(HttpServletRequest request)
    {
        PoolData poolData = (PoolData)request.getSession().getAttribute("PoolData");
        PoolResults results = poolData.calcPoolResults(request.getParameter("poolDate"));
        return(results);
    }
}
