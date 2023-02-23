package com.amaoai.msocksrv

import devtools.framework.PropertiesSourceLoaders
import devtools.framework.logging.LoggerFactory
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.string.StringDecoder
import org.springframework.context.ConfigurableApplicationContext
import java.util.Properties

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

/* Creates on 2023/2/20. */

/** spring全局上下文 */
typealias SpringApplicationContext = ConfigurableApplicationContext

/**
 * Channel初始化对象
 *
 * @author Amaoai
 */
private class MsrvChannelInitializer : ChannelInitializer<SocketChannel>() {

    override fun initChannel(p0: SocketChannel) {
        // 设置管线阶段处理器
        p0.pipeline()
            .addLast(StringDecoder())
            .addLast(MsrvSocketChannelHandler())
    }

}

/**
 * 长连接服务器
 *
 * @param springApplicationContext spring程序上下文
 * @param args 保留参数
 * @author Amaoai
 */
private class MsrvServerSocket(val springApplicationContext: SpringApplicationContext, val args: Array<String>) {

    /**
     * 事件线程组
     */
    private val bossGroup = NioEventLoopGroup()

    /**
     * 事件线程组
     */
    private val workerGroup = NioEventLoopGroup()

    /**
     * 日志组件
     */
    private val LOG = LoggerFactory.getLogger(MsrvServerSocket::class.java)

    /**
     * 初始化ServerBootstrap
     */
    private fun initializeServerBootstrap(properties: Properties) {
        try {
            val serverBootstrap = ServerBootstrap()
                // 添加两个线程组
                .group(bossGroup, workerGroup)
                // 设置通道实现类型
                .channel(NioServerSocketChannel::class.java)
                // 设置线程队列得到的连接个数
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 设置保持活动连接状态
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 初始化通道对象
                .childHandler(MsrvChannelInitializer())
            // 绑定端口号，启动服务端
            val port = properties.getProperty("port").toInt()
            LOG.info("Msrv socket server runs on port $port...")
            val channelFuture = serverBootstrap.bind(port).sync()
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync()
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            bossGroup.shutdownGracefully().sync()
            workerGroup.shutdownGracefully().sync()
        }
    }

    /**
     * 启动长连接服务
     */
    fun start(propname: String = "msrv.socket") {
        val prop = PropertiesSourceLoaders.loadProperties(propname) ?: throw Error("$propname not found.")
        initializeServerBootstrap(prop)
    }

}

/**
 * 长连接服务启动器
 */
object MsrvServerSocketApplication {

    @JvmStatic
    fun run(configurableApplicationContext: SpringApplicationContext, args: Array<String>): Unit {
        MsrvServerSocket(configurableApplicationContext, args).start()
    }

}