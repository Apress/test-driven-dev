package com.apress.tddbook;

public class PlayerResultsData
{
    public String playerName;
    public int correctPicks;

    PlayerResultsData(String name, int correctPicks)
    {
        playerName = name;
        this.correctPicks = correctPicks;
    }
}
