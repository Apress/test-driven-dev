package com.apress.tddbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Vector;
import java.io.Serializable;

/**
 *
 *
 **/
public class FootballPool  implements Serializable
{
    private Vector gameList = new Vector();
    private int _tieBreakerGame;
    private String m_poolDate;
    private String m_status = "Unknown";
    private String m_winner = "";

    public void setStatus(String m_status)
    {
        this.m_status = m_status;
    }

    public FootballPool()
    {

    }

    public FootballPool(String poolDate)
    {
        m_poolDate = poolDate;
    }


    public void addGame(String awayTeam, String homeTeam)
    {
        gameList.addElement(new Game(awayTeam, homeTeam));
    }


    public String getHomeTeam(int i)
    {
        Game game = (Game) gameList.elementAt(i);
        return game.getHomeTeam();
    }


    public String getAwayTeam(int i)
    {
        Game game = (Game) gameList.elementAt(i);
        return game.getAwayTeam();
    }

    public int size()
    {
        return (gameList.size());
    }

    public int getTieBreakerGame()
    {
        return _tieBreakerGame;
    }

    public Vector getGameList()
    {
        return gameList;
    }

    /**
     * Sets the status of the pool to open
     */
    public void openPool()
    {
        m_status = "Open";
    }

    public String getStatus()
    {
        return (m_status);
    }

    public void setTieBreakerGame(int num) throws NoSuchGameException
    {
        if ((num < 0) || (num > gameList.size()))
        {
            throw(new NoSuchGameException());
        }
        _tieBreakerGame = num;

    }

    public String getWinner()
    {
        return m_winner;
    }

    public void setWinner(String m_winner)
    {
        this.m_winner = m_winner;
    }

    public String getPoolDate()
    {
        return m_poolDate;
    }

    public void setPoolDate(String poolDate)
    {
        m_poolDate = poolDate;
    }

}
