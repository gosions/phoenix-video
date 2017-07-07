/*
Navicat MySQL Data Transfer

Source Server         : 192.168.101.42
Source Server Version : 50158
Source Host           : 192.168.101.42:3306
Source Database       : phoenix_video

Target Server Type    : MYSQL
Target Server Version : 50158
File Encoding         : 65001

Date: 2017-07-07 11:12:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for c3p0test
-- ----------------------------
DROP TABLE IF EXISTS `c3p0test`;
CREATE TABLE `c3p0test` (
  `a` char(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_video
-- ----------------------------
DROP TABLE IF EXISTS `tb_video`;
CREATE TABLE `tb_video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) NOT NULL COMMENT '视频标题',
  `tag` varchar(255) DEFAULT NULL COMMENT '标签',
  `description` varchar(1024) DEFAULT NULL COMMENT '简介/描述',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态，0--未审核，1--正常，2--审核驳回，3--禁用，4--删除',
  `forbidden_reason` varchar(512) DEFAULT NULL COMMENT '视频禁用的原因',
  `top` tinyint(4) DEFAULT '0' COMMENT '是否置顶，0--否，1--是',
  `like_time` bigint(20) DEFAULT '0' COMMENT '点赞次数',
  `play_time` bigint(20) DEFAULT '0' COMMENT '播放次数',
  `discuss_time` bigint(20) DEFAULT '0' COMMENT '评论次数',
  `duration` float(10,2) DEFAULT NULL COMMENT '视频时长',
  `attachment_id` bigint(20) DEFAULT NULL COMMENT '原视频附件',
  `transform_attachment_id` bigint(20) DEFAULT NULL COMMENT '转码后的视频附件',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` bigint(20) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`id`),
  KEY `index_title` (`title`),
  KEY `index_status` (`status`),
  KEY `index_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='视频主表';

-- ----------------------------
-- Table structure for tb_video_attachment
-- ----------------------------
DROP TABLE IF EXISTS `tb_video_attachment`;
CREATE TABLE `tb_video_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_name` varchar(1024) DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `file_type` varchar(30) DEFAULT NULL COMMENT '文件类型',
  `download_url` varchar(512) DEFAULT NULL COMMENT '下载地址',
  `screenshot_url_a` varchar(512) DEFAULT NULL COMMENT '视频截图1',
  `screenshot_url_b` varchar(512) DEFAULT NULL COMMENT '视频截图2',
  `screenshot_url_c` varchar(512) DEFAULT NULL COMMENT '视频截图3',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `aliyun_video_id` varchar(255) DEFAULT NULL COMMENT '阿里云上对应的视频id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='视频附件表';

-- ----------------------------
-- Table structure for tb_video_discuss
-- ----------------------------
DROP TABLE IF EXISTS `tb_video_discuss`;
CREATE TABLE `tb_video_discuss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL COMMENT '视频id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `content` varchar(1024) DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_video_id` (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='视频评论记录';

-- ----------------------------
-- Table structure for tb_video_enshrine
-- ----------------------------
DROP TABLE IF EXISTS `tb_video_enshrine`;
CREATE TABLE `tb_video_enshrine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL COMMENT '视频id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `video_id` (`video_id`,`user_id`),
  KEY `index_video_id` (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='视频收藏记录';

-- ----------------------------
-- Table structure for tb_video_like
-- ----------------------------
DROP TABLE IF EXISTS `tb_video_like`;
CREATE TABLE `tb_video_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL COMMENT '视频id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频点赞记录';

-- ----------------------------
-- Table structure for tb_video_play
-- ----------------------------
DROP TABLE IF EXISTS `tb_video_play`;
CREATE TABLE `tb_video_play` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL COMMENT '视频id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `play_duration` bigint(20) DEFAULT NULL COMMENT '播放时长',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_video_id` (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='视频播放记录';

-- ----------------------------
-- Table structure for tb_video_report
-- ----------------------------
DROP TABLE IF EXISTS `tb_video_report`;
CREATE TABLE `tb_video_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL COMMENT '视频id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `report_type` varchar(30) DEFAULT NULL COMMENT '举报类型',
  `report_describe` varchar(1024) DEFAULT NULL COMMENT '举报描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_video_id` (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='视频举报记录';

-- ----------------------------
-- Table structure for tb_video_share
-- ----------------------------
DROP TABLE IF EXISTS `tb_video_share`;
CREATE TABLE `tb_video_share` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL COMMENT '视频id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `share_url` varchar(512) DEFAULT NULL COMMENT '分享路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_video_id` (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='视频分享记录';
