package com.apress.tddbook.gui;

import com.compaq.zso.AttachLayout;
import com.compaq.zso.Attachments;
import com.apress.tddbook.FootballPool;
import com.apress.tddbook.Game;
import com.apress.tddbook.gui.ejbmocks.FBPoolServer;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class NewPoolPanel extends JPanel
{
    private JLabel poolNameLabel = new JLabel("Name");
    private JTextField poolNameText = new JTextField();
    private JLabel closeDateLabel = new JLabel("Closing Date");
    private JTextField closeDateText = new JTextField();
    private JLabel teamListLabel = new JLabel("Team List");
    private JList teamList = new JList();
    private JTable gameTable = new JTable(13, 2);
    private JButton addHomeTeamButton = new JButton("Add Home Team");
    private JButton addAwayTeamButton = new JButton("Add Away Team");
    private JButton delGameButton = new JButton("Delete Game");
    private JButton setTieBreakButton = new JButton("Set Tie Break Game");

    /** Handle to the EJB */
    FBPoolServer m_fbPoolServer;
    /** New FootballPool object used to store new pool */
    FootballPool m_fbPool = new FootballPool();


    public NewPoolPanel(FBPoolServer fbPoolServer)
    {
        m_fbPoolServer = fbPoolServer;

        initComponents();
    }
    private void initComponents()
    {
        addHomeTeamButton.setName("addHomeTeamButton");
        addAwayTeamButton.setName("addAwayTeamButton");
        delGameButton.setName("delGameButton");
        setTieBreakButton.setName("setTieBreakButton");

        this.setLayout(new AttachLayout());
        Attachments fc = new Attachments();
        fc.topAttachment = fc.ATTACH_CONTAINER;
        fc.topOffset = 5;
        fc.leftAttachment = fc.ATTACH_POSITION;
        fc.leftPosition = 50;

        this.add(poolNameLabel, fc);

        fc.topAttachment = fc.ATTACH_CONTAINER;
        fc.topOffset = 5;
        fc.leftAttachment = fc.ATTACH_COMPONENT;
        fc.leftComponent = poolNameLabel;
        fc.rightAttachment = fc.ATTACH_POSITION;
        fc.rightPosition = 75;

        this.add(poolNameText, fc);

        fc.clear();
        fc.topAttachment = fc.ATTACH_COMPONENT;
        fc.topComponent = poolNameText;
        fc.topOffset = 5;
        fc.leftAttachment = fc.ATTACH_POSITION;
        fc.leftPosition = 50;


        this.add(closeDateLabel, fc);

        fc.clear();
        fc.topAttachment = fc.ATTACH_COMPONENT;
        fc.topComponent = poolNameText;
        fc.topOffset = 5;
        fc.leftAttachment = fc.ATTACH_COMPONENT;
        fc.leftComponent = closeDateLabel;
        fc.rightAttachment = fc.ATTACH_POSITION;
        fc.rightPosition = 75;

        this.add(closeDateText, fc);


        fc.clear();
        fc.topAttachment = fc.ATTACH_COMPONENT;
        fc.topComponent = closeDateText;
        fc.topOffset = 5;
        fc.leftAttachment = fc.ATTACH_POSITION;
        fc.leftPosition = 50;
        fc.rightAttachment = fc.ATTACH_CONTAINER;
        fc.bottomAttachment = fc.ATTACH_POSITION;
        fc.bottomPosition = 80;

        TableModel model = new NewPoolGamesTableModel(m_fbPool);
        gameTable.setModel(model);
        gameTable.setRowSelectionAllowed(true);
        JScrollPane gameTableScrollPane = new JScrollPane();
        gameTableScrollPane.setViewportView(gameTable);
        this.add(gameTableScrollPane, fc);

        fc.clear();
        fc.topAttachment = fc.ATTACH_COMPONENT;
        fc.topComponent = gameTableScrollPane;
        fc.topOffset = 5;
        fc.leftAttachment = fc.ATTACH_POSITION;
        fc.leftPosition = 50;

        setTieBreakButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setTieBreakGame();
            }
        });

        this.add(setTieBreakButton, fc);


        fc.topAttachment = fc.ATTACH_COMPONENT;
        fc.topComponent = closeDateText;
        fc.topOffset = 20;
        fc.leftAttachment = fc.ATTACH_CONTAINER;
        fc.leftOffset = 10;


        this.add(teamListLabel, fc);
        fc.clear();
        fc.topAttachment = fc.ATTACH_COMPONENT;
        fc.topComponent = teamListLabel;
        fc.topOffset = 5;
        fc.leftAttachment = fc.ATTACH_CONTAINER;
        fc.leftOffset = 10;
        fc.bottomAttachment = fc.ATTACH_CONTAINER;

        JScrollPane teamListScrollPane = new JScrollPane();

        teamList.setListData(getTeamNameList());
        teamList.setVisibleRowCount(14);
        teamListScrollPane.setViewportView(teamList);
        this.add(teamListScrollPane, fc);

        fc.clear();
        fc.topAttachment = fc.ATTACH_COMPONENT;
        fc.topComponent = teamListLabel;
        fc.topOffset = 20;
        fc.leftAttachment = fc.ATTACH_COMPONENT;
        fc.leftComponent = teamListScrollPane;
        fc.leftOffset = 10;

        addHomeTeamButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addNewHomeTeam();
            }
        });

        this.add(addHomeTeamButton, fc);

        fc.topAttachment = fc.ATTACH_COMPONENT;
        fc.topComponent = addHomeTeamButton;
        fc.topOffset = 5;
        fc.leftAttachment = fc.ATTACH_COMPONENT;
        fc.leftComponent = teamListScrollPane;
        fc.leftOffset = 10;

        addAwayTeamButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addNewAwayTeam();
            }
        });

        this.add(addAwayTeamButton, fc);

        fc.topAttachment = fc.ATTACH_COMPONENT;
        fc.topComponent = addAwayTeamButton;
        fc.topOffset = 5;
        fc.leftAttachment = fc.ATTACH_COMPONENT;
        fc.leftComponent = teamListScrollPane;
        fc.leftOffset = 10;

        delGameButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                delSelectedGame();
            }
        });

        this.add(delGameButton, fc);

        this.setSize(300,400);
    }

    private Vector getTeamNameList()
    {
        return(m_fbPoolServer.getTeamNameList());
    }

    /**
     * Adds a new Game to the football pull with the selected home team
     */
    private void addNewHomeTeam()
    {
        Vector gameList = m_fbPool.getGameList();
        // Get last game if home team not defined then add selected home team
        // otherwise add new game with selected home team
        if(gameList.size() > 0)
        {
           Game lastGame = (Game)gameList.elementAt(gameList.size() -1);
           if(lastGame.getHomeTeam().length() > 0)
           {
               Game newGame = new Game("", (String)teamList.getSelectedValue());
               gameList.addElement(newGame);
           }
           else
           {
               lastGame.setHomeTeam((String)teamList.getSelectedValue());
           }
        }
        else
        {
            Game newGame = new Game("", (String)teamList.getSelectedValue());
            gameList.addElement(newGame);
        }
        TableModel model = new NewPoolGamesTableModel(m_fbPool);
        gameTable.setModel(model);
    }
    /**
     * Adds a new Game to the football pull with the selected away team
     */
    private void addNewAwayTeam()
    {
        Vector gameList = m_fbPool.getGameList();
        // Get last game if home team not defined then add selected home team
        // otherwise add new game with selected home team
        if(gameList.size() > 0)
        {
           Game lastGame = (Game)gameList.elementAt(gameList.size() -1);
           if(lastGame.getAwayTeam().length() > 0)
           {
               Game newGame = new Game((String)teamList.getSelectedValue(), "");
               gameList.addElement(newGame);
           }
           else
           {
               lastGame.setAwayTeam((String)teamList.getSelectedValue());
           }
        }
        else
        {
            Game newGame = new Game((String)teamList.getSelectedValue(), "");
            gameList.addElement(newGame);
        }
        TableModel model = new NewPoolGamesTableModel(m_fbPool);
        gameTable.setModel(model);
    }
    private void delSelectedGame()
    {
        int selectedRow = gameTable.getSelectedRow();
        Vector gameList = m_fbPool.getGameList();
        gameList.removeElementAt(selectedRow);
        TableModel model = new NewPoolGamesTableModel(m_fbPool);
        gameTable.setModel(model);
    }

    private void setTieBreakGame()
    {
        int selectedRow = gameTable.getSelectedRow();
        try
        {
            m_fbPool.setTieBreakerGame(selectedRow);
        }
        catch (Exception noSuchGameException)
        {
            System.out.println("Error setting tie break game exception is " + noSuchGameException);
        }
        gameTable.updateUI();
    }

    public void savePool()
    {
        m_fbPool.setPoolDate(poolNameText.getText());
        m_fbPoolServer.addNewPool(m_fbPool);
    }
}
