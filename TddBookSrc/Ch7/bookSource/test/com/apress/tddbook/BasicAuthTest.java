package com.apress.tddbook;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebResponse;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 * HttpUnit test to test that the web server login works properly
 */
public class BasicAuthTest extends TestCase {


    /**
     * default constructor - just calls super();
     */
    public BasicAuthTest(String s) {
        super(s);
    }
    /**
     * Tests to check whether a browser can login to the Football Pool site and get the home page
     */
    public void testHomePage() {
        WebConversation conversation = new WebConversation();
        conversation.setAuthorization("tom", "tom");
        WebRequest request = new GetMethodWebRequest("http://localhost:8080/FootBallPool");
        try
        {
        //Get response should be initial page
        WebResponse response = conversation.getResponse(request);
        Document welcomeDoc = response.getDOM();
        NodeList h1NodeList = welcomeDoc.getElementsByTagName("h1");

        if (h1NodeList.getLength() > 0) {
            Node h1Node = h1NodeList.item(0);
            Node textNode = h1Node.getFirstChild();
            String h1NodeStr = textNode.getNodeValue();
            assertEquals("Welcome to the Football Pool", h1NodeStr);
        } else {
            fail("Error getting home page Excepion");
        }
        }
        catch(Exception e)
        {
            System.out.println("Error getting home page Exception is " + e);
        }
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(BasicAuthTest.class);
        return suite;
    }

    public static final void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
