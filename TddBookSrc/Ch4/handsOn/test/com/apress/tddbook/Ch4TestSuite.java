
package com.apress.tddbook;

import junit.framework.TestSuite;
import junit.framework.Test;
import com.apress.tddbook.servlets.PlayerPickServletTester;
import com.apress.tddbook.jsps.PlayerPickJSPTester;
import com.apress.tddbook.servlets.PlayersResultsServletTester;

public class Ch4TestSuite extends TestSuite
{
    public Ch4TestSuite(){
    }

    public static Test suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(PlayerPickServletTester.class);
        suite.addTestSuite(PlayerPickJSPTester.class);
        suite.addTestSuite(PlayersResultsServletTester.class);
        return suite;
    }

    public static final void main(String[] args){
        junit.textui.TestRunner.run(suite());
    }
}
