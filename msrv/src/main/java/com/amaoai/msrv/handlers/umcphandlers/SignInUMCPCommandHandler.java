package com.amaoai.msrv.handlers.umcphandlers;

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

import com.amaoai.export.opensso.api.feign.UnifiedUserAuthenticationServiceAPI;
import com.amaoai.msrv.handlers.contxt.SocketHandlerContext;
import com.amaoai.msrv.handlers.iface.UMCPCommandHandlerAdapter;
import com.amaoai.msrv.handlers.iface.UMCPCommandHandlerSelect;
import com.amaoai.msrv.protocol.umcp.UMCPCommand;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;
import com.amaoai.msrv.protocol.umcp.attch.UserToken;

/**
 * 用户登录处理器
 *
 * @author Vincent Luo
 */
@UMCPCommandHandlerSelect(command = UMCPCommand.SIGN_IN_SEND)
public class SignInUMCPCommandHandler extends UMCPCommandHandlerAdapter {

    /**
     * 统一认证服务接口
     */
    private UnifiedUserAuthenticationServiceAPI unifiedUserAuthenticationServiceAPI;

    @Override
    public void active(SocketHandlerContext socketHandlerContext) {
        unifiedUserAuthenticationServiceAPI =
                socketHandlerContext.springBeanFactory().getBean(UnifiedUserAuthenticationServiceAPI.class);
    }

    @Override
    public void handler(UMCProtocol umcp, SocketHandlerContext socketHandlerContext) {
        UserToken userToken = umcp.attach();
    }

}
