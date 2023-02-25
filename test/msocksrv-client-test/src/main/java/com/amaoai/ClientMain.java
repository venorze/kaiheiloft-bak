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
import com.amaoai.msocksrv.protocol.MCMUNDecoder;
import com.amaoai.msocksrv.protocol.MCMUNEncoder;
import com.amaoai.msocksrv.protocol.MCMUNProtocol;
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
import lombok.Data;

import java.util.Date;
import java.util.Scanner;

import static devtools.framework.StringUtils.vfmt;


/**
 * @author Amaoai
 */
public class ClientMain {

    static int count = 0;

    @Data
    static class MThread {
        private Thread thread = null;

        private boolean start = false;

        public void start(Runnable runnable) {
            if (!start) {
                thread = new Thread(runnable);
                thread.start();
                start = true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        MThread mThread = new MThread();

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
                                            mThread.start(() -> {
                                                do {
                                                    System.out.print("输入发送消息条数：");
                                                    int loopCount = scanner.nextInt();
                                                    System.out.print("\n");
                                                    MCMUNProtocol mcmunDataPack = new MCMUNProtocol();
                                                    mcmunDataPack.setType(MCMUNProtocol.MessageType.TEXT);
                                                    mcmunDataPack.setAttach(Lists.ofList("attch0", "attch1", "attch2"));
                                                    mcmunDataPack.setTime(new Date());
                                                    JUnits.performance(() -> {
                                                        for (int i = 0; i < loopCount; i++) {
                                                            mcmunDataPack.setMid("A10001" + i);
                                                            mcmunDataPack.setSender("S10000" + i);
                                                            mcmunDataPack.setReceiver("R10000" + i);
                                                            mcmunDataPack.setMessage(vfmt("Hello World({})", i));
                                                            // 发送数据
                                                            ChannelFuture channelFuture = ctx.writeAndFlush(mcmunDataPack);
                                                            System.out.println(vfmt("（{}）SUCCESS（{}）", (count++), channelFuture));
                                                        }
                                                    }, vfmt("发送{}条消息", loopCount));
                                                    ctx.channel().flush();
                                                } while (true);
                                            });
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println("收到来自服务器的消息：" + msg);
                                        }

                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                            cause.printStackTrace();
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
