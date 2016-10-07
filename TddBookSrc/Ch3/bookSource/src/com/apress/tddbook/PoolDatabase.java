package com.apress.tddbook;

import java.util.Vector;


public interface PoolDatabase
{
    public Vector getPoolWithStatus(String status);
    public void savePlayersPicks(PlayersPicks picks);
    public PlayersPicks getPlayersPicks(String name, String poolDate);
}
