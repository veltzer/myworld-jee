-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: myworld
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `TbBsCompanies`
--

DROP TABLE IF EXISTS `TbBsCompanies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbBsCompanies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `url` varchar(256) DEFAULT NULL,
  `imagePath` varchar(256) NOT NULL,
  `fromDate` date DEFAULT NULL,
  `funcTeaching` tinyint(1) DEFAULT '0',
  `funcConsulting` tinyint(1) DEFAULT '0',
  `funcCertification` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1 COMMENT='this is a list of the companies I taught in';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbBsCourses`
--

DROP TABLE IF EXISTS `TbBsCourses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbBsCourses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=latin1 COMMENT='this is the list of courses that I teach';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbDbDatabase`
--

DROP TABLE IF EXISTS `TbDbDatabase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbDbDatabase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbDbField`
--

DROP TABLE IF EXISTS `TbDbField`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbDbField` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `dbTableId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `type` (`type`),
  KEY `dbTableId` (`dbTableId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbDbFunction`
--

DROP TABLE IF EXISTS `TbDbFunction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbDbFunction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `dbDatabaseId` int(11) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `dbDatabaseId` (`dbDatabaseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbDbProcedure`
--

DROP TABLE IF EXISTS `TbDbProcedure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbDbProcedure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `dbDatabaseId` int(11) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `dbDatabaseId` (`dbDatabaseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbDbTable`
--

DROP TABLE IF EXISTS `TbDbTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbDbTable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `dbDatabaseId` int(11) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `dbDatabaseId` (`dbDatabaseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbDbTrigger`
--

DROP TABLE IF EXISTS `TbDbTrigger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbDbTrigger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `dbDatabaseId` int(11) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `dbDatabaseId` (`dbDatabaseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbDbUser`
--

DROP TABLE IF EXISTS `TbDbUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbDbUser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `dbDatabaseId` int(11) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `dbDatabaseId` (`dbDatabaseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbIdGrp`
--

DROP TABLE IF EXISTS `TbIdGrp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbIdGrp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbIdGrpPerson`
--

DROP TABLE IF EXISTS `TbIdGrpPerson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbIdGrpPerson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `personId` (`personId`),
  KEY `groupId` (`groupId`),
  CONSTRAINT `TbIdGrpPerson_ibfk_1` FOREIGN KEY (`personId`) REFERENCES `TbIdPerson` (`id`),
  CONSTRAINT `TbIdGrpPerson_ibfk_2` FOREIGN KEY (`personId`) REFERENCES `TbIdPerson` (`id`),
  CONSTRAINT `TbIdGrpPerson_ibfk_3` FOREIGN KEY (`groupId`) REFERENCES `TbIdGrp` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbIdPerson`
--

DROP TABLE IF EXISTS `TbIdPerson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbIdPerson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(20) DEFAULT NULL,
  `surname` varchar(20) DEFAULT NULL,
  `othername` varchar(20) DEFAULT NULL,
  `ordinal` int(11) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `firstname_2` (`firstname`,`surname`,`othername`,`ordinal`),
  UNIQUE KEY `firstname_3` (`firstname`,`surname`),
  KEY `firstname` (`firstname`),
  KEY `surname` (`surname`),
  KEY `othername` (`othername`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1 COMMENT='this is one person and can be used for everything...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbIdPersonId`
--

DROP TABLE IF EXISTS `TbIdPersonId`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbIdPersonId` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(40) NOT NULL,
  `personIdTypeId` int(11) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `importance` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `importance` (`importance`,`personIdTypeId`),
  KEY `personIdTypeId` (`personIdTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='This is an actual persons id for something.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbIdPersonIdType`
--

DROP TABLE IF EXISTS `TbIdPersonIdType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbIdPersonIdType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `short` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_2` (`name`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='This is an id type like israeli id card, dr lic and more';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbLcContinent`
--

DROP TABLE IF EXISTS `TbLcContinent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbLcContinent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `locPlanetId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `locPlanetId` (`locPlanetId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbLcGalaxy`
--

DROP TABLE IF EXISTS `TbLcGalaxy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbLcGalaxy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbLcNamed`
--

DROP TABLE IF EXISTS `TbLcNamed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbLcNamed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COMMENT='locations in the world (computer, home, cinema, etc)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbLcPlanet`
--

DROP TABLE IF EXISTS `TbLcPlanet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbLcPlanet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `locGalaxyId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `locGalaxyId` (`locGalaxyId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbMsLilypond`
--

DROP TABLE IF EXISTS `TbMsLilypond`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbMsLilypond` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source` mediumblob NOT NULL,
  `pdf` mediumblob NOT NULL,
  `ps` mediumblob NOT NULL,
  `midi` mediumblob NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `subtitle` varchar(100) DEFAULT NULL,
  `composer` varchar(100) DEFAULT NULL,
  `copyright` varchar(100) DEFAULT NULL,
  `style` varchar(100) DEFAULT NULL,
  `piece` varchar(100) DEFAULT NULL,
  `poet` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbRsBlob`
--

DROP TABLE IF EXISTS `TbRsBlob`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbRsBlob` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data` blob,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbRsColor`
--

DROP TABLE IF EXISTS `TbRsColor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbRsColor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `red` int(11) NOT NULL,
  `green` int(11) NOT NULL,
  `blue` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_2` (`name`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=754 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbRsConfig`
--

DROP TABLE IF EXISTS `TbRsConfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbRsConfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `data` varchar(2024) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbRsImage`
--

DROP TABLE IF EXISTS `TbRsImage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbRsImage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remark` varchar(200) DEFAULT NULL,
  `image` blob,
  `name` varchar(20) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbRsTag`
--

DROP TABLE IF EXISTS `TbRsTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbRsTag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbRsText`
--

DROP TABLE IF EXISTS `TbRsText`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbRsText` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `text` varchar(2024) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbTgTag`
--

DROP TABLE IF EXISTS `TbTgTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbTgTag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbTgTagGrp`
--

DROP TABLE IF EXISTS `TbTgTagGrp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbTgTagGrp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbTgTagGrpMembers`
--

DROP TABLE IF EXISTS `TbTgTagGrpMembers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbTgTagGrpMembers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagId` int(11) NOT NULL,
  `tagGrpId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tagId` (`tagId`),
  KEY `fk_tagGrpId` (`tagGrpId`),
  CONSTRAINT `TbTgTagGrpMembers_ibfk_1` FOREIGN KEY (`tagId`) REFERENCES `TbTgTag` (`id`),
  CONSTRAINT `TbTgTagGrpMembers_ibfk_2` FOREIGN KEY (`tagGrpId`) REFERENCES `TbTgTagGrp` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbTgTagRelation`
--

DROP TABLE IF EXISTS `TbTgTagRelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbTgTagRelation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fromTagId` int(11) NOT NULL,
  `toTagId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fromTagId` (`fromTagId`),
  KEY `toTagId` (`toTagId`),
  CONSTRAINT `TbTgTag_ibfk_1` FOREIGN KEY (`fromTagId`) REFERENCES `TbTgTag` (`id`),
  CONSTRAINT `TbTgTag_ibfk_2` FOREIGN KEY (`toTagId`) REFERENCES `TbTgTag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbWgWord`
--

DROP TABLE IF EXISTS `TbWgWord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbWgWord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(40) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `wordGroupId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `text_2` (`text`,`wordGroupId`),
  KEY `wordGroupId` (`wordGroupId`),
  KEY `text` (`text`),
  KEY `wordGroupId_2` (`wordGroupId`,`text`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbWgWordGroup`
--

DROP TABLE IF EXISTS `TbWgWordGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbWgWordGroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbWkProducer`
--

DROP TABLE IF EXISTS `TbWkProducer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbWkProducer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `url` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbWkWork`
--

DROP TABLE IF EXISTS `TbWkWork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbWkWork` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `length` decimal(9,2) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `typeId` int(11) NOT NULL,
  `producerId` int(11) DEFAULT NULL,
  `viewDate` datetime NOT NULL,
  `viewerId` int(11) NOT NULL,
  `locationId` int(11) NOT NULL,
  `remark` varchar(40) DEFAULT NULL,
  `rating` int(11) NOT NULL,
  `review` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_producerId` (`producerId`),
  KEY `fk_typeId` (`typeId`),
  KEY `fk_locationId` (`locationId`),
  KEY `creatorId` (`creatorId`),
  KEY `viewerId` (`viewerId`),
  CONSTRAINT `TbWkWork_ibfk_1` FOREIGN KEY (`producerId`) REFERENCES `TbWkProducer` (`id`),
  CONSTRAINT `TbWkWork_ibfk_2` FOREIGN KEY (`typeId`) REFERENCES `TbWkWorkType` (`id`),
  CONSTRAINT `TbWkWork_ibfk_3` FOREIGN KEY (`locationId`) REFERENCES `TbLcNamed` (`id`),
  CONSTRAINT `TbWkWork_ibfk_5` FOREIGN KEY (`creatorId`) REFERENCES `TbIdGrp` (`id`),
  CONSTRAINT `TbWkWork_ibfk_6` FOREIGN KEY (`viewerId`) REFERENCES `TbIdGrp` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TbWkWorkType`
--

DROP TABLE IF EXISTS `TbWkWorkType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TbWkWorkType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `name_2` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1 COMMENT='Type of a title: movie, book, abook, audio lecture';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
