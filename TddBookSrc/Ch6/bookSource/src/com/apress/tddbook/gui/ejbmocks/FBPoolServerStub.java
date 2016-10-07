/*
 * Mock implementation of the FBPoolServer interface
 */
package com.apress.tddbook.gui.ejbmocks;

import com.apress.tddbook.FootballPool;
import com.apress.tddbook.ejb.FBPoolServer;

import javax.ejb.RemoveException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import java.util.Vector;
import java.rmi.RemoteException;

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

    /**
     * Gets a list of the pools that are defined
     */
    public Vector getPoolList()
    {
        return (m_poolList);
    }

    /**
     * Get all the information about a particular pool
     */
    public FootballPool getPoolInfo(String poolName)
    {
        FootballPool pool = new FootballPool("2004-09-18");
        try
        {
            if (poolName.equals("2004-09-18"))
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
            else if (poolName.equals("2004-09-11"))
            {
                /*pool.setAwayTeam(7, "Kansas City");
                pool.setHomeTeam(1, "Green Bay");
                pool.setAwayTeam(6, "Houston");
                pool.setHomeTeam(0, "Tennessee");
                pool.setAwayTeam(5, "Carolina");
                pool.setHomeTeam(3, "Indianapolis");
                pool.setAwayTeam(4, "NY Giants");
                pool.setHomeTeam(2, "New England");
                pool.setAwayTeam(3, "Chicago");
                pool.setHomeTeam(5, "New Orleans");
                pool.setAwayTeam(2, "Oakland");
                pool.setHomeTeam(4, "Cleveland");
                pool.setAwayTeam(1, "Philadelphia");
                pool.setHomeTeam(7, "Dallas");
                pool.setAwayTeam(0, "Tampa Bay");
                pool.setHomeTeam(6, "Washington");
                pool.setAwayTeam(13, "Miami");
                pool.setHomeTeam(9, "Jacksonville");
                pool.setAwayTeam(12, "Pittsburgh");
                pool.setHomeTeam(8, "Denver");
                pool.setAwayTeam(11, "Buffalo");
                pool.setHomeTeam(11, "NY Jets");
                pool.setAwayTeam(10, "Baltimore");
                pool.setHomeTeam(10, "Arizona");
                pool.setAwayTeam(9, "San Francisco");
                pool.setHomeTeam(13, "Seattle");
                pool.setAwayTeam(8, "Atlanta");
                pool.setHomeTeam(12, "St. Louis"); */
            }
            else if (poolName.equals("2004-09-25"))
            {
                /*pool.setAwayTeam(3, "Kansas City");
                pool.setHomeTeam(13, "Green Bay");
                pool.setAwayTeam(4, "Houston");
                pool.setHomeTeam(12, "Tennessee");
                pool.setAwayTeam(5, "Carolina");
                pool.setHomeTeam(11, "Indianapolis");
                pool.setAwayTeam(6, "NY Giants");
                pool.setHomeTeam(10, "New England");
                pool.setAwayTeam(7, "Chicago");
                pool.setHomeTeam(9, "New Orleans");
                pool.setAwayTeam(8, "Oakland");
                pool.setHomeTeam(8, "Cleveland");
                pool.setAwayTeam(9, "Philadelphia");
                pool.setHomeTeam(7, "Dallas");
                pool.setAwayTeam(10, "Tampa Bay");
                pool.setHomeTeam(6, "Washington");
                pool.setAwayTeam(11, "Miami");
                pool.setHomeTeam(5, "Jacksonville");
                pool.setAwayTeam(12, "Pittsburgh");
                pool.setHomeTeam(4, "Denver");
                pool.setAwayTeam(13, "Buffalo");
                pool.setHomeTeam(3, "NY Jets");
                pool.setAwayTeam(0, "Baltimore");
                pool.setHomeTeam(2, "Arizona");
                pool.setAwayTeam(1, "San Francisco");
                pool.setHomeTeam(1, "Seattle");
                pool.setAwayTeam(2, "Atlanta");
                pool.setHomeTeam(0, "St. Louis"); */
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
        m_poolList.addElement("2004-09-11");
        m_poolList.addElement("2004-09-18");
        m_poolList.addElement("2004-09-25");
    }

    /**
     * Gets the status for a particular pool
     */
    public String getStatus(String poolName)
    {
        if (poolName.equals("2004-09-11"))
        {
            return (week1Status);
        }
        else if (poolName.equals("2004-09-18"))
        {
            return (week2Status);
        }
        else if (poolName.equals("2004-09-25"))
        {
            return (week3Status);
        }
        else
        {
            return ("Unknown");
        }
    }

    public void openPool(String poolName)
    {
        if (poolName.equals("2004-09-11"))
        {
            week1Status = "Open";
        }
        else if (poolName.equals("2004-09-18"))
        {
            week2Status = "Open";
        }
        else if (poolName.equals("2004-09-25"))
        {
            week3Status = "Open";
        }
    }

    private void myDebug(String msg)
    {
        System.out.println("DEBUG: " + msg);
    }

    public void remove() throws RemoteException, RemoveException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getPrimaryKey() throws RemoteException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EJBHome getEJBHome() throws RemoteException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isIdentical(EJBObject ejbObject) throws RemoteException
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Handle getHandle() throws RemoteException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
