package com.amaoai.msrv;

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

/* Creates on 2023/2/27. */

import com.amaoai.msrv.handlers.UMCProtocolSocketHandler;
import com.amaoai.msrv.protocol.umcp.UMCPDecoder;
import com.amaoai.msrv.protocol.umcp.UMCPEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetSocketAddress;

/**
 * @author Vincent Luo
 */
public class SocketBootstrap {

    /**
     * 处理Channel的线程组
     */
    private final NioEventLoopGroup BOSS_EVENT_LOOP_GROUP = new NioEventLoopGroup();
    private final NioEventLoopGroup WORKER_EVENT_LOOP_GROUP = new NioEventLoopGroup();

    /**
     * 启动Netty服务
     *
     * @param configurableApplicationContext springboot上下文
     * @param args                           保留参数
     */
    public static void run(ConfigurableApplicationContext configurableApplicationContext,
                           String[] args) {
        new SocketBootstrap().run0(args, configurableApplicationContext);
    }

    /**
     * 启动ServerSocket服务
     *
     * @param args 保留参数
     */
    private void run0(String[] args, ConfigurableApplicationContext configurableApplicationContext) {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(BOSS_EVENT_LOOP_GROUP, WORKER_EVENT_LOOP_GROUP)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new UMCPEncoder());
                            pipeline.addLast(new UMCPDecoder());
                            pipeline.addLast(new UMCProtocolSocketHandler(configurableApplicationContext));
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(11451)).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            shutdownGracefully(BOSS_EVENT_LOOP_GROUP, WORKER_EVENT_LOOP_GROUP);
        }
    }

    /**
     * 关闭EventLoopGroup
     */
    @SneakyThrows
    private static void shutdownGracefully(NioEventLoopGroup... groups) {
        for (MultithreadEventLoopGroup group : groups)
            group.shutdownGracefully().sync();
    }

}
