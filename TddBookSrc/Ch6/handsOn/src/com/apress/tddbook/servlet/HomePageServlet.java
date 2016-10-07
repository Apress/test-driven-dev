package com.apress.tddbook.servlet;

import com.apress.tddbook.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import java.util.Vector;
import java.util.Enumeration;
import java.io.IOException;
import java.rmi.RemoteException;


public class HomePageServlet extends HttpServlet
{

    PoolData poolData;
    public void init(ServletConfig config) throws ServletException
    {

        try
        {
            super.init(config);
            System.out.println("Initializing HomePageServlet");
            Enumeration e = config.getInitParameterNames();
            while (e.hasMoreElements())
            {
                System.out.println("Enueration name is " + e.nextElement());
            }
            String dbLocation = config.getInitParameter("dbLocation");
            String dbUser = config.getInitParameter("dbUser");
            String dbPass = config.getInitParameter("dbPass");
            System.out.println(dbLocation + " " + dbUser + " " + dbPass);
            PoolDatabase poolDB = new PoolDatabaseImpl(dbLocation, dbUser, dbPass);
            poolData = new PoolDataImpl();
            poolData.set_poolDB(poolDB);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        System.out.println("HomePageServlet is executing...");
        System.out.println("Players name is " + request.getParameter("username"));

        request.setAttribute("playersName", request.getParameter("username"));
        request.setAttribute("poolInfo", getFBPoolInfo(request, response));
        request.setAttribute("poolResults", getPoolResults(request, response));
        request.setAttribute("playersPickRecs", getPlayersPicRec(request, response));

        System.out.println("Dispatching JSP for output...");
        response.setContentType("text/html");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/HomePage.jsp");
        dispatcher.include(request, response);
        System.out.println("- Dispatched JSP successfully");

        System.out.println("HomePageServlet has finished...");
        return;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }

    private FootballPool getFBPoolInfo(HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("PoolDate is " + request.getParameter("poolDate"));
        FootballPool pool = poolData.getPoolbyDate(request.getParameter("poolDate"));
        System.out.println("Got Football pool size is " + pool.getGameList().size());
        return (pool);
    }

    private Vector getPoolResults(HttpServletRequest request, HttpServletResponse response)
    {
        Vector poolResults = new Vector();
        PoolResults results;

        results = new PoolResults("Matt Carol", 12);
        poolResults.addElement(results);
        results = new PoolResults("Bob Coffee", 11);
        poolResults.addElement(results);
        results = new PoolResults("Mary Pat", 11);
        poolResults.addElement(results);
        results = new PoolResults("Jennifer Ryan", 10);
        poolResults.addElement(results);
        results = new PoolResults("Steve Murphy", 9);
        poolResults.addElement(results);
        results = new PoolResults("Joe Andrews", 7);
        poolResults.addElement(results);

        return (poolResults);
    }

    private Vector getPlayersPicRec(HttpServletRequest request, HttpServletResponse response)
    {
        Vector pickRecList = new Vector();
        PlayerPickRec pickRec;

        pickRec = new PlayerPickRec(1, 9);
        pickRecList.addElement(pickRec);
        pickRec = new PlayerPickRec(2, 10);
        pickRecList.addElement(pickRec);
        pickRec = new PlayerPickRec(3, 6);
        pickRecList.addElement(pickRec);
        pickRec = new PlayerPickRec(4, 8);
        pickRecList.addElement(pickRec);
        pickRec = new PlayerPickRec(5, 11);
        pickRecList.addElement(pickRec);

        return (pickRecList);
    }

}
