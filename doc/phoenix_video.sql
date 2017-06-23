/*
Navicat MySQL Data Transfer

Source Server         : 192.168.130.220
Source Server Version : 50158
Source Host           : 192.168.130.220:3306
Source Database       : phoenix_video

Target Server Type    : MYSQL
Target Server Version : 50158
File Encoding         : 65001

Date: 2017-06-23 15:22:00
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
-- Records of c3p0test
-- ----------------------------

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
  `attachment_id` bigint(20) DEFAULT NULL COMMENT '原视频附件',
  `transform_attachment_id` bigint(20) DEFAULT NULL COMMENT '转码后的视频附件',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` bigint(20) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='视频主表';

-- ----------------------------
-- Records of tb_video
-- ----------------------------
INSERT INTO `tb_video` VALUES ('1', '111', null, null, '2', null, '0', '0', '5', '0', null, null, '2017-05-26 10:13:05', '1');
INSERT INTO `tb_video` VALUES ('2', '112', null, null, '3', null, '0', '0', '0', '0', null, null, '2017-05-26 10:24:38', '1');
INSERT INTO `tb_video` VALUES ('3', '113', null, null, '4', null, '0', '0', '0', '0', null, null, '2017-05-26 10:25:29', '1');
INSERT INTO `tb_video` VALUES ('4', '113', null, null, '1', null, '0', '0', '0', '0', null, null, '2017-05-26 10:26:30', '1');
INSERT INTO `tb_video` VALUES ('5', '1112', null, null, '1', null, '0', '0', '0', '0', null, null, '2017-05-31 11:19:01', '1');
INSERT INTO `tb_video` VALUES ('6', 'string', 'string', 'string', '1', null, '0', '0', '0', '0', '0', '0', '2017-06-06 15:14:41', '0');
INSERT INTO `tb_video` VALUES ('7', '速度与激情7', '速度,激情', '速度与激情7', '2', null, '0', '99', '100', '0', '0', '0', '2017-06-06 15:26:17', '7');
INSERT INTO `tb_video` VALUES ('8', '速度与激情6', '速度,激情', '速度与激情6', '2', null, '0', '99', '100', '0', '0', '0', '2017-06-06 15:37:00', '7');
INSERT INTO `tb_video` VALUES ('9', '速度与激情6', '速度,激情', '速度与激情6', '3', null, '1', '99', '100', '0', '0', '0', '2017-06-06 15:38:24', '7');
INSERT INTO `tb_video` VALUES ('10', '33333', null, null, '3', null, '0', '0', '0', '33333', null, null, '2017-06-06 15:57:24', '33333');
INSERT INTO `tb_video` VALUES ('11', '33333', null, null, '0', null, '0', '0', '0', '33346', null, null, '2017-06-06 15:59:32', '33333');
INSERT INTO `tb_video` VALUES ('12', '33333', null, null, '0', null, '0', '0', '0', '33333', null, null, '2017-06-06 16:01:37', '33333');
INSERT INTO `tb_video` VALUES ('13', '33333', null, null, '1', null, '0', '0', '0', '33333', '10', null, '2017-06-06 16:04:46', '33333');
INSERT INTO `tb_video` VALUES ('14', 'string', 'string', 'string', '0', null, '0', '0', '0', '0', '11', '12', '2017-06-06 16:07:31', '0');
INSERT INTO `tb_video` VALUES ('15', '', 'string', 'string', '0', null, '0', '0', '0', '0', '13', '14', '2017-06-16 11:14:33', '0');
INSERT INTO `tb_video` VALUES ('16', '', 'string', 'string', '0', null, '0', '0', '0', '0', '15', '16', '2017-06-16 11:18:25', '0');
INSERT INTO `tb_video` VALUES ('17', 'string', 'string', 'string', '0', null, '0', '0', '0', '0', '17', '18', '2017-06-16 15:40:52', '0');
INSERT INTO `tb_video` VALUES ('18', 'string', 'string', 'string', '0', null, '0', '0', '0', '0', '19', '20', '2017-06-16 15:41:33', '0');
INSERT INTO `tb_video` VALUES ('19', '33333', null, null, '0', null, '0', '0', '0', '33333', '21', null, '2017-06-19 14:20:15', '33333');
INSERT INTO `tb_video` VALUES ('20', '33333', null, null, '0', null, '0', '0', '0', '33333', '22', null, '2017-06-19 14:21:03', '33333');
INSERT INTO `tb_video` VALUES ('21', '33333', null, null, '0', null, '0', '0', '0', '33333', '23', null, '2017-06-19 18:23:11', '33333');
INSERT INTO `tb_video` VALUES ('22', '33333', null, null, '0', null, '0', '0', '0', '33333', '24', null, '2017-06-19 18:23:48', '33333');
INSERT INTO `tb_video` VALUES ('23', '33333', null, null, '0', null, '0', '0', '0', '33333', '25', null, '2017-06-19 18:24:46', '33333');

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
  `duration` bigint(20) DEFAULT NULL COMMENT '视频时长',
  `screenshot_url_a` varchar(512) DEFAULT NULL COMMENT '视频截图1',
  `screenshot_url_b` varchar(512) DEFAULT NULL COMMENT '视频截图2',
  `screenshot_url_c` varchar(512) DEFAULT NULL COMMENT '视频截图3',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `aliyun_video_id` varchar(255) DEFAULT NULL COMMENT '阿里云上对应的视频id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='视频附件表';

-- ----------------------------
-- Records of tb_video_attachment
-- ----------------------------
INSERT INTO `tb_video_attachment` VALUES ('1', 'string', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-06 15:14:41', null);
INSERT INTO `tb_video_attachment` VALUES ('2', '速度与激情7.mkv', '0', 'mkv', 'string', '0', 'string', 'string', 'string', '2017-06-06 15:26:17', null);
INSERT INTO `tb_video_attachment` VALUES ('3', '速度与激情6.mkv', '0', 'mkv', 'string', '0', 'string', 'string', 'string', '2017-06-06 15:37:00', null);
INSERT INTO `tb_video_attachment` VALUES ('4', 'string', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-06 15:37:00', null);
INSERT INTO `tb_video_attachment` VALUES ('5', '速度与激情6.mkv', '0', 'mkv', 'string', '0', 'string', 'string', 'string', '2017-06-06 15:38:24', null);
INSERT INTO `tb_video_attachment` VALUES ('6', 'string', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-06 15:38:24', null);
INSERT INTO `tb_video_attachment` VALUES ('7', '33333', null, null, null, null, null, null, null, '2017-06-06 15:57:39', null);
INSERT INTO `tb_video_attachment` VALUES ('8', '33333', null, null, null, null, null, null, null, '2017-06-06 15:59:36', null);
INSERT INTO `tb_video_attachment` VALUES ('9', '33333', null, null, null, null, null, null, null, '2017-06-06 16:01:41', null);
INSERT INTO `tb_video_attachment` VALUES ('10', '33333', null, null, null, null, null, null, null, '2017-06-06 16:04:49', null);
INSERT INTO `tb_video_attachment` VALUES ('11', 'string', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-06 16:07:31', '2d6d10481e6945b48c3fa5a8766a6dd2');
INSERT INTO `tb_video_attachment` VALUES ('12', 'string', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-06 16:07:32', '2d6d10481e6945b48c3fa5a8766a6dd2');
INSERT INTO `tb_video_attachment` VALUES ('13', 'string', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-16 11:14:33', '2d6d10481e6945b48c3fa5a8766a6dd2');
INSERT INTO `tb_video_attachment` VALUES ('14', 'string', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-16 11:14:33', '2d6d10481e6945b48c3fa5a8766a6dd2');
INSERT INTO `tb_video_attachment` VALUES ('15', 'string', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-16 11:18:25', '2d6d10481e6945b48c3fa5a8766a6dd2');
INSERT INTO `tb_video_attachment` VALUES ('16', 'string', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-16 11:18:25', '2d6d10481e6945b48c3fa5a8766a6dd2');
INSERT INTO `tb_video_attachment` VALUES ('17', 'string', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-16 15:40:52', 'string');
INSERT INTO `tb_video_attachment` VALUES ('18', '', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-16 15:40:52', 'string');
INSERT INTO `tb_video_attachment` VALUES ('19', '', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-16 15:41:33', 'string');
INSERT INTO `tb_video_attachment` VALUES ('20', '', '0', 'string', 'string', '0', 'string', 'string', 'string', '2017-06-16 15:41:33', 'string');
INSERT INTO `tb_video_attachment` VALUES ('21', '33333', null, null, null, null, null, null, null, '2017-06-19 14:20:15', null);
INSERT INTO `tb_video_attachment` VALUES ('22', '33333', null, null, null, null, null, null, null, '2017-06-19 14:21:03', null);
INSERT INTO `tb_video_attachment` VALUES ('23', '33333', null, null, null, null, null, null, null, '2017-06-19 18:23:11', null);
INSERT INTO `tb_video_attachment` VALUES ('24', '33333', null, null, null, null, null, null, null, '2017-06-19 18:23:48', null);
INSERT INTO `tb_video_attachment` VALUES ('25', '33333', null, null, null, null, null, null, null, '2017-06-19 18:24:46', null);

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='视频评论记录';

-- ----------------------------
-- Records of tb_video_discuss
-- ----------------------------
INSERT INTO `tb_video_discuss` VALUES ('1', '11', '20', '评论评论我是评论', '2017-06-07 15:15:12');
INSERT INTO `tb_video_discuss` VALUES ('2', '11', '21', '评论评论我是评论', '2017-06-07 15:15:25');
INSERT INTO `tb_video_discuss` VALUES ('3', '11', '22', '评论评论我是评论', '2017-06-07 15:15:32');
INSERT INTO `tb_video_discuss` VALUES ('4', '11', '23', '评论评论我是评论', '2017-06-07 15:15:36');
INSERT INTO `tb_video_discuss` VALUES ('5', '11', '24', '评论评论我是评论', '2017-06-07 15:15:39');
INSERT INTO `tb_video_discuss` VALUES ('6', '11', '25', '评论评论我是评论', '2017-06-07 15:15:42');
INSERT INTO `tb_video_discuss` VALUES ('7', '11', '26', '评论评论我是评论', '2017-06-07 15:15:46');
INSERT INTO `tb_video_discuss` VALUES ('8', '11', '27', '评论评论我是评论', '2017-06-07 15:15:49');
INSERT INTO `tb_video_discuss` VALUES ('9', '11', '28', '评论评论我是评论', '2017-06-07 15:15:53');
INSERT INTO `tb_video_discuss` VALUES ('10', '11', '29', '评论评论我是评论', '2017-06-07 15:15:57');
INSERT INTO `tb_video_discuss` VALUES ('11', '11', '30', '评论评论我是评论', '2017-06-07 15:16:02');
INSERT INTO `tb_video_discuss` VALUES ('12', '11', '31', '评论评论我是评论', '2017-06-07 15:16:05');
INSERT INTO `tb_video_discuss` VALUES ('13', '11', '32', '评论评论我是评论', '2017-06-07 15:16:09');

-- ----------------------------
-- Table structure for tb_video_enshrine
-- ----------------------------
DROP TABLE IF EXISTS `tb_video_enshrine`;
CREATE TABLE `tb_video_enshrine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL COMMENT '视频id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频收藏记录';

-- ----------------------------
-- Records of tb_video_enshrine
-- ----------------------------

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
-- Records of tb_video_like
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='视频播放记录';

-- ----------------------------
-- Records of tb_video_play
-- ----------------------------
INSERT INTO `tb_video_play` VALUES ('1', '1', '1', '8640', '2017-06-06 16:22:20');
INSERT INTO `tb_video_play` VALUES ('2', '1', '2', '86400', '2017-05-31 11:27:43');
INSERT INTO `tb_video_play` VALUES ('3', '1', '3', '565', '2017-06-07 16:22:45');
INSERT INTO `tb_video_play` VALUES ('4', '1', '4', '54', '2017-06-07 19:37:49');
INSERT INTO `tb_video_play` VALUES ('5', '1', '8', '56', '2017-06-05 19:37:52');
INSERT INTO `tb_video_play` VALUES ('6', '1', '3', '86400', '2017-06-19 14:20:16');
INSERT INTO `tb_video_play` VALUES ('7', '1', '3', '86400', '2017-06-19 14:21:04');
INSERT INTO `tb_video_play` VALUES ('8', '1', '3', '86400', '2017-06-19 18:23:11');
INSERT INTO `tb_video_play` VALUES ('9', '1', '3', '86400', '2017-06-19 18:23:48');
INSERT INTO `tb_video_play` VALUES ('10', '1', '3', '86400', '2017-06-19 18:24:47');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频举报记录';

-- ----------------------------
-- Records of tb_video_report
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频分享记录';

-- ----------------------------
-- Records of tb_video_share
-- ----------------------------
