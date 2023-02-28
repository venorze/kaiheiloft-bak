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

import com.amaoai.framework.redis.RedisOperationFactory;
import com.amaoai.msrv.handlers.UMCProtocolSocketHandler;
import com.amaoai.msrv.handlers.contxt.ConfigurableHandlerAdapterContext;
import com.amaoai.msrv.protocol.umcp.UMCPDecoder;
import com.amaoai.msrv.protocol.umcp.UMCPEncoder;
import com.amaoai.msrv.spring.RedisConnectProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetSocketAddress;

/**
 * @author Vincent Luo
 */
public class NettyApplicationBootstrap {

    /**
     * 处理Channel的线程组
     */
    private final NioEventLoopGroup BOSS_EVENT_LOOP_GROUP = new NioEventLoopGroup();
    private final NioEventLoopGroup WORKER_EVENT_LOOP_GROUP = new NioEventLoopGroup();

    /**
     * 启动Netty服务
     */
    public static void run(String[] args,
                           ConfigurableApplicationContext configurableApplicationContext) {
        // 初始化 configurableHandlerAdapterContext 上下文
        ConfigurableHandlerAdapterContext configurableHandlerAdapterContext =
              initializationConfigurableHandlerAdapterContext(args, configurableApplicationContext);
        // 启动Netty服务
        new NettyApplicationBootstrap().poll(configurableHandlerAdapterContext);
    }

    /**
     * 启动ServerSocket服务
     */
    private void poll(ConfigurableHandlerAdapterContext configurableHandlerAdapterContext) {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(BOSS_EVENT_LOOP_GROUP, WORKER_EVENT_LOOP_GROUP)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 添加处理器
                            ch.pipeline()
                                .addLast(new IdleStateHandler(120, 0, 0))
                                .addLast(new UMCPEncoder())
                                .addLast(new UMCPDecoder())
                                .addLast(new UMCProtocolSocketHandler(configurableHandlerAdapterContext));
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
     * 初始化上下文
     */
    private static ConfigurableHandlerAdapterContext
      initializationConfigurableHandlerAdapterContext(String[] args,
                                                      ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableHandlerAdapterContext configurableHandlerAdapterContext =
              new ConfigurableHandlerAdapterContext(args, configurableApplicationContext);
        // 获取Redis配置
        RedisConnectProperties redisConnectProperties =
              configurableHandlerAdapterContext.getRequiredBean(RedisConnectProperties.class);
        // 连接Redis
        configurableHandlerAdapterContext.setRedisOperationPool(
              RedisOperationFactory.connect(redisConnectProperties.getAddr(), redisConnectProperties.getPort()));
        return configurableHandlerAdapterContext;
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
