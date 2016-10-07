/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook;

import junit.framework.TestCase;

import java.util.Vector;

public class FBPoolTestData extends TestCase
{
    /**
     * Creates an instance of a Football Pool containing a set of games for a given date
     *
     * @param date Date of the FootballPool to create
     * @return An instance of the Football pool if it matches one of the predetermined dates null otherwise
     */
    public FootballPool createFbPool(String date)
    {
        if (date.equals("2004-09-11"))
        {
            FootballPool pool = new FootballPool("2004-09-11");
            pool.addGame("NY Jets", "Miami");
            pool.addGame("San Francisco", "NY Giants");
            return (pool);
        }
        else if (date.equals("2004-09-18"))
        {
            FootballPool pool = new FootballPool("2004-09-18");
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
        else if (date.equals("2004-09-25"))
        {
            FootballPool pool = new FootballPool("2004-09-18");
            pool.addGame("St. Louis", "Green Bay");
            pool.addGame("Tampa Bay", "NY Jets");
            pool.addGame("Carolina", "Indianapolis");
            pool.addGame("NY Giants", "New England");
            pool.addGame("Chicago", "New Orleans");
            pool.addGame("Oakland", "Kansas City");
            pool.addGame("Philadelphia", "Dallas");
            pool.addGame("Houston", "Washington");
            pool.addGame("Miami", "Tennessee");
            pool.addGame("Pittsburgh", "Denver");
            pool.addGame("Buffalo", "Jacksonville");
            pool.addGame("Baltimore", "Arizona");
            pool.addGame("San Francisco", "Seattle");
            pool.addGame("Atlanta", "Cleveland");
            return (pool);
        }
        return (null);
    }

    /**
     * @param fbPool Pool to verify. Must match on of the predetermine sets of data
     * @return true if the FootballPool matches one of the dates and has the correct data false otherwise
     */
    public boolean verifyPool(FootballPool fbPool)
    {
        boolean isPoolValid = false;
        if (fbPool.getPoolDate().equals("2004-09-11"))
        {
            assertEquals(2, fbPool.size());
            assertEquals("Game 1 away team", "Miami", fbPool.getHomeTeam(0));
            assertEquals("Game 2 away team", "NY Giants", fbPool.getHomeTeam(1));
            assertEquals("Game 1 home team", "NY Jets", fbPool.getAwayTeam(0));
            assertEquals("Game 2 home team", "San Francisco", fbPool.getAwayTeam(1));
            isPoolValid = true;
        }
        else if (fbPool.getPoolDate().equals("2004-09-18"))
        {
            assertEquals("Kansas City", fbPool.getAwayTeam(0));
            assertEquals("Green Bay", fbPool.getHomeTeam(0));
            assertEquals("Houston", fbPool.getAwayTeam(7));
            assertEquals("Tennessee", fbPool.getHomeTeam(1));
            assertEquals("Carolina", fbPool.getAwayTeam(2));
            assertEquals("Indianapolis", fbPool.getHomeTeam(2));
            assertEquals("NY Giants", fbPool.getAwayTeam(3));
            assertEquals("New England", fbPool.getHomeTeam(3));
            assertEquals("Chicago", fbPool.getAwayTeam(4));
            assertEquals("New Orleans", fbPool.getHomeTeam(4));
            assertEquals("Oakland", fbPool.getAwayTeam(5));
            assertEquals("Cleveland", fbPool.getHomeTeam(13));
            assertEquals("Philadelphia", fbPool.getAwayTeam(6));
            assertEquals("Dallas", fbPool.getHomeTeam(6));
            assertEquals("Tampa Bay", fbPool.getAwayTeam(1));
            assertEquals("Washington", fbPool.getHomeTeam(7));
            assertEquals("Miami", fbPool.getAwayTeam(8));
            assertEquals("Jacksonville", fbPool.getHomeTeam(8));
            assertEquals("Pittsburgh", fbPool.getAwayTeam(9));
            assertEquals("Denver", fbPool.getHomeTeam(9));
            assertEquals("Buffalo", fbPool.getAwayTeam(10));
            assertEquals("NY Jets", fbPool.getHomeTeam(1));
            assertEquals("Baltimore", fbPool.getAwayTeam(11));
            assertEquals("Arizona", fbPool.getHomeTeam(11));
            assertEquals("San Francisco", fbPool.getAwayTeam(12));
            assertEquals("Seattle", fbPool.getHomeTeam(12));
            assertEquals("Atlanta", fbPool.getAwayTeam(13));
            assertEquals("St. Louis", fbPool.getHomeTeam(0));
            isPoolValid = true;

        }
        else if (fbPool.getPoolDate().equals("2004-09-25"))
        {
            assertEquals("Kansas City", fbPool.getAwayTeam(0));
            assertEquals("Green Bay", fbPool.getHomeTeam(0));
            assertEquals("Houston", fbPool.getAwayTeam(7));
            assertEquals("Tennessee", fbPool.getHomeTeam(8));
            assertEquals("Carolina", fbPool.getAwayTeam(2));
            assertEquals("Indianapolis", fbPool.getHomeTeam(2));
            assertEquals("NY Giants", fbPool.getAwayTeam(3));
            assertEquals("New England", fbPool.getHomeTeam(3));
            assertEquals("Chicago", fbPool.getAwayTeam(4));
            assertEquals("New Orleans", fbPool.getHomeTeam(4));
            assertEquals("Oakland", fbPool.getAwayTeam(5));
            assertEquals("Cleveland", fbPool.getHomeTeam(5));
            assertEquals("Philadelphia", fbPool.getAwayTeam(6));
            assertEquals("Dallas", fbPool.getHomeTeam(6));
            assertEquals("Tampa Bay", fbPool.getAwayTeam(7));
            assertEquals("Washington", fbPool.getHomeTeam(7));
            assertEquals("Miami", fbPool.getAwayTeam(8));
            assertEquals("Jacksonville", fbPool.getHomeTeam(8));
            assertEquals("Pittsburgh", fbPool.getAwayTeam(9));
            assertEquals("Denver", fbPool.getHomeTeam(9));
            assertEquals("Buffalo", fbPool.getAwayTeam(10));
            assertEquals("NY Jets", fbPool.getHomeTeam(10));
            assertEquals("Baltimore", fbPool.getAwayTeam(11));
            assertEquals("Arizona", fbPool.getHomeTeam(11));
            assertEquals("San Francisco", fbPool.getAwayTeam(12));
            assertEquals("Seattle", fbPool.getHomeTeam(12));
            assertEquals("Atlanta", fbPool.getAwayTeam(13));
            assertEquals("St. Louis", fbPool.getHomeTeam(13));
            isPoolValid = true;

        }
        return (isPoolValid);
    }
}
