package com.apress.tddbook.servlets;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class SimpleServletTester extends TestCase
{
    MockControl HttpServletReqMockCntl;
    MockControl HttpServletRespMockCntl;

    public SimpleServletTester(String s) {
        super(s);
    }

    /**
     * Tests the getUser method of the servlet
     */
    public void testServletGetUser(){
        StringWriter strWriter = new StringWriter();
        //Mock out the servlet request and response objects
        HttpServletRequest mockServletRequest = (HttpServletRequest)HttpServletReqMockCntl.getMock();
        HttpServletResponse mockServletResponse = (HttpServletResponse)HttpServletRespMockCntl.getMock();
        //Train the request object
        mockServletRequest.getAttribute("UserName");
        HttpServletReqMockCntl.setReturnValue("TimmyC");
        HttpServletReqMockCntl.replay();
        try
        {
            mockServletResponse.getWriter();
            HttpServletRespMockCntl.setReturnValue(new PrintWriter(strWriter));
        }
        catch (IOException e)
        {
            System.out.println("Error mocking out response.getWriter() exception is "  + e);
        }
        HttpServletRespMockCntl.replay();
        SimpleServlet simpleServlet = new SimpleServlet();
        String user = null;
        try
        {
            simpleServlet.doGet(mockServletRequest, mockServletResponse);
        }
        catch (ServletException e)
        {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        assertEquals("User that call this servlet is TimmyC", strWriter.toString());
    }

    public void setUp() {

        HttpServletReqMockCntl = MockControl.createControl(HttpServletRequest.class);
        HttpServletRespMockCntl = MockControl.createControl(HttpServletResponse.class);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SimpleServletTester.class);
        return suite;
    }


    public static final void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
