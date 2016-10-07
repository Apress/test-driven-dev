package com.apress.tddbook;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FootBallPoolTester extends TestCase {



    public FootBallPoolTester(String s) {
        super(s);
    }

    public void testPoolCreate() throws Exception {
        FootballPool pool = new FootballPool("9/07/2003");
        pool.addGame("Jets", "Dolphins" );
        pool.addGame("49ers","Giants"  );
        assertEquals(2, pool.size());
        assertEquals( "Game 1 away team", "Dolphins", pool.getHomeTeam(0) );
        assertEquals( "Game 2 away team", "Giants", pool.getHomeTeam(1) );
        assertEquals( "Game 1 home team", "Jets", pool.getAwayTeam(0) );
        assertEquals( "Game 2 home team", "49ers", pool.getAwayTeam(1) );
    }

    public void testTiebreakerSelect() throws Exception {
        FootballPool pool = new FootballPool("9/07/2003");
        pool.addGame("Jets", "Dolphins" );
        pool.addGame("49ers","Giants"  );
        pool.setTieBreakerGame( 1 );

        try {
            pool.setTieBreakerGame( 3 );
            fail( "Permitted selection of non-existent game as the tiebreaker" );
        }
        catch (NoSuchGameException e) {}
    }

    public void testSetPoolToOpen()
    {
        FootballPool pool = new FootballPool("9/07/2003");
        pool.addGame("Jets", "Dolphins");
        pool.addGame("49ers","Giants");
        pool.openPool();
        assertEquals("Open", pool.getStatus());
    }
    public static Test suite() {
        TestSuite suite = new TestSuite(FootBallPoolTester.class);
        return suite;
    }

    public static final void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
