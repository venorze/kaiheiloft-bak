package com.amaoai.msrv.handlers;

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

import com.amaoai.framework.collections.Maps;
import com.amaoai.framework.refection.ClassLoaders;
import com.amaoai.framework.refection.ClassUtils;
import com.amaoai.msrv.handlers.contxt.SocketHandlerContext;
import com.amaoai.msrv.handlers.iface.UMCPCommandHandlerAdapter;
import com.amaoai.msrv.handlers.iface.UMCPCommandHandlerSelect;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;
import com.amaoai.msrv.protocol.umcp.UMCPCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * NioSocketChannel处理器
 *
 * @author Vincent Luo
 */
public class UMCProtocolSocketHandler extends ChannelInboundHandlerAdapter {

    /**
     * springboot上下文对象
     */
    private final ConfigurableApplicationContext configurableApplicationContext;

    /**
     * 每个处理器的上下文对象
     */
    private SocketHandlerContext socketHandlerContext;

    /**
     * 协议命令处理器集合
     */
    private final static Map<UMCPCommand, UMCPCommandHandlerAdapter> commandHandlers =
            Maps.newHashMap();


    static {
        loadUMCPCommandHandlers();
    }

    /**
     * 加载UMCP命令处理器
     */
    private static void loadUMCPCommandHandlers() {
        List<Class<?>> handlerClasses =
                ClassLoaders.scanPackages("com.amaoai.msrv.handlers.umcphandlers");
        // 遍历处理器类
        for (Class<?> handlerClass : handlerClasses) {
            if (handlerClass.isAnnotationPresent(UMCPCommandHandlerSelect.class)) {
                // 获取注解
                UMCPCommandHandlerSelect umcpCommandHandlerSelect =
                        handlerClass.getAnnotation(UMCPCommandHandlerSelect.class);
                // 实例化处理器
                commandHandlers.put(umcpCommandHandlerSelect.command(),
                        (UMCPCommandHandlerAdapter) ClassUtils.newInstance(handlerClass));
            }
        }
    }

    public static void main(String[] args) {
        System.out.println();
    }

    public UMCProtocolSocketHandler(ConfigurableApplicationContext configurableApplicationContext) {
        this.configurableApplicationContext = configurableApplicationContext;
    }

    /**
     * 当连接建立时初始化处理器上下文呢
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        socketHandlerContext =
                new SocketHandlerContext(ctx, configurableApplicationContext);
    }

    /**
     * 读取到客户端发来的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof UMCProtocol umcp)
            selectUMCPCommandHandler(umcp);
    }

    /**
     * 选择UMCP命令对应的处理器
     */
    private void selectUMCPCommandHandler(UMCProtocol umcp) {
        // 查找命令对应的处理器
        UMCPCommandHandlerAdapter umcpCommandHandlerAdapter = commandHandlers.get(umcp.command());
        if (umcpCommandHandlerAdapter != null) {
            // 判断当前处理器是不是第一次调用
            if (!umcpCommandHandlerAdapter.isActived()) {
                umcpCommandHandlerAdapter.active(socketHandlerContext);
                umcpCommandHandlerAdapter.actived();
            }
            // 执行处理器函数
            umcpCommandHandlerAdapter.handler(umcp, socketHandlerContext);
        }
    }

}
