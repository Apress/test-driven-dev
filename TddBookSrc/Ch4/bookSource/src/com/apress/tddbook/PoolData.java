package com.apress.tddbook;

public interface PoolData
{
    FootballPool getOpenPool();

    void savePlayersPicks(PlayersPicks playerPicks);

    PlayersPicks getPlayersPicks(String playersName, String poolDate);

    PoolDatabase get_poolDB();

    void set_poolDB(PoolDatabase _poolDB);
}
