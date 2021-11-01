/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : ent

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 01/11/2021 10:02:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ent_change_log
-- ----------------------------
DROP TABLE IF EXISTS `ent_change_log`;
CREATE TABLE `ent_change_log`  (
  `id` bigint(20) NOT NULL,
  `ent_id` int(11) DEFAULT NULL,
  `al_before` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `al_after` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `al_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ent_info
-- ----------------------------
DROP TABLE IF EXISTS `ent_info`;
CREATE TABLE `ent_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ent_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_ent_id`(`ent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
