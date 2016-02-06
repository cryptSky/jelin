SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `answer`
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PLAYER_NUMBER` int(11) DEFAULT NULL,
  `TIME` int(11) DEFAULT NULL,
  `VARIANT` int(11) DEFAULT NULL,
  `QUESTION_ID` int(11) DEFAULT NULL,
  `ROUND_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_bhk1o35j26ty1q8ciowsf5d1m` (`QUESTION_ID`),
  KEY `FK_c62igdsfhg1lau1c68v75khtt` (`ROUND_ID`),
  CONSTRAINT `FK_bhk1o35j26ty1q8ciowsf5d1m` FOREIGN KEY (`QUESTION_ID`) REFERENCES `question` (`ID`),
  CONSTRAINT `FK_c62igdsfhg1lau1c68v75khtt` FOREIGN KEY (`ROUND_ID`) REFERENCES `game_round` (`ID`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of answer
-- ----------------------------

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