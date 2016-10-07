package com.apress.tddbook;

public class PlayerResultsData
{
    public String playerName;
    public int correctPicks;

    public PlayerResultsData(String name, int correctPicks)
    {
        playerName = name;
        this.correctPicks = correctPicks;
    }
}
