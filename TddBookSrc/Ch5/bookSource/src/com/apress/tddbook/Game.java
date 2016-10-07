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

    Game( String awayTeam, String homeTeam ) {
        _homeTeam = homeTeam;
        _awayTeam = awayTeam;
    }

    String getHomeTeam() { return _homeTeam; }
    String getAwayTeam() { return _awayTeam; }
    String getPickedTeam() { return _pickedTeam; }
    public void set_pickedGame(String pickedGame){
        _pickedTeam = pickedGame;
    }
}
