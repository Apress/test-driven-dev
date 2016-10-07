/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook;

import java.util.Vector;

public class PlayersPicks
{
    private Vector _gameList = new Vector();
    private String _playersName;
    private String _poolDate;


    public PlayersPicks(String playersName, String poolDate, Vector gameList) {
        _gameList = gameList;
        _playersName = playersName;
        _poolDate = poolDate;
    }


    public void makePick( int gameNum, String pickTeam ) {
        Game game = (Game)_gameList.elementAt(gameNum);
        game.set_pickedGame(pickTeam);
    }


    public String getHomeTeam( int i ) {
        Game game = (Game)_gameList.elementAt(i);
        return game.getHomeTeam();
    }


    public String getAwayTeam( int i ) {
        Game game = (Game)_gameList.elementAt(i);
        return game.getAwayTeam();
    }

    public String getPickedTeam( int i ) {
        Game game = (Game)_gameList.elementAt(i);
        return game.getPickedTeam();
    }

    public int size(){
        return(_gameList.size());
    }
    public String getPlayersName(){
        return _playersName;
    }
    public String getPoolDate(){
        return _poolDate;
    }
}
