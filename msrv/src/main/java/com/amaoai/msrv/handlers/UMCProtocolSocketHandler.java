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
import com.amaoai.msrv.handlers.contxt.ClientChannelHandlerContext;
import com.amaoai.msrv.protocol.umcp.UMCPCMD;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
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
    private ClientChannelHandlerContext cchx;

    /**
     * 协议命令处理器集合
     */
    private final static Map<UMCPCMD, UMCPCMDHandlerAdapter> commandHandlers =
            Maps.newHashMap();

    static {
        // 加载 UMCP 命令处理器
        autoloadUMCPCommandHandlers();
    }

    /**
     * 加载UMCP命令处理器
     */
    private static void autoloadUMCPCommandHandlers() {
        List<Class<?>> handlerClasses =
                ClassLoaders.scanPackages("com.amaoai.msrv.handlers.umcphandlers");
        // 遍历处理器类
        for (Class<?> handlerClass : handlerClasses) {
            if (handlerClass.isAnnotationPresent(UMCPCMDHandlerMark.class)) {
                // 获取注解
                UMCPCMDHandlerMark UMCPCMDHandlerMark =
                        handlerClass.getAnnotation(UMCPCMDHandlerMark.class);
                // 实例化处理器
                commandHandlers.put(UMCPCMDHandlerMark.cmd(),
                        (UMCPCMDHandlerAdapter) ClassUtils.newInstance(handlerClass));
            }
        }
    }

    public UMCProtocolSocketHandler(ConfigurableApplicationContext configurableApplicationContext) {
        this.configurableApplicationContext = configurableApplicationContext;
    }

    /**
     * 当连接建立时初始化处理器上下文呢
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        cchx = new ClientChannelHandlerContext(ctx, configurableApplicationContext);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        selectAndRunUMCPCMDHandler(UMCProtocol.DISCONNECT, cchx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        // 处理用户写空闲事件
        if (evt instanceof IdleStateEvent event &&
            event.state() == IdleState.READER_IDLE) {
            // 如果写空闲事件被触发表示用户可能连接已经断开
            selectAndRunUMCPCMDHandler(UMCProtocol.DISCONNECT, cchx);
        }
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
        UMCPCMDHandlerAdapter UMCPCMDHandlerAdapter = selectUMCPCMDHandler(umcp);
        if (UMCPCMDHandlerAdapter != null) {
            // 处理心跳包
            if (umcp.cmd() == UMCPCMD.HEARTBEAT) {
                selectAndRunUMCPCMDHandler(umcp, cchx);
                return;
            }

            // 检查用户是否已经登录
            if (umcp.cmd() != UMCPCMD.SIGN_IN_SEND &&
                  !cchx.isValid()) {
                // 通知客户端
                cchx.notifyClientMarkedValidStatus("用户未认证！");
                // 断开连接
                selectUMCPCMDHandler(UMCProtocol.DISCONNECT)
                      .handler(UMCProtocol.DISCONNECT, cchx);
                return;
            }

            // 判断用户是否重复登录
            if (umcp.cmd() == UMCPCMD.SIGN_IN_SEND && cchx.isValid()) {
                cchx.notifyClientMarkedValidStatus("请勿重复认证！");
                return;
            }

            // 判断当前处理器是不是第一次调用
            if (!UMCPCMDHandlerAdapter.isActived()) {
                UMCPCMDHandlerAdapter.active(cchx);
                UMCPCMDHandlerAdapter.actived();
            }

            // 执行处理器函数
            UMCPCMDHandlerAdapter.handler(umcp, cchx);
        }
    }

    /**
     * 选择 UMCP CMD 处理器
     */
    public static UMCPCMDHandlerAdapter selectUMCPCMDHandler(UMCProtocol umcp) {
        return commandHandlers.get(umcp.cmd());
    }

    /**
     * 选择并运行 UMCP CMD 处理器
     */
    public static void selectAndRunUMCPCMDHandler(UMCProtocol umcp, ClientChannelHandlerContext cchx) {
        selectUMCPCMDHandler(umcp).handler(umcp, cchx);
    }

}
