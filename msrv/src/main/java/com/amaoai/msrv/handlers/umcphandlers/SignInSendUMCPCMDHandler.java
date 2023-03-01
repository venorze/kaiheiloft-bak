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
import com.amaoai.msrv.handlers.UMCPCMDHandlerAdapter;
import com.amaoai.msrv.handlers.UMCPCMDHandlerMark;
import com.amaoai.msrv.handlers.contxt.SessionConnectionHandlerContext;
import com.amaoai.msrv.protocol.umcp.UMCPCMD;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;
import com.amaoai.msrv.protocol.umcp.attch.UserAuthorization;
import com.amaoai.msrv.user.UserStatus;

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
    public void active(SessionConnectionHandlerContext session) {
        unifiedUserAuthenticationServiceAPI =
                session.springBeanFactory().getBean(UnifiedUserAuthenticationServiceAPI.class);
    }

    @Override
    public void handler(UMCProtocol umcp, SessionConnectionHandlerContext session) {
        // 用户登录
        UserTokenPayload userTokenPayload = sign_in(umcp, session);
        if (userTokenPayload != null) {
            Long userid = userTokenPayload.getUserId();
            // 注册有效通道标识
            SessionConnectionHandlerContext.markValidSessionChannelHandlerContext(userid, session);
            session.notifySessionMarkedValidStatus("认证成功，欢迎登录[{}]", userid);
            // 用户状态存入redis缓存中
            UserStatus userStatus = new UserStatus(userid, UserStatus.USER_STATUS_ONLINE);
            session.executeRedisOperation(ops -> ops.setByte(session.owner(), userStatus));
        }
    }

    /**
     * 处理用户登录操作
     */
    private UserTokenPayload sign_in(UMCProtocol umcp, SessionConnectionHandlerContext session) {
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
            session.notifySessionMarkedValidStatus("用户认证失败，认证信息错误或服务器异常");
            session.close();
            e.printStackTrace();
        }

        return null;
    }

}
