/*
 Navicat Premium Data Transfer

 Source Server         : my-mysql
 Source Server Type    : MySQL
 Source Server Version : 50561
 Source Host           : localhost
 Source Database       : house

 Target Server Type    : MySQL
 Target Server Version : 50561
 File Encoding         : utf-8

 Date: 06/20/2019 09:15:43 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `district`
-- ----------------------------
DROP TABLE IF EXISTS `district`;
CREATE TABLE `district` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city_code` varchar(20) DEFAULT NULL,
  `platform_id` tinyint(4) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `sub_district` varchar(50) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`city_code`, `platform_id`,`district`, `sub_district`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
