/*
 Navicat Premium Data Transfer

 Source Server         : 零售开发测试库
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 192.168.5.3:3306
 Source Schema         : flowable_demo

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 29/07/2022 09:08:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_form
-- ----------------------------
DROP TABLE IF EXISTS `t_form`;
CREATE TABLE `t_form`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `day` int(0) NULL DEFAULT NULL COMMENT '请假时长',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
  `status` int(0) NULL DEFAULT 0 COMMENT '状态0发起 1撤回  2通过 3终止',
  `process_instance_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程实例id',
  `model_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程key',
  `create_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_form
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (13, '张三');
INSERT INTO `t_user` VALUES (14, '李四');
INSERT INTO `t_user` VALUES (15, '王五');


CREATE TABLE `t_group` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(20) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `t_group`(`id`, `name`) VALUES (1, '组1');
INSERT INTO `t_group`(`id`, `name`) VALUES (2, '组2');



CREATE TABLE `t_user_group` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `user_id` int DEFAULT NULL,
                                `group_id` int DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `t_user_group`(`id`, `user_id`, `group_id`) VALUES (1, 13, 1);
INSERT INTO `t_user_group`(`id`, `user_id`, `group_id`) VALUES (2, 14, 2);
INSERT INTO `t_user_group`(`id`, `user_id`, `group_id`) VALUES (3, 15, 2);


CREATE TABLE `t_role` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `name` varchar(20) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `t_role`(`id`, `name`) VALUES (1, '开发部');
INSERT INTO `t_role`(`id`, `name`) VALUES (2, '测试部');
INSERT INTO `t_role`(`id`, `name`) VALUES (3, '人事部');


CREATE TABLE `t_user_role` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `user_id` int DEFAULT NULL,
                               `role_id` int DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `t_user_role`(`id`, `user_id`, `role_id`) VALUES (1, 13, 1);
INSERT INTO `t_user_role`(`id`, `user_id`, `role_id`) VALUES (2, 14, 2);
INSERT INTO `t_user_role`(`id`, `user_id`, `role_id`) VALUES (3, 15, 3);


SET FOREIGN_KEY_CHECKS = 1;
