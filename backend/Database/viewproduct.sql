/*
 Navicat Premium Data Transfer

 Source Server         : JPetStore
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : mypetstore-ssm

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 07/03/2025 15:14:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for viewproduct
-- ----------------------------
DROP TABLE IF EXISTS `viewproduct`;
CREATE TABLE `viewproduct`  (
  `productId` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `viewCount` int NULL DEFAULT NULL,
  PRIMARY KEY (`productId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of viewproduct
-- ----------------------------
INSERT INTO `viewproduct` VALUES ('AV-CB-01', 0);
INSERT INTO `viewproduct` VALUES ('AV-SB-02', 0);
INSERT INTO `viewproduct` VALUES ('FI-FW-01', 2);
INSERT INTO `viewproduct` VALUES ('FI-FW-02', 1);
INSERT INTO `viewproduct` VALUES ('FI-SW-01', 1);
INSERT INTO `viewproduct` VALUES ('FI-SW-02', 0);
INSERT INTO `viewproduct` VALUES ('FL-DLH-02', 0);
INSERT INTO `viewproduct` VALUES ('FL-DSH-01', 0);
INSERT INTO `viewproduct` VALUES ('K9-BD-01', 0);
INSERT INTO `viewproduct` VALUES ('K9-CW-01', 0);
INSERT INTO `viewproduct` VALUES ('K9-DL-01', 0);
INSERT INTO `viewproduct` VALUES ('K9-PO-02', 0);
INSERT INTO `viewproduct` VALUES ('K9-RT-01', 0);
INSERT INTO `viewproduct` VALUES ('K9-RT-02', 0);
INSERT INTO `viewproduct` VALUES ('RP-LI-02', 0);
INSERT INTO `viewproduct` VALUES ('RP-SN-01', 0);

SET FOREIGN_KEY_CHECKS = 1;
