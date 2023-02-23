package com.amaoai.msocksrv

import com.amaoai.mcmun.MCMUNProtocol
import devtools.framework.io.ByteBuffer
import devtools.framework.io.ObjectSerializationUtils
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
class SocksrvSocketChannelHandler : AbstractChannelInboundHandler() {

    companion object {
        var receptionCount = 0
    }

    /**
     * 读取客户端发送消息
     */
    override fun channelRead(channelHandlerContext: ChannelHandlerContext, buffer: Any) {
        val byteBuffer = ByteBuffer.wrap(buffer as ByteArray)
        val eq = byteBuffer.readInt()
        val obj = ObjectSerializationUtils.unserializationQuietly<MCMUNProtocol>(byteBuffer.remainBytes)
        // println("magic number match: ${(byteBuffer.readInt() == MCMUNProtocol.FUCK_MAGIC_NUM)}")
        // println("mcmun protocol object: ${ObjectSerializationUtils.unserializationQuietly<MCMUNProtocol>(byteBuffer.remainBytes)}")
        if (eq == MCMUNProtocol.FUCK_MAGIC_NUM)
            obj is Any

        receptionCount++

        if (obj.type == MCMUNProtocol.MessageType.IMAGE)
            println("总共接收到了: ${receptionCount}条消息")
    }

}