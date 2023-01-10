-- ---------------------------------
-- 用户信息表
-- ---------------------------------
CREATE TABLE `userinfo`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键ID',
    `create_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `username`     varchar(16)  NOT NULL COMMENT '用户名',
    `password`     varchar(32)  NOT NULL COMMENT '用户密码',
    `nickname`     varchar(16)  NOT NULL COMMENT '用户昵称',
    `avatar`       varchar(255) NOT NULL DEFAULT 'N' COMMENT '用户头像',
    `birthday`     timestamp    NOT NULL COMMENT '生日',
    `bio`          varchar(180) COMMENT '用户个人经历、介绍',
    `phone_number` int                   DEFAULT NULL COMMENT '手机号',
    `email`        varchar(32)           DEFAULT NULL COMMENT '电子邮箱',
    `realname`     char(1)      NOT NULL DEFAULT 'N' COMMENT '是否实名认证，N否 Y是，默认：N',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 实名认证表
-- ---------------------------------
CREATE TABLE `realname`
(
    `id`          varchar(32) NOT NULL COMMENT '主键ID',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `user_id`     int         NOT NULL COMMENT '关联用户ID',
    `realname`    varchar(16) NOT NULL COMMENT '真实姓名',
    `id_number`   varchar(18) NOT NULL COMMENT '身份证号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;

-- ---------------------------------
-- 登录日志表
-- ---------------------------------
CREATE TABLE `logon_logs`
(
    `id`          varchar(32) NOT NULL COMMENT '主键ID',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `user_id`     int         NOT NULL COMMENT '关联用户ID',
    `address`     varchar(32) NOT NULL COMMENT '登录地点',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin;