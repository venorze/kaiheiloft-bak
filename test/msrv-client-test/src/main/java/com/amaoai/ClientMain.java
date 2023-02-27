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

import com.amaoai.msrv.protocol.umcp.UMCPCMD;
import com.amaoai.msrv.protocol.umcp.UMCPDecoder;
import com.amaoai.msrv.protocol.umcp.UMCPEncoder;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;
import com.amaoai.msrv.protocol.umcp.attch.UserAuthorization;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;

import java.util.Scanner;


/**
 * @author Amaoai
 */
public class ClientMain {

    static int count = 1;

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

    static UserAuthorization userAuthorization = new UserAuthorization("1eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImNsYWltcyI6eyJ1aWQiOjI4NzM0ODU3MDM3NjI3MzkyMCwidW5hbWUiOiJrYWloZWlsb2Z0In0sImV4cCI6MTY3NzUwNzY0Mn0.5acDaEd8pdvG2ILHY_nbcS2bcgx5lr7fw9wWSrFDl7M");

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
                                    .addLast(new UMCPEncoder())
                                    .addLast(new UMCPDecoder())
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            UMCProtocol umcp = new UMCProtocol(userAuthorization, UMCPCMD.SIGN_IN_SEND);
                                            ctx.writeAndFlush(umcp);
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println("用户登录请求，接收到客户端回复：" + msg);
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
