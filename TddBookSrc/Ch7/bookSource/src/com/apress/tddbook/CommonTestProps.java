
package com.apress.tddbook;


import java.util.Properties;
import java.io.*;

public class CommonTestProps
{
    public String Use_Stub = "true";

    String m_propFileName;
    Properties m_propBundle;

    public CommonTestProps(String propFileName)
    {
        try
        {
            m_propFileName = propFileName;
            m_propBundle = new Properties();
            InputStream inStream = new FileInputStream(propFileName);
            m_propBundle.load(inStream);

            Use_Stub = m_propBundle.getProperty("Use_Stub");
        }
        catch (Exception e)
        {
            // if file not found, log error and use the default values
            System.out.println("Error loading properties from file " + propFileName + " Exception is " + e);
        }
    }

    public String getProperty(String key)
    {
        return (m_propBundle.getProperty(key));
    }

    /**
     * Set a value of a property in the file
     *
     * @param key   Name of the property
     * @param value New value for this property
     */
    public void setProperty(String key, String value)
    {
        m_propBundle.setProperty(key, value);
    }

    /**
     * save all properties to the file
     */
    public void saveProps() throws IOException
    {
        OutputStream outStream = new FileOutputStream(m_propFileName);
        m_propBundle.store(outStream, "Test Properties");
    }
}
