package com.amaoai.msrv.protocol.umcp;

/* ************************************************************************
 *
 * Copyright (C) 2020 Vincent Luo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/* Creates on 2023/2/25. */

import java.io.Serializable;

/**
 * @author Vincent Luo
 */
public enum UMCPCommand implements Serializable {

    /**
     * 用户登录，校验
     */
    SIGN_IN_SEND,

    /**
     * 收到消息后回复客户端
     */
    SIGN_IN_ACK,

    /**
     * 发送消息
     */
    SEND,

    /**
     * 收到消息后回复客户端
     */
    ACK,

    /**
     * 心跳包
     */
    HEARTBEAT,

    /**
     * 客户端申请断开连接
     */
    DISCONNECT,

}
