-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 06, 2017 at 03:06 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

CREATE USER 'Yash777'@'%' IDENTIFIED BY  'Yash@777';

GRANT ALL PRIVILEGES ON * . * TO  'Yash777'@'%' IDENTIFIED BY  '***' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;

GRANT ALL PRIVILEGES ON  `Yash777\_%` . * TO  'Yash777'@'%';

--
-- Database: `applicationdatabase`
--

-- --------------------------------------------------------

--
-- Table structure for table `container_users`
--

CREATE TABLE IF NOT EXISTS `container_users` (
  `user_name` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `user_pass` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `container_users`
--

INSERT INTO `container_users` (`user_name`, `user_pass`) VALUES
('tomcat_jdbc', 'realmjdbc'),
('yash', 'Yash777');

-- --------------------------------------------------------

--
-- Table structure for table `container_user_roles`
--

CREATE TABLE IF NOT EXISTS `container_user_roles` (
  `user_name` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `role_name` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`user_name`,`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `container_user_roles`
--

INSERT INTO `container_user_roles` (`user_name`, `role_name`) VALUES
('tomcat_jdbc', 'tomcat'),
('yash', 'manager');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `FIRST_NAME` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `LAST_NAME` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `EMAIL` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `PASSWORD` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `HASH_PASSWORD_TOKEN` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `CHANGE_PASSWORD` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'false',
  `FORGET_PASSWORD_URL` varchar(500) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID`, `USER_NAME`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`, `HASH_PASSWORD_TOKEN`, `CHANGE_PASSWORD`, `FORGET_PASSWORD_URL`) VALUES
(1, 'Yash777', 'Yashwanth', 'Merugu', 'yashwanth.merugu@gmail.com', 'Yash@123', '618719146', 'true', 'null'),
(2, 'Yash7', 'Yash', 'M', 'yashwanth.m@gmail.com', 'Yash@777', '', 'false', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
