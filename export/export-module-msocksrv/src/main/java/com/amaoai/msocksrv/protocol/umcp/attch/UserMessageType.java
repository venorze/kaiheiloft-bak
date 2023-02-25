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

/**
 * 消息类型
 *
 * @author Vincent Luo
 */
public interface UserMessageType {

    /**
     * 纯文本消息，不包含任何附件。消息前缀
     */
    byte TEXT = (byte) 'T';

    /**
     * 包含附件的文本消息
     */
    byte ATTACHMENT_TEXT = (byte) 'A';

    /**
     * 图片消息
     */
    byte IMAGE = (byte) 'I';

    /**
     * 语音消息
     */
    byte VOICE = (byte) 'V';

    /**
     * 视频消息
     */
    byte VIDEO = (byte) 'D';

    /**
     * http链接或分享消息
     */
    byte SHARED = (byte) 'L';

}
