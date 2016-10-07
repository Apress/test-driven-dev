package com.apress.tddbook;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.Vector;

import org.easymock.MockControl;


public class PostResultsTester extends TestCase
{
    private MockControl poolDBMockcontrol;

    public PostResultsTester(String s)
    {
        super(s);
    }

    public void setUp()
    {
        poolDBMockcontrol = MockControl.createControl(PoolDatabase.class);
    }

    /**
     * Test used to insure the scores can be posted for a Game
     */
    public void testPostGameScore()
    {
        Game game = new Game("Kansas City", "Green Bay");
        game.postScore(28, 31);
        int homeScore = game.getHomeTeamScore();
        assertEquals(31, homeScore);
        int awayScore = game.getAwayTeamScore();
        assertEquals(28, awayScore);
        String winningTeam = game.getWinningTeamName();
        assertEquals("Green Bay", winningTeam);
    }

    /**
     * Test used to insure that a FootballPool can be updated with the scores of the games
     * and that the winning team can be calculated correctly
     */
    public void testPostGameScoresToPool()
    {
      //Get mock object
        PoolDatabase mockPoolDB = (PoolDatabase)poolDBMockcontrol.getMock();
        //Train the mock object
        mockPoolDB.getPoolWithStatus("Open");
        //Create return value for mock method
        Vector mockPoolList = createFbPoolList();
        poolDBMockcontrol.setReturnValue(mockPoolList);
        //Training over set the mock to replay trained behavior when called
        poolDBMockcontrol.replay();

        PoolData poolData = new PoolData();
        //Set PoolDB to mock Object
        poolData.set_poolDB(mockPoolDB);
        //Get Pool
        FootballPool pool = poolData.getOpenPool();
        //Post score for some games
        pool.postGameScore(0,28,31);
        pool.postGameScore(3,7,3);
        pool.postGameScore(5,8,31);
        pool.postGameScore(10,14,7);

        //Verify scores were posted correctly
        Vector gameList = pool.getGameList();
        Game game = (Game)gameList.elementAt(0);
        assertEquals(28,game.getAwayTeamScore());
        assertEquals(31,game.getHomeTeamScore());
        assertEquals("Green Bay", game.getWinningTeamName());

        game = (Game)gameList.elementAt(3);
        assertEquals(7,game.getAwayTeamScore());
        assertEquals(3,game.getHomeTeamScore());
        assertEquals("NY Giants", game.getWinningTeamName());

        game = (Game)gameList.elementAt(5);
        assertEquals(8,game.getAwayTeamScore());
        assertEquals(31,game.getHomeTeamScore());
        assertEquals("Cleveland", game.getWinningTeamName());

        game = (Game)gameList.elementAt(10);
        assertEquals(14,game.getAwayTeamScore());
        assertEquals(7,game.getHomeTeamScore());
        assertEquals("Buffalo", game.getWinningTeamName());

    }

    /**
     * Tests to make sure that the winning player is correctly picked after posting the game scores
     */
    public void testCalcWinner()
    {
        //Get mock object
        PoolDatabase mockPoolDB = (PoolDatabase)poolDBMockcontrol.getMock();
        //Train the mock object
        mockPoolDB.getPoolWithDate("2003-09-18");
        Vector mockPoolList = createFbPoolList();
        poolDBMockcontrol.setReturnValue(mockPoolList.elementAt(0));
        mockPoolDB.getAllPicks("2003-09-18");
        poolDBMockcontrol.setReturnValue(createPlayersPicks());
        mockPoolDB.savePoolResults(null);
        poolDBMockcontrol.setMatcher(MockControl.ALWAYS_MATCHER);
        //Training over set the mock to replay trained behavior when called
        poolDBMockcontrol.replay();
        PoolData poolData = new PoolData();
        //Set PoolDB to mock Object
        poolData.set_poolDB(mockPoolDB);
        //Calculate the pool results
        PoolResults results = poolData.calcPoolResults("2003-09-18");
        // Check the winner and results for each player to make sure they are correct
        assertEquals("ScottM", results.winningPlayersName);
        Vector resultsList = results.playerResultsList;
        PlayerResultsData playerResults = (PlayerResultsData)resultsList.elementAt(0);
        assertEquals("EddyC", playerResults.playerName);
        assertEquals(9, playerResults.correctPicks);

        playerResults = (PlayerResultsData)resultsList.elementAt(1);
        assertEquals("ScottM", playerResults.playerName);
        assertEquals(9, playerResults.correctPicks);

        playerResults = (PlayerResultsData)resultsList.elementAt(2);
        assertEquals("JenC", playerResults.playerName);
        assertEquals(5, playerResults.correctPicks);
    }

