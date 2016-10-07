/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook.gui;

import com.apress.tddbook.gui.ejbmocks.FBPoolServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewPoolDialog extends JDialog
{
    private NewPoolPanel poolPanel;
    private JButton okButton = new JButton("OK");
    private JButton cancelButton = new JButton("Cancel");

    FBPoolServer m_fbPoolServer;

    public NewPoolDialog(FBPoolServer fbPoolServer)
    {
        m_fbPoolServer = fbPoolServer;
        initComponents();
        this.show();
    }

    public void initComponents()
    {
        okButton.setName("okButton");
        okButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                saveNewPool();
            }
        });

        cancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                closeDialog();
            }
        });

        this.getContentPane().setLayout(new BorderLayout());
        poolPanel = new NewPoolPanel(m_fbPoolServer);
        this.getContentPane().add(poolPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        this.setSize(600, 400);
    }
    public void saveNewPool()
    {
        poolPanel.savePool();
        this.hide();
    }

    public void closeDialog()
    {
        this.hide();
    }
}
