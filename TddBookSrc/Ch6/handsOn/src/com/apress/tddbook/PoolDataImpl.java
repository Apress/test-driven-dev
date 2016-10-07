package com.apress.tddbook;

import java.util.Vector;

public class PoolDataImpl implements PoolData
{
    /**
     * handle to the database access methods
     */
    PoolDatabase _poolDB;

    public PoolDataImpl()
    {

    }

    /**
     * Gets the currently open football pool.  There should be only one pool.  If there
     * is more than one pool then this is an error
     *
     * @return The currently open football pool or null if there are no open pools or more than
     *         one open football pool
     */
    public FootballPool getOpenPool()
    {
        FootballPool fbPool = null;
        Vector poolList = null;
        try
        {
            poolList = _poolDB.getPoolWithStatus("Open");
            if (poolList.size() == 1)
            {
                fbPool = (FootballPool) poolList.elementAt(0);
            }
        }
        catch (NoPoolFoundException e)
        {
          System.out.println("Error. No pool found");
        }

        return (fbPool);
    }

    public FootballPool getPoolbyDate(String date)
    {
        return (_poolDB.getPoolWithDate(date));
    }

    public void savePlayersPicks(PlayersPicks playerPicks) throws SavePlayerPickException
    {
        String name = playerPicks.getPlayersName();
        String date = playerPicks.getPoolDate();
        _poolDB.savePlayersPicks(playerPicks);
    }

    public PlayersPicks getPlayersPicks(String playersName, String poolDate)
    {
        try
        {
            return (_poolDB.getPlayersPicks(playersName, poolDate));
        }
        catch (PicksNotFoundException e)
        {
            System.out.println("Error. No picks found");
            return(null);
        }
    }

    public PoolDatabase get_poolDB()
    {
        return _poolDB;
    }

    public void set_poolDB(PoolDatabase _poolDB)
    {
        this._poolDB = _poolDB;
    }
}
