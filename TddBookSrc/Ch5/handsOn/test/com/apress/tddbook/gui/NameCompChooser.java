
package com.apress.tddbook.gui;

import org.netbeans.jemmy.ComponentChooser;

import java.awt.*;
/**
 * Used to find components using the name of the components
 */
public class NameCompChooser implements ComponentChooser
{
    String m_compName;
    public NameCompChooser(String name)
    {
        m_compName = name;
    }
    public boolean checkComponent(Component component)
    {
        String compName = component.getName();
        if((compName != null) && (compName.equals(m_compName)))
        {
            return true;
        }
        else
        {
           return false;
        }
    }

    public String getDescription()
    {
        return "Finds components based on components name";
    }

}
