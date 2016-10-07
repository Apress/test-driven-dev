/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook;

public class Game
{
    private String _homeTeam;
    private String _awayTeam;
    private String _pickedTeam;

    public Game( String awayTeam, String homeTeam ) {
        _homeTeam = homeTeam;
        _awayTeam = awayTeam;
    }

    public String getHomeTeam() { return _homeTeam; }
    public void setHomeTeam(String homeTeam)
    {
        _homeTeam = homeTeam;
    }
    public String getAwayTeam() { return _awayTeam; }
    public void setAwayTeam(String awayTeam)
    {
        _awayTeam = awayTeam;
    }
    public String getPickedTeam() { return _pickedTeam; }
    public void set_pickedGame(String pickedGame){
        _pickedTeam = pickedGame;
    }
}
