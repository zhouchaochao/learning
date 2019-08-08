

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `o_action`
-- ----------------------------
DROP TABLE IF EXISTS `o_action`;
CREATE TABLE `o_action` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名字',
  `api_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联o_api表的id',
  `arguments` mediumtext COMMENT '参数配置.json arr',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '类型。默认1',
  `description` varchar(512) NOT NULL DEFAULT '' COMMENT '描述',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT ' -1：删除,0：未生效,1：生效，',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人mis id',
  `create_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的local时间',
  `create_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的utc时间',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人',
  `update_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人mis id',
  `update_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的local时间',
  `update_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的utc时间',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(255) NOT NULL DEFAULT '' COMMENT '语言',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc偏移',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`),
  KEY `idx_api_id` (`api_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='action定义';

-- ----------------------------
--  Table structure for `o_api`
-- ----------------------------
DROP TABLE IF EXISTS `o_api`;
CREATE TABLE `o_api` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `api_name` varchar(64) NOT NULL DEFAULT '' COMMENT 'api名',
  `api_url` varchar(256) NOT NULL DEFAULT '' COMMENT 'api地址',
  `api_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'api类型(0 info 1 action)',
  `request_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '请求类型 (1 post 2 get 3 resttemplate post)',
  `body_param` text COMMENT '入参列表',
  `base_param` varchar(128) NOT NULL DEFAULT '' COMMENT '入参列表',
  `header_param` varchar(256) NOT NULL DEFAULT '' COMMENT 'http请求header json',
  `body_param_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'http请求参数类型 (0 json 1 formdata 3 get)',
  `sign` int(11) NOT NULL DEFAULT '0' COMMENT '签名类型',
  `result_reg` varchar(256) NOT NULL DEFAULT '' COMMENT '结果解析规则 jsonpath',
  `timeout` int(11) NOT NULL DEFAULT '1000' COMMENT '超时时间',
  `retrycount` int(11) NOT NULL DEFAULT '1' COMMENT '重试次数',
  `cache_time` int(11) NOT NULL DEFAULT '0' COMMENT '缓存时间',
  `country` varchar(64) NOT NULL DEFAULT '' COMMENT '国家码',
  `lang` varchar(64) NOT NULL DEFAULT '' COMMENT '语言',
  `extra` varchar(1024) NOT NULL DEFAULT '' COMMENT 'api额外描述信息',
  `create_user` varchar(128) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 本地',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 utc',
  `update_user` varchar(128) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间 本地',
  `update_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间 utc',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc时间差\\nutc时间差',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 (0不可用 1可用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1120 DEFAULT CHARSET=utf8 COMMENT='api表';

-- ----------------------------
--  Table structure for `o_apply`
-- ----------------------------
DROP TABLE IF EXISTS `o_apply`;
CREATE TABLE `o_apply` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `process_instance_id` varchar(255) NOT NULL DEFAULT '' COMMENT '流程实例id',
  `apply_title` varchar(255) NOT NULL DEFAULT '' COMMENT '申请标题',
  `apply_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '申请类型。0:上线，1:下线',
  `apply_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审批状态。0:审批中，1:审批通过，2:审批不通过，3:审批取消',
  `business_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联的业务id，比如流程申请表id，表单申请表id',
  `business_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '关联的业务类型。0:场景 1：流程 2：表单',
  `version` varchar(50) NOT NULL DEFAULT '' COMMENT '业务的版本',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '申请描述',
  `opinion` varchar(255) NOT NULL DEFAULT '' COMMENT '审批意见',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据的状态 1正常 2逻辑删除',
  `apply_data` mediumtext COMMENT '申请的数据',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人mis id',
  `create_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的local时间',
  `create_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的utc时间',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人',
  `update_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人mis id',
  `update_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的local时间',
  `update_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的utc时间',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(255) NOT NULL DEFAULT '' COMMENT '语言',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc偏移',
  PRIMARY KEY (`id`),
  KEY `idx_business_id` (`business_id`),
  KEY `idx_process_instance_id` (`process_instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8 COMMENT='申请表';

-- ----------------------------
--  Table structure for `o_auth`
-- ----------------------------
DROP TABLE IF EXISTS `o_auth`;
CREATE TABLE `o_auth` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `unique_id` varchar(100) NOT NULL DEFAULT '' COMMENT '权限唯一编号',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '权限归属,1:场景;2:流程;3:表单;4:审批流',
  `user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名,对应name',
  `user_mis` varchar(100) NOT NULL DEFAULT '' COMMENT '用户mis, 对应empid',
  `user_email` varchar(100) NOT NULL DEFAULT '' COMMENT '用户邮箱,对应ldap',
  `user_auth_grade` tinyint(4) NOT NULL DEFAULT '0' COMMENT '权限等级,1:管理员;2:编辑权限;3:读权限;',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1:逻辑删除',
  `country` varchar(64) NOT NULL DEFAULT '' COMMENT '国家码',
  `lang` varchar(64) NOT NULL DEFAULT '' COMMENT '语言',
  `create_by` varchar(128) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 本地',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 utc',
  `update_by` varchar(128) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间 本地',
  `update_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间 utc',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc时间差',
  PRIMARY KEY (`id`),
  KEY `idx_unique_id_user_mis_is_deleted` (`unique_id`,`user_mis`,`is_deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=513 DEFAULT CHARSET=utf8 COMMENT='权限管理表';

-- ----------------------------
--  Table structure for `o_flow`
-- ----------------------------
DROP TABLE IF EXISTS `o_flow`;
CREATE TABLE `o_flow` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `flow_name` varchar(64) NOT NULL DEFAULT '' COMMENT '擎天柱流程 name',
  `flow_key` varchar(100) NOT NULL DEFAULT '' COMMENT '流程 Key',
  `flow_module_id` varchar(100) NOT NULL DEFAULT '' COMMENT '流程模型 ID',
  `version` varchar(20) NOT NULL DEFAULT '' COMMENT '流程版本',
  `init_data_pattern` varchar(200) NOT NULL DEFAULT '' COMMENT '初始参数模式',
  `init_api_pattern` varchar(200) NOT NULL DEFAULT '' COMMENT '初始info,待删除',
  `init_info_ids` varchar(200) NOT NULL DEFAULT '' COMMENT '初始infoid',
  `init_api_ids` varchar(200) NOT NULL DEFAULT '' COMMENT '初始 apiid',
  `deploy_db_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '流程部署id',
  `trigger_config` mediumtext COMMENT '触发器，json',
  `source` varchar(50) NOT NULL DEFAULT 'fu' COMMENT '来源',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:人工;1:zn',
  `flow_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '流程状态,0:新流程;1:测试中;2:已生效',
  `flow_bpm_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '流程审批状态,0:待审核;1:上线审核中;2:上线审核通过;3:上线审核取消;4:上线审核失败;',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:正常;1:隐藏但不删除;2:软删除;',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `apollo_key` varchar(200) NOT NULL DEFAULT '' COMMENT 'apollo_key',
  `flow_kind` varchar(20) NOT NULL DEFAULT '' COMMENT '流程种类',
  `owner` varchar(128) NOT NULL DEFAULT '' COMMENT '负责人',
  `business_name` varchar(128) NOT NULL DEFAULT '' COMMENT '业务线',
  `country` varchar(20) NOT NULL DEFAULT 'zh' COMMENT '国家码',
  `lang` varchar(20) NOT NULL DEFAULT 'cn' COMMENT '语言码',
  `utc_offset` int(10) NOT NULL DEFAULT '480' COMMENT '时区',
  `create_by` varchar(100) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(100) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '当地更新时间',
  `create_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '当地创建时间',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT 'utc创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_flow_key_version` (`flow_key`,`version`),
  KEY `idx_update_time_local` (`update_time_local`),
  KEY `idx_create_time_local` (`create_time_local`),
  KEY `idx_flow_key` (`flow_key`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='流程模型表';

-- ----------------------------
--  Table structure for `o_flow_deploy`
-- ----------------------------
DROP TABLE IF EXISTS `o_flow_deploy`;
CREATE TABLE `o_flow_deploy` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `flow_module_id` varchar(100) NOT NULL DEFAULT '' COMMENT '流程模型 ID',
  `process_definition_id` varchar(200) NOT NULL DEFAULT '' COMMENT '流程定义id',
  `deploy_id` varchar(100) NOT NULL DEFAULT '' COMMENT '倚天返回部署 id',
  `deploy_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '部署流程状态,0:正常;1:删除;2:挂起;',
  `deploy_type` varchar(30) NOT NULL DEFAULT '' COMMENT '部署类型',
  `country` varchar(20) NOT NULL DEFAULT 'zh' COMMENT '国家码',
  `lang` varchar(20) NOT NULL DEFAULT 'cn' COMMENT '语言码',
  `utc_offset` int(10) NOT NULL DEFAULT '480' COMMENT '时区',
  `create_by` varchar(100) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(100) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '当地更新时间',
  `create_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '当地创建时间',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT 'utc创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_flow_module_defintion` (`flow_module_id`,`process_definition_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='流程部署表';

-- ----------------------------
--  Table structure for `o_flow_info`
-- ----------------------------
DROP TABLE IF EXISTS `o_flow_info`;
CREATE TABLE `o_flow_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增 id',
  `flow_name_show` varchar(64) NOT NULL DEFAULT '' COMMENT '流程名称展示',
  `flow_name` varchar(64) NOT NULL DEFAULT '' COMMENT '流程名称',
  `apollo_key` varchar(64) NOT NULL DEFAULT '' COMMENT '小流量控制',
  `channel` int(20) NOT NULL DEFAULT '0' COMMENT '流程所属渠道',
  `country` varchar(20) NOT NULL DEFAULT 'zh' COMMENT '国家码',
  `lang` varchar(20) NOT NULL DEFAULT 'cn' COMMENT '语言码',
  `utc_offset` int(10) NOT NULL DEFAULT '480' COMMENT '时区',
  `create_by` varchar(100) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(100) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '当地更新时间',
  `create_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '当地创建时间',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT 'utc创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_flow_name` (`flow_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='流程基本信息';

-- ----------------------------
--  Table structure for `o_flow_instance`
-- ----------------------------
DROP TABLE IF EXISTS `o_flow_instance`;
CREATE TABLE `o_flow_instance` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `flow_module_id` varchar(100) NOT NULL DEFAULT '' COMMENT '流程模型 ID',
  `process_instance_id` varchar(100) NOT NULL DEFAULT '' COMMENT '实例 ID',
  `business_key` varchar(20) NOT NULL DEFAULT '' COMMENT 'business_key',
  `privilege` varchar(100) NOT NULL DEFAULT '' COMMENT '权限集合',
  `mis` varchar(50) NOT NULL DEFAULT '' COMMENT 'misid',
  `instance_status` varchar(20) NOT NULL DEFAULT '0' COMMENT '实例状态,0:正常;1:已结束;',
  `init_data` text COMMENT '初始参数模式',
  `relation_id` varchar(20) NOT NULL DEFAULT '' COMMENT '关联的业务方的 id',
  `channel_id` int(20) NOT NULL DEFAULT '0' COMMENT '业务方渠道',
  `country` varchar(20) NOT NULL DEFAULT 'zh' COMMENT '国家码',
  `lang` varchar(20) NOT NULL DEFAULT 'cn' COMMENT '语言码',
  `utc_offset` int(10) NOT NULL DEFAULT '480' COMMENT '时区',
  `create_by` varchar(100) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(100) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time_local` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '当地更新时间',
  `create_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '当地创建时间',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT 'utc创建时间',
  `end_data` text COMMENT '实例结束参数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_process_instance_id` (`process_instance_id`),
  KEY `idx_create_time_local` (`create_time_local`)
) ENGINE=InnoDB AUTO_INCREMENT=306 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='流程实例表';

-- ----------------------------
--  Table structure for `o_form`
-- ----------------------------
DROP TABLE IF EXISTS `o_form`;
CREATE TABLE `o_form` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `key_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '表单ID，可重复。keyId相同表示是同一个表单的多个版本',
  `flow_key` varchar(50) NOT NULL DEFAULT '' COMMENT '流程模型ID',
  `project_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '项目id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '表单名字',
  `model_json` mediumtext COMMENT '表单内容，前端定义的',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型。0:完整表单;1:公共表单组件;',
  `info` mediumtext COMMENT 'json.表单绑定的info变量信息',
  `form_api` varchar(512) NOT NULL DEFAULT '[]' COMMENT 'json.表单绑定api,是否必选。执行时检验.',
  `trigger_config` mediumtext COMMENT '触发器，json',
  `form_config` mediumtext COMMENT '表单配置，json',
  `apply_status` tinyint(4) NOT NULL DEFAULT '4' COMMENT '审批状态。0:审批中，1:审批通过，2:审批不通过，3:审批取消 4:申请执行完成',
  `apply_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'apply表的ID',
  `apply_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '申请类型。0:上线，1:下线',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：生效，1：未生效 2：删除',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人mis id',
  `create_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的local时间',
  `create_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的utc时间',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人',
  `update_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人mis id',
  `update_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的local时间',
  `update_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的utc时间',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(255) NOT NULL DEFAULT '' COMMENT '语言',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc偏移',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_update_local_time` (`update_local_time`),
  KEY `idx_key_id` (`key_id`),
  KEY `idx_create_local_time` (`create_local_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1092 DEFAULT CHARSET=utf8 COMMENT='表单定义';

-- ----------------------------
--  Table structure for `o_form_apply`
-- ----------------------------
DROP TABLE IF EXISTS `o_form_apply`;
CREATE TABLE `o_form_apply` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `form_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '表单id',
  `flow_key` varchar(50) NOT NULL DEFAULT '' COMMENT '流程模型id',
  `project_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '项目id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '表单名字',
  `model_json` mediumtext COMMENT '表单内容，前端定义的',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型。0:表单;1:表单组件;2：公共表单',
  `info` mediumtext COMMENT 'json.表单绑定的info变量信息',
  `form_api` varchar(512) NOT NULL DEFAULT '[]' COMMENT 'json.表单绑定api,是否必选。执行时检验.',
  `trigger_config` mediumtext COMMENT '触发器，json',
  `form_config` mediumtext COMMENT '表单配置，json',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：生效，1：不生效 2：删除',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `apply_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '申请类型。0:上线，1:下线',
  `apply_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审批状态。0:审批中，1:审批通过，2:审批不通过，3:审批取消',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人mis id',
  `create_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的local时间',
  `create_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的utc时间',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人',
  `update_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人mis id',
  `update_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的local时间',
  `update_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的utc时间',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(255) NOT NULL DEFAULT '' COMMENT '语言',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc偏移',
  PRIMARY KEY (`id`),
  KEY `idx_flow_key` (`flow_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单申请表';

-- ----------------------------
--  Table structure for `o_form_history`
-- ----------------------------
DROP TABLE IF EXISTS `o_form_history`;
CREATE TABLE `o_form_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `flow_key` varchar(50) NOT NULL DEFAULT '' COMMENT '流程模型ID',
  `form_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '表单id',
  `project_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '项目id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '表单名字',
  `model_json` mediumtext COMMENT '表单内容，前端定义的',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型。0:完整表单;1:公共表单组件;',
  `info` mediumtext COMMENT 'json.表单绑定的info变量信息',
  `form_api` varchar(512) NOT NULL DEFAULT '' COMMENT 'json.表单绑定api,是否必选。执行时检验.',
  `trigger_config` mediumtext COMMENT '触发器，json',
  `form_config` mediumtext COMMENT '表单配置，json',
  `apply_status` tinyint(4) NOT NULL DEFAULT '4' COMMENT '审批状态。0:审批中，1:审批通过，2:审批不通过，3:审批取消 4:申请执行完成',
  `apply_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '申请类型。0:上线，1:下线',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：生效，1：未生效 2：删除',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人mis id',
  `create_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的local时间',
  `create_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的utc时间',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人',
  `update_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人mis id',
  `update_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的local时间',
  `update_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的utc时间',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(255) NOT NULL DEFAULT '' COMMENT '语言',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc偏移',
  PRIMARY KEY (`id`),
  KEY `idx_form_id` (`form_id`),
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单历史变更记录';

-- ----------------------------
--  Table structure for `o_form_instance`
-- ----------------------------
DROP TABLE IF EXISTS `o_form_instance`;
CREATE TABLE `o_form_instance` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `process_instance_id` varchar(255) NOT NULL DEFAULT '' COMMENT '流程实例id',
  `node_id` varchar(255) NOT NULL DEFAULT '' COMMENT '节点id',
  `task_id` varchar(255) NOT NULL DEFAULT '' COMMENT '任务id',
  `form_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '表单id',
  `model_json` mediumtext COMMENT '表单实例的表单内容',
  `commit_info` mediumtext COMMENT '表单实例提交的info变量信息',
  `global_info` mediumtext COMMENT '表单实例的全局info变量信息',
  `form_api` varchar(255) NOT NULL DEFAULT '' COMMENT '表单实例的表单绑定api操作list',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人mis id',
  `create_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的local时间',
  `create_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的utc时间',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(255) NOT NULL DEFAULT '' COMMENT '语言',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc偏移',
  PRIMARY KEY (`id`),
  KEY `idx_process_instance_id` (`process_instance_id`),
  KEY `idx_create_local_time` (`create_local_time`)
) ENGINE=InnoDB AUTO_INCREMENT=225 DEFAULT CHARSET=utf8 COMMENT='表单实例定义';

-- ----------------------------
--  Table structure for `o_form_tool`
-- ----------------------------
DROP TABLE IF EXISTS `o_form_tool`;
CREATE TABLE `o_form_tool` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `tool_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '标识一个工具',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '工具名字',
  `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '版本id，自增',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `model_json` mediumtext COMMENT '内容，表单工具。前端定义',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型。0:表单工具',
  `info` mediumtext COMMENT 'json.表单绑定的info变量信息',
  `base_api_id` mediumtext COMMENT 'arr json.初始化时执行,将api返回的所有info纳入globalinfo',
  `base_info_id` varchar(512) NOT NULL DEFAULT '' COMMENT 'id json arr.调用工具时需要传的info。校验是否传递完整.',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:生效; 1:不生效; 2:删除',
  `trigger_config` mediumtext COMMENT '触发器，json',
  `form_config` mediumtext COMMENT '表单配置，json',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人mis id',
  `create_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的local时间',
  `create_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的utc时间',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人',
  `update_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人mis id',
  `update_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的local时间',
  `update_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的utc时间',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(255) NOT NULL DEFAULT '' COMMENT '语言',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc偏移',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_tool_id_version` (`tool_id`,`version`),
  KEY `idx_tool_id` (`tool_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单工具定义';

-- ----------------------------
--  Table structure for `o_global_info`
-- ----------------------------
DROP TABLE IF EXISTS `o_global_info`;
CREATE TABLE `o_global_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `process_instance_id` varchar(255) NOT NULL DEFAULT '' COMMENT '流程实例id',
  `node_id` varchar(255) NOT NULL DEFAULT '' COMMENT '节点id',
  `task_id` varchar(255) NOT NULL DEFAULT '' COMMENT '任务id',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型.0:全局globalinfo,1:初始表单后的globalinfo,2:提交表单后的globalinfo',
  `info` mediumtext COMMENT 'info变量数据',
  `create_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的local时间',
  `create_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的utc时间',
  `update_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的local时间',
  `update_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的utc时间',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(255) NOT NULL DEFAULT '' COMMENT '语言',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc偏移',
  PRIMARY KEY (`id`),
  KEY `idx_process_instance_id` (`process_instance_id`),
  KEY `idx_process_task_node` (`process_instance_id`,`task_id`,`node_id`),
  KEY `idx_create_local_time` (`create_local_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1011 DEFAULT CHARSET=utf8 COMMENT='流程实例的全局info值表';

-- ----------------------------
--  Table structure for `o_info`
-- ----------------------------
DROP TABLE IF EXISTS `o_info`;
CREATE TABLE `o_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name_real` varchar(64) NOT NULL DEFAULT '' COMMENT '参数名',
  `name_show` varchar(64) NOT NULL DEFAULT '' COMMENT '展示名',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '数据类型 string(1, "string"), int(2, "integer"), long(3, "long"), double(4, "double"), short(5, "short"), boolean(6, "boolean"), date(7, "date"), list(8, "list")',
  `source` tinyint(4) NOT NULL DEFAULT '0' COMMENT '数据来源 1元数据 2 api数据 3 系统数据 4 自定义数据',
  `api_id` int(11) NOT NULL DEFAULT '0' COMMENT 'api_id',
  `jsonpath` varchar(128) NOT NULL DEFAULT '' COMMENT '数据解析jsonpath',
  `base_param` varchar(128) NOT NULL DEFAULT '' COMMENT '入参列表',
  `api_body_param` mediumtext COMMENT 'info请求api默认参数',
  `transformer` mediumtext COMMENT 'info转换处理',
  `business` varchar(128) NOT NULL DEFAULT '' COMMENT '业务线',
  `owner` varchar(128) NOT NULL DEFAULT '' COMMENT '负责人',
  `country` varchar(64) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(64) NOT NULL DEFAULT '' COMMENT '语言',
  `description` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `create_user` varchar(128) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time_local` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `create_time_utc` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间utc',
  `update_user` varchar(128) DEFAULT '' COMMENT '升级人',
  `update_time_local` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `update_time_utc` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间utc',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc时间差',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `idx_name_real` (`name_real`),
  KEY `idx_name_show` (`name_show`),
  KEY `idx_country` (`country`),
  KEY `idx_api_id` (`api_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4356 DEFAULT CHARSET=utf8 COMMENT='info表';

-- ----------------------------
--  Table structure for `o_instance_tasks`
-- ----------------------------
DROP TABLE IF EXISTS `o_instance_tasks`;
CREATE TABLE `o_instance_tasks` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增 id',
  `process_instance_id` varchar(200) NOT NULL DEFAULT '' COMMENT '流程实例id',
  `process_task_id` varchar(100) NOT NULL DEFAULT '' COMMENT '任务 id',
  `node_id` varchar(500) NOT NULL DEFAULT '' COMMENT '节点 id',
  `is_recall` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1:回滚',
  `data` text COMMENT '参数',
  `result` text COMMENT '返回',
  `mis` varchar(200) NOT NULL DEFAULT '' COMMENT '执行人',
  `country` varchar(20) NOT NULL DEFAULT 'zh' COMMENT '国家码',
  `lang` varchar(20) NOT NULL DEFAULT 'cn' COMMENT '语言码',
  `utc_offset` int(10) NOT NULL DEFAULT '480' COMMENT '时区',
  `create_by` varchar(100) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(100) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time_local` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '当地更新时间',
  `create_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '当地创建时间',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT 'utc创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_process_task_id` (`process_task_id`),
  KEY `idx_create_time_local` (`create_time_local`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8 COMMENT='流程任务表';

-- ----------------------------
--  Table structure for `o_project`
-- ----------------------------
DROP TABLE IF EXISTS `o_project`;
CREATE TABLE `o_project` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '项目名字',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '项目描述',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人mis id',
  `create_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的local时间',
  `create_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的utc时间',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人',
  `update_user_misid` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人mis id',
  `update_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的local时间',
  `update_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的utc时间',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(255) NOT NULL DEFAULT '' COMMENT '语言',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc偏移',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10045 DEFAULT CHARSET=utf8 COMMENT='项目表';

-- ----------------------------
--  Table structure for `o_scene`
-- ----------------------------
DROP TABLE IF EXISTS `o_scene`;
CREATE TABLE `o_scene` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `scene_id` varchar(100) NOT NULL DEFAULT '' COMMENT '场景唯一编号',
  `scene_name` varchar(100) NOT NULL DEFAULT '' COMMENT '场景名称',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '场景描述',
  `scene_bind_id` varchar(100) NOT NULL DEFAULT '[]' COMMENT '场景与流程绑定条目',
  `bind_rules` varchar(200) NOT NULL DEFAULT '' COMMENT '绑定规则',
  `scene_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态,0:未生效,1:已生效',
  `scene_bpm_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审批状态,0:待审核;1:上线审核中;2:上线审核通过;3:下线审核中;4:下线审核通过;5:审核失败',
  `extra_info` varchar(500) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `version` varchar(200) NOT NULL DEFAULT '' COMMENT '场景版本',
  `country` varchar(64) NOT NULL DEFAULT '' COMMENT '国家码',
  `lang` varchar(64) NOT NULL DEFAULT '' COMMENT '语言',
  `create_by` varchar(128) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 本地',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 utc',
  `update_by` varchar(128) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间 本地',
  `update_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间 utc',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc时间差',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_scene_id_version` (`scene_id`,`version`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='场景表';

-- ----------------------------
--  Table structure for `o_scene_bind_flow`
-- ----------------------------
DROP TABLE IF EXISTS `o_scene_bind_flow`;
CREATE TABLE `o_scene_bind_flow` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `flow_db_id` bigint(4) NOT NULL DEFAULT '0' COMMENT '绑定的流程id',
  `is_necessary` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否必须走完',
  `is_default_show` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否默认显示',
  `is_ihap` tinyint(4) NOT NULL DEFAULT '0' COMMENT ' 是否是ihap',
  `tags` varchar(100) NOT NULL DEFAULT '' COMMENT '返回tag',
  `country` varchar(64) NOT NULL DEFAULT '' COMMENT '国家码',
  `lang` varchar(64) NOT NULL DEFAULT '' COMMENT '语言',
  `create_by` varchar(128) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 本地',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 utc',
  `update_by` varchar(128) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间 本地',
  `update_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间 utc',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc时间差',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='场景绑定流程表';

-- ----------------------------
--  Table structure for `o_scene_bind_rule`
-- ----------------------------
DROP TABLE IF EXISTS `o_scene_bind_rule`;
CREATE TABLE `o_scene_bind_rule` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `scene_id` varchar(128) NOT NULL DEFAULT '' COMMENT '场景编号',
  `rule_item_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '绑定规则id集合',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '绑定逻辑:与或非',
  `value` varchar(128) NOT NULL DEFAULT '' COMMENT '规则实际值',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：生效，1：不生效 2：删除',
  `version` varchar(100) NOT NULL DEFAULT '' COMMENT '规则版本号',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `country` varchar(64) NOT NULL DEFAULT '' COMMENT '国家码',
  `lang` varchar(64) NOT NULL DEFAULT '' COMMENT '语言',
  `create_by` varchar(128) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 本地',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 utc',
  `update_by` varchar(128) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time_local` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间 本地',
  `update_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间 utc',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc时间差',
  PRIMARY KEY (`id`),
  KEY `idx_scene_id` (`scene_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='场景绑定规则表';

-- ----------------------------
--  Table structure for `o_team`
-- ----------------------------
DROP TABLE IF EXISTS `o_team`;
CREATE TABLE `o_team` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `team_name` varchar(64) NOT NULL DEFAULT '' COMMENT '团队中文名称',
  `team_owner_emails` varchar(128) NOT NULL DEFAULT '' COMMENT '团队负责人邮箱前缀，逗号分离',
  `create_user` varchar(128) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 utc',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 (0正常 1软删除/禁用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='团队空间表';

-- ----------------------------
--  Table structure for `o_team_member`
-- ----------------------------
DROP TABLE IF EXISTS `o_team_member`;
CREATE TABLE `o_team_member` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `team_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '团队ID',
  `member_name` varchar(64) NOT NULL DEFAULT '' COMMENT '单个团队成员邮箱名称',
  `member_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 读 1 写 2 管理员',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 (0正常 1软删除/禁用)',
  `create_time_utc` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间 utc',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='团队成员表';

-- ----------------------------
--  Table structure for `o_template`
-- ----------------------------
DROP TABLE IF EXISTS `o_template`;
CREATE TABLE `o_template` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '模板名称',
  `business` varchar(255) NOT NULL DEFAULT '[]' COMMENT '模板所属产品线,从1开始定义，0是无意义的,是个数组字符串',
  `model_json` mediumtext COMMENT '模板内容，前端定义的',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型。0:默认模板;1:预留位置',
  `info` mediumtext COMMENT 'json.模板绑定的info变量信息',
  `form_api` varchar(512) NOT NULL DEFAULT '[]' COMMENT 'json. 模板绑定api,是否必选。执行时检验.',
  `trigger_config` mediumtext COMMENT '触发器，json',
  `form_config` mediumtext COMMENT '模板配置，json',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未启用; 1:已启用; 2:已删除',
  `create_user` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的local时间',
  `create_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建的utc时间',
  `update_user` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人',
  `update_local_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的local时间',
  `update_utc_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新的utc时间',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国家',
  `lang` varchar(255) NOT NULL DEFAULT '' COMMENT '语言',
  `utc_offset` int(11) NOT NULL DEFAULT '0' COMMENT 'utc偏移',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='模板定义';

SET FOREIGN_KEY_CHECKS = 1;



