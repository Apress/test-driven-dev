/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook.gui.ejbmocks;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import com.apress.tddbook.FootballPool;

import java.util.Vector;

public class FBPoolServerTest extends TestCase {



    public FBPoolServerTest(String s) {
        super(s);
    }

    /**
     * Test the ability to get the pool info for a specific pool
     */
    public void testGetPoolInfo()
    {
        FBPoolServer fbPoolServer = new FBPoolServerStub();
        FootballPool pool = fbPoolServer.getPoolInfo("2004-09-17");
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

        pool = fbPoolServer.getPoolInfo("2004-09-10");
        assertEquals("Kansas City",pool.getAwayTeam(7));
        assertEquals("Green Bay",pool.getHomeTeam(1));
        assertEquals("Houston",pool.getAwayTeam(6));
        assertEquals("Tennessee",pool.getHomeTeam(0));
        assertEquals("Carolina",pool.getAwayTeam(5));
        assertEquals("Indianapolis",pool.getHomeTeam(3));
        assertEquals("NY Giants",pool.getAwayTeam(4));
        assertEquals("New England",pool.getHomeTeam(2));
        assertEquals("Chicago",pool.getAwayTeam(3));
        assertEquals("New Orleans",pool.getHomeTeam(5));
        assertEquals("Oakland",pool.getAwayTeam(2));
        assertEquals("Cleveland",pool.getHomeTeam(4));
        assertEquals("Philadelphia",pool.getAwayTeam(1));
        assertEquals("Dallas",pool.getHomeTeam(7));
        assertEquals("Tampa Bay",pool.getAwayTeam(0));
        assertEquals("Washington",pool.getHomeTeam(6));
        assertEquals("Miami",pool.getAwayTeam(13));
        assertEquals("Jacksonville",pool.getHomeTeam(9));
        assertEquals("Pittsburgh",pool.getAwayTeam(12));
        assertEquals("Denver",pool.getHomeTeam(8));
        assertEquals("Buffalo",pool.getAwayTeam(11));
        assertEquals("NY Jets",pool.getHomeTeam(11));
        assertEquals("Baltimore",pool.getAwayTeam(10));
        assertEquals("Arizona",pool.getHomeTeam(10));
        assertEquals("San Francisco",pool.getAwayTeam(9));
        assertEquals("Seattle",pool.getHomeTeam(13));
        assertEquals("Atlanta",pool.getAwayTeam(8));
        assertEquals("St. Louis",pool.getHomeTeam(12));

        pool = fbPoolServer.getPoolInfo("2004-09-24");
        assertEquals("Kansas City",pool.getAwayTeam(3));
        assertEquals("Green Bay",pool.getHomeTeam(13));
        assertEquals("Houston",pool.getAwayTeam(4));
        assertEquals("Tennessee",pool.getHomeTeam(12));
        assertEquals("Carolina",pool.getAwayTeam(5));
        assertEquals("Indianapolis",pool.getHomeTeam(11));
        assertEquals("NY Giants",pool.getAwayTeam(6));
        assertEquals("New England",pool.getHomeTeam(10));
        assertEquals("Chicago",pool.getAwayTeam(7));
        assertEquals("New Orleans",pool.getHomeTeam(9));
        assertEquals("Oakland",pool.getAwayTeam(8));
        assertEquals("Cleveland",pool.getHomeTeam(8));
        assertEquals("Philadelphia",pool.getAwayTeam(9));
        assertEquals("Dallas",pool.getHomeTeam(7));
        assertEquals("Tampa Bay",pool.getAwayTeam(10));
        assertEquals("Washington",pool.getHomeTeam(6));
        assertEquals("Miami",pool.getAwayTeam(11));
        assertEquals("Jacksonville",pool.getHomeTeam(5));
        assertEquals("Pittsburgh",pool.getAwayTeam(12));
        assertEquals("Denver",pool.getHomeTeam(4));
        assertEquals("Buffalo",pool.getAwayTeam(13));
        assertEquals("NY Jets",pool.getHomeTeam(3));
        assertEquals("Baltimore",pool.getAwayTeam(0));
        assertEquals("Arizona",pool.getHomeTeam(2));
        assertEquals("San Francisco",pool.getAwayTeam(1));
        assertEquals("Seattle",pool.getHomeTeam(1));
        assertEquals("Atlanta",pool.getAwayTeam(2));
        assertEquals("St. Louis",pool.getHomeTeam(0));
    }
    /**
     * Tests the ability to get a list of the available pools
     */
    public void testGetPoolList(){
        FBPoolServer fbPoolServer = new FBPoolServerStub();
        Vector poolList = fbPoolServer.getPoolList();
        assertEquals(poolList.size(), 3);
        String poolName = (String)poolList.elementAt(0);
        assertEquals("2004-09-10", poolName);
        poolName = (String)poolList.elementAt(1);
        assertEquals("2004-09-17", poolName);
        poolName = (String)poolList.elementAt(2);
        assertEquals("2004-09-24", poolName);
    }
    public void testGetPoolStatus()
    {
        FBPoolServer fbPoolServer = new FBPoolServerStub();
        String poolStatus = fbPoolServer.getStatus("2004-09-10");
        assertEquals("Closed", poolStatus);
        poolStatus = fbPoolServer.getStatus("2004-09-17");
        assertEquals("Open", poolStatus);
        poolStatus = fbPoolServer.getStatus("2004-09-24");
        assertEquals("Created", poolStatus);
    }
    public void testOpenPool()
    {
        FBPoolServer fbPoolServer = new FBPoolServerStub();

        String poolStatus = fbPoolServer.getStatus("2004-09-24");
        assertEquals("Created", poolStatus);
        fbPoolServer.openPool("2004-09-24");
        poolStatus = fbPoolServer.getStatus("2004-09-24");
        assertEquals("Open", poolStatus);
    }
    public static Test suite() {
        TestSuite suite = new TestSuite(FBPoolServerTest.class);
        return suite;
    }

    public static final void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
