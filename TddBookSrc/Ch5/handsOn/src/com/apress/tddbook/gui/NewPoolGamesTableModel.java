package com.apress.tddbook.gui;

import com.apress.tddbook.FootballPool;

import javax.swing.table.AbstractTableModel;

public class NewPoolGamesTableModel extends AbstractTableModel
{
    FootballPool m_poolData;

    public NewPoolGamesTableModel(FootballPool poolData)
    {
        m_poolData = poolData;
    }

    public int getRowCount()
    {
        return (m_poolData.getGameList().size());
    }

    public int getColumnCount()
    {
        //There are only always two columns
        return (2);
    }


    public String getColumnName(int col)
    {
        switch (col)
        {
            case 0:
                return ("Home Team");
            case 1:
                return ("Away Team");
            default:
                return ("Unknown");
        }
    }

    public Class getColumnClass(int col)
    {
        return (String.class);
    }

    public boolean isCellEditable(int row, int col)
    {
        switch (col)
        {
            case 0:
                return (false);
            case 1:
                return (false);
            default:
                return (false);
        }
    }

    public Object getValueAt(int row, int col)
    {
        switch (col)
        {
            case 0: // Home Team
                if (m_poolData.getTieBreakerGame() == row)
                {
                    // Mark tie break game with asterisk at end of team name
                    return (m_poolData.getHomeTeam(row) + " *");
                }
                else
                {
                    return (m_poolData.getHomeTeam(row));
                }
            case 1: // Away Team
                if (m_poolData.getTieBreakerGame() == row)
                {
                    // Mark tie break game with asterisk at end of team name
                    return (m_poolData.getAwayTeam(row) + " *");
                }
                else
                {
                    return (m_poolData.getAwayTeam(row));
                }
            default:
                return ("");
        }
    }


    public void setValueAt(Object p1, int row, int col)
    {

    }
}
