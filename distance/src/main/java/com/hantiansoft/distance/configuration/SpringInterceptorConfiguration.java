package com.hantiansoft.distance.configuration;

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
import com.hantiansoft.distance.DistanceBootstrap;
import com.hantiansoft.distance.remotecall.OpenSSORemoteCall;
import com.hantiansoft.framework.R;
import com.hantiansoft.spring.framework.WebRequests;
import com.hantiansoft.spring.framework.annotation.OpenAPI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Vincent Luo
 */
public class SpringInterceptorConfiguration implements HandlerInterceptor {

    /**
     * 远程调用认证服务
     */
    private OpenSSORemoteCall openSSORemoteCall;

    /**
     * 验证token并设置当前请求Attribute
     */
    @SuppressWarnings("unchecked")
    private boolean verifierTokenAndSetAttributes(HttpServletResponse response) throws IOException {
        R<Map<String, Object>> claimsRet = openSSORemoteCall.verifier(WebRequests.getAuthorization());
        if (claimsRet.isSuccess()) {
            var claims = (Map<String, Object>) claimsRet.getData();
            WebRequests.setAttribute("uid", claims.get("uid"));
            WebRequests.setAttribute("uname", claims.get("uname"));
            return true;
        }

        // 解决中文乱码异常
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // token校验失败返回异常信息
        var printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(claimsRet));
        printWriter.flush();

        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (openSSORemoteCall == null)
            openSSORemoteCall = DistanceBootstrap.ApplicationContext.getBean(OpenSSORemoteCall.class);

        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            // 如果没有注解 OpenAPI 那么就校验Token
            if (!method.isAnnotationPresent(OpenAPI.class))
                return verifierTokenAndSetAttributes(response);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
