-- ---------------------------------
-- 用户信息表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_user_info`;
CREATE TABLE `khl_user_info`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键ID',
    `create_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `username`     varchar(16)  NOT NULL COMMENT '用户名',
    `password`     varchar(32)  NOT NULL COMMENT '用户密码',
    `nickname`     varchar(16)  NOT NULL COMMENT '用户昵称',
    `avatar`       varchar(160) NOT NULL DEFAULT 'N' COMMENT '用户头像',
    `birthday`     timestamp    NOT NULL COMMENT '生日',
    `bio`          varchar(180) COMMENT '用户个人经历、介绍',
    `gender`       varchar(1)   NOT NULL COMMENT '性别, M 男，W 女',
    `phone_number` int                   DEFAULT NULL COMMENT '手机号',
    `email`        varchar(32)           DEFAULT NULL COMMENT '电子邮箱',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 实名认证表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_realname`;
CREATE TABLE `khl_realname`
(
    `id`          varchar(32) NOT NULL COMMENT '主键ID',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `user_id`     varchar(32) NOT NULL COMMENT '关联用户ID',
    `realname`    varchar(16) NOT NULL COMMENT '真实姓名',
    `id_number`   varchar(18) NOT NULL COMMENT '身份证号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 登录日志表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_logon_logs`;
CREATE TABLE `khl_logon_logs`
(
    `id`          varchar(32) NOT NULL COMMENT '主键ID',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `user_id`     varchar(32) NOT NULL COMMENT '关联用户ID',
    `address`     varchar(32) NOT NULL COMMENT '登录地点',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 用户好友表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_user_friend`;
CREATE TABLE `khl_user_friend`
(
    `id`          varchar(32)  NOT NULL COMMENT '主键ID',
    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `user_id`     varchar(32) not null COMMENT '用户ID',
    `friend_id`   varchar(32) not null COMMENT '好友ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;


-- ---------------------------------
-- 俱乐部表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_group`;
CREATE TABLE `khl_group`
(
    `id`          varchar(32)  NOT NULL COMMENT '主键ID',
    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`        varchar(20)  NOT NULL COMMENT '俱乐部名称',
    `avatar`      varchar(160) NOT NULL COMMENT '俱乐部头像',
    `introduce`   varchar(180) COMMENT '俱乐部介绍',
    `tags`        varchar(24) COMMENT '俱乐部标签，用空格分割，最多支持5个',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 俱乐部频道表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_group_channel`;
CREATE TABLE `khl_group_channel`
(
    `id`          varchar(32) NOT NULL COMMENT '主键ID',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`        varchar(20) NOT NULL COMMENT '俱乐部频道名称',
    `group_id`     varchar(32) NOT NULL COMMENT '俱乐部ID',
    `type`        char(1) not null comment '频道类型，T文字频道，V语音频道',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 俱乐部公告表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_group_announcement`;
CREATE TABLE `khl_group_announcement`
(
    `id`          varchar(32) NOT NULL COMMENT '主键ID',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `group_id`     varchar(32) NOT NULL COMMENT '俱乐部ID',
    `content`     varchar(562) COMMENT '公告内容',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 俱乐部成员表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_group_member`;
CREATE TABLE `khl_group_member`
(
    `id`          varchar(32) NOT NULL COMMENT '主键ID',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `group_id`     varchar(32) NOT NULL COMMENT '俱乐部ID',
    `user_id`     varchar(32) NOT NULL COMMENT '用户ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 俱乐部管理员表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_group_admin`;
CREATE TABLE `khl_group_admin`
(
    `id`          varchar(32) NOT NULL COMMENT '主键ID',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `group_id`     varchar(32) NOT NULL COMMENT '俱乐部ID',
    `user_id`     varchar(32) NOT NULL COMMENT '用户ID',
    `superadmin`  char(1)     NOT NULL DEFAULT 'N' COMMENT '是否超级管理员，N否 Y是，默认：N',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 俱乐部成员申请加入表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_group_agree_status`;
CREATE TABLE `khl_group_agree_status`
(
    `id`             varchar(32) NOT NULL COMMENT '主键ID',
    `create_time`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `group_id`        varchar(32) NOT NULL COMMENT '俱乐部ID',
    `user_id`        varchar(32) NOT NULL COMMENT '用户ID',
    `request_remark` varchar(50) COMMENT '申请备注',
    `agree_status`   char(1)     NOT NULL DEFAULT 'T' COMMENT 'T待处理，Y同意，N拒绝',
    `refusal_reason` varchar(50) COMMENT '拒绝原因',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 俱乐部成员邀请新成员加入表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_group_invite`;
CREATE TABLE `khl_group_invite`
(
    `id`           varchar(32) NOT NULL COMMENT '主键ID',
    `create_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `group_id`      varchar(32) NOT NULL COMMENT '俱乐部ID',
    `user_id`      varchar(32) NOT NULL COMMENT '用户ID',
    `inviter_id`   varchar(32) NOT NULL COMMENT '邀请人ID',
    `agree_status` char(1)     NOT NULL DEFAULT 'T' COMMENT 'T待处理，Y同意，N拒绝',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 表情贴纸表
-- ---------------------------------
DROP TABLE IF EXISTS `khl_emoji_map`;
CREATE TABLE `khl_emoji_map`
(
    `id`           varchar(32) NOT NULL COMMENT '主键ID',
    `create_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`         varchar(16) NOT NULL comment '表情名称',
    `url`          varchar(32) NOT NULL comment '表情URL',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;