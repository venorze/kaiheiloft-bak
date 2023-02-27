package com.amaoai.msrv.handlers.contxt;

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

import com.amaoai.framework.StringUtils;
import com.amaoai.msrv.handlers.umcphandlers.SignInSendUMCPCMDHandler;
import com.amaoai.msrv.protocol.umcp.UMCPCMD;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vincent Luo
 */
public class ClientChannelHandlerContext {

    /**
     * channel处理器上下文
     */
    private final ChannelHandlerContext channelHandlerContext;

    /**
     * springboot上下文
     */
    private final ConfigurableApplicationContext configurableApplicationContext;

    /**
     * 用户名
     */
    private String user;

    /**
     * 已经通过登录认证注册好的用户Channel
     */
    private static final Map<String, ClientChannelHandlerContext>
            markedValidClientChannelHandlerContext = new ConcurrentHashMap<>(1024);

    public ClientChannelHandlerContext(ChannelHandlerContext ctx,
                                       ConfigurableApplicationContext configurableApplicationContext) {
        this.channelHandlerContext = ctx;
        this.configurableApplicationContext = configurableApplicationContext;
    }

    /**
     * 向客户端写入数据
     */
    @SuppressWarnings("UnusedReturnValue")
    public ChannelFuture writeAndFlush(Object msg) {
        return channelHandlerContext.channel().writeAndFlush(msg);
    }

    /**
     * @return 提供SpringBoot上下文对象
     */
    public ConfigurableApplicationContext springConfigurableApplicationContext() {
        return configurableApplicationContext;
    }

    /**
     * @return 提供SpringBeanFactory对象
     */
    public ConfigurableListableBeanFactory springBeanFactory() {
        return springConfigurableApplicationContext().getBeanFactory();
    }

    /**
     * @return 所属用户
     */
    public String user() {
        return user;
    }

    public Channel channel() {
        return channelHandlerContext.channel();
    }

    /**
     * @return 用户是否通过了登录认证
     */
    public boolean isValid() {
        return user != null && markedValidClientChannelHandlerContext.containsKey(user);
    }

    /**
     * 通知客户端用户认证状态
     */
    public void notifyClientMarkedValidStatus(String fmt, Object... args) {
        writeAndFlush(new UMCProtocol(user != null ? UMCPCMD.CMDFLAG_SIGN_IN_SUCCESS : UMCPCMD.CMDFLAG_SIGN_IN_FAILED,
                ("SIGN IN ACK - " + StringUtils.vfmt(fmt, args)), UMCPCMD.SIGN_IN_ACK));
    }

    /**
     * 标记有效的客户端
     *
     * @see SignInSendUMCPCMDHandler#handler
     */
    public static void markValidClientChannelHandlerContext(String user, ClientChannelHandlerContext cchx) {
        synchronized (markedValidClientChannelHandlerContext) {
            markedValidClientChannelHandlerContext.put(user, cchx);
            // 设置当前客户端通道的所属用户
            cchx.user = user;
        }
    }

    /**
     * 标记无效的客户端
     *
     * @see SignInSendUMCPCMDHandler#handler
     */
    public static void markUnValidClientChannelHandlerContext(ClientChannelHandlerContext cchx) {
        synchronized (markedValidClientChannelHandlerContext) {
            cchx.close();
            if (cchx.user != null)
                markedValidClientChannelHandlerContext.remove(cchx.user);
        }
    }

    /**
     * 获取在线的客户端
     */
    public static ClientChannelHandlerContext online(String user) {
        return markedValidClientChannelHandlerContext.get(user);
    }


    /**
     * 强制关闭连接
     */
    public void close() {
        channelHandlerContext.channel().close();
    }

}
