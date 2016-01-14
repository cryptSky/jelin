/*
Navicat MySQL Data Transfer

Source Server         : dolphin
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : jelindb

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2015-12-27 20:39:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IS_CATEGORY` bit(1) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `GROUP_ID` int(11) NOT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_o414lix15aqd7lv38srlqj851` (`GROUP_ID`),
  KEY `FK_51jyal9pwdgtotvk1eojkvfxv` (`PARENT_ID`),
  CONSTRAINT `FK_51jyal9pwdgtotvk1eojkvfxv` FOREIGN KEY (`PARENT_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `FK_o414lix15aqd7lv38srlqj851` FOREIGN KEY (`GROUP_ID`) REFERENCES `questiongroup` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '', 'Sport', '1', null, null);
INSERT INTO `category` VALUES ('2', '', 'Geography', '1', null, null);
INSERT INTO `category` VALUES ('3', '', 'Football', '1', '1', null);
INSERT INTO `category` VALUES ('4', '', 'Tennis', '1', '1', null);
INSERT INTO `category` VALUES ('5', '', 'Racing', '1', '1', null);
INSERT INTO `category` VALUES ('6', '', 'Flags', '1', '2', null);
INSERT INTO `category` VALUES ('7', '', 'Nations of the world', '1', '2', null);

-- ----------------------------
-- Table structure for `category_category`
-- ----------------------------
DROP TABLE IF EXISTS `category_category`;
CREATE TABLE `category_category` (
  `Category_ID` int(11) NOT NULL,
  `childCategories_ID` int(11) NOT NULL,
  UNIQUE KEY `UK_lvcg4n9lucgdyyaeu0cqu7o49` (`childCategories_ID`),
  KEY `FK_d914s4ya4runn2sj1isa89amd` (`Category_ID`),
  CONSTRAINT `FK_d914s4ya4runn2sj1isa89amd` FOREIGN KEY (`Category_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `FK_lvcg4n9lucgdyyaeu0cqu7o49` FOREIGN KEY (`childCategories_ID`) REFERENCES `category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category_category
-- ----------------------------

-- ----------------------------
-- Table structure for `country`
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of country
-- ----------------------------

-- ----------------------------
-- Table structure for `difficulty`
-- ----------------------------
DROP TABLE IF EXISTS `difficulty`;
CREATE TABLE `difficulty` (
  `DIFFICULTY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(10) NOT NULL,
  `LEVEL` int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`DIFFICULTY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of difficulty
-- ----------------------------
INSERT INTO `difficulty` VALUES ('1', 'A1', '1', 'Elementary');
INSERT INTO `difficulty` VALUES ('2', 'A2', '2', 'Upper-Elementary');
INSERT INTO `difficulty` VALUES ('3', 'B1', '3', 'Pre-Intermediate');
INSERT INTO `difficulty` VALUES ('4', 'B2', '4', 'Intermediate');
INSERT INTO `difficulty` VALUES ('5', 'B3', '5', 'Upper-Intermediate');
INSERT INTO `difficulty` VALUES ('6', 'C1', '6', 'Advanced');
INSERT INTO `difficulty` VALUES ('7', 'C2', '7', 'Proficiency');

-- ----------------------------
-- Table structure for `game`
-- ----------------------------
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IS_RANDOM` bit(1) NOT NULL,
  `DIFFICULTY_ID` int(11) DEFAULT NULL,
  `THEME_ID` int(11) NOT NULL,
  `CREATOR_ID` int(11) DEFAULT NULL,
  `STATE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_3ma6amth2cl6bdu3b79s2hlto` (`THEME_ID`),
  KEY `FK_dhr23q0e32ypjr4tjlqf30654` (`DIFFICULTY_ID`),
  KEY `FK_9x242l7q3qdjbgrv6wfmustpc` (`CREATOR_ID`),
  CONSTRAINT `FK_3ma6amth2cl6bdu3b79s2hlto` FOREIGN KEY (`THEME_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `FK_9x242l7q3qdjbgrv6wfmustpc` FOREIGN KEY (`CREATOR_ID`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `FK_dhr23q0e32ypjr4tjlqf30654` FOREIGN KEY (`DIFFICULTY_ID`) REFERENCES `difficulty` (`DIFFICULTY_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of game
-- ----------------------------
INSERT INTO `game` VALUES ('4', '', '5', '2', '1', '0');
INSERT INTO `game` VALUES ('7', '', '1', '1', '7', '0');

-- ----------------------------
-- Table structure for `gamecharacter`
-- ----------------------------
DROP TABLE IF EXISTS `gamecharacter`;
CREATE TABLE `gamecharacter` (
  `CHARACTER_ID` int(11) NOT NULL,
  `ACRONS` int(11) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `GOLD_ACRONS` int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `SPECIAL` bit(1) NOT NULL,
  PRIMARY KEY (`CHARACTER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gamecharacter
-- ----------------------------
INSERT INTO `gamecharacter` VALUES ('1', '0', 'The most evil guy ever', '450', 'Coon', '');
INSERT INTO `gamecharacter` VALUES ('2', '0', 'Big, kind and a bit plump man', '0', 'Jelin', '');
INSERT INTO `gamecharacter` VALUES ('3', '0', 'Cute, slim and a bit cruel lady with charming eyes', '0', 'Daisy', '');

-- ----------------------------
-- Table structure for `gameopponent`
-- ----------------------------
DROP TABLE IF EXISTS `gameopponent`;
CREATE TABLE `gameopponent` (
  `GAME_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `STATUS` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `gmuser` (`USER_ID`),
  KEY `gmgame` (`GAME_ID`),
  CONSTRAINT `gmgame` FOREIGN KEY (`GAME_ID`) REFERENCES `game` (`ID`),
  CONSTRAINT `gmuser` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gameopponent
-- ----------------------------
INSERT INTO `gameopponent` VALUES ('4', '3', '1', '1');
INSERT INTO `gameopponent` VALUES ('4', '2', '7', '1');
INSERT INTO `gameopponent` VALUES ('7', '9', '8', '1');

-- ----------------------------
-- Table structure for `locality`
-- ----------------------------
DROP TABLE IF EXISTS `locality`;
CREATE TABLE `locality` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `REGION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_rn2uijnouc8lnxxpnjujmghy5` (`REGION_ID`),
  CONSTRAINT `FK_rn2uijnouc8lnxxpnjujmghy5` FOREIGN KEY (`REGION_ID`) REFERENCES `region` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of locality
-- ----------------------------

-- ----------------------------
-- Table structure for `questiongroup`
-- ----------------------------
DROP TABLE IF EXISTS `questiongroup`;
CREATE TABLE `questiongroup` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(255) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of questiongroup
-- ----------------------------
INSERT INTO `questiongroup` VALUES ('1', 'public group', 'public');
INSERT INTO `questiongroup` VALUES ('2', 'private group', 'private ');
INSERT INTO `questiongroup` VALUES ('3', 'bonus group', 'bonus');

-- ----------------------------
-- Table structure for `region`
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `COUNTRY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_6g2kw0cm4ibdrstgjps2l2w23` (`COUNTRY_ID`),
  CONSTRAINT `FK_6g2kw0cm4ibdrstgjps2l2w23` FOREIGN KEY (`COUNTRY_ID`) REFERENCES `country` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of region
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ROLE_ID` int(11) NOT NULL,
  `ROLE` varchar(255) NOT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_USER');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(255) NOT NULL,
  `USERNAME` varchar(255) NOT NULL,
  `CHARACTER_ID` int(255) DEFAULT NULL,
  `LAST_GAME_TIME` datetime DEFAULT NULL,
  `NET_STATUS` int(11) DEFAULT NULL,
  `PROCESS_STATUS` int(11) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UK_gmrrm359qu7ogusdu9r2qr637` (`EMAIL`),
  UNIQUE KEY `UK_4xc5q2uhghtb4qrbyheoohvs` (`USERNAME`),
  KEY `user_character` (`CHARACTER_ID`),
  CONSTRAINT `FK_a0iswqmyvemmkn5cvk1aqw9c0` FOREIGN KEY (`CHARACTER_ID`) REFERENCES `gamecharacter` (`CHARACTER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'vitalii.oleksiv@gmail.com', 'vitalii', '1', null, '0', '1');
INSERT INTO `user` VALUES ('2', 'vasya@gmail.com', 'vasya', '2', null, '1', '3');
INSERT INTO `user` VALUES ('3', 'valera@gmail.com', 'valera', '3', null, '0', '3');
INSERT INTO `user` VALUES ('7', 'john@gmail.com', 'john', null, null, '0', '1');
INSERT INTO `user` VALUES ('8', 'paul@gmail.com', 'paul', null, null, '0', '0');
INSERT INTO `user` VALUES ('9', 'jeremy@gmail.com', 'jeremy', null, null, '0', '3');

-- ----------------------------
-- Table structure for `usercharacter`
-- ----------------------------
DROP TABLE IF EXISTS `usercharacter`;
CREATE TABLE `usercharacter` (
  `USER_ID` int(11) NOT NULL,
  `CHARACTER_ID` int(255) NOT NULL,
  PRIMARY KEY (`USER_ID`,`CHARACTER_ID`),
  KEY `FK_rr7e0hkjtnv7kp173slul61uw` (`CHARACTER_ID`),
  CONSTRAINT `FK_50nwht8bb3k8yq15xgdx0ap7a` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usercharacter
-- ----------------------------

-- ----------------------------
-- Table structure for `userinterests`
-- ----------------------------
DROP TABLE IF EXISTS `userinterests`;
CREATE TABLE `userinterests` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GAMES_PLAYED` int(11) NOT NULL,
  `DIFFICULTY_ID` int(11) NOT NULL,
  `CATEGORY_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_4jmjf3ch79otemoy4pk4125tw` (`DIFFICULTY_ID`),
  KEY `FK_lio1m9owtmwc9kegwgbxlw7yu` (`CATEGORY_ID`),
  KEY `FK_9xga4s8eixyuh6rvachdfj7hh` (`USER_ID`),
  CONSTRAINT `FK_4jmjf3ch79otemoy4pk4125tw` FOREIGN KEY (`DIFFICULTY_ID`) REFERENCES `difficulty` (`DIFFICULTY_ID`),
  CONSTRAINT `FK_9xga4s8eixyuh6rvachdfj7hh` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `FK_lio1m9owtmwc9kegwgbxlw7yu` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinterests
-- ----------------------------

-- ----------------------------
-- Table structure for `usermodel`
-- ----------------------------
DROP TABLE IF EXISTS `usermodel`;
CREATE TABLE `usermodel` (
  `USERNAME` varchar(42) NOT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `PASSWORD` varchar(42) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USERNAME`),
  UNIQUE KEY `UK_1r4efagf56k77jxjiuo48w7n1` (`EMAIL`),
  KEY `FK_3y87i3k3cm0sqdd3vlih00vqx` (`ROLE_ID`),
  CONSTRAINT `FK_3y87i3k3cm0sqdd3vlih00vqx` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usermodel
-- ----------------------------
INSERT INTO `usermodel` VALUES ('jeremy', 'jeremy@gmail.com', 'jeremy1', '1');
INSERT INTO `usermodel` VALUES ('john', 'john@gmail.com', 'john1', '1');
INSERT INTO `usermodel` VALUES ('paul', 'paul@gmail.com', 'paul1', '1');
INSERT INTO `usermodel` VALUES ('valera', 'valera@gmail.com', 'val', '1');
INSERT INTO `usermodel` VALUES ('vasya', 'vasya@gmail.com', 'vasya', '1');
INSERT INTO `usermodel` VALUES ('vitalii', 'vitalii.oleksiv@gmail.com', 'vit', '1');
