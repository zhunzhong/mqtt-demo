/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 05/02/2023 17:22:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for meter
-- ----------------------------
DROP TABLE IF EXISTS `meter`;
CREATE TABLE `meter`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `current` double NULL DEFAULT NULL,
  `voltage` double NULL DEFAULT NULL,
  `phase` double NULL DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `group_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ts` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `device_id`(`device_id`) USING BTREE,
  INDEX `location`(`location`) USING BTREE,
  INDEX `group_id`(`group_id`) USING BTREE,
  INDEX `ts`(`ts`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 582 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
