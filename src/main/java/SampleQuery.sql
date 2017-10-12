-- Select Max Version:
-- Ex: select MAX(column_name) from table_name « MAX(column_name)
--     select MAX(column_name) as max from table_name « max
-- SQL WHERE Clause: is used to filter records.
-- SQL Aliases: are used to temporarily rename a table or a column heading.
SELECT max(`java_version`) as max FROM `Platform` WHERE `key`=?;
SELECT `java_version` FROM `Platform` WHERE `key`=? and `java_version` = (select max(`java_version`) from `Platform` WHERE `key` = ?)

-- Update Main-Table based on the max java-verion of Sub-Table
UPDATE `main`, (SELECT max(`java_version`) as maxval FROM `sub` WHERE `key`=? ) as t SET  `mainCol2` =  ? WHERE `mainCol1` = ? and `mainCol4` = ? and `mainCol_Sub` = t.maxval;


-- [phpMyAdmin](https://www.phpmyadmin.net/) is a free software tool written in PHP, intended to handle the administration of MySQL over the Web.
-- phpMyAdmin supports a wide range of operations on MySQL and MariaDB.

-- [Demo URL](https://demo.phpmyadmin.net/master-config/)

SET GLOBAL general_log = 'ON';

DROP DATABASE IF EXISTS `github` ;
CREATE DATABASE `github`;

CREATE USER 'Yashwanth'@'localhost' IDENTIFIED BY PASSWORD 'secret#';

-- Error : Password hash should be a 41/16-digit hexadecimal number
-- loginName « soe000000001 : pQ4ckfoGGnawSJCY0lYD0Q==
-- password  « Agent-11 : 2HCynXkSsXP4S8Kcx6DQ3jAEZH7WYoMtrwZ8B6WQh9MM0heLXnBSDVrHiqaS78aw


SELECT PASSWORD('clearTextPasswd');


-- The hashed password value returned by PASSWORD()
--
-- | 16-digit hexadecimal number | 41-digit hexadecimal number |
-- | :---:         |     :---:      |
-- | select password('123456') | SELECT PASSWORD(  'secret#' ) |
-- | 2ff898e158cd0311 | +A4E63019B68A83978F16623B47BECDDA49853BAC | 

-- hash string 1 value «
CREATE USER 'Yashwanth'@'localhost' IDENTIFIED BY PASSWORD  '+A4E63019B68A83978F16623B47BECDDA49853BAC'

-- (OR)

-- clear-text string value «
CREATE USER 'Yashwanth2'@'localhost' IDENTIFIED BY 'secret#'

--

GRANT ALL PRIVILEGES ON  `mydb`. * TO  'username'@'localhost' IDENTIFIED BY  '@password'

-- SQL INSERT INTO Statement: is used to insert new records in a table.
-- NOTE: we cannot add WHERE Clause to INSERT Query for that use UPDATE Query.

INSERT INTO table_name ( column1, column2,...columnN ) VALUES ( value1, value2,...valueN );

-- SQL UPDATE Statement « is used to update existing records in a table.
-- http://dev.mysql.com/doc/refman/5.7/en/update.html

UPDATE table_name SET field1=new-value1, field2=new-value2 [WHERE Clause] -- « some_column=some_value;

INSERT INTO table_name (column1,column2,column3)
SELECT column1, column2, column3 FROM  table_name
WHERE column1 = 'some_value'

INSERT INTO table (a,b,c) VALUES (1,2,3) ON DUPLICATE KEY UPDATE c=3;

INSERT INTO `objectrepository_temp` (`dom_objtemp`) 
VALUES ('someID') 
ON DUPLICATE KEY UPDATE `version_objtemp` = (SELECT MAX(`version_objtemp`) as max FROM `objectrepository_temp` WHERE `pagename_objtemp` = 'wal' AND `projectid_objtemp` = '7' AND `addedby_objtemp` = '7')
WHERE
`pagename_objtemp` = 'wal' AND `projectid_objtemp` = '7' AND `addedby_objtemp` = '7' AND `version_objtemp` = max


UPDATE `objectrepository_temp`, (SELECT max(`version_objtemp`) as maxval FROM `objectrepository_temp` WHERE `pagename_objtemp`=? ) as t SET  `dom_objtemp` =  ?  WHERE `pagename_objtemp` = ? and `projectid_objtemp` = ? and `addedby_objtemp` = ?  and `version_objtemp` = t.maxval;

-- JOIN operations with 3 tables « http://www.dofactory.com/sql/join

SELECT C.`cycle_name`, B.`build_name`, R.`name` FROM `testcycle_details` C JOIN `build_details` B ON B.`build_id` = C.`build_id` JOIN `release_details` R ON R.`inc_id` = C.`release_id` WHERE C.`cycle_id` = 95

SELECT `cycle_id` as CID FROM `parllelconfiguarions` WHERE `id_pc` = 5903

SELECT Config.`id_pc`, C.`cycle_name`, B.`build_name`, R.`name`FROM `parllelconfiguarions` Config 
JOIN `testcycle_details` C ON C.`cycle_id` = Config.`cycle_id` JOIN `build_details` B ON B.`build_id` = C.`build_id` JOIN `release_details` R ON R.`inc_id` = C.`release_id` WHERE Config.`id_pc` = 5903

SELECT count(*) FROM `objectrepository_temp` where `type_objtemp`='Link' and configid_pts IN (13344, 13342, 13340)

-- =====

SELECT TOP 2 * FROM Customers;
SELECT TOP 50 PERCENT * FROM Customers;

SELECT * FROM `emp` ORDER BY NAME ASC LIMIT 5,20

SELECT * FROM `emp` WHERE name = 'raghu' AND ( id = 16 OR id = 17)

SELECT * FROM `emp` WHERE id BETWEEN 5 AND 15
SELECT * FROM `emp` WHERE id IN (5,15)

-- The following SQL statement selects all customers with a City % ending with the letter "s":  && starting with the letter “ber” %
SELECT * FROM Customers WHERE City LIKE '%s';
SELECT * FROM Customers WHERE City LIKE 'ber%';

-- The following SQL statement selects all customers with a Country % containing the pattern "land": %
SELECT * FROM Customers WHERE Country NOT LIKE '%land%';

SELECT * FROM Customers WHERE City LIKE 'L_n_on'; specified letters must be same.