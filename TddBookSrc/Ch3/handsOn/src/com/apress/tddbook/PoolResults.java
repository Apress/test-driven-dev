package com.apress.tddbook;

import java.util.Vector;

public class PoolResults
{
    public String poolDate;
    public String winningPlayersName;
    public Vector playerResultsList;

    public PoolResults(String date, String winner, Vector resultsList)
    {
        poolDate = date;
        winningPlayersName = winner;
        playerResultsList = resultsList;
    }
}
