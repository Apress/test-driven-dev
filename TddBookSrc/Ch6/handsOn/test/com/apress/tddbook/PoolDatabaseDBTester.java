package com.apress.tddbook;

import org.dbunit.DatabaseTestCase;
import org.dbunit.Assertion;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.DatabaseConnection;

import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class PoolDatabaseDBTester extends DatabaseTestCase
{
    public PoolDatabaseDBTester(String name)
    {
        super(name);
    }

    protected IDatabaseConnection getConnection() throws Exception
    {
        Class driverClass = Class.forName("org.hsqldb.jdbcDriver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");
        return new DatabaseConnection(jdbcConnection);
    }

    protected IDataSet getDataSet() throws Exception
    {
        String dbFileDir = System.getProperty("DBDataFileDir");
        return new FlatXmlDataSet(new FileInputStream(dbFileDir + "\\dataset1.xml"));
    }

    /**
     * Tests the getPoolWithStatus method of the PoolDatabase with a real database
     *
     * @throws Exception
     */
    public void testGetFBPoolFromDB() throws Exception
    {
        PoolDatabase poolDB = new PoolDatabaseImpl("jdbc:hsqldb:hsql://localhost", "sa", "");
        Vector openPoolList = poolDB.getPoolWithStatus("Open");
        //DB should only have one open pool
        assertEquals(1, openPoolList.size());
        //Get pool and check to make sure it has the correct games
        FootballPool openPool = (FootballPool) openPoolList.elementAt(0);
        assertEquals("Kansas City", openPool.getAwayTeam(0));
        assertEquals("Green Bay", openPool.getHomeTeam(0));
        assertEquals("Houston", openPool.getAwayTeam(1));
        assertEquals("Tennessee", openPool.getHomeTeam(1));
        assertEquals("Carolina", openPool.getAwayTeam(2));
        assertEquals("Indianapolis", openPool.getHomeTeam(2));
        assertEquals("NY Giants", openPool.getAwayTeam(3));
        assertEquals("New England", openPool.getHomeTeam(3));
        assertEquals("Chicago", openPool.getAwayTeam(4));
        assertEquals("New Orleans", openPool.getHomeTeam(4));
        assertEquals("Oakland", openPool.getAwayTeam(5));
        assertEquals("Cleveland", openPool.getHomeTeam(5));
        assertEquals("Philadelphia", openPool.getAwayTeam(6));
        assertEquals("Dallas", openPool.getHomeTeam(6));
        assertEquals("Tampa Bay", openPool.getAwayTeam(7));
        assertEquals("Washington", openPool.getHomeTeam(7));
        assertEquals("Miami", openPool.getAwayTeam(8));
        assertEquals("Jacksonville", openPool.getHomeTeam(8));
        assertEquals("Pittsburgh", openPool.getAwayTeam(9));
        assertEquals("Denver", openPool.getHomeTeam(9));
        assertEquals("Buffalo", openPool.getAwayTeam(10));
        assertEquals("NY Jets", openPool.getHomeTeam(10));
        assertEquals("Baltimore", openPool.getAwayTeam(11));
        assertEquals("Arizona", openPool.getHomeTeam(11));
        assertEquals("San Francisco", openPool.getAwayTeam(12));
        assertEquals("Seattle", openPool.getHomeTeam(12));
        assertEquals("Atlanta", openPool.getAwayTeam(13));
        assertEquals("St. Louis", openPool.getHomeTeam(13));
    }

    /**
     * Tests the savePlayerPicks of the PoolDatabase with a real DB
     */
    public void testSavePlayerPicksToDB() throws Exception, ClassNotFoundException
    {
        PoolDatabase poolDB = new PoolDatabaseImpl("jdbc:hsqldb:hsql://localhost", "sa", "");
        Vector openPoolList = poolDB.getPoolWithStatus("Open");
        FootballPool openPool = (FootballPool) openPoolList.elementAt(0);
        PlayersPicks playPicks = createPlayersPicks(openPool);
        //Save the player picks to the database
        poolDB.savePlayersPicks(playPicks);

        //verify that the database contains the correct data
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("Picks");

        // Load expected data from an XML dataset
        String dbFileDir = System.getProperty("DBDataFileDir");
        IDataSet expectedDataSet = new FlatXmlDataSet(new FileInputStream(dbFileDir + "\\expectedDataset1.xml"));
        ITable expectedTable = expectedDataSet.getTable("Picks");

        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, actualTable);

    }

    public void testGetPlayerPicksFromDB() throws Exception, ClassNotFoundException
    {
        // Reset the database contents with the data needed for the test to pass
        String dbFileDir = System.getProperty("DBDataFileDir");
        IDataSet dataSet = new FlatXmlDataSet(new FileInputStream(dbFileDir + "\\dataset2.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), dataSet);
        String playerName = "BOBG";
        String poolDate = "2003-09-17";
        PoolDatabase poolDB = new PoolDatabaseImpl("jdbc:hsqldb:hsql://localhost", "sa", "");
        //Get picks from Database
        PlayersPicks picks = poolDB.getPlayersPicks(playerName, poolDate);
        //Verify that the picks are correct
        assertEquals("Green Bay", picks.getPickedTeam(0));
        assertEquals("Tennessee", picks.getPickedTeam(1));
        assertEquals("Indianapolis", picks.getPickedTeam(2));
        assertEquals("New England", picks.getPickedTeam(3));
        assertEquals("New Orleans", picks.getPickedTeam(4));
        assertEquals("Cleveland", picks.getPickedTeam(5));
        assertEquals("Dallas", picks.getPickedTeam(6));
        assertEquals("Washington", picks.getPickedTeam(7));
        assertEquals("Jacksonville", picks.getPickedTeam(8));
        assertEquals("Denver", picks.getPickedTeam(9));
        assertEquals("NY Jets", picks.getPickedTeam(10));
        assertEquals("Arizona", picks.getPickedTeam(11));
        assertEquals("Seattle", picks.getPickedTeam(12));
        assertEquals("St. Louis", picks.getPickedTeam(13));

    }

    /**
     * Tests the getPoolWithStatus method of the PoolDatabase with a real database that doesn't have an open pool
     *
     * @throws Exception
     */
    public void testGetFBPoolOpenError() throws Exception
    {
        // Reset the database contents with the data with no open pool
        String dbFileDir = System.getProperty("DBDataFileDir");
        IDataSet dataSet = new FlatXmlDataSet(new FileInputStream(dbFileDir + "\\dataset3.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), dataSet);
        PoolDatabase poolDB = new PoolDatabaseImpl("jdbc:hsqldb:hsql://localhost", "sa", "");

        Vector openPoolList = null;
        try
        {
            openPoolList = poolDB.getPoolWithStatus("Open");
            fail("getPoolWithStatus did not throw correct exception");
        }
        catch (NoPoolFoundException e)
        {
            //if we get here then the correct exception was thrown

        }
    }

    /**
     * Tests the savePlayerPicks of the PoolDatabase with a real DB with a bad football pool
     */
    public void testSavePlayerPicksBadPool() throws Exception, ClassNotFoundException
    {
        PoolDatabase poolDB = new PoolDatabaseImpl("jdbc:hsqldb:hsql://localhost", "sa", "");

        FootballPool pool = createFbPool();
        PlayersPicks playPicks = createBadPlayersPicks(pool);
        //Save the player picks to the database
        try
        {
            poolDB.savePlayersPicks(playPicks);
            fail("savePlayerPicks did not throw correct exception");
        }
        catch (SavePlayerPickException e)
        {
            //if we get here then the correct exception was thrown

        }
    }

    public void testGetPlayerPicksBadPlayer() throws Exception, ClassNotFoundException
    {
        // Reset the database contents with the data needed for the test to pass
        String dbFileDir = System.getProperty("DBDataFileDir");
        IDataSet dataSet = new FlatXmlDataSet(new FileInputStream(dbFileDir + "\\dataset2.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), dataSet);
        String playerName = "FrankG";
        String poolDate = "2003-09-17";
        PoolDatabase poolDB = new PoolDatabaseImpl("jdbc:hsqldb:hsql://localhost", "sa", "");
        //Get picks from Database
        try
        {
            PlayersPicks picks = poolDB.getPlayersPicks(playerName, poolDate);
            fail("getPlayerPicks did not throw correct exception");
        }
        catch(PicksNotFoundException e)
        {

        }

    }

    private PlayersPicks createPlayersPicks(FootballPool fbPool)
    {
        Vector gameList = fbPool.getGameList();
        PlayersPicks playerPicks = new PlayersPicks("EDDYC", "2003-09-17", gameList);
        for (int i = 0; i < gameList.size(); i++)
        {
            Game game = (Game) gameList.elementAt(i);
            playerPicks.makePick(i, game.getHomeTeam());
        }
        return (playerPicks);
    }

    private PlayersPicks createBadPlayersPicks(FootballPool fbPool)
    {
        Vector gameList = fbPool.getGameList();
        PlayersPicks playerPicks = new PlayersPicks("WayneC", "2004-08-13", gameList);
        for (int i = 0; i < gameList.size(); i++)
        {
            Game game = (Game) gameList.elementAt(i);
            playerPicks.makePick(i, game.getHomeTeam());
        }
        return (playerPicks);
    }
    private FootballPool createFbPool()
    {
        FootballPool pool = new FootballPool("8/13/2004");
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
}
