package com.apress.tddbook.ejb;

import com.apress.tddbook.FootballPool;

import javax.ejb.EJBObject;
import java.util.Vector;
import java.rmi.RemoteException;

/*
 * This interfaces is used to define the communucation between the GUI and the stateless session used to
 * manage the football pool
 */

public interface FBPoolServer extends EJBObject
{
    /** Gets a list of the pools that are defined */
    public Vector getPoolList() throws RemoteException ;
    /** Get all the information about a particular pool */
    public FootballPool getPoolInfo(String poolName) throws RemoteException ;
    /** Gets the status of the pool */
    public String getStatus(String poolName) throws RemoteException ;
    /** Sets the status of the specified pool to open */
    public void openPool(String poolName) throws RemoteException ;
}
