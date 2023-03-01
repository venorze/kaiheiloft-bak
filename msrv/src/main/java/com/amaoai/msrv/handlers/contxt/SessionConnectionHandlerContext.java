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
import com.amaoai.framework.redis.RedisOperation;
import com.amaoai.msrv.handlers.umcphandlers.SignInSendUMCPCMDHandler;
import com.amaoai.msrv.protocol.umcp.UMCPCMD;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vincent Luo
 */
public class SessionConnectionHandlerContext {

    /**
     * channel处理器上下文
     */
    private final ChannelHandlerContext channelHandlerContext;

    /**
     * springboot上下文
     */
    private final ConfigurableHandlerAdapterContext configurableHandlerAdapterContext;

    /**
     * 当前会话所有者（用户ID）
     */
    private Long owner;

    /**
     * 已经通过登录认证注册好的用户Channel
     */
    private static final Map<Long, SessionConnectionHandlerContext>
            markedValidSessionChannelHandlerContext = new ConcurrentHashMap<>(1024);

    public interface RedisOperationFunction {
        void apply(RedisOperation ops);
    }

    public SessionConnectionHandlerContext(ChannelHandlerContext ctx,
                                           ConfigurableHandlerAdapterContext configurableHandlerAdapterContext) {
        this.channelHandlerContext = ctx;
        this.configurableHandlerAdapterContext = configurableHandlerAdapterContext;
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
        return configurableHandlerAdapterContext.getConfigurableApplicationContext();
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
    public Long owner() {
        return owner;
    }

    public Channel channel() {
        return channelHandlerContext.channel();
    }

    /**
     * @return 用户是否通过了登录认证
     */
    public boolean isValid() {
        return owner != null && markedValidSessionChannelHandlerContext.containsKey(owner);
    }

    /**
     * 通知客户端用户认证状态
     */
    public void notifySessionMarkedValidStatus(String fmt, Object... args) {
        writeAndFlush(new UMCProtocol(owner != null ? UMCPCMD.CMDFLAG_SIGN_IN_SUCCESS : UMCPCMD.CMDFLAG_SIGN_IN_FAILED,
                ("SIGN IN ACK - " + StringUtils.vfmt(fmt, args)), UMCPCMD.SIGN_IN_ACK));
    }

    /**
     * 标记有效的客户端
     *
     * @see SignInSendUMCPCMDHandler#handler
     */
    public static void markValidSessionChannelHandlerContext(Long owner, SessionConnectionHandlerContext session) {
        synchronized (markedValidSessionChannelHandlerContext) {
            markedValidSessionChannelHandlerContext.put(owner, session);
            // 设置当前客户端通道的所属用户
            session.owner = owner;
        }
    }

    /**
     * 标记无效的客户端
     *
     * @see SignInSendUMCPCMDHandler#handler
     */
    public static void markUnValidSessionChannelHandlerContext(SessionConnectionHandlerContext session) {
        synchronized (markedValidSessionChannelHandlerContext) {
            session.close();
            if (session.owner != null)
                markedValidSessionChannelHandlerContext.remove(session.owner);
        }
    }

    /**
     * 获取在线的客户端
     */
    public static SessionConnectionHandlerContext online(Long owner) {
        return markedValidSessionChannelHandlerContext.get(owner);
    }


    /**
     * 强制关闭连接
     */
    public void close() {
        channelHandlerContext.channel().close();
    }

    /**
     * 获取 Jedis 对象，使用完必须close()
     */
    public void executeRedisOperation(RedisOperationFunction function) {
        try (RedisOperation resource = configurableHandlerAdapterContext.redisOperationResource()) {
            resource.select(RedisOperation.REDIS_DATABASE_IDX_1);
            function.apply(resource);
        }
    }

}
