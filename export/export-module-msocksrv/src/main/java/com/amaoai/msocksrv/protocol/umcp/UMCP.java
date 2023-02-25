package com.amaoai.msocksrv.protocol.umcp;

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
import devtools.framework.time.DateUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * UMCP 用户消息指令协议，消息传输协议
 *
 * @author Amaoai
 */
@Data
public class UMCP implements Serializable {

    /**
     * 命令类型
     */
    private UMCPCommand command;

    /**
     * 当前数据包ID
     */
    private String pid = VersatileGenerator.uuid();

    /**
     * 发送时间
     */
    private final Long timestamp = DateUtils.currentTimestamp();

    /**
     * 携带附件
     */
    private Object attach;

    /**
     * 空构造函数，默认指令是SEND
     */
    public UMCP() {
        this(null, UMCPCommand.SEND);
    }

    /**
     * 带附件的造函数，默认指令是SEND
     */
    public UMCP(Object attach) {
        this(attach, UMCPCommand.SEND);
    }

    /**
     * 带附件和指令的构造函数
     */
    public UMCP(Object attach, UMCPCommand command) {
        this.attach = attach;
        this.command = command;
    }

}
