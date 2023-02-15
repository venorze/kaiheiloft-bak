package com.hantiansoft.msrv.socket

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ChannelInitializer, EventLoopGroup}
import io.netty.channel.epoll.{EpollEventLoopGroup, EpollServerSocketChannel}
import io.netty.channel.socket.SocketChannel
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.ConfigurableApplicationContext

import java.net.InetSocketAddress

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

/* Creates on 2023/2/16. */

/**
 * 长连接服务器
 *
 * @author Vincent Luo
 * @param springBeanFactory SpringBeanFactory
 * @param args              启动参数
 */
class MsrvServerSocket(val springBeanFactory: ConfigurableListableBeanFactory, val args: Array[String]) {

    private val eventLoopGroup: EventLoopGroup = new EpollEventLoopGroup()

    try {
        val serverBootstrap = new ServerBootstrap()
            .group(eventLoopGroup)
            .channel(classOf[EpollServerSocketChannel])
            .localAddress(new InetSocketAddress(69852))
            .handler(new ChannelInitializer[SocketChannel] {
                override def initChannel(socketChannel: SocketChannel): Unit = {
                }
            })
    }

}

/**
 * ServerSocket启动类
 */
object ServerSocketApplication {

    /**
     * 启动ServerSocket服务器
     *
     * @param args 启动参数（保留参数）
     */
    def run(configurableApplicationContext: ConfigurableApplicationContext, args: Array[String]): Unit = {
        new MsrvServerSocket(configurableApplicationContext.getBeanFactory, args)
    }

}
