@file:Suppress("NOTHING_TO_INLINE")

package com.amaoai.msocksrv

import io.netty.channel.Channel
import java.net.SocketAddress

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

/**
 * 长连接服务器全局上下文，管理客户端
 *
 * @author Amaoai
 */
object SocksrvApplicationContext {

    /**
     * 管理连接建立成功的客户端
     */
    private val clientChannels =
        HashMap<SocketAddress, Channel>(64);

    /**
     * 添加连接建立成功的客户端
     */
    @JvmStatic
    fun addClientChannel(addr: SocketAddress, ch: Channel) {
        Thread {
            synchronized(clientChannels) {
                clientChannels[addr] = ch
            }
        }
    }

    /**
     * 获取连接建立成功的客户端
     */
    @JvmStatic
    fun getClientChannel(addr: SocketAddress) = clientChannels[addr]

    /**
     * 获取当前有多少个客户端连接
     */
    fun clientCount() = clientChannels.size

}