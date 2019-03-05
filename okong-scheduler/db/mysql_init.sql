/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50724
Source Host           : 127.0.0.1:3306
Source Database       : java-project

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-03-05 23:15:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sched_config`
-- ----------------------------
DROP TABLE IF EXISTS `sched_config`;
CREATE TABLE `sched_config` (
  `id` bigint(20) NOT NULL,
  `name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `target_service_type` varchar(2) DEFAULT NULL COMMENT '目标任务类型：01 springcloud 02 http 03 dubbo',
  `targer_service` varchar(50) DEFAULT NULL COMMENT '目标服务：可为服务地址，或者dubbo服务名',
  `cron_config` varchar(20) DEFAULT NULL COMMENT 'cron表达式',
  `status` varchar(1) DEFAULT NULL COMMENT '状态：1启用 0 停用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注说明',
  `extra_dubbo_group` varchar(50) DEFAULT NULL COMMENT 'dubbo组名',
  `extra_dubbo_version` varchar(50) DEFAULT NULL COMMENT 'dubbo服务版本信息',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sched_config
-- ----------------------------
INSERT INTO `sched_config` VALUES ('1', '测试task-01', '01', 'http://shedlock-demo/api/task', '*/15 * * * * ?', '1', null, null, null, '2019-03-05 21:00:42', '2019-03-05 21:00:45');
INSERT INTO `sched_config` VALUES ('2', '测试task-02', '02', 'http://127.0.0.1:8001/api/task', '*/15 * * * * ?', '1', null, null, null, null, null);
