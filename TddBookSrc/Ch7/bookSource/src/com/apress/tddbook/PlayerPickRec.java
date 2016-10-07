/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook;

public class PlayerPickRec
{
    public int m_weekNum;
    public int m_correctPicks;

    public PlayerPickRec(int weekNum, int corrPicks)
    {
        setWeekNum(weekNum);
        setCorrectPicks(corrPicks);
    }
    public int getWeekNum()
    {
        return m_weekNum;
    }

    public void setWeekNum(int weekNum)
    {
        this.m_weekNum = weekNum;
    }

    public int getCorrectPicks()
    {
        return m_correctPicks;
    }

    public void setCorrectPicks(int correctPicks)
    {
        this.m_correctPicks = correctPicks;
    }
}
