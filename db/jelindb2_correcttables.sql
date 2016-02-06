/*
Navicat MySQL Data Transfer

Source Server         : dolphin
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : jelindb

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2016-01-15 16:56:36
*/

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
  KEY `FK_bhk1o35j26ty1q8ciowsf5d1m` (`QUESTION_ID`) USING BTREE,
  KEY `FK_c62igdsfhg1lau1c68v75khtt` (`ROUND_ID`) USING BTREE,
  CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`QUESTION_ID`) REFERENCES `question` (`ID`),
  CONSTRAINT `answer_ibfk_2` FOREIGN KEY (`ROUND_ID`) REFERENCES `game_round` (`ID`)
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
  KEY `FK_o414lix15aqd7lv38srlqj851` (`GROUP_ID`) USING BTREE,
  KEY `FK_51jyal9pwdgtotvk1eojkvfxv` (`PARENT_ID`) USING BTREE,
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `category_ibfk_2` FOREIGN KEY (`GROUP_ID`) REFERENCES `questiongroup` (`ID`)
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
-- Table structure for `country`
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` VALUES ('1', 'Ukraine');
INSERT INTO `country` VALUES ('2', 'USA');
INSERT INTO `country` VALUES ('3', 'Dominican Republic');

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
-- Table structure for `enhancer`
-- ----------------------------
DROP TABLE IF EXISTS `enhancer`;
CREATE TABLE `enhancer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACORNS` int(11) NOT NULL,
  `ACORNS_PERCENT` int(11) NOT NULL,
  `DESCRIPTON` varchar(255) DEFAULT NULL,
  `IS_EQUIPMENT` bit(1) DEFAULT NULL,
  `GOLD_ACORNS` int(11) NOT NULL,
  `ICON` varchar(255) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `POINTS_PERCENT` int(11) NOT NULL,
  `CHARACTER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_2hey1w6i08964dgfumgit9l5o` (`CHARACTER_ID`) USING BTREE,
  CONSTRAINT `enhancer_ibfk_1` FOREIGN KEY (`CHARACTER_ID`) REFERENCES `gamecharacter` (`CHARACTER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enhancer
-- ----------------------------
INSERT INTO `enhancer` VALUES ('1', '5000', '10', '+10% acorns & ps', '', '0', 'icons/glasses.png', 'Glasses', '10', '2');
INSERT INTO `enhancer` VALUES ('2', '7100', '15', '+15% acorns & ps', '', '0', 'icons/glasses.png', 'Glasses', '15', '3');
INSERT INTO `enhancer` VALUES ('3', '7500', '10', '+10% acorns & ps', '', '0', 'icons/microphone.png', 'Microphone', '10', '1');
INSERT INTO `enhancer` VALUES ('4', '3000', '100', '+100% acorns & ps', '', '0', 'icons/indian_coffe.png', 'Indian Coffee', '100', '1');
INSERT INTO `enhancer` VALUES ('5', '3000', '100', '+100% acorns & ps', '', '0', 'icons/indian_coffe.png', 'Indian Coffee', '100', '2');
INSERT INTO `enhancer` VALUES ('6', '7500', '15', '+15 acorns & ps', '', '0', 'icons/shorts.png', 'Shorts', '15', '2');

