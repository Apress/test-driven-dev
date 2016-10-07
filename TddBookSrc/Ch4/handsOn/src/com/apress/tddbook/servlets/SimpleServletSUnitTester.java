
package com.apress.tddbook.servlets;

import com.apress.tddbook.servlets.PlayerPickServlet;
import com.apress.tddbook.servlets.SimpleServlet;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.servlet.http.HttpServletRequest;

public class SimpleServletSUnitTester extends TestCase
{


    public SimpleServletSUnitTester(String s) {
        super(s);
    }

    /**
     * Tests the getUser method of the servlet
     */
    public void testServletGetUser(){
        ServletRunner sr = new ServletRunner();
        sr.registerServlet("SimpleServlet", SimpleServlet.class.getName());
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new PostMethodWebRequest("http://localhost/SimpleServlet");
        try
        {
            InvocationContext ic = sc.newInvocation(request);
            SimpleServlet simpleServlet = (SimpleServlet) ic.getServlet();
            assertNull("A session already exists", ic.getRequest().getSession(false));
            HttpServletRequest simpleServletRequest = ic.getRequest();
            simpleServletRequest.setAttribute("UserName", "TimmyC");
            String userName = simpleServlet.getUser(ic.getRequest(), ic.getResponse());
            assertEquals("TimmyC", userName);
        }
        catch (Exception e)
        {
            fail("Error testing getUser exception is " + e);
            e.printStackTrace();
        }


    }

    public void setUp() {

    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SimpleServletSUnitTester.class);
        return suite;
    }


    public static final void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
