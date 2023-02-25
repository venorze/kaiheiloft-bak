package com.amaoai.msocksrv

import com.amaoai.msocksrv.iface.AbstractChannelInboundHandler
import com.amaoai.msocksrv.protocol.umcp.UMCP
import com.amaoai.msocksrv.protocol.umcp.UMCPCommand
import devtools.framework.Assert
import io.netty.channel.ChannelHandlerContext

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

/* Creates on 2023/2/22. */

/**
 * @author Amaoai
 */
class ServerSocketHandler : AbstractChannelInboundHandler() {

    companion object {
        var readCompleteCount = 1
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun exceptionCaught(channelHandlerContext: ChannelHandlerContext, throwable: Throwable) {
        throwable.printStackTrace()
    }

    /**
     * 读取客户端发送消息
     */
    @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
    override fun channelRead(channelHandlerContext: ChannelHandlerContext, any: Any) {
        // 记录所有发送的的消息
        if (any is UMCP) {
            when (any.command) {
                //
                // 收到来自客户端的消息
                //
                UMCPCommand.SEND -> {
                    // 接收到来自客户端的消息
                    println("(${(readCompleteCount++)}) 接收到来自客户端的消息：${any}")
                    // 通知客户端服务器已收到消息
                    notifyClient(channelHandlerContext, any)
                }

                //
                // 只有回复客户端的时候客户端比较这个Command
                //
                UMCPCommand.ACK -> Assert.doNothing()
            }
        }
    }

    /**
     * 通知客户端服务器已收到消息
     */
    private fun notifyClient(channelHandlerContext: ChannelHandlerContext, umcp: UMCP) {
        umcp.command = UMCPCommand.ACK
        umcp.attach = null
        channelHandlerContext.channel().writeAndFlush(umcp)
    }

}