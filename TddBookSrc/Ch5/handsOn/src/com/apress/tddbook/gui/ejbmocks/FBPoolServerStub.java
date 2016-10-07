/*
 * Mock implementation of the FBPoolServer interface
 */
package com.apress.tddbook.gui.ejbmocks;

import com.apress.tddbook.FootballPool;

import java.util.Vector;

public class FBPoolServerStub implements FBPoolServer
{
    Vector m_poolInfoList = new Vector();
    Vector m_poolList = new Vector();
    String week1Status = "Closed";
    String week2Status = "Open";
    String week3Status = "Created";

    public FBPoolServerStub()
    {
        //initialize the pool list and pool info list with dummy information that will be used by the mock
        initPoolData();
    }

    /** Gets a list of the pools that are defined */
    public Vector getPoolList()
    {
        return (m_poolList);
    }

    /** Get all the information about a particular pool */
    public FootballPool getPoolInfo(String poolDate)
    {
        FootballPool pool = new FootballPool(poolDate);
        try
        {
            if (poolDate.equals("2004-09-17"))
            {
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
            }
            else if (poolDate.equals("2004-09-10"))
            {

                pool.addGame("Tampa Bay","Tennessee");
                pool.addGame("Philadelphia","Green Bay");
                pool.addGame("Oakland","New England");
                pool.addGame("Chicago","Indianapolis");
                pool.addGame("NY Giants","Cleveland");
                pool.addGame("Carolina","New Orleans");
                pool.addGame("Houston","Washington");
                pool.addGame("Kansas City","Dallas");
                pool.addGame("Atlanta","Denver");
                pool.addGame("San Francisco","Jacksonville");
                pool.addGame("Baltimore","Arizona");
                pool.addGame("Buffalo","NY Jets");
                pool.addGame("Pittsburgh","St. Louis");
                pool.addGame("Miami","Seattle");
            }
            else if (poolDate.equals("2004-09-24"))
            {
                pool.addGame( "Baltimore","St. Louis");
                pool.addGame("San Francisco","Seattle");
                pool.addGame("Atlanta","Arizona");
                pool.addGame("Kansas City","NY Jets");
                pool.addGame("Houston","Denver");
                pool.addGame("Carolina","Jacksonville");
                pool.addGame("NY Giants","Washington");
                pool.addGame("Chicago","Dallas");
                pool.addGame("Oakland","Cleveland");
                pool.addGame("Philadelphia","New Orleans");
                pool.addGame("Tampa Bay","New England");
                pool.addGame("Miami","Indianapolis");
                pool.addGame("Pittsburgh","Tennessee");
                pool.addGame("Buffalo","Green Bay");
            }
        }
        catch (Exception e)
        {
            myDebug("Exception getting football pool info Exception is " + e);
        }
        return (pool);
    }

    private void initPoolData()
    {
        m_poolList.addElement("2004-09-10");
        m_poolList.addElement("2004-09-17");
        m_poolList.addElement("2004-09-24");
    }

    /** Gets the status for a particular pool */
    public String getStatus(String poolName)
    {
        if (poolName.equals("2004-09-10"))
        {
            return(week1Status);
        }
        else if (poolName.equals("2004-09-17"))
        {
            return(week2Status);
        }
        else if (poolName.equals("2004-09-24"))
        {
            return(week3Status);
        }
        else
        {
            return("Unknown");
        }
    }

    public void openPool(String poolName)
    {
        if (poolName.equals("2004-09-10"))
        {
            week1Status = "Open";
        }
        else if (poolName.equals("2004-09-17"))
        {
            week2Status = "Open";
        }
        else if (poolName.equals("2004-09-24"))
        {
            week3Status = "Open";
        }
    }

    public Vector getTeamNameList()
    {
        Vector teamNameList = new Vector();
        teamNameList.addElement("NY Giants");
        teamNameList.addElement("Green Bay");
        teamNameList.addElement("New England");
        teamNameList.addElement("Oakland");
        teamNameList.addElement("Chicago");
        teamNameList.addElement("Miami");
        teamNameList.addElement("Tampa Bay");
        teamNameList.addElement("Tennessee");
        teamNameList.addElement("Philadelphia");
        teamNameList.addElement("Green Bay");
        teamNameList.addElement("Indianapolis");
        teamNameList.addElement("Cleveland");
        teamNameList.addElement("Carolina");
        teamNameList.addElement("New Orleans");
        teamNameList.addElement("Houston");
        teamNameList.addElement("Washington");
        teamNameList.addElement("Kansas City");
        teamNameList.addElement("Dallas");
        teamNameList.addElement("Atlanta");
        teamNameList.addElement("Denver");
        teamNameList.addElement("San Francisco");
        teamNameList.addElement("Jacksonville");
        teamNameList.addElement("Baltimore");
        teamNameList.addElement("Arizona");
        teamNameList.addElement("Buffalo");
        teamNameList.addElement("NY Jets");
        teamNameList.addElement("Pittsburgh");
        teamNameList.addElement("St. Louis");
        teamNameList.addElement("Miami");
        teamNameList.addElement("Seattle");

        return(teamNameList);
    }

    public void addNewPool(FootballPool fbPool)
    {
        
    }

    private void myDebug(String msg)
    {
        System.out.println("DEBUG: " + msg);
    }

}
