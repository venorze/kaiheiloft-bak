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
import com.amaoai.export.opensso.modx.UserTokenPayload;
import com.amaoai.framework.R;
import com.amaoai.framework.exception.OptionFailedException;
import com.amaoai.msrv.handlers.contxt.SessionChannelHandlerContext;
import com.amaoai.msrv.handlers.UMCPCMDHandlerAdapter;
import com.amaoai.msrv.handlers.UMCPCMDHandlerMark;
import com.amaoai.msrv.protocol.umcp.UMCPCMD;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;
import com.amaoai.msrv.protocol.umcp.attch.UserAuthorization;

/**
 * 用户登录处理器
 *
 * @author Vincent Luo
 */
@UMCPCMDHandlerMark(cmd = UMCPCMD.SIGN_IN_SEND)
public class SignInSendUMCPCMDHandler extends UMCPCMDHandlerAdapter {

    /**
     * 统一认证服务接口
     */
    private UnifiedUserAuthenticationServiceAPI unifiedUserAuthenticationServiceAPI;

    @Override
    public void active(SessionChannelHandlerContext schx) {
        unifiedUserAuthenticationServiceAPI =
                schx.springBeanFactory().getBean(UnifiedUserAuthenticationServiceAPI.class);
    }

    @Override
    public void handler(UMCProtocol umcp, SessionChannelHandlerContext schx) {
        // 用户登录
        UserTokenPayload userTokenPayload = sign_in(umcp, schx);
        if (userTokenPayload != null) {
            // 注册有效通道标识
            String username = userTokenPayload.getUsername();
            SessionChannelHandlerContext.markValidSessionChannelHandlerContext(username, schx);
            schx.notifySessionMarkedValidStatus("认证成功，欢迎登录[{}]", username);
        }
    }

    /**
     * 处理用户登录操作
     */
    private UserTokenPayload sign_in(UMCProtocol umcp, SessionChannelHandlerContext schx) {
        try {
            UserAuthorization userAuthorization = umcp.attach();
            // 处理用户登录
            R<UserTokenPayload> payload =
                  unifiedUserAuthenticationServiceAPI.verifier(userAuthorization.getAuthorization());
            // 判断用户是否登录成功
            if (!payload.isSuccess())
                throw new OptionFailedException();

            return payload.to(UserTokenPayload.class);
        } catch (Throwable e) {
            schx.notifySessionMarkedValidStatus("用户认证失败，认证信息错误或服务器异常");
            schx.close();
            e.printStackTrace();
        }

        return null;
    }

}
