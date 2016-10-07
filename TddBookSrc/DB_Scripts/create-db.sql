create table pool (
pool_id int primary key,
date date,
winner varchar,
status varchar);

create table game (
pool_id int,
game_num int,
home_team varchar,
away_team varchar);

create table picks (
pool_id int,
user_name varchar,
game_num int,
picked_team varchar);