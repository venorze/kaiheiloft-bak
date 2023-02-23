package com.amaoai;

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

import com.alibaba.fastjson.JSON;
import com.amaoai.mcmun.MCMUNDecoder;
import com.amaoai.mcmun.MCMUNEncoder;
import com.amaoai.mcmun.MCMUNProtocol;
import devtools.framework.JUnits;
import devtools.framework.StringUtils;
import devtools.framework.collections.Lists;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

/**
 * @author Amaoai
 */
public class ClientMain {

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new MCMUNEncoder())
                                    .addLast(new MCMUNDecoder())
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            do {
                                                System.out.print("按下任意键发送100万条消息....");
                                                System.in.read();
                                                System.out.print("\n");
                                                MCMUNProtocol mcmunDataPack = new MCMUNProtocol();
                                                mcmunDataPack.setType(MCMUNProtocol.MessageType.TEXT);
                                                mcmunDataPack.setAttach(Lists.ofList("attch0", "attch1", "attch2"));
                                                mcmunDataPack.setTime(new Date());
                                                JUnits.performance(() -> {
                                                    for (int i = 0; i < 1000000; i++) {
                                                        mcmunDataPack.setMid("A10001" + i);
                                                        mcmunDataPack.setSender("S10000" + i);
                                                        mcmunDataPack.setReceiver("R10000" + i);
                                                        mcmunDataPack.setMessage(StringUtils.vfmt("Hello World({})", i));
                                                        // 发送数据
                                                        ctx.writeAndFlush(mcmunDataPack);
                                                        // System.out.println("发送数据：" + mcmunDataPack);
                                                    }
                                                }, "发送一百万条消息");
                                                mcmunDataPack.setType(MCMUNProtocol.MessageType.IMAGE);
                                                ctx.writeAndFlush(mcmunDataPack);
                                            } while (true);
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println("收到来自服务器的消息：\n" + JSON.toJSONString(msg));
                                        }
                                    });
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 11451).sync();
            future.channel().closeFuture().sync();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            nioEventLoopGroup.shutdownGracefully().sync();
        }

    }

}
