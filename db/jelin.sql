/*
Navicat MySQL Data Transfer

Source Server         : dolphin
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : jelin

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2015-12-19 18:56:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE` varchar(255) NOT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_USER');
INSERT INTO `role` VALUES ('2', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(255) NOT NULL,
  `USERNAME` varchar(255) NOT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `user_model`
-- ----------------------------
DROP TABLE IF EXISTS `user_model`;
CREATE TABLE `user_model` (
  `USERNAME` varchar(255) NOT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  PRIMARY KEY (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_model
-- ----------------------------
INSERT INTO `user_model` VALUES ('admin', 'admin@gmail.com', 'admin');
INSERT INTO `user_model` VALUES ('vasya', 'vasya@gmail.com', 'pass');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `USERNAME` varchar(255) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USERNAME`,`ROLE_ID`),
  KEY `FK_qxlog73d0t2auuod93t5qfw9h` (`ROLE_ID`),
  CONSTRAINT `FK_qxlog73d0t2auuod93t5qfw9h` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`),
  CONSTRAINT `FK_sd72f2nhi93p7cysmjkcom20x` FOREIGN KEY (`USERNAME`) REFERENCES `user_model` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('vasya', '1');
INSERT INTO `user_role` VALUES ('admin', '2');
