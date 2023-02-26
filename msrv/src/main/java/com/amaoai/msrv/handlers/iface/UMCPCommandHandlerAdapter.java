package com.amaoai.msrv.handlers.iface;

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

import com.amaoai.msrv.handlers.contxt.SocketHandlerContext;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;

/**
 * UMCP协议命令处理器
 *
 * @author Vincent Luo
 */
public abstract class UMCPCommandHandlerAdapter {

    /**
     * 处理器是不是第一次进入活动状态
     */
    private boolean actived;

    /**
     * 当第一次进入处理器时
     */
    public void active(SocketHandlerContext socketHandlerContext) {
        // do nothing...
    }

    /**
     * 判断是不是第一次调用active()
     */
    public boolean isActived() {
        return actived;
    }

    /**
     * 状态变换为true
     */
    public void actived() {
        actived = true;
    }

    /**
     * 子类实现处理函数
     */
    public abstract void handler(UMCProtocol umcp, SocketHandlerContext socketHandlerContext);

}
