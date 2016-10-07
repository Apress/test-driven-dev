
package com.apress.tddbook;

import junit.framework.TestSuite;
import junit.framework.Test;

public class Ch3TestSuite extends TestSuite
{
    public Ch3TestSuite(){
    }

    public static Test suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(FootBallPoolTester.class);
        suite.addTestSuite(PlayerPickTester.class);
        //suite.addTest(new FootBallPoolTester("testSetPoolToOpen"));
        return suite;
    }

    public static final void main(String[] args){
        junit.textui.TestRunner.run(suite());
    }
}
