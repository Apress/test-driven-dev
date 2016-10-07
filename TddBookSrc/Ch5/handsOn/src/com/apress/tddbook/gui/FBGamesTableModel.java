package com.apress.tddbook.gui;

import com.apress.tddbook.FootballPool;

import javax.swing.table.AbstractTableModel;

public class FBGamesTableModel extends AbstractTableModel
{
    FootballPool m_poolData;

    public FBGamesTableModel(FootballPool poolData)
    {
        m_poolData = poolData;
    }

    public int getRowCount()
    {
        return (m_poolData.getGameList().size());
    }

    public int getColumnCount()
    {
        //There are only always three columns
        return (3);
    }


    public java.lang.String getColumnName(int col)
    {
        switch (col)
        {
            case 0:
                return ("");
            case 1:
                return ("Home Team");
            case 2:
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
            case 2:
                return (false);
            default:
                return (false);
        }
    }

    public Object getValueAt(int row, int col)
    {
        switch (col)
        {
            case 0: //Row number
                return (new Integer(row+1).toString());
            case 1: // Home Team
                return (m_poolData.getHomeTeam(row));
            case 2: // Away Team
                return (m_poolData.getAwayTeam(row));
            default:
                return ("");
        }
    }


    public void setValueAt(Object p1, int row, int col)
    {

    }
}
