package com.amaoai.msocksrv

import com.amaoai.mcmun.MCMUNProtocol
import devtools.framework.io.ByteBuffer
import devtools.framework.io.IOUtils
import devtools.framework.io.ObjectSerializationUtils
import io.netty.channel.ChannelHandlerContext
import stdlibkt.SEEK_CUR

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
    override fun channelRead(channelHandlerContext: ChannelHandlerContext, buffer: Any) {
        // 将数据写入到字节缓冲区中
        val readbuf = ByteBuffer.wrap(remByteBuffer.clear())
        readbuf.write(buffer as ByteArray)
        readbuf.flip()
        readpack(readbuf)
    }

    /**
     * 解析数据包
     */
    fun readpack(readbuf: ByteBuffer) {
        // 解析数据包
        val magicNumber = readbuf.readInt()
        val protocolVersion = readbuf.readInt()

        // 如果魔数和版本都相同那么代表这是一个正确的数据包起始
        if (magicNumber == MCMUNProtocol.MAGIC_NUMBER &&
            protocolVersion == MCMUNProtocol.VERSION) {
            // 获取数据包长度
            val len = readbuf.readInt()

            // 如果长度小于未读大小那么表示这是一个半包数据
            if (readbuf.remsize() < len) {
                // 因为前面调用了三次readInt，跳过了12个字节，所以需要读写指针往后移动八位
                readbuf.seek(-(IOUtils.SIZE_OF_INT * 3), SEEK_CUR)
                remByteBuffer.write(readbuf.remBytes)
                return
            }

            // 读取到一个完整的数据包
            val bytebuf = IOUtils.getByteArray(len)
            readbuf.read(bytebuf)
            val mcmunProtocol =
                ObjectSerializationUtils.unserializationQuietly<MCMUNProtocol>(bytebuf)

            println("（${(readCompleteCount++)}）读取到数据包：${mcmunProtocol}，剩余字节：${readbuf.remsize()}")

            if (!readbuf.eof())
                readpack(readbuf)
        }
    }

}