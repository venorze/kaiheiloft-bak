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

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Vincent Luo
 */
@Data
public class SocketHandlerContext {

    private ChannelHandlerContext ctx;

    private ConfigurableApplicationContext configurableApplicationContext;

    public SocketHandlerContext(ChannelHandlerContext ctx,
                                ConfigurableApplicationContext configurableApplicationContext) {
        this.ctx = ctx;
        this.configurableApplicationContext = configurableApplicationContext;
    }

    /**
     * @return 提供当前客户端的ChannelHandlerContext函数
     */
    public ChannelHandlerContext ctx() {
        return this.ctx;
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

}
