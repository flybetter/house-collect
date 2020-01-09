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

 Date: 06/18/2019 15:55:41 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `block`
-- ----------------------------
DROP TABLE IF EXISTS `block`;
CREATE TABLE `block` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city_code` varchar(20) DEFAULT NULL,
  `platform_id` tinyint(4) DEFAULT NULL,
  `block_name` varchar(200) DEFAULT NULL,
  `block_id` varchar(50) DEFAULT NULL,
  `local_block_name` varchar(200) DEFAULT NULL,
  `local_block_id` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`city_code`, `platform_id`, `block_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
