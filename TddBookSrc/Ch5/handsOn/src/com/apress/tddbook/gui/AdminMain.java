/**
 */
package com.apress.tddbook.gui;

import com.apress.tddbook.gui.ejbmocks.FBPoolServer;
import com.apress.tddbook.gui.ejbmocks.FBPoolServerStub;
import com.apress.tddbook.FootballPool;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.util.Vector;

public class AdminMain extends JFrame
{
    JSplitPane sPane = new JSplitPane();
    JLabel listLabel = new JLabel("Pool List");
    JList poolList = new JList();
    JScrollPane listScrollPane = new JScrollPane();
    JButton newPoolButton = new JButton("New Pool");
    JButton closePoolButton = new JButton("Close Pool");
    JButton openPoolButton = new JButton("Open Pool");
    JButton deletePoolButton = new JButton("Delete");
    JLabel gameTableLabel = new JLabel("Week 1 Games");
    JLabel statusLabel = new JLabel("Status:Unknown");
    JLabel closeDateLabel = new JLabel("Closing Date 9-19-2003");
    JTable gameTable = new JTable(14, 3);
    FBPoolServer m_fbPoolServer;

    public AdminMain()
    {
        m_fbPoolServer = getFBPoolServerBean();
        //Initialize GUI components
        initComponents();
        //Populate Pool List
        populatePoolList();

        this.setTitle("Football Pool Administrator");
        this.setName("Football Pool Administrator");
        this.setSize(400, 500);
        this.setVisible(true);
        this.pack();
    }

    /**
     * Sets up all the components
     */
    public void initComponents()
    {
        newPoolButton.setName("NewPoolButton");
        openPoolButton.setName("OpenPoolButton");
        statusLabel.setName("StatusLabel");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        openPoolButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                openPool();
            }
        });

        newPoolButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                newPool();
            }
        });
        //Set the table column names
        String colName = gameTable.getColumnName(0);
        TableColumn column = gameTable.getColumn(colName);
        column.setMaxWidth(50);
        column.setHeaderValue("");
        colName = gameTable.getColumnName(1);
        column = gameTable.getColumn(colName);
        column.setHeaderValue("Home Team");
        colName = gameTable.getColumnName(2);
        column = gameTable.getColumn(colName);
        column.setHeaderValue("Away Team");

        JScrollPane tableScrollPane = new JScrollPane();
        JButton editGamesButton = new JButton("Edit Games");
        JButton postScoresButton = new JButton("Post Scores");
        JPanel listPanel = new JPanel();
        JPanel tablePanel = new JPanel();
        JPanel listButtonPanel = new JPanel();
        JPanel tableButtonPanel = new JPanel();

        listPanel.setLayout(new BorderLayout());
        listPanel.add(listLabel, BorderLayout.NORTH);
        poolList.setVisibleRowCount(6);
        poolList.addListSelectionListener(
                new ListSelectionListener()
                {
                    public void valueChanged(ListSelectionEvent e)
                    {
                        updateGameTable();
                    }
                });
        listScrollPane.setViewportView(poolList);
        listPanel.add(listScrollPane, BorderLayout.CENTER);

        listButtonPanel.add(newPoolButton);
        listButtonPanel.add(openPoolButton);
        listButtonPanel.add(closePoolButton);
        listButtonPanel.add(deletePoolButton);

        listPanel.add(listButtonPanel, BorderLayout.SOUTH);

        JPanel tableLabelPane = new JPanel();
        tableLabelPane.setLayout(new BorderLayout());
        tableLabelPane.add(gameTableLabel, BorderLayout.WEST);
        tableLabelPane.add(statusLabel, BorderLayout.CENTER);
        tableLabelPane.add(closeDateLabel, BorderLayout.EAST);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(tableLabelPane, BorderLayout.NORTH);
        tableScrollPane.setViewportView(gameTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        tableButtonPanel.add(editGamesButton);
        tableButtonPanel.add(postScoresButton);

        tablePanel.add(tableButtonPanel, BorderLayout.SOUTH);

        sPane.setLeftComponent(listPanel);
        sPane.setRightComponent(tablePanel);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });


        this.getContentPane().add(sPane);
    }

    /**
     * Opens the currently selected Pool
     */
    private void openPool()
    {
        String poolName = (String) poolList.getSelectedValue();
        if (poolName != null)
        {
            String poolStatus = m_fbPoolServer.getStatus(poolName);
            if (poolStatus.equals("Open")) // Can't open a pool that is already opened
            {
                JOptionPane.showMessageDialog(this, "Pool Already Opened", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                m_fbPoolServer.openPool(poolName);
                poolList.clearSelection();
                poolList.setSelectedValue(poolName, true);
            }
        }
        else //Nothing selected show error
        {
            JOptionPane.showMessageDialog(this, "No Pool Selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the new pool dialog box
     */
    public void newPool()
    {
        NewPoolDialog newPoolDialog = new NewPoolDialog(m_fbPoolServer);
    }
    public FBPoolServer getFBPoolServerBean()
    {
        return (new FBPoolServerStub());
    }

    /**
     * Fills in the pool list in the GUI with the data from the FBPoolServer
     */
    private void populatePoolList()
    {
        Vector gameList = m_fbPoolServer.getPoolList();
        poolList.setListData(gameList);
    }

    /**
     * Called when an item in the pool list is selected
     */
    private void updateGameTable()
    {
        String poolName = (String) poolList.getSelectedValue();
        if ((poolName != null) && (poolName.length() > 0))
        {
            FootballPool poolData = m_fbPoolServer.getPoolInfo(poolName);
            //Use selectedPool to create a table model that will be used to populate the table
            TableModel model = new FBGamesTableModel(poolData);
            gameTable.setModel(model);
            String colName = gameTable.getColumnName(0);
            TableColumn column = gameTable.getColumn(colName);
            column.setMaxWidth(50);
            String poolStatus = m_fbPoolServer.getStatus(poolName);
            statusLabel.setText("Status:" + poolStatus);
        }
    }

    public static void exitGUI()
    {
        System.exit(0);
    }

    public static void main(String[] args)
    {
        AdminMain fbAdmin = new AdminMain();
    }
}
