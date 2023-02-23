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

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MCMUNProtocol protocol, ByteBuf byteBuf)
            throws Exception {
        var devByteBuf = ByteBuffer.alloc();
        devByteBuf.write(MCMUNProtocol.FUCK_MAGIC_NUM);
        devByteBuf.write(ObjectSerializationUtils.serializationQuietly(protocol));
        // 写入到Netty数据
        byteBuf.writeBytes(devByteBuf.getBytes());
    }

}
