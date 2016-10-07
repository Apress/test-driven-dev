package com.apress.tddbook;

import java.util.Vector;


public class FootballPool
{
    private Vector gameList = new Vector();
    private int _tieBreakerGame = 100;
    private String m_poolDate;
    private String m_status = "Unknown";

    public FootballPool(String poolDate)
    {
        m_poolDate = poolDate;
    }

    public FootballPool()
    {
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

    public void setPoolDate(String poolDate)
    {
        m_poolDate = poolDate;
    }

    public void setTieBreakerGame(int num) throws NoSuchGameException
    {
        if ((num < 0) || (num > gameList.size()))
        {
            throw(new NoSuchGameException());
        }
        _tieBreakerGame = num;

    }
}
