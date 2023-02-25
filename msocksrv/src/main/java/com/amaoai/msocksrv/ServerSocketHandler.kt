package com.amaoai.msocksrv

import com.amaoai.msocksrv.iface.AbstractChannelInboundHandler
import com.amaoai.msocksrv.protocol.MCMUNDecoder
import com.amaoai.msocksrv.protocol.MCMUNProtocol
import devtools.framework.io.ByteBuffer
import io.netty.channel.ChannelHandlerContext
import stdlibkt.fflush
import stdlibkt.fopen
import stdlibkt.fputs

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
        var readCompleteCount = 0
        val remByteBuffer: ByteBuffer = ByteBuffer.alloc()
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun exceptionCaught(channelHandlerContext: ChannelHandlerContext, throwable: Throwable) {
        throwable.printStackTrace()
    }

    /**
     * 读取客户端发送消息
     */
    override fun channelRead(channelHandlerContext: ChannelHandlerContext, obj: Any) {
        // 记录所有发送的的消息
        println("（${(readCompleteCount++)}）读取到数据包：$obj")
        // 通知客户端服务器已收到消息
        notifyClient(channelHandlerContext, obj as MCMUNProtocol)
    }

    private fun notifyClient(channelHandlerContext: ChannelHandlerContext, obj: MCMUNProtocol) {
        obj.success = MCMUNProtocol.MESSAGE_STATUS_SUCCESS
        channelHandlerContext.channel().writeAndFlush(obj)
    }

}