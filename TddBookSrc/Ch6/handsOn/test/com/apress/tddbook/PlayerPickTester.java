/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import java.util.Vector;

import org.easymock.MockControl;

public class PlayerPickTester extends TestCase {
    private MockControl poolDBMockcontrol;

    public PlayerPickTester(String s) {
        super(s);
    }

    /**
     * Tests to make sure we can correctly get a list of games from the currently open football pool
     */
    public void testGetOpenPoolGameList()
    {

        //Get mock object
        PoolDatabase mockPoolDB = (PoolDatabase)poolDBMockcontrol.getMock();
        //Train the mock object
        try
        {
            mockPoolDB.getPoolWithStatus("Open");
        }
        catch (NoPoolFoundException e)
        {
            //No need to do anything here since setting up the mock will not throw an exception
        }
        //Create return value for mock method
        Vector mockPoolList = createFbPoolList();
        poolDBMockcontrol.setReturnValue(mockPoolList);
        //Training over set the mock to replay trained behavior when called
        poolDBMockcontrol.replay();


        PoolData poolData = new PoolDataImpl();
        //Set PoolDB to mock Object
        poolData.set_poolDB(mockPoolDB);

        FootballPool pool = poolData.getOpenPool();
        assertEquals("Kansas City",pool.getAwayTeam(0));
        assertEquals("Green Bay",pool.getHomeTeam(0));
        assertEquals("Houston",pool.getAwayTeam(1));
        assertEquals("Tennessee",pool.getHomeTeam(1));
        assertEquals("Carolina",pool.getAwayTeam(2));
        assertEquals("Indianapolis",pool.getHomeTeam(2));
        assertEquals("NY Giants",pool.getAwayTeam(3));
        assertEquals("New England",pool.getHomeTeam(3));
        assertEquals("Chicago",pool.getAwayTeam(4));
        assertEquals("New Orleans",pool.getHomeTeam(4));
        assertEquals("Oakland",pool.getAwayTeam(5));
        assertEquals("Cleveland",pool.getHomeTeam(5));
        assertEquals("Philadelphia",pool.getAwayTeam(6));
        assertEquals("Dallas",pool.getHomeTeam(6));
        assertEquals("Tampa Bay",pool.getAwayTeam(7));
        assertEquals("Washington",pool.getHomeTeam(7));
        assertEquals("Miami",pool.getAwayTeam(8));
        assertEquals("Jacksonville",pool.getHomeTeam(8));
        assertEquals("Pittsburgh",pool.getAwayTeam(9));
        assertEquals("Denver",pool.getHomeTeam(9));
        assertEquals("Buffalo",pool.getAwayTeam(10));
        assertEquals("NY Jets",pool.getHomeTeam(10));
        assertEquals("Baltimore",pool.getAwayTeam(11));
        assertEquals("Arizona",pool.getHomeTeam(11));
        assertEquals("San Francisco",pool.getAwayTeam(12));
        assertEquals("Seattle",pool.getHomeTeam(12));
        assertEquals("Atlanta",pool.getAwayTeam(13));
        assertEquals("St. Louis",pool.getHomeTeam(13));

        poolDBMockcontrol.verify();
    }

     /**
     * Tests to make sure we can handles the empty list error correctly when trying to get a list of games
      * from the currently open football pool
     */
    public void testGetOpenPoolEmptyListError() {
        //Get mock object
        PoolDatabase mockPoolDB = (PoolDatabase)poolDBMockcontrol.getMock();
        //Train the mock object
         try
         {
             mockPoolDB.getPoolWithStatus("Open");
         }
         catch (NoPoolFoundException e)
         {
             //No need to do anything here since setting up the mock will not throw an exception
         }
         //Create return value for mock method that will cause an error because the list is empty
        Vector mockPoolList = new Vector();
        poolDBMockcontrol.setReturnValue(mockPoolList);
        //Training over set the mock to replay trained behavior when called
        poolDBMockcontrol.replay();

        PoolDataImpl poolData = new PoolDataImpl();
        //Set PoolDB to mock Object
        poolData.set_poolDB(mockPoolDB);

        FootballPool pool = poolData.getOpenPool();
        assertEquals(null, pool);

        poolDBMockcontrol.verify();
    }

