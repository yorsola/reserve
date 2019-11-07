/*
Navicat MySQL Data Transfer

Source Server         : spring-boot-samples
Source Server Version : 50554
Source Host           : localhost:3306
Source Database       : reserve

Target Server Type    : MYSQL
Target Server Version : 50554
File Encoding         : 65001

Date: 2019-11-07 03:18:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) NOT NULL COMMENT '用户 openid',
  `document_type` varchar(10) NOT NULL COMMENT '证件类型，111-二代居民身份证，411-护照，990=其他，991=港澳居民往来内地通行证，992-香港永久性居民身份证，993-台胞证',
  `possessor_number` varchar(50) NOT NULL COMMENT '使用人证件号',
  `possessor_name` varchar(50) NOT NULL COMMENT '使用人名字',
  `possessor_img` varchar(50) DEFAULT NULL COMMENT '使用人头像',
  `possessor_phone` varchar(20) NOT NULL COMMENT '使用人电话',
  `round_id` int(10) unsigned NOT NULL COMMENT '场次 id',
  `code` varchar(50) DEFAULT NULL COMMENT '入场序列码值',
  `company` varchar(50) NOT NULL COMMENT '所属单位',
  `type` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '电子票类型，0-成人电子票，1-儿童电子票',
  `state` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '审核状态，1-审核成功，2-审核拒绝，0-审核中',
  `examine_id` varchar(32) DEFAULT NULL COMMENT '备审 id',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  `valid` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '有效性,1-存在，0-不存在',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_id` (`openid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for campaign
-- ----------------------------
DROP TABLE IF EXISTS `campaign`;
CREATE TABLE `campaign` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '活动 id',
  `campaign_name` varchar(50) NOT NULL COMMENT '活动名称',
  `campaign_location` varchar(50) NOT NULL COMMENT '活动地址',
  `campaign_img` varchar(1024) DEFAULT NULL COMMENT '活动图片',
  `longitude` varchar(50) NOT NULL COMMENT '经度',
  `latitude` varchar(50) NOT NULL COMMENT '纬度',
  `campaign_time` datetime NOT NULL COMMENT '活动时间',
  `campaign_details` varchar(1024) DEFAULT NULL COMMENT '活动详情',
  `campaign_guidelines` varchar(1024) DEFAULT NULL COMMENT '参与须知',
  `campaign_problems` varchar(1024) DEFAULT NULL COMMENT '常见问答',
  `valid` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '有效性，1-存在，0-不存在',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of campaign
-- ----------------------------
INSERT INTO `campaign` VALUES ('1', '', '珠海市拱北区区情侣中路12号', '', '113.592245', ' 22.245207', '2019-12-20 20:00:00', '', '', '', '1');

-- ----------------------------
-- Table structure for round
-- ----------------------------
DROP TABLE IF EXISTS `round`;
CREATE TABLE `round` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `round_info` varchar(50) NOT NULL COMMENT '场次信息',
  `campaign_id` bigint(10) unsigned NOT NULL COMMENT '活动 id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of round
-- ----------------------------
INSERT INTO `round` VALUES ('1', 'A区入口：昌盛路、粤海东路', '1');
INSERT INTO `round` VALUES ('2', 'B区入口：粤海东路、联安路', '1');
INSERT INTO `round` VALUES ('3', 'C区入口：联安路、水湾路', '1');
INSERT INTO `round` VALUES ('4', 'D区入口：水湾路', '1');
INSERT INTO `round` VALUES ('5', 'E区入口：情侣南路延长线', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) NOT NULL COMMENT 'openid',
  `session_key` varchar(50) NOT NULL COMMENT '会话密钥',
  `access_token` varchar(50) DEFAULT NULL COMMENT '访问令牌',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  `valid` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '有效性,1-存在，0-不存在',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `openid` (`openid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
