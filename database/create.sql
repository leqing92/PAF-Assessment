-- Task 1
-- Write SQL statements in this file to create the brewery database and 

DROP SCHEMA if EXISTS brewery;

CREATE SCHEMA brewery;

USE brewery;
-- populate the database with the given SQL files

source beers.sql;
source breweries.sql;
source categories.sql;
source geocodes.sql;
source styles.sql;