    /**
     * Tests the getPoolResults to make sure we can get a set of Pool results for a Pool
     */
    public void testGetPoolResults()
    {
        //Get mock object
        PoolDatabase mockPoolDB = (PoolDatabase)poolDBMockcontrol.getMock();
        //Train the mock object
        mockPoolDB.getPoolResults("2003-09-18");
        poolDBMockcontrol.setReturnValue(createPoolResults());
        //Training over set the mock to replay trained behavior when called
        poolDBMockcontrol.replay();

        PoolData poolData = new PoolData();
        //Set PoolDB to mock Object
        poolData.set_poolDB(mockPoolDB);

        //Get pool results for a particular pool
        PoolResults results = poolData.getPoolResults("2003-09-18");

        //Make sure the results match the expected value
        assertEquals("ScottM", results.winningPlayersName);
        Vector resultsList = results.playerResultsList;
        PlayerResultsData playerResults = (PlayerResultsData)resultsList.elementAt(0);
        assertEquals("EddyC", playerResults.playerName);
        assertEquals(9, playerResults.correctPicks);

        playerResults = (PlayerResultsData)resultsList.elementAt(1);
        assertEquals("ScottM", playerResults.playerName);
        assertEquals(9, playerResults.correctPicks);

        playerResults = (PlayerResultsData)resultsList.elementAt(2);
        assertEquals("JenC", playerResults.playerName);
        assertEquals(5, playerResults.correctPicks);
    }
    private Vector createFbPoolList()
    {
        Vector poolList = new Vector();
        FootballPool pool = new FootballPool("2003-09-18");
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

        pool.postGameScore(0,28,31);
        pool.postGameScore(1,8,24);
        pool.postGameScore(2,3,17);
        pool.postGameScore(3,21,31);
        pool.postGameScore(4,13,21);
        pool.postGameScore(5,28,35);
        pool.postGameScore(6,28,3);
        pool.postGameScore(7,14,3);
        pool.postGameScore(8,31,28);
        pool.postGameScore(9,13,21);
        pool.postGameScore(10,10,17);
        pool.postGameScore(11,21,7);
        pool.postGameScore(12,3,13);
        pool.postGameScore(13,24,14);
        try
        {
            pool.setTieBreakerGame(13);
        }
        catch (NoSuchGameException e)
        {
            e.printStackTrace();
        }

        poolList.addElement(pool);
        return (poolList);
    }

    private Vector createPlayersPicks()
    {
        Vector pickList = new Vector();
        FootballPool fbPool = (FootballPool)createFbPoolList().elementAt(0);
        Vector gameList = fbPool.getGameList();
        PlayersPicks playerPicks = new PlayersPicks("EddyC", "2003-09-18", gameList);
        for(int i = 0; i < gameList.size(); i++)
        {
            Game game = (Game) gameList.elementAt(i);
            playerPicks.makePick(i, game.getHomeTeam());
        }
        playerPicks.setTiebreakScore(31);
        pickList.addElement(playerPicks);
        fbPool = (FootballPool)createFbPoolList().elementAt(0);
        gameList = fbPool.getGameList();
        playerPicks = new PlayersPicks("ScottM", "2003-09-18", (Vector)gameList.clone());
        for(int i = 0; i < gameList.size(); i++)
        {
            Game game = (Game) gameList.elementAt(i);
            playerPicks.makePick(i, game.getHomeTeam());
        }
        playerPicks.setTiebreakScore(34);
        pickList.addElement(playerPicks);
        fbPool = (FootballPool)createFbPoolList().elementAt(0);
        gameList = fbPool.getGameList();
        playerPicks = new PlayersPicks("JenC", "2003-09-18", gameList);
        for(int i = 0; i < gameList.size(); i++)
        {
            Game game = (Game) gameList.elementAt(i);
            playerPicks.makePick(i, game.getAwayTeam());
        }
        playerPicks.setTiebreakScore(21);
        pickList.addElement(playerPicks);

        return(pickList);
    }
    /**
     * Creates a set of Pool results to be used in the tests
     *
     * @return
     */
    private PoolResults createPoolResults()
    {
        Vector resultsList = new Vector();
        PlayerResultsData playerResults = new PlayerResultsData("EddyC", 9);
        resultsList.addElement(playerResults);

        playerResults = new PlayerResultsData("ScottM", 9);
        resultsList.addElement(playerResults);

        playerResults = new PlayerResultsData("JenC", 5);
        resultsList.addElement(playerResults);

        PoolResults results = new PoolResults("2003-09-18", "ScottM", resultsList);
        return(results);
    }
    public static Test suite()
    {
        TestSuite suite = new TestSuite(PostResultsTester.class);
        return suite;
    }

    public static final void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }
}
