package com.amaoai.kaiheiloft.configuration;

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
import com.amaoai.export.opensso.api.feign.UnifiedUserAuthenticationServiceAPI;
import devtools.framework.StringUtils;
import com.amaoai.kaiheiloft.KaiheiloftBootstrap;
import devtools.framework.R;
import com.amaoai.kaiheiloft.system.KaiheiloftApplicationContext;
import com.amaoai.export.opensso.modx.UserTokenPayload;
import com.amaoai.spring.framework.WebRequests;
import com.amaoai.spring.framework.annotation.OpenAPI;
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
    private UnifiedUserAuthenticationServiceAPI unifiedUserAuthenticationServiceAPI;

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

        R<UserTokenPayload> claimsRet = unifiedUserAuthenticationServiceAPI.verifier(authorization);
        if (claimsRet.isSuccess()) {
            var payload = (UserTokenPayload) claimsRet.to(UserTokenPayload.class);
            WebRequests.setAttribute(KaiheiloftApplicationContext.WEB_REQUEST_ATTRIBUTE_USER_ID, payload.getUserId());
            WebRequests.setAttribute(KaiheiloftApplicationContext.WEB_REQUEST_ATTRIBUTE_USERNAME, payload.getUsername());
            return true;
        }

        return eprint(response, claimsRet);
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (unifiedUserAuthenticationServiceAPI == null)
            unifiedUserAuthenticationServiceAPI = KaiheiloftBootstrap.ApplicationContext.getBean(UnifiedUserAuthenticationServiceAPI.class);

        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            // 如果没有注解 OpenAPI 那么就校验Token
            if (!method.isAnnotationPresent(OpenAPI.class))
                return verifierTokenAndSetAttributes(response);
        }

        return true;
    }

}
