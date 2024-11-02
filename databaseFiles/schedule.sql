/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : schedule

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2021-06-02 11:59:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `adminID` bigint(11) NOT NULL AUTO_INCREMENT,
  `adminName` varchar(20) NOT NULL,
  `adminPassword` varchar(30) NOT NULL,
  `adminGender` smallint(1) DEFAULT '1',
  `adminDate` date DEFAULT NULL,
  PRIMARY KEY (`adminID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', 'admin', '1', '1999-07-28');
INSERT INTO `admin` VALUES ('3', 'sa', 'sa', '1', '2021-05-22');
INSERT INTO `admin` VALUES ('6', 'qwer', 'q', '1', '2021-04-24');
INSERT INTO `admin` VALUES ('10', '测试', '12345', '0', '2021-04-24');
INSERT INTO `admin` VALUES ('11', '测试1', '123456', '1', '2021-04-21');
INSERT INTO `admin` VALUES ('12', 'asdad', 'asd', '1', '2021-04-24');
INSERT INTO `admin` VALUES ('14', '123', '123', '1', '2021-05-06');
INSERT INTO `admin` VALUES ('17', 'ceshi111111', '1234', '0', null);

-- ----------------------------
-- Table structure for `schedule`
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
  `scheduleID` bigint(11) NOT NULL AUTO_INCREMENT,
  `userID` bigint(11) unsigned NOT NULL,
  `scheduleTitle` varchar(50) NOT NULL,
  `scheduleDetail` text,
  `startTime` datetime NOT NULL,
  `endTime` datetime DEFAULT NULL,
  `isAlert` int(1) unsigned NOT NULL DEFAULT '0',
  `isOk` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`scheduleID`),
  KEY `userID` (`userID`),
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES ('1', '12345678901', '测试', '测试', '2021-05-11 09:32:29', '2021-05-12 09:32:32', '0', '1');
INSERT INTO `schedule` VALUES ('170', '12345678901', '是否v', '', '2021-05-12 10:49:00', null, '0', '1');
INSERT INTO `schedule` VALUES ('171', '12345678901', '阿达v', '', '2021-05-11 10:54:00', null, '0', '1');
INSERT INTO `schedule` VALUES ('172', '12345678901', 'SFC', '安定', '2021-05-11 10:56:00', null, '0', '1');
INSERT INTO `schedule` VALUES ('173', '12345678901', '岁的法国v', '但是v', '2021-05-11 10:56:00', null, '0', '1');
INSERT INTO `schedule` VALUES ('174', '12345678901', 'SDV', '', '2021-05-11 11:03:00', null, '0', '1');
INSERT INTO `schedule` VALUES ('175', '12345678901', '额呵呵', '', '2021-05-12 14:57:00', null, '0', '1');
INSERT INTO `schedule` VALUES ('176', '12345678901', '吃饭', '', '2021-05-12 21:38:00', null, '0', '1');
INSERT INTO `schedule` VALUES ('177', '12345678901', '吃饭', '', '2021-05-12 10:42:00', null, '0', '0');
INSERT INTO `schedule` VALUES ('178', '12345678901', '写作业', '', '2021-05-12 10:42:00', null, '0', '0');
INSERT INTO `schedule` VALUES ('179', '123', '去二分', '', '2021-05-12 20:50:00', null, '0', '1');
INSERT INTO `schedule` VALUES ('180', '123', 'a\'s\'d\'f', '', '2021-05-13 20:50:00', null, '0', '0');
INSERT INTO `schedule` VALUES ('181', '123', '阿斯顿v', '', '2021-05-18 20:50:00', null, '0', '0');
INSERT INTO `schedule` VALUES ('182', '12345678901', '123', '123', '2021-05-18 21:48:00', null, '1', '0');
INSERT INTO `schedule` VALUES ('183', '12345678901', '123', '', '2021-05-18 21:49:00', '2021-05-19 00:00:00', '0', '0');
INSERT INTO `schedule` VALUES ('184', '12345678901', '撒地方', '', '2021-05-19 21:51:00', '2021-05-27 00:00:00', '0', '0');
INSERT INTO `schedule` VALUES ('185', '12345678901', '日程信息', '', '2021-05-04 08:05:00', null, '1', '0');
INSERT INTO `schedule` VALUES ('186', '12345678901', '日程信息', '', '2021-05-22 08:05:00', null, '0', '1');
INSERT INTO `schedule` VALUES ('187', '12345678901', '日程信息', '', '2021-05-22 08:05:00', null, '0', '1');
INSERT INTO `schedule` VALUES ('188', '12345678901', '日程信息', '微软', '2021-05-22 08:05:00', null, '0', '0');
INSERT INTO `schedule` VALUES ('189', '12345678901', '日程信息', '', '2021-05-26 08:05:00', null, '0', '0');
INSERT INTO `schedule` VALUES ('190', '12345678901', '日程', '', '2021-05-22 11:42:00', null, '1', '0');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userID` bigint(11) unsigned NOT NULL,
  `userName` varchar(20) NOT NULL,
  `userPassword` varchar(30) NOT NULL,
  `userGender` smallint(1) DEFAULT '1',
  `userDate` date DEFAULT NULL,
  `problemOne` varchar(255) NOT NULL,
  `answerOne` varchar(255) NOT NULL,
  `problemTwo` varchar(255) NOT NULL,
  `answerTwo` varchar(255) NOT NULL,
  `problemThree` varchar(255) NOT NULL,
  `answerThree` varchar(255) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '测试', 'test', 1, '2024-11-02', '你叫什么名字？', '测试', '你喜欢做什么？', '学习', '你来自哪里？', '中国');
