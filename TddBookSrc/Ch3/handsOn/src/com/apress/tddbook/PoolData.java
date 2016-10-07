
package com.apress.tddbook;

import java.util.Vector;

public class PoolData
{
    /** handle to the database access methods */
    PoolDatabase m_poolDB;
    public PoolData()
    {

    }
    /**
     * Gets the currently open football pool.  There should be only one pool.  If there
     * is more than one pool then this is an error
     *
     * @return The currently open football pool or null if there are no open pools or more than
     * one open football pool
     */
    public FootballPool getOpenPool()
    {
        FootballPool fbPool = null;
        Vector poolList = m_poolDB.getPoolWithStatus("Open");
        if(poolList.size() == 1)
        {
            fbPool = (FootballPool)poolList.elementAt(0);
        }
        return(fbPool);
    }

    public void savePlayersPicks(PlayersPicks playerPicks)
    {
        m_poolDB.savePlayersPicks(playerPicks);
    }
    public PlayersPicks getPlayersPicks(String playersName, String poolDate)
    {
        return(m_poolDB.getPlayersPicks(playersName, poolDate));
    }
    public void updateFootballPool(FootballPool pool)
    {
        m_poolDB.updatePool(pool);
    }

    /**
     * Calcuates the winner of the weekly football pool given the date of the pool. This method assumes that
     * the scores have been posted for the pool for this date
     *
     * @param date
     * @return   PoolResults or null if there is a problem with the calculation
     */
    public PoolResults calcPoolResults(String date)
    {
        int mostCorrectPicks = 0;

        Vector resultsList = new Vector();
        //Get the Football Pool for the specified date
        FootballPool pool = m_poolDB.getPoolWithDate(date);
        //Get all the player picks for the specified date
        Vector pickList = m_poolDB.getAllPicks(date);
        // for each set of player picks calculate the correct number of picks
        //Also calculate the most number of correct picks
        for(int i = 0; i < pickList.size(); i++)
        {
            PlayersPicks picks = (PlayersPicks)pickList.elementAt(i);
            PlayerResultsData playerResults = calcResults(picks, pool);
            resultsList.addElement(playerResults);
            if(playerResults.correctPicks > mostCorrectPicks)
            {
                mostCorrectPicks = playerResults.correctPicks;
            }
        }
        String winner = findPoolWinner(mostCorrectPicks, pickList, pool, resultsList);

        //Create Pool Results
        PoolResults results = new PoolResults(date, winner, resultsList);
        //Store results in database
        m_poolDB.savePoolResults(results);
        //Return results
        return(results);
    }
    /**
     * Picks the winner of the pool based on the number of correct picks an the tie breaker
     *
     * @param pickList
     * @param resultsList
     * @return
     */
    public String findPoolWinner(int mostCorrectPicks, Vector pickList, FootballPool pool, Vector resultsList)
    {
        Vector bestPlayerList = new Vector();

        //First go through the resultList and find all the players with the mostCorrectPicks
        for(int i = 0; i < resultsList.size(); i++)
        {
            PlayerResultsData results = (PlayerResultsData) resultsList.elementAt(i);
            if(results.correctPicks == mostCorrectPicks)
            {
                bestPlayerList.addElement(results.playerName);
            }
        }
        //if the list contains only one name then that person is the winner
        if(bestPlayerList.size() == 1)
        {
            return((String)bestPlayerList.elementAt(0));
        }
        else // else we have to use the tie break score
        {
            return(getTieBreakWinner(bestPlayerList, pickList, pool));
        }
    }
    /**
     * Takes a list of player names and picks and returns the name of the player with the tie break
     * closes to the combine score of the tie break game
     *
     * @param bestPlayerList
     * @param pickList
     * @return
     */

    private String getTieBreakWinner(Vector bestPlayerList, Vector pickList, FootballPool pool)
    {
        String winnersName = "Nobody";
        int closestTieBreakScore = 10000;

        // find combined score of tiebreak game
        Vector gameList = pool.getGameList();
        Game tieBreakGame = (Game)gameList.elementAt(pool.getTieBreakerGame());
        int actualTieBreakScore = tieBreakGame.getHomeTeamScore() + tieBreakGame.getAwayTeamScore();


        // find player with tiebreak score closest to actual tiebreak score
        for(int i = 0; i < bestPlayerList.size(); i++)
        {
            String playersName = (String)bestPlayerList.elementAt(i);
            PlayersPicks picks = findPlayersPicks(playersName, pickList);
            int tieBreakDiff = actualTieBreakScore - picks.getTiebreakScore();
            if((tieBreakDiff > 0) && (tieBreakDiff < closestTieBreakScore))
            {
                winnersName = playersName;
                closestTieBreakScore = tieBreakDiff;
            }
        }
        return(winnersName);
    }

    /**
     * Finds the a set of players picks given a players name
     *
     * @param playersName
     * @param pickList
     * @return
     */
    private PlayersPicks findPlayersPicks(String playersName, Vector pickList)
    {
        PlayersPicks picks = null;

        for(int i = 0; i < pickList.size(); i++)
        {
            picks = (PlayersPicks)pickList.elementAt(i);
            if(picks.getPlayersName().equals(playersName))
            {
                return(picks);
            }
        }
        // if we get here then the players picks were not found so return null
        return(null);
    }
    public Vector getAllPicks(String date)
    {
        Vector pickList = new Vector();

        return(pickList);
    }

    public PoolResults getPoolResults(String date)
    {
        return(m_poolDB.getPoolResults(date));
    }
    public PoolDatabase get_poolDB()
    {
        return m_poolDB;
    }

    public void set_poolDB(PoolDatabase _poolDB)
    {
        this.m_poolDB = _poolDB;
    }

    /**
     * Given a set of player picks and a Football pool this methods calculates the number of correct picks
     * made by the player and returns the results in a PlayerResultsData object
     *
     * @param picks
     * @param pool
     * @return
     */
    private PlayerResultsData calcResults(PlayersPicks picks, FootballPool pool)
    {
        Vector gameList = pool.getGameList();
        int numCorrectPicks = 0;

        for(int i = 0; i < gameList.size(); i++)
        {
            Game game = (Game) gameList.elementAt(i);
            String winningTeamName = game.getWinningTeamName();
            if(winningTeamName.equals(picks.getPickedTeam(i)))
            {
                numCorrectPicks++;
            }
        }
        return(new PlayerResultsData(picks.getPlayersName(), numCorrectPicks));
    }
}
