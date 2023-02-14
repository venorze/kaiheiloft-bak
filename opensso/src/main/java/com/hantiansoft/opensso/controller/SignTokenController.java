package com.hantiansoft.opensso.controller;

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

/* Creates on 2023/1/20. */

import com.hantiansoft.export.kaiheiloft.api.feign.UserServiceApi;
import com.hantiansoft.export.kaiheiloft.modx.UserInfo;
import com.hantiansoft.export.kaiheiloft.modx.UserSign;
import com.hantiansoft.export.opensso.modx.UserTokenPayload;
import com.hantiansoft.framework.R;
import com.hantiansoft.framework.collections.Maps;
import com.hantiansoft.opensso.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/")
public class SignTokenController {

    @Autowired
    private UserServiceApi userServiceApi;

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 用户登录
     */
    @GetMapping("/sign_in")
    public R<UserInfo> sign_in(@RequestBody @Valid UserSign userSign) {
        R<UserInfo> ret = userServiceApi.sign_in(userSign);
        // 判断是否登录错误
        if (!ret.isSuccess())
            return ret;

        // 构建token荷载
        var userinfo = ret.to(UserInfo.class);
        Map<String, Object> payload = Maps.ofMap("uid", userinfo.getId(), "uname", userinfo.getUsername());

        // 登录成功
        return R.ok(
                "user", userinfo,
                "token", authenticationService.createToken(payload)
        );
    }

    /**
     * 验证token
     */
    @PostMapping("/nopen/verifier/private")
    public R<UserTokenPayload> verifier(@RequestHeader("Authorization") String authorization) {
        if (authenticationService.verifier(authorization)) {
            // 获取token信息
            Map<String, Object> claims = authenticationService.getClaims(authorization);
            String uid = String.valueOf(claims.get("uid"));
            String uname = String.valueOf(claims.get("uname"));

            // 构建返回对象
            UserTokenPayload userTokenPayload = new UserTokenPayload();
            userTokenPayload.setUserId(Long.valueOf(uid));
            userTokenPayload.setUsername(uname);

            return R.ok(userTokenPayload);
        }

        return R.fail("token不正确或已过期");
    }

}
