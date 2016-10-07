package com.apress.tddbook;

import java.util.Vector;


public interface PoolDatabase
{
    public Vector getPoolWithStatus(String status);
    public Vector getAllPoolDates();
    public FootballPool getPoolWithDate(String status);
    public void savePlayersPicks(PlayersPicks picks);
    public void setPoolStatus(String poolDate, String status);
    public PlayersPicks getPlayersPicks(String name, String poolDate);
}
