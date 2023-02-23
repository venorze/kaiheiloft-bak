package com.amaoai.mcmun;

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

/* Creates on 2023/2/23. */

import devtools.framework.generators.VersatileGenerator;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息服务器通信协议，命名为（MCMUN协议）
 *
 * @author Amaoai
 */
@Data
public class MCMUNProtocol implements Serializable {

    /**
     * 协议魔数整数
     */
    public static int MAGIC_NUMBER = 0xFFD488;

    /**
     * 协议版本号
     */
    public static int VERSION =
            VersatileGenerator.makeVersion(1, 6, 0);

    /**
     * 消息类型
     */
    public interface MessageType {
        /**
         * 纯文本消息，不包含任何附件。消息前缀
         */
        byte TEXT = 'T';

        /**
         * 包含附件的文本消息
         */
        byte ATTACHMENT_TEXT = 'A';

        /**
         * 图片消息
         */
        byte IMAGE = 'I';

        /**
         * 语音消息
         */
        byte VOICE = 'V';

        /**
         * 视频消息
         */
        byte VIDEO = 'D';

        /**
         * http链接或分享消息
         */
        byte SHARED = 'L';
    }

    /**
     * 消息ID
     */
    private String mid;

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
     * @see MessageType
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
     * 消息状态（是否发送成功，以 Y/N 表示）
     */
    private Byte success = 'N';

    /**
     * 已读成员
     */
    private List<String> readmemb;

    /**
     * 消息贴纸
     */
    private Map<String, Integer> stickers;

    /**
     * 发送时间
     */
    private Date time;

}
