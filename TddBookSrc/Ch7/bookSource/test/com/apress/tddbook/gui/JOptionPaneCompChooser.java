
package com.apress.tddbook.gui;

import org.netbeans.jemmy.ComponentChooser;

import javax.swing.*;
import java.awt.*;
/**
 * Used to find components that are JOptionPanes
 */
public class JOptionPaneCompChooser implements ComponentChooser
{
    String m_compName;
    public JOptionPaneCompChooser()
    {
    }
    public boolean checkComponent(Component component)
    {
        System.out.println(component.getClass().getName());
        if(component instanceof JOptionPane)
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
        return "Finds components that are class JOptionPane";
    }

}
