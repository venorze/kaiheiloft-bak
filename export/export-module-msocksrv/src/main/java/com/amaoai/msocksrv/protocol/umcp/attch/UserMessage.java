package com.amaoai.msocksrv.protocol.umcp.attch;

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

import devtools.framework.generators.VersatileGenerator;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用户消息
 *
 * @author Vincent Luo
 */
@Data
public class UserMessage
        implements Serializable {

    /**
     * 消息ID
     */
    private final String mid =
            VersatileGenerator.uuid();

    /**
     * 发送者
     */
    private String sender;

    /**
     * 接收者
     */
    private String receiver;

    /**
     * 消息类型
     * @see UserMessageType
     */
    private Byte type;

    /**
     * 消息主体，存放主要的消息信息。
     *
     * @see #type
     */
    private String message;

    /**
     * 消息附件
     */
    private List<String> attach;

    /**
     * 已读成员
     */
    private List<String> readmemb;

    /**
     * 消息贴纸
     */
    private Map<String, Integer> stickers;

}
