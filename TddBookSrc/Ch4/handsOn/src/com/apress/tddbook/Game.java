package com.apress.tddbook;

public class Game
{
    private String _homeTeam;
    private String _awayTeam;
    private String _pickedTeam;
    private int _homeTeamScore;
    private int _awayTeamScore;
    private String _winningTeamName;

    Game(String awayTeam, String homeTeam)
    {
        _homeTeam = homeTeam;
        _awayTeam = awayTeam;
    }

    String getHomeTeam()
    {
        return _homeTeam;
    }

    String getAwayTeam()
    {
        return _awayTeam;
    }

    String getWinningTeamName()
    {
        return _winningTeamName;
    }
    int getHomeTeamScore()
    {
        return _homeTeamScore;
    }

    int getAwayTeamScore()
    {
        return _awayTeamScore;
    }
    String getPickedTeam()
    {
        return _pickedTeam;
    }

    public void set_pickedGame(String pickedGame)
    {
        _pickedTeam = pickedGame;
    }

    public void postScore(int awayTeamScore, int homeTeamScore)
    {
        _homeTeamScore = homeTeamScore;
        _awayTeamScore = awayTeamScore;
        if (homeTeamScore > awayTeamScore)
        {
            _winningTeamName = _homeTeam;
        }
        else if( awayTeamScore > homeTeamScore)
        {
            _winningTeamName = _awayTeam;
        }
        else //tie game
        {
            _winningTeamName = "tie";
        }
    }

}
