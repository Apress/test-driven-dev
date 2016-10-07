/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;
import java.util.Vector;

public class FBPoolTestClient
{
    FBPoolServer m_fbPoolServer;
    Hashtable env = new Hashtable();

    public FBPoolTestClient()
    {

        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        env.put(Context.PROVIDER_URL, "localhost:1099");
        env.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
        getFBPoolBean();
    }

    public void getFBPoolBean()
    {
        FBPoolServerHome fbPoolHome = null;
        try
        {
            Context ctx = new InitialContext(env);
            System.out.println("Before lookup of FBPoolHome");
            fbPoolHome = (FBPoolServerHome) javax.rmi.PortableRemoteObject.narrow(ctx.lookup("FBPoolServerEJB"), FBPoolServerHome.class);

            System.out.println("Before create on FBPoolServerHome");
            m_fbPoolServer = (FBPoolServer) fbPoolHome.create();
            Vector poolList = m_fbPoolServer.getPoolList();
            

            System.out.println("After create on FBPoolServerHome and cast to FBPoolServerRemote");
        }
        catch (Exception e)
        {
            System.out.println("FBPoolServer JNDI Lookup or EJB Create Error: ");
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        FBPoolTestClient fbClient = new FBPoolTestClient();
    }
}