      /**
     * Tests to make sure we can handles the list too big error correctly when trying to get a list of games
      * from the currently open football pool
     */
    public void testGetOpenPoolListSizeError() {
        //Get mock object
        PoolDatabase mockPoolDB = (PoolDatabase)poolDBMockcontrol.getMock();
        //Train the mock object
          try
          {
              mockPoolDB.getPoolWithStatus("Open");
          }
          catch (NoPoolFoundException e)
          {
              //No need to do anything here since setting up the mock will not throw an exception
          }
          //Create return value for mock method that will cause an error because the list is too big
        Vector mockPoolList = new Vector();
        mockPoolList.addElement("element1");
        mockPoolList.addElement("element2");
        poolDBMockcontrol.setReturnValue(mockPoolList);
        //Training over set the mock to replay trained behavior when called
        poolDBMockcontrol.replay();

        PoolDataImpl poolData = new PoolDataImpl();
        //Set PoolDB to mock Object
        poolData.set_poolDB(mockPoolDB);

        FootballPool pool = poolData.getOpenPool();
        assertEquals(null, pool);

        poolDBMockcontrol.verify();
    }

    public void testStorePlayersPicks()
    {
        //Get mock object
        PoolDatabase mockPoolDB = (PoolDatabase)poolDBMockcontrol.getMock();
        //Train the mock object
        try
        {
            mockPoolDB.getPoolWithStatus("Open");
        }
        catch (NoPoolFoundException e)
        {
            //No need to do anything here since setting up the mock will not throw an exception
        }
        //Create return value for mock method
        Vector mockPoolList = createFbPoolList();
        poolDBMockcontrol.setReturnValue(mockPoolList);
        PlayersPicks mockplayerPicks = createPlayersPicks();
        try
        {
            mockPoolDB.savePlayersPicks(mockplayerPicks);
        }
        catch (SavePlayerPickException e)
        {
            //No need to do anything here since setting up the mock will not throw an exception
        }
        poolDBMockcontrol.setMatcher(MockControl.ALWAYS_MATCHER);
        try
        {
            mockPoolDB.getPlayersPicks("EddyC", "9/17/2003");
        }
        catch (PicksNotFoundException e)
        {
            //No need to do anything here since setting up the mock will not throw an exception
        }
        poolDBMockcontrol.setReturnValue(createPlayersPicks());
        //Training over set the mock to replay trained behavior when called
        poolDBMockcontrol.replay();

        PoolDataImpl poolData = new PoolDataImpl();
        //Set PoolDB to mock Object
        poolData.set_poolDB(mockPoolDB);
        //Get list of games for the open pool
        FootballPool pool = poolData.getOpenPool();
        Vector gameList = pool.getGameList();
        //For each game make a pick
        PlayersPicks playerPicks = new PlayersPicks("EddyC", "9/17/2003", gameList);
        for(int i = 0; i < gameList.size(); i++)
        {
            Game game = (Game) gameList.elementAt(i);
            playerPicks.makePick(i, game.getHomeTeam());
        }
        try
        {
            poolData.savePlayersPicks(playerPicks);
        }
        catch (SavePlayerPickException e)
        {
            //No need to do anything here since setting up the mock will not throw an exception
        }
        //Get the user picks and verify that they were set correctly
        PlayersPicks savedPicks = poolData.getPlayersPicks("EddyC", "9/17/2003");
        for(int i = 0; i < savedPicks.size(); i++)
        {
            String homeTeam = savedPicks.getHomeTeam(i);
            assertEquals(homeTeam, savedPicks.getPickedTeam(i));
        }

        poolDBMockcontrol.verify();
    }
    public void setUp() {
        poolDBMockcontrol = MockControl.createControl(PoolDatabase.class);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(PlayerPickTester.class);
        return suite;
    }

    private Vector createFbPoolList()
    {
        Vector poolList = new Vector();
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
        poolList.addElement(pool);
        return (poolList);
    }
    private PlayersPicks createPlayersPicks()
    {
        FootballPool fbPool = (FootballPool)createFbPoolList().elementAt(0);
        Vector gameList = fbPool.getGameList();
        PlayersPicks playerPicks = new PlayersPicks("EddyC", "9/17/2003", gameList);
        for(int i = 0; i < gameList.size(); i++)
        {
            Game game = (Game) gameList.elementAt(i);
            playerPicks.makePick(i, game.getHomeTeam());
        }
        return(playerPicks);
    }
    public static final void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
