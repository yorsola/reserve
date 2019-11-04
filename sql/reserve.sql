/*
Navicat MySQL Data Transfer

Source Server         : spring-boot-samples
Source Server Version : 50554
Source Host           : localhost:3306
Source Database       : reserve

Target Server Type    : MYSQL
Target Server Version : 50554
File Encoding         : 65001

Date: 2019-11-04 21:38:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户 id',
  `
possessor_number` varchar(50) NOT NULL COMMENT '使用人证件号',
  `possessor_name` varchar(50) NOT NULL COMMENT '使用人名字',
  `possessor_phone` varchar(20) NOT NULL COMMENT '使用人电话',
  `round` varchar(50) NOT NULL COMMENT '场次',
  `code` varchar(50) DEFAULT NULL COMMENT '入场序列码',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '电子票类型，0-成人，1-儿童',
  `state` int(1) DEFAULT NULL COMMENT '审核状态，1-审核成功，2-审核拒绝，0-审核中',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  `valid` int(1) NOT NULL DEFAULT '1' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `order_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for campaign
-- ----------------------------
DROP TABLE IF EXISTS `campaign`;
CREATE TABLE `campaign` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `campaign_name` varchar(50) NOT NULL COMMENT '活动名称',
  `campaign_location` varchar(50) NOT NULL COMMENT '活动地址',
  `campaign_time` datetime NOT NULL COMMENT '活动时间',
  `campaign_round` int(5) NOT NULL COMMENT '活动场次',
  `campaign_details` varchar(1024) NOT NULL COMMENT '活动详情',
  `campaign_guidelines` varchar(1024) NOT NULL COMMENT '参与须知',
  `campaign_problems` varchar(1024) NOT NULL COMMENT '常见问答',
  `valid` int(1) NOT NULL DEFAULT '1' COMMENT '有效性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) NOT NULL COMMENT 'openid',
  `session_key` varchar(50) NOT NULL COMMENT '会话密钥',
  `access_token` varchar(50) DEFAULT NULL COMMENT '访问令牌',
  `created` date DEFAULT NULL COMMENT '创建时间',
  `updated` date DEFAULT NULL COMMENT '修改时间',
  `valid` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '有效性',
  PRIMARY KEY (`id`),
  KEY `openid` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
