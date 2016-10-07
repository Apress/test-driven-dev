
package com.apress.tddbook;

import junit.framework.TestSuite;
import junit.framework.Test;
import com.apress.tddbook.gui.AdminGUITester;
import com.apress.tddbook.gui.AdminGUIJemmyTester;
import com.apress.tddbook.gui.ejbmocks.FBPoolServerTest;

public class Ch5TestSuite extends TestSuite
{
    public Ch5TestSuite(){
    }

    public static Test suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(AdminGUITester.class);
        suite.addTestSuite(AdminGUIJemmyTester.class);
        suite.addTestSuite(FBPoolServerTest.class);        
        return suite;
    }

    public static final void main(String[] args){
        junit.textui.TestRunner.run(suite());
    }
}
