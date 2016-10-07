/*
 * Mock implementation of the FBPoolServer interface
 */
package com.apress.tddbook.ejb;

import com.apress.tddbook.FootballPool;
import com.apress.tddbook.PoolDatabase;
import com.apress.tddbook.PoolDatabaseImpl;


import javax.ejb.*;
import java.util.Vector;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class FBPoolServerEJB implements SessionBean, FBPoolServer
{
    Vector m_poolInfoList = new Vector();

    String m_dbLocation = "jdbc:hsqldb:hsql://localhost:1701";    
    String m_userName = "sa";
    String m_passwd = "";
    PoolDatabase m_poolDB;

    public FBPoolServerEJB() throws SQLException, ClassNotFoundException
    {
        m_poolDB = new PoolDatabaseImpl(m_dbLocation, m_userName, m_passwd);
    }

    public FBPoolServerEJB(String dbLocation) throws SQLException, ClassNotFoundException
    {
        m_poolDB = new PoolDatabaseImpl(dbLocation, m_userName, m_passwd);
    }
    /**
     * Gets a list of the pools that are defined
     */
    public Vector getPoolList() throws RemoteException
    {
        Vector poolList = m_poolDB.getAllPoolDates();
        return (poolList);
    }

    /**
     * Get all the information about a particular pool
     */
    public FootballPool getPoolInfo(String poolDate) throws RemoteException
    {
        FootballPool pool = m_poolDB.getPoolWithDate(poolDate);
        return (pool);
    }


    /**
     * Gets the status for a particular pool
     */
    public String getStatus(String poolDate) throws RemoteException
    {
        FootballPool pool = m_poolDB.getPoolWithDate(poolDate);
        if(pool == null)
        {
           return ("Unknown");
        }
        else
        {
            return(pool.getStatus());
        }
    }

    public void openPool(String poolDate) throws RemoteException
    {
        m_poolDB.setPoolStatus(poolDate, "Open");
    }

    public void ejbActivate() throws EJBException, RemoteException
    {

    }

    public void ejbPassivate() throws EJBException, RemoteException
    {

    }

    public void ejbRemove() throws EJBException, RemoteException
    {

    }

    public void ejbCreate() throws RemoteException
    {
    }

    public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException
    {

    }


    public void remove() throws RemoteException, RemoveException
    {

    }

    public Object getPrimaryKey() throws RemoteException
    {
        return null;
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
