package com.apress.tddbook.servlets;

import com.apress.tddbook.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.Vector;

public class PlayerPickServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        FootballPool fbPool = getOpenPool(request);
        request.setAttribute("openPool", fbPool);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Ch4/handsOn/src/com/apress/tddbook/jsps/PickPage.jsp");
        dispatcher.include(request, response);
        return;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }

    FootballPool getOpenPool(HttpServletRequest request)
    {
        Object obj = request.getSession().getAttribute("PoolData");
        System.out.println("Pool Data is type " + obj.getClass().getName());
        PoolData poolData = (PoolData)request.getSession().getAttribute("PoolData");
        FootballPool pool = poolData.getOpenPool();
        return(pool);
    }

    void storePlayersPicks(HttpServletRequest request)
    {
        System.out.println("Storing picks for " +  request.getParameter("username"));
        System.out.println("Game 1 picks is  " + request.getParameter("game_1_pick"));
        FootballPool fbPool = getOpenPool(request);
        Vector gameList = fbPool.getGameList();
        PlayersPicks playerPicks = new PlayersPicks(request.getParameter("username"), request.getParameter("poolDate"), gameList);
        for(int i = 0; i < gameList.size(); i++)
        {
            String gameParam = "game_" + (i+1) + "_pick";
            playerPicks.makePick(i, request.getParameter(gameParam));
        }

        PoolData poolData = (PoolData)request.getSession().getAttribute("PoolData");
        poolData.savePlayersPicks(playerPicks);

        request.getSession().setAttribute(request.getParameter("username") + "s_PlayerPicks", playerPicks);

    }
    /**
     *
     * @param request
     * @return
     */
    PlayersPicks getPlayerPicks(HttpServletRequest request)
    {
        PoolData poolData = (PoolData)request.getSession().getAttribute("PoolData");
        PlayersPicks playerPicks = poolData.getPlayersPicks(request.getParameter("username"), request.getParameter("poolDate"));
        return(playerPicks);
    }
}
