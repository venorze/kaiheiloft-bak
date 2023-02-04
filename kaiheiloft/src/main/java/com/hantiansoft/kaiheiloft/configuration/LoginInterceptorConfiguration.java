package com.hantiansoft.kaiheiloft.configuration;

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

/* Creates on 2023/1/22. */

import com.alibaba.fastjson.JSON;
import com.hantiansoft.framework.StringUtils;
import com.hantiansoft.kaiheiloft.KaiheiloftBootstrap;
import com.hantiansoft.kaiheiloft.remotecall.OpenSSORemoteCall;
import com.hantiansoft.framework.R;
import com.hantiansoft.kaiheiloft.system.KaiheiloftApplicationContext;
import com.hantiansoft.linkmod.opensso.TokenPayloadLinkmod;
import com.hantiansoft.spring.framework.WebRequests;
import com.hantiansoft.spring.framework.annotation.OpenAPI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author Vincent Luo
 */
public class LoginInterceptorConfiguration implements HandlerInterceptor {

    /**
     * 远程调用认证服务
     */
    private OpenSSORemoteCall openSSORemoteCall;

    /**
     * 拦截器异常返回, 消息传入String类型
     */
    private static boolean eprint(HttpServletResponse response, String message) throws IOException {
        // 解决中文乱码异常
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // token校验失败返回异常信息
        var printWriter = response.getWriter();
        printWriter.write(message);
        printWriter.flush();

        return false;
    }

    /**
     * 拦截器异常返回, 消息传入#R类型
     */
    private static boolean eprint(HttpServletResponse response, R<?> result) throws IOException {
        return eprint(response, JSON.toJSONString(result));
    }

    /**
     * 验证token并设置当前请求Attribute
     */
    private boolean verifierTokenAndSetAttributes(HttpServletResponse response) throws IOException {
        String authorization = WebRequests.getAuthorization();
        if (StringUtils.isEmpty(authorization))
            return eprint(response, R.fail(R.Status.S401, "用户未登录"));

        R<TokenPayloadLinkmod> claimsRet = openSSORemoteCall.verifier(authorization);
        if (claimsRet.isSuccess()) {
            var payload = (TokenPayloadLinkmod) claimsRet.to(TokenPayloadLinkmod.class);
            WebRequests.setAttribute(KaiheiloftApplicationContext.WEB_REQUEST_ATTRIBUTE_USER_ID, payload.getUserId());
            WebRequests.setAttribute(KaiheiloftApplicationContext.WEB_REQUEST_ATTRIBUTE_USERNAME, payload.getUsername());
            return true;
        }

        return eprint(response, claimsRet);
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (openSSORemoteCall == null)
            openSSORemoteCall = KaiheiloftBootstrap.ApplicationContext.getBean(OpenSSORemoteCall.class);

        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            // 如果没有注解 OpenAPI 那么就校验Token
            if (!method.isAnnotationPresent(OpenAPI.class))
                return verifierTokenAndSetAttributes(response);
        }

        return true;
    }

}
