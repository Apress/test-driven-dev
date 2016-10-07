package com.apress.tddbook;

import java.util.Vector;


public interface PoolDatabase
{
    public Vector getPoolWithStatus(String status);
    public void savePlayersPicks(PlayersPicks picks);
    public PlayersPicks getPlayersPicks(String name, String poolDate);
    public void updatePool(FootballPool poll);
    public Vector getAllPicks(String date);
    public FootballPool getPoolWithDate(String date);
    public void savePoolResults(PoolResults results);
    public PoolResults getPoolResults(String date);    
}
