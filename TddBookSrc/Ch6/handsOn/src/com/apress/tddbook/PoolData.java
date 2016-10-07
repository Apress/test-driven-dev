package com.apress.tddbook;

/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */

public interface PoolData
{
    FootballPool getOpenPool();

    FootballPool getPoolbyDate(String date);

    void savePlayersPicks(PlayersPicks playerPicks) throws SavePlayerPickException;

    PlayersPicks getPlayersPicks(String playersName, String poolDate);

    PoolDatabase get_poolDB();

    void set_poolDB(PoolDatabase _poolDB);
}
