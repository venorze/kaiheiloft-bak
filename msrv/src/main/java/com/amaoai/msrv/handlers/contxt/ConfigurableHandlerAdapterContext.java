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

/* Creates on 2023/2/28. */

import com.amaoai.framework.redis.RedisOperation;
import com.amaoai.framework.redis.RedisOperationPool;
import lombok.Data;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Vincent Luo
 */
@Data
public class ConfigurableHandlerAdapterContext {

    /**
     * 启动参数
     */
    private String[] args;

    /**
     * springboot应用上下文
     */
    private ConfigurableApplicationContext configurableApplicationContext;

    /**
     * redis会话
     */
    @Setter
    private RedisOperationPool redisOperationPool;

    public ConfigurableHandlerAdapterContext(String[] args, ConfigurableApplicationContext configurableApplicationContext) {
        this.args = args;
        this.configurableApplicationContext = configurableApplicationContext;
    }

    /**
     * @return 获取Redis连接池
     */
    public RedisOperation redisOperationResource() {
        return redisOperationPool.getResource();
    }

    /**
     * 从Spring容器中获取Bean对象
     */
    public <T> T getRequiredBean(Class<T> required) {
        return configurableApplicationContext.getBeanFactory().getBean(required);
    }

}
