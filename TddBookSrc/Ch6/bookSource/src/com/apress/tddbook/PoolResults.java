/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook;

public class PoolResults
{
    public String playerName;
    public int correctPicks;

    public PoolResults(String name, int corrPicks)
    {
        setPlayerName(name);
        setCorrectPicks(corrPicks);
    }
    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }


    public int getCorrectPicks()
    {
        return correctPicks;
    }

    public void setCorrectPicks(int correctPicks)
    {
        this.correctPicks = correctPicks;
    }


}
