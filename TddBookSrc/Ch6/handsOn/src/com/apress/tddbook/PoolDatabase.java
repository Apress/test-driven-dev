package com.apress.tddbook;

import java.util.Vector;


public interface PoolDatabase
{
    public Vector getPoolWithStatus(String status) throws NoPoolFoundException;
    public Vector getAllPoolDates();
    public FootballPool getPoolWithDate(String status);
    public void savePlayersPicks(PlayersPicks picks) throws SavePlayerPickException;
    public void setPoolStatus(String poolDate, String status);
    public PlayersPicks getPlayersPicks(String name, String poolDate) throws PicksNotFoundException;
}
