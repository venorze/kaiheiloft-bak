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

/* Creates on 2023/2/23. */

import com.amaoai.framework.generators.VersatileGenerator;
import com.amaoai.framework.time.DateUtils;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * UMCProtocol 用户消息指令协议，消息传输协议
 *
 * @author Amaoai
 */
@ToString
public class UMCProtocol implements Serializable {

    /**
     * 命令类型
     */
    @NotNull
    private UMCPCMD cmd;

    /**
     * 当前数据包ID
     */
    @Getter
    private final String pid =
            VersatileGenerator.uuid();

    /**
     * 发送时间
     */
    @Getter
    private Long timestamp =
            DateUtils.currentTimestamp();

    /**
     * 标志位
     */
    @Getter
    private final int flags;

    /**
     * 携带附件
     */
    private Object attach;

    /* 默认标志位 */
    public static final int NO_FLAGS = 0;

    /* 断开连接 */
    public static final UMCProtocol DISCONNECT = new UMCProtocol(NO_FLAGS, null, UMCPCMD.DISCONNECT);
    /* 心跳包 */
    public static final UMCProtocol HEARTBEAT = new UMCProtocol(NO_FLAGS, null, UMCPCMD.HEARTBEAT);

    /**
     * 空构造函数，默认指令是SEND
     */
    public UMCProtocol() {
        this(null);
    }

    /**
     * 带附件的造函数，默认指令是SEND
     */
    public UMCProtocol(Object attach) {
        this(attach, UMCPCMD.SEND);
    }

    /**
     * 带附件和指令的构造函数
     */
    public UMCProtocol(Object attach, @NotNull UMCPCMD cmd) {
        this(NO_FLAGS, attach, cmd);
    }

    /**
     * 全部参数构造函数
     */
    public UMCProtocol(int flags, Object attach, @NotNull UMCPCMD cmd) {
        this.flags = flags;
        this.attach = attach;
        this.cmd = cmd;
    }

    /**
     * @return 将当前数据包设置成回复数据包
     */
    public UMCProtocol ack() {
        // 清空不必要的数据，减少带宽的占用
        timestamp = null;
        attach = null;
        cmd = UMCPCMD.ACK;
        // 返回当前对象
        return this;
    }

    @NotNull
    public UMCPCMD cmd() {
        return cmd;
    }

    /**
     * 获取附件，并隐式转换成对应的类
     */
    @SuppressWarnings("unchecked")
    public <T> T attach() {
        return (T) attach;
    }

}
