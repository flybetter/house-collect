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

 Date: 06/19/2019 08:47:36 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sell`
-- ----------------------------
DROP TABLE IF EXISTS `sell`;
CREATE TABLE `sell` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city_code` varchar(20) DEFAULT NULL,
  `platform_id` tinyint(4) DEFAULT NULL,
  `house_id` varchar(50) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `sub_district` varchar(50) DEFAULT NULL,
  `block_name` varchar(100) DEFAULT NULL,
  `block_id` varchar(50) DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `unit_price` decimal(10,2) DEFAULT NULL,
  `room_count` tinyint(4) DEFAULT NULL,
  `hall_count` tinyint(4) DEFAULT NULL,
  `toilet_count` tinyint(4) DEFAULT NULL,
  `total_floor` tinyint(4) DEFAULT NULL,
  `floor_code` tinyint(4) DEFAULT NULL,
  `forward` varchar(20) DEFAULT NULL,
  `decoration` varchar(20) DEFAULT NULL,
  `build_area` decimal(10,2) DEFAULT NULL,
  `build_year` smallint(6) DEFAULT NULL,
  `has_lift` tinyint(4) DEFAULT NULL,
  `property_right_year` tinyint(4) DEFAULT NULL,
  `list_time` varchar(8) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`city_code`, `platform_id`, `house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
