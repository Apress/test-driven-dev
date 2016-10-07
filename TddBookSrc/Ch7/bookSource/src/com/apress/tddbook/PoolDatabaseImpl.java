/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook;

import java.util.Vector;
import java.sql.*;

public class PoolDatabaseImpl implements PoolDatabase
{
    private Connection m_Conn;

    public PoolDatabaseImpl(String dbPath, String userName, String passwd) throws SQLException, ClassNotFoundException
    {
        Class driverClass = Class.forName("org.hsqldb.jdbcDriver");
        m_Conn = DriverManager.getConnection(dbPath, userName, passwd);
    }

    /**
     * Returns a list of Football Pools with the given status
     *
     * @param status Status to look for
     * @return
     */
    public Vector getPoolWithStatus(String status)
    {
        int poolID;
        Date poolDate;
        String poolWinner;
        String poolStatus;
        int gameNum;
        String homeTeam;
        String awayTeam;

        FootballPool fbPool = null;
        Vector poolList = new Vector();
        //Query pool table to get find pool with the given status
        String sqlQuery = "Select pool_id, date, winner, status from pool where status = '" + status + "'";
        try
        {
            PreparedStatement poolTBStmt = m_Conn.prepareStatement(sqlQuery);
            ResultSet poolTBResults = poolTBStmt.executeQuery();
            boolean morePoolResults = poolTBResults.next();
            while (morePoolResults)
            {
                poolID = poolTBResults.getInt(1);
                poolDate = poolTBResults.getDate(2);
                poolWinner = poolTBResults.getString(3);
                poolStatus = poolTBResults.getString(4);
                fbPool = new FootballPool(poolDate.toString());
                //Query game table to get games for this poolID
                sqlQuery = "Select game_num, home_team, away_team from game where pool_id = " + poolID + "order by game_num";
                PreparedStatement gameTBStmt = m_Conn.prepareStatement(sqlQuery);
                ResultSet gameTBResults = gameTBStmt.executeQuery();
                boolean moreGameResults = gameTBResults.next();
                while (moreGameResults)
                {
                    gameNum = gameTBResults.getInt(1);
                    homeTeam = gameTBResults.getString(2);
                    awayTeam = gameTBResults.getString(3);
                    fbPool.addGame(awayTeam, homeTeam);
                    moreGameResults = gameTBResults.next();
                }
                poolList.addElement(fbPool);
                morePoolResults = poolTBResults.next();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error getting getting Pool.  Exception is " + e);
            e.printStackTrace();
        }

        return poolList;
    }

    public Vector getAllPoolDates()
    {
        Date poolDate;
        String homeTeam;
        String awayTeam;

        Vector poolList = new Vector();
        //Query pool table to get all pools
        String sqlQuery = "Select date from pool";
        try
        {
            PreparedStatement poolTBStmt = m_Conn.prepareStatement(sqlQuery);
            ResultSet poolTBResults = poolTBStmt.executeQuery();
            boolean morePoolResults = poolTBResults.next();
            while (morePoolResults)
            {
                poolDate = poolTBResults.getDate(1);
                poolList.addElement(poolDate.toString());
                morePoolResults = poolTBResults.next();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error getting getting Pool.  Exception is " + e);
            e.printStackTrace();
        }

        return poolList;
    }

    /**
     * Gets a football pool with the specified date
     *
     * @param poolDateStr Date of the football pool to get
     * @return
     */
    public FootballPool getPoolWithDate(String poolDateStr)
    {
        int poolID;
        Date poolDate;
        String poolWinner;
        String poolStatus;
        int gameNum;
        String homeTeam;
        String awayTeam;

        FootballPool fbPool = null;
        fbPool = new FootballPool();
        System.out.println("Before FB Pool creation");
        fbPool.setPoolDate("2003-09-11");
        System.out.println("Created FB Pool");
        //Query pool table to get find pool with specified date
        String sqlQuery = "Select pool_id, date, winner, status from pool where date = '" + poolDateStr + "'";
        try
        {
            PreparedStatement poolTBStmt = m_Conn.prepareStatement(sqlQuery);
            ResultSet poolTBResults = poolTBStmt.executeQuery();
            boolean morePoolResults = poolTBResults.next();
            if (morePoolResults)
            {
                poolID = poolTBResults.getInt(1);
                poolDate = poolTBResults.getDate(2);
                poolWinner = poolTBResults.getString(3);
                poolStatus = poolTBResults.getString(4);
                poolDateStr = poolDate.toString();
                fbPool = new FootballPool(poolDateStr.toString());
                fbPool.setStatus(poolStatus);
                fbPool.setWinner(poolWinner);
                //Query game table to get games for this poolID
                sqlQuery = "Select game_num, home_team, away_team from game where pool_id = " + poolID + "order by game_num";
                PreparedStatement gameTBStmt = m_Conn.prepareStatement(sqlQuery);
                ResultSet gameTBResults = gameTBStmt.executeQuery();
                boolean moreGameResults = gameTBResults.next();
                while (moreGameResults)
                {
                    gameNum = gameTBResults.getInt(1);
                    homeTeam = gameTBResults.getString(2);
                    awayTeam = gameTBResults.getString(3);
                    fbPool.addGame(awayTeam, homeTeam);
                    moreGameResults = gameTBResults.next();
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error getting getting Pool.  Exception is " + e);
            e.printStackTrace();
        }

        return (fbPool);
    }
    public void setPoolStatus(String poolDate, String status)
    {
        String sqlQuery = "Update pool set status = '" + status + "' where pool_id = " +
                "select pool_id from pool where date = '" + poolDate + "'";
        try
        {
            PreparedStatement poolTBStmt = m_Conn.prepareStatement(sqlQuery);
            poolTBStmt.execute();
        }
        catch (SQLException e)
        {
            System.out.println("Error updating pool status.  Exception is " + e);
            e.printStackTrace();
        }
    }
    public void savePlayersPicks(PlayersPicks picks)
    {
        int poolID;
        String playersName = picks.getPlayersName();
        String poolDate = picks.getPoolDate();
        //Get pool_id for given pool date
        String sqlQuery = "Select pool_id from pool where date = '" + poolDate + "'";
        try
        {
            PreparedStatement poolTBStmt = m_Conn.prepareStatement(sqlQuery);
            ResultSet poolTBResults = poolTBStmt.executeQuery();
            boolean morePoolResults = poolTBResults.next();
            if (morePoolResults)
            {
                poolID = poolTBResults.getInt(1);
                //insert a row into the pick table for each game
                String insertStr;
                for (int i = 0; i < 14; i++)
                {
                    insertStr = "Insert into picks (pool_id, user_name, game_num, picked_team) " +
                            "Values(" + poolID + ",'" + playersName + "'," + (i + 1)
                            + ",'" + picks.getPickedTeam(i) + "'" + ")";
                    PreparedStatement pickTBStmt = m_Conn.prepareStatement(insertStr);
                    pickTBStmt.execute();
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error saving picks.  Exception is " + e);
            e.printStackTrace();
        }
    }

    public PlayersPicks getPlayersPicks(String name, String poolDate)
    {
        int poolID;
        int gameNum;
        String pickedTeam;
        String homeTeam;
        String awayTeam;
        Game game;
        Vector gameList = new Vector();
        PlayersPicks picks = null;
        //Get pool_id for given pool date
        String sqlQuery = "Select pool_id from pool where date = '" + poolDate + "'";
        try
        {
            PreparedStatement poolTBStmt = m_Conn.prepareStatement(sqlQuery);
            ResultSet poolTBResults = poolTBStmt.executeQuery();
            boolean morePoolResults = poolTBResults.next();
            if (morePoolResults)
            {
                poolID = poolTBResults.getInt(1);
                //Get the rows from the pick table that match the pool_id and name
                String SelecttStr;
                for (int i = 0; i < 14; i++)
                {
                    SelecttStr = "select g.game_num, g.home_team, g.away_team,p.picked_team from picks p, game g  " +
                            "where g.pool_id = p.pool_id and g.pool_id=" + poolID + " and user_name = '" + name + "'" +
                            " and p.game_num = g.game_num order by game_num";

                    PreparedStatement pickTBStmt = m_Conn.prepareStatement(SelecttStr);
                    ResultSet pickResults = pickTBStmt.executeQuery();
                    boolean morePickResults = pickResults.next();
                    while (morePickResults)
                    {
                        gameNum = pickResults.getInt(1);
                        homeTeam = pickResults.getString(2);
                        awayTeam = pickResults.getString(3);
                        pickedTeam = pickResults.getString(4);
                        game = new Game(awayTeam, homeTeam);
                        game.set_pickedGame(pickedTeam);
                        gameList.addElement(game);
                        morePickResults = pickResults.next();
                    }
                    picks = new PlayersPicks(name, poolDate, gameList);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error saving picks.  Exception is " + e);
            e.printStackTrace();
        }
        return (picks);
    }
}
