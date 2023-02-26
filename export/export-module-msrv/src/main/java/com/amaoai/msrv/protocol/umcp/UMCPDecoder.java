package com.amaoai.msrv.protocol.umcp;

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

import com.amaoai.framework.io.ByteBuffer;
import com.amaoai.framework.io.IOUtils;
import com.amaoai.framework.io.ObjectSerializationUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 协议解码器
 *
 * @author Amaoai
 */
public class UMCPDecoder extends ByteToMessageDecoder {

    private final ByteBuffer remByteBuffer = ByteBuffer.alloc();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
            throws Exception {
        // 将数据写入到字节缓冲区中
        ByteBuffer readbuf = ByteBuffer.wrap(remByteBuffer.clear());
        // 将粘包半包的字节和当前接收到的字节组合在一起解析
        int bufSize = byteBuf.readableBytes();
        byte[] tmpBuf = IOUtils.getByteArray(bufSize);
        byteBuf.readBytes(tmpBuf);
        readbuf.write(tmpBuf);
        readbuf.flip();
        // 解析数据包
        readpack(readbuf, list);
    }

    /**
     * 解析数据包
     */
    public void readpack(ByteBuffer readbuf, List<Object> list) {
        // 解析数据包
        int magicNumber = readbuf.readInt();
        int protocolVersion = readbuf.readInt();

        // 如果魔数和版本都相同那么代表这是一个正确的数据包起始
        if (magicNumber == UMCPVersion.MAGIC_NUMBER &&
                protocolVersion == UMCPVersion.VERSION) {
            // 获取数据包长度
            int len = readbuf.readInt();

            // 如果长度小于未读大小那么表示这是一个半包数据
            if (readbuf.remsize() < len) {
                // 因为前面调用了三次readInt，跳过了12个字节，所以需要读写指针往后移动八位
                readbuf.seek(-(IOUtils.SIZE_OF_INT * 3), IOUtils.SEEK_CUR);
                remByteBuffer.write(readbuf.getRemBytes());
                return;
            }

            // 读取到一个完整的数据包
            byte[] bytebuf = IOUtils.getByteArray(len);
            readbuf.read(bytebuf);
            Object umcpAttach =
                    ObjectSerializationUtils.unserializationQuietly(bytebuf);
            list.add(umcpAttach);

            if (!readbuf.eof())
                readpack(readbuf, list);
        }
    }

}