-- ----------------------------
-- Table structure for `game`
-- ----------------------------
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IS_RANDOM` bit(1) NOT NULL,
  `DIFFICULTY_ID` int(11) DEFAULT NULL,
  `THEME_ID` int(11) DEFAULT NULL,
  `CREATOR_ID` int(11) NOT NULL,
  `STATE` int(11) DEFAULT NULL,
  `INIT_DATE` datetime DEFAULT NULL,
  `READINESS` int(11) DEFAULT NULL,
  `CURRENT_ROUND` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_3ma6amth2cl6bdu3b79s2hlto` (`THEME_ID`) USING BTREE,
  KEY `FK_dhr23q0e32ypjr4tjlqf30654` (`DIFFICULTY_ID`) USING BTREE,
  KEY `FK_9x242l7q3qdjbgrv6wfmustpc` (`CREATOR_ID`) USING BTREE,
  KEY `FK_fj1n7q79vsaqafjaftrqlhdrh` (`CURRENT_ROUND`) USING BTREE,
  CONSTRAINT `game_ibfk_1` FOREIGN KEY (`THEME_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `game_ibfk_2` FOREIGN KEY (`CREATOR_ID`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `game_ibfk_3` FOREIGN KEY (`DIFFICULTY_ID`) REFERENCES `difficulty` (`DIFFICULTY_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `game_ibfk_4` FOREIGN KEY (`CURRENT_ROUND`) REFERENCES `game_round` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of game
-- ----------------------------
INSERT INTO `game` VALUES ('4', '', '5', '2', '1', '0', null, null, null);
INSERT INTO `game` VALUES ('7', '', '1', '1', '7', '0', null, null, null);
INSERT INTO `game` VALUES ('10', '', '1', '1', '10', '2', null, null, null);
INSERT INTO `game` VALUES ('11', '', '1', '2', '10', '0', null, null, null);
INSERT INTO `game` VALUES ('12', '', '1', '1', '15', '0', null, null, null);
INSERT INTO `game` VALUES ('15', '', '1', '1', '2', '1', '2016-01-08 22:13:46', '0', '1');

-- ----------------------------
-- Table structure for `game_bot`
-- ----------------------------
DROP TABLE IF EXISTS `game_bot`;
CREATE TABLE `game_bot` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ANSWER_PROBABILITY` double NOT NULL,
  `AVATAR` varchar(255) NOT NULL,
  `ENHANCEMENTS` varchar(255) NOT NULL,
  `NICKNAME` varchar(255) NOT NULL,
  `POINTS` int(11) NOT NULL,
  `READING_SPEED` double NOT NULL,
  `CATEGORY_ID` int(11) DEFAULT NULL,
  `CHARACTER_ID` int(11) DEFAULT NULL,
  `DIFFICULTY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_h63jctimpil5wykk1ddspleo` (`NICKNAME`) USING BTREE,
  KEY `FK_ibcf5kch3jj6ql778uxn9u3qx` (`CATEGORY_ID`) USING BTREE,
  KEY `FK_jixoukn3f5w5tv01oyvc8mldp` (`CHARACTER_ID`) USING BTREE,
  KEY `FK_7o2w327sk8qcgvn83pib45sb0` (`DIFFICULTY_ID`) USING BTREE,
  CONSTRAINT `game_bot_ibfk_1` FOREIGN KEY (`DIFFICULTY_ID`) REFERENCES `difficulty` (`DIFFICULTY_ID`),
  CONSTRAINT `game_bot_ibfk_2` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `game_bot_ibfk_3` FOREIGN KEY (`CHARACTER_ID`) REFERENCES `game_character` (`CHARACTER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of game_bot
-- ----------------------------
INSERT INTO `game_bot` VALUES ('1', '0.7', 'avatar', 'enhancement', 'vova', '120', '0.7', '3', '1', '2');
INSERT INTO `game_bot` VALUES ('2', '0.5', 'avatar', 'enhancement', 'terminator', '120', '0.5', '3', '1', '2');
INSERT INTO `game_bot` VALUES ('3', '0.8', 'avatar', 'enhancement', 'lola', '120', '0.8', '3', '1', '2');

-- ----------------------------
-- Table structure for `game_character`
-- ----------------------------
DROP TABLE IF EXISTS `game_character`;
CREATE TABLE `game_character` (
  `CHARACTER_ID` int(11) NOT NULL,
  `ACRONS` int(11) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `GOLD_ACRONS` int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `SPECIAL` bit(1) NOT NULL,
  PRIMARY KEY (`CHARACTER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of game_character
-- ----------------------------
INSERT INTO `game_character` VALUES ('1', '0', 'The most evil guy ever', '450', 'Coon', '');
INSERT INTO `game_character` VALUES ('2', '0', 'Big, kind and a bit plump man', '0', 'Jelin', '');
INSERT INTO `game_character` VALUES ('3', '0', 'Cute, slim and a bit cruel lady with charming eyes', '0', 'Daisy', '');

-- ----------------------------
-- Table structure for `game_opponent`
-- ----------------------------
DROP TABLE IF EXISTS `game_opponent`;
CREATE TABLE `game_opponent` (
  `GAME_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `STATUS` int(11) DEFAULT NULL,
  `PLAYER_NUM` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `gmuser` (`USER_ID`) USING BTREE,
  KEY `gmgame` (`GAME_ID`) USING BTREE,
  CONSTRAINT `game_opponent_ibfk_1` FOREIGN KEY (`GAME_ID`) REFERENCES `game` (`ID`),
  CONSTRAINT `game_opponent_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of game_opponent
-- ----------------------------
INSERT INTO `game_opponent` VALUES ('15', '14', '19', '1', '2');
INSERT INTO `game_opponent` VALUES ('15', '13', '20', '1', '3');
INSERT INTO `game_opponent` VALUES ('15', '12', '21', '1', '4');

-- ----------------------------
-- Table structure for `game_round`
-- ----------------------------
DROP TABLE IF EXISTS `game_round`;
CREATE TABLE `game_round` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROUND_NUMBER` int(11) DEFAULT NULL,
  `GAME_ID` int(11) DEFAULT NULL,
  `HOST` int(11) DEFAULT NULL,
  `PLAYER1_POINTS` int(11) NOT NULL,
  `PLAYER2_POINTS` int(11) NOT NULL,
  `PLAYER3_POINTS` int(11) NOT NULL,
  `PLAYER4_POINTS` int(11) NOT NULL,
  `CATEGORY_ID` int(11) DEFAULT NULL,
  `ANSWER_COUNT` int(11) DEFAULT NULL,
  `QUESTION_NUMBER` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_p07frm4fil83u6a19i1a6bkby` (`GAME_ID`) USING BTREE,
  KEY `FK_54xlh09tvp90x3ij2n9or3mo7` (`HOST`) USING BTREE,
  KEY `FK_frj1ko3ah7j0u0hm5laasau1e` (`CATEGORY_ID`) USING BTREE,
  CONSTRAINT `game_round_ibfk_1` FOREIGN KEY (`HOST`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `game_round_ibfk_2` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `game_round_ibfk_3` FOREIGN KEY (`GAME_ID`) REFERENCES `game` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of game_round
-- ----------------------------
INSERT INTO `game_round` VALUES ('1', '0', '15', '14', '0', '0', '0', '0', '5', null, null);
INSERT INTO `game_round` VALUES ('2', '1', '15', '12', '0', '0', '0', '0', null, null, null);
INSERT INTO `game_round` VALUES ('3', '2', '15', '13', '0', '0', '0', '0', null, null, null);
INSERT INTO `game_round` VALUES ('4', '3', '15', '2', '0', '0', '0', '0', null, null, null);

-- ----------------------------
-- Table structure for `game_round_question`
-- ----------------------------
DROP TABLE IF EXISTS `game_round_question`;
CREATE TABLE `game_round_question` (
  `game_round_ID` int(11) NOT NULL,
  `questions_ID` int(11) NOT NULL,
  UNIQUE KEY `UK_bu2e6mawcn4ksb1ehp27xdcqr` (`questions_ID`) USING BTREE,
  KEY `FK_d0j4a1v2ne38y5gnxcnq26sgp` (`game_round_ID`) USING BTREE,
  CONSTRAINT `game_round_question_ibfk_1` FOREIGN KEY (`questions_ID`) REFERENCES `question` (`ID`),
  CONSTRAINT `game_round_question_ibfk_2` FOREIGN KEY (`game_round_ID`) REFERENCES `game_round` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of game_round_question
-- ----------------------------

-- ----------------------------
-- Table structure for `image_layer`
-- ----------------------------
DROP TABLE IF EXISTS `image_layer`;
CREATE TABLE `image_layer` (
  `LAYER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `IMAGE` varchar(255) NOT NULL,
  `LAYER_NUM` int(11) NOT NULL,
  `CHARACTER_ID` int(11) NOT NULL,
  `ENHANCER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`LAYER_ID`),
  KEY `FK_ctt6wpjmoc0fp27yrg6uferis` (`CHARACTER_ID`) USING BTREE,
  KEY `FK_i5895p5bcitl5c5fye01u624n` (`ENHANCER_ID`) USING BTREE,
  CONSTRAINT `image_layer_ibfk_1` FOREIGN KEY (`CHARACTER_ID`) REFERENCES `game_character` (`CHARACTER_ID`),
  CONSTRAINT `image_layer_ibfk_2` FOREIGN KEY (`ENHANCER_ID`) REFERENCES `enhancer` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of image_layer
-- ----------------------------
INSERT INTO `image_layer` VALUES ('1', 'images/dragon/fon.png', '1', '2', null);
INSERT INTO `image_layer` VALUES ('2', 'images/dragon/island.png', '2', '2', null);
INSERT INTO `image_layer` VALUES ('3', 'images/dragon/character.png', '3', '2', null);
INSERT INTO `image_layer` VALUES ('4', 'images/dragon/glass.png', '4', '2', null);
INSERT INTO `image_layer` VALUES ('5', 'images/dragon/glasses.png', '5', '2', '1');
INSERT INTO `image_layer` VALUES ('6', 'images/dragon/shorts .png', '6', '2', '6');
INSERT INTO `image_layer` VALUES ('10', 'images/coon/character.png', '3', '1', null);
INSERT INTO `image_layer` VALUES ('11', 'images/coon/microphone.png', '5', '1', '3');
INSERT INTO `image_layer` VALUES ('20', 'images/squ/character.png', '3', '3', null);

-- ----------------------------
-- Table structure for `locality`
-- ----------------------------
DROP TABLE IF EXISTS `locality`;
CREATE TABLE `locality` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `REGION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_rn2uijnouc8lnxxpnjujmghy5` (`REGION_ID`) USING BTREE,
  CONSTRAINT `locality_ibfk_1` FOREIGN KEY (`REGION_ID`) REFERENCES `region` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of locality
-- ----------------------------
INSERT INTO `locality` VALUES ('1', 'Nadvirna', '2');
INSERT INTO `locality` VALUES ('2', 'Bytkiv', '2');
INSERT INTO `locality` VALUES ('3', 'Fastiv', '3');
INSERT INTO `locality` VALUES ('4', 'Boyarka', '3');

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ANSWER` int(11) NOT NULL,
  `QUESTION` varchar(255) NOT NULL,
  `TIME_A` int(11) NOT NULL,
  `TIME_B` int(11) NOT NULL,
  `TIME_C` int(11) NOT NULL,
  `VAR1` varchar(255) NOT NULL,
  `VAR2` varchar(255) NOT NULL,
  `VAR3` varchar(255) NOT NULL,
  `VAR4` varchar(255) NOT NULL,
  `CATEGORY_ID` int(11) DEFAULT NULL,
  `DIFFICULTY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_b10760elme44qcq3cxqcjichb` (`CATEGORY_ID`) USING BTREE,
  KEY `FK_p37ugf9qo3dwogqok1ph5rvac` (`DIFFICULTY_ID`) USING BTREE,
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `question_ibfk_2` FOREIGN KEY (`DIFFICULTY_ID`) REFERENCES `difficulty` (`DIFFICULTY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('1', '1', 'B кaкoм из этиx видoв спopтa paзыгpывaeтся бoльшe всex кoмплeктoв мeдaлeй нa зимнeй Oлимпиaдe ?', '11000', '3000', '3000', 'Двоеборье', 'Лыжные гонки', 'Конькобежный спорт', 'Биатлон', '1', '2');
INSERT INTO `question` VALUES ('2', '2', 'B кaкoм из этиx видoв спopтa paзыгpывaeтся бoльшe всex кoмплeктoв мeдaлeй нa зимнeй B кaкoм штaтe СШA нaxoдится стoлицa бyдyщиx зимниx Oлимп. игp?', '5000', '2000', '1000', 'Юта', 'Колорадо', 'Вайоминг', 'Калифорния', '1', '2');
INSERT INTO `question` VALUES ('3', '3', 'B кaкoй стpaнe нaxoдится гopoд Xиxoн?', '7000', '2000', '1000', 'Греция', 'Израиль', 'Испания', 'Италия', '2', '2');
INSERT INTO `question` VALUES ('4', '1', 'B кaкoй стpaнe нaxoдится сaмый высoкий вoдoпaд в миpe?', '5000', '2000', '2000', 'Венесуэла', 'Аргентина', 'Танзания', 'Замбия', '2', '2');
INSERT INTO `question` VALUES ('5', '3', 'Kaк paньшe нaзывaлaсь гpeкo-pимскaя бopьбa?', '11000', '3000', '3000', 'вольная', 'дзюдо', 'классическая', 'самбо', '1', '2');
INSERT INTO `question` VALUES ('6', '2', 'Bыстyпaя зa кaкoй клyб в 1997 гoдy бaскeтбoлист Aллeн Aйвepсoн зaслyжил звaниe \"Лyчший нoвичoк HБA\"?', '7000', '2000', '1000', 'Денвер Наггетс', 'Филадельфия-76', 'Хьюстон Рокетс', 'Чикаго Буллз', '1', '2');
INSERT INTO `question` VALUES ('7', '1', 'Ha кaкoм кoнтинeнтe paспoлoжeн вyлкaн Эpeбyс?', '8000', '3000', '1000', 'Антарктида', 'Африка', 'Северная Америка', 'Южная Америка', '2', '2');
INSERT INTO `question` VALUES ('8', '2', 'Ha тeppитopии кaкoй стpaны paспoлoжeнo yстьe peки Meкoнг?', '5000', '1500', '1500', 'Бирма', 'Вьетнам', 'Камбоджа', 'Китай', '2', '2');

-- ----------------------------
-- Table structure for `question_group`
-- ----------------------------
DROP TABLE IF EXISTS `question_group`;
CREATE TABLE `question_group` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question_group
-- ----------------------------
INSERT INTO `question_group` VALUES ('1', 'public group', 'public');
INSERT INTO `question_group` VALUES ('2', 'private group', 'private ');
INSERT INTO `question_group` VALUES ('3', 'bonus group', 'bonus');

-- ----------------------------
-- Table structure for `question_result`
-- ----------------------------
DROP TABLE IF EXISTS `question_result`;
CREATE TABLE `question_result` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ANSWER` int(11) DEFAULT NULL,
  `PLAYER_NUMBER` int(11) DEFAULT NULL,
  `SCORE` int(11) DEFAULT NULL,
  `ROUND_ID` int(11) DEFAULT NULL,
  `QUESTION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_39612wbswtbve3obsss9n6uqj` (`ROUND_ID`) USING BTREE,
  KEY `FK_rek3fwa617a8ypr5fnocao8rj` (`QUESTION_ID`) USING BTREE,
  CONSTRAINT `question_result_ibfk_1` FOREIGN KEY (`ROUND_ID`) REFERENCES `game_round` (`ID`),
  CONSTRAINT `question_result_ibfk_2` FOREIGN KEY (`QUESTION_ID`) REFERENCES `question` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question_result
-- ----------------------------

-- ----------------------------
-- Table structure for `region`
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `COUNTRY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_6g2kw0cm4ibdrstgjps2l2w23` (`COUNTRY_ID`) USING BTREE,
  CONSTRAINT `region_ibfk_1` FOREIGN KEY (`COUNTRY_ID`) REFERENCES `country` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of region
-- ----------------------------
INSERT INTO `region` VALUES ('1', 'Kyiv', '1');
INSERT INTO `region` VALUES ('2', 'Ivano-Frankisvka oblast', '1');
INSERT INTO `region` VALUES ('3', 'Kyiv oblast', '1');

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
INSERT INTO `role` VALUES ('2', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for `score_summary`
-- ----------------------------
DROP TABLE IF EXISTS `score_summary`;
CREATE TABLE `score_summary` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACRONS` int(11) DEFAULT NULL,
  `SCORE` int(11) NOT NULL,
  `GAME_ID` int(11) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_dfd7w3n7fusukadvd2xcx8u1p` (`GAME_ID`) USING BTREE,
  KEY `FK_7e947wl379imyjgjdkjduknml` (`USER_ID`) USING BTREE,
  CONSTRAINT `score_summary_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `score_summary_ibfk_2` FOREIGN KEY (`GAME_ID`) REFERENCES `game` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of score_summary
-- ----------------------------

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
  `TYPE` int(11) NOT NULL,
  `BOT_ID` int(11) DEFAULT NULL,
  `ACORNS` int(11) DEFAULT NULL,
  `GOLD_ACORNS` int(11) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UK_gmrrm359qu7ogusdu9r2qr637` (`EMAIL`) USING BTREE,
  UNIQUE KEY `UK_4xc5q2uhghtb4qrbyheoohvs` (`USERNAME`) USING BTREE,
  KEY `user_character` (`CHARACTER_ID`) USING BTREE,
  KEY `FK_p81kojifq6m7shgkqs3m0w9cx` (`BOT_ID`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`CHARACTER_ID`) REFERENCES `game_character` (`CHARACTER_ID`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`BOT_ID`) REFERENCES `game_bot` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'vitalii.oleksiv@gmail.com', 'vitalii', '2', null, '0', '0', '0', null, '0', '0');
INSERT INTO `user` VALUES ('2', 'vasya@gmail.com', 'vasya', '2', null, '1', '4', '0', null, '0', '0');
INSERT INTO `user` VALUES ('3', 'valera@gmail.com', 'valera', '3', null, '0', '3', '0', null, '0', '0');
INSERT INTO `user` VALUES ('7', 'john@gmail.com', 'john', null, null, '0', '1', '0', null, '0', '0');
INSERT INTO `user` VALUES ('8', 'paul@gmail.com', 'paul', null, null, '0', '3', '0', null, '0', '0');
INSERT INTO `user` VALUES ('9', 'jeremy@gmail.com', 'jeremy', null, null, '0', '3', '0', null, '0', '0');
INSERT INTO `user` VALUES ('10', 'ibr@gmail.com', 'ibrahim', '1', null, '0', '1', '0', null, '0', '0');
INSERT INTO `user` VALUES ('11', 'rokko@gmail.com', 'rokko', null, null, '0', '0', '0', null, '0', '0');
INSERT INTO `user` VALUES ('12', 'roman@gmail.com', 'roman', null, null, '0', '4', '0', null, '0', '0');
INSERT INTO `user` VALUES ('13', 'roland@gmail.com', 'roland', null, null, '0', '4', '0', null, '0', '0');
INSERT INTO `user` VALUES ('14', 'opp@gmail.com', 'opponent', null, null, '0', '4', '0', null, '0', '0');
INSERT INTO `user` VALUES ('15', 'user@gmail.com', 'user', '2', null, '0', '1', '0', null, '2500', '50');

-- ----------------------------
-- Table structure for `user_character`
-- ----------------------------
DROP TABLE IF EXISTS `user_character`;
CREATE TABLE `user_character` (
  `USER_ID` int(11) NOT NULL,
  `CHARACTER_ID` int(255) NOT NULL,
  PRIMARY KEY (`USER_ID`,`CHARACTER_ID`),
  KEY `FK_rr7e0hkjtnv7kp173slul61uw` (`CHARACTER_ID`) USING BTREE,
  CONSTRAINT `user_character_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_character
-- ----------------------------
INSERT INTO `user_character` VALUES ('15', '1');
INSERT INTO `user_character` VALUES ('1', '2');
INSERT INTO `user_character` VALUES ('15', '2');
INSERT INTO `user_character` VALUES ('15', '3');

-- ----------------------------
-- Table structure for `user_enhancer`
-- ----------------------------
DROP TABLE IF EXISTS `user_enhancer`;
CREATE TABLE `user_enhancer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RECEIVE_DATE` datetime DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `ENHANCER_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_3ugnjwdhkks262hriy0ikqnd7` (`ENHANCER_ID`) USING BTREE,
  KEY `FK_9o4bvq5rcu9kprwved6s5r0c0` (`USER_ID`) USING BTREE,
  CONSTRAINT `user_enhancer_ibfk_1` FOREIGN KEY (`ENHANCER_ID`) REFERENCES `enhancer` (`ID`),
  CONSTRAINT `user_enhancer_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_enhancer
-- ----------------------------
INSERT INTO `user_enhancer` VALUES ('1', null, '0', '1', '15');
INSERT INTO `user_enhancer` VALUES ('2', '2016-01-12 15:30:48', '0', '6', '15');
INSERT INTO `user_enhancer` VALUES ('3', null, '0', '4', '15');
INSERT INTO `user_enhancer` VALUES ('4', null, '0', '3', '15');

-- ----------------------------
-- Table structure for `user_interests`
-- ----------------------------
DROP TABLE IF EXISTS `user_interests`;
CREATE TABLE `user_interests` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GAMES_PLAYED` int(11) NOT NULL,
  `DIFFICULTY_ID` int(11) NOT NULL,
  `CATEGORY_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_tow1r7ay1ndbhjhm4h1hw0yey` (`DIFFICULTY_ID`) USING BTREE,
  KEY `FK_70f1n3cg1ynupbualyn7u12fn` (`CATEGORY_ID`) USING BTREE,
  KEY `FK_6cpad11284t0r5ro3rm85oukc` (`USER_ID`) USING BTREE,
  CONSTRAINT `user_interests_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `user_interests_ibfk_2` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `user_interests_ibfk_3` FOREIGN KEY (`DIFFICULTY_ID`) REFERENCES `difficulty` (`DIFFICULTY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_interests
-- ----------------------------

-- ----------------------------
-- Table structure for `user_model`
-- ----------------------------
DROP TABLE IF EXISTS `user_model`;
CREATE TABLE `user_model` (
  `USERNAME` varchar(42) NOT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `PASSWORD` varchar(42) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USERNAME`),
  UNIQUE KEY `UK_g82auop76r7ayyxajcs3yipco` (`EMAIL`) USING BTREE,
  KEY `FK_5qpaav3nklyqcbb5vy6109pd1` (`ROLE_ID`) USING BTREE,
  CONSTRAINT `user_model_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_model
-- ----------------------------
INSERT INTO `user_model` VALUES ('ibrahim', 'ibr@gmail.com', 'ibr', '1');
INSERT INTO `user_model` VALUES ('jeremy', 'jeremy@gmail.com', 'jeremy1', '1');
INSERT INTO `user_model` VALUES ('john', 'john@gmail.com', 'john1', '1');
INSERT INTO `user_model` VALUES ('opponent', 'opp@gmail.com', 'opp1', '1');
INSERT INTO `user_model` VALUES ('paul', 'paul@gmail.com', 'paul1', '1');
INSERT INTO `user_model` VALUES ('rokko', 'rokko@gmail.com', 'rokko1', '1');
INSERT INTO `user_model` VALUES ('roland', 'roland@gmail.com', 'roland1', '1');
INSERT INTO `user_model` VALUES ('roman', 'roman@gmail.com', 'roman1', '1');
INSERT INTO `user_model` VALUES ('user', 'user@gmail.com', 'user1', '1');
INSERT INTO `user_model` VALUES ('valera', 'valera@gmail.com', 'val', '1');
INSERT INTO `user_model` VALUES ('vasya', 'vasya@gmail.com', 'vasya', '1');
INSERT INTO `user_model` VALUES ('vitalii', 'vitalii.oleksiv@gmail.com', 'vit', '1');
