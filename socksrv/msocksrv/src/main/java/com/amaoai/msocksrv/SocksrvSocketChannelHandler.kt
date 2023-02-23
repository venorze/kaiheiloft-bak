package com.amaoai.msocksrv

import com.amaoai.mcmun.MCMUNEncoder
import com.amaoai.mcmun.MCMUNProtocol
import devtools.framework.io.ByteBuffer
import devtools.framework.io.IOUtils
import devtools.framework.io.ObjectSerializationUtils
import devtools.framework.time.DateUtils
import io.netty.channel.ChannelHandlerContext
import stdlibkt.SEEK_END

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
        var readCompleteDataCount = 0L
        val remainByteBuffer: ByteBuffer = ByteBuffer.alloc()
        var startTime = 0L
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun exceptionCaught(channelHandlerContext: ChannelHandlerContext, throwable: Throwable) {
        throwable.printStackTrace()
    }

    /**
     * 读取客户端发送消息
     */
    override fun channelRead(channelHandlerContext: ChannelHandlerContext, buffer: Any) {
        if (startTime == 0L) {
            startTime = System.currentTimeMillis()
            println("当前时间：${DateUtils.vfmt(startTime)}")
        }

        // 将数据写入到字节缓冲区中
        val readbuf = ByteBuffer.wrap(remainByteBuffer.clear())
        readbuf.write(buffer as ByteArray)
        readbuf.flip()
        procpack(readbuf)
    }

    /**
     * 数据包解析函数
     */
    private fun procpack(buf: ByteBuffer) {
        // 魔数和版本匹配表示是一个合法的数据包
        if (buf.readInt() == MCMUNProtocol.MAGIC_NUMBER &&
            buf.readInt() == MCMUNProtocol.VERSION) {
            // 获取数据包长度
            val len = buf.readInt()

            // 如果数据包长度大于可读字节数，那么就表示这是一个半包数据
            if (len > buf.readable()) {
                remainByteBuffer.write(buf.bytes)
                return
            }

            //
            // 解析完整的数据包
            //
            buf.flip()
            buf.seek(MCMUNEncoder.MARK_FIELD_SIZE_COUNT)
            val objarr = IOUtils.getByteArray(len)
            buf.read(objarr)
            val protocol =
                ObjectSerializationUtils.unserializationQuietly<MCMUNProtocol>(objarr)
            readCompleteDataCount++
            println("解析的第${readCompleteDataCount}个数据包，内容：$protocol。bytebuf剩余字节：${buf.size()}")

            //
            // 判断buf中还有没有剩余的字节，如果有那就表示还有数据未读完。
            //
            if (!buf.eof())
                procpack(ByteBuffer.wrap(buf.remainBytes))
        }
    }

}