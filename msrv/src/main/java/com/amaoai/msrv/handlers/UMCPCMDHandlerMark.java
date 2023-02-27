package com.amaoai.msrv.handlers;

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

/* Creates on 2023/2/27. */

import com.amaoai.msrv.protocol.umcp.UMCPCMD;

import java.lang.annotation.*;

/**
 * 被注解的类表示是 UMCP 协议命令的处理器，当前注解只针对 com.amaoai.msrv.handlers.umcphandlers 包
 * 下的类生效。并且类必须实现 UMCPCMDHandlerAdapter 接口。
 *
 * @author Vincent Luo
 * @see UMCProtocolSocketHandler#loadUMCPCommandHandlers
 * @see UMCPCMDHandlerAdapter
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UMCPCMDHandlerMark {

    /**
     * 指定处理器处理哪条指令
     */
    UMCPCMD cmd();

    /**
     * 自动回复收到数据包命令
     */
    UMCPCMD rep() default UMCPCMD.NO_ACK_REQUIRED;

}
