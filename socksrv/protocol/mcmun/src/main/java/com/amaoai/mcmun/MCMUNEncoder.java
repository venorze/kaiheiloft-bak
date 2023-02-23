package com.amaoai.mcmun;

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

import devtools.framework.io.ByteBuffer;
import devtools.framework.io.ObjectSerializationUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 协议编码器
 *
 * @author Amaoai
 */
public class MCMUNEncoder extends MessageToByteEncoder<MCMUNProtocol> {

    /**
     * 标记字段的总长度
     */
    public static int MARK_FIELD_SIZE_COUNT =  ByteBuffer.SIZE_OF_INT * 3;

    /**
     * 编码器会将协议对象转换成字节流。内存布局下图：
     *
     * 0 - 4：魔数，用于识别协议是否是 MCMUN 协议
     * 4 - 8：版本号，MajorVersion
     * 8 - 12：数据包总长度
     * 其他：数据包内容
     * 结尾倒数
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MCMUNProtocol protocol, ByteBuf byteBuf)
            throws Exception {
        var devByteBuf = ByteBuffer.alloc();
        // 解析数据包
        byte[] mcmunBytes = ObjectSerializationUtils.serializationQuietly(protocol);
        // 魔数
        devByteBuf.write(MCMUNProtocol.MAGIC_NUMBER);
        // 协议版本号
        devByteBuf.write(MCMUNProtocol.VERSION);
        // 数据包大小
        devByteBuf.write(mcmunBytes.length);
        // 数据包内容
        devByteBuf.write(mcmunBytes);
        // 写入到SocketChannel
        byteBuf.writeBytes(devByteBuf.getBytes());
    }

}
