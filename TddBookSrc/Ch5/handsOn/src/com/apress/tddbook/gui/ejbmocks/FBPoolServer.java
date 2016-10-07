package com.apress.tddbook.gui.ejbmocks;

import com.apress.tddbook.FootballPool;

import java.util.Vector;

/*
 * This interfaces is used to define the communucation between the GUI and the stateless session used to
 * manage the football pool
 */

public interface FBPoolServer
{
    /** Gets a list of the pools that are defined */
    public Vector getPoolList();
    /** Get all the information about a particular pool */
    public FootballPool getPoolInfo(String poolName);
    /** Gets the status of the pool */
    public String getStatus(String poolName);
    /** Sets the status of the specified pool to open */
    public void openPool(String poolName);
    /** Gets a list of Strings of all the names of the Football Teams */
    public Vector getTeamNameList();
    /** Adds a new football pool to the database*/
    public void addNewPool(FootballPool fbPool);

}
