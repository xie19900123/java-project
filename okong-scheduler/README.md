# okong-scheduler
>基于SpringCloud和quartz的一个统一调度服务，利用客户端负载均衡进行服务调用。

# 技术选型
1. 核心框架：`SpringBoot 2.0.3.RELEASE`、`Springcloud Finchley.SR1` 
2. 任务调度：`Quartz` 
3. 持久层框架：`MyBatis` + `MyBatis-Plus` 
4. 数据库：`mysql`

# 数据库脚本
```sql
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
)
```
# 待办
1. 加入日志记录功能
2. 支持dubbo服务调用
3. 失败重试