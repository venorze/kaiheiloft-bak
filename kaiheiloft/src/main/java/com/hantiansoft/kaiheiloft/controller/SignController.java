package com.hantiansoft.kaiheiloft.controller;

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

/* Creates on 2023/1/16. */

import com.hantiansoft.kaiheiloft.modx.UserSignUpModx;
import com.hantiansoft.kaiheiloft.service.UserService;
import com.hantiansoft.framework.BeanUtils;
import com.hantiansoft.framework.R;
import com.hantiansoft.linkmod.kaiheiloft.UserInfoLinkMod;
import com.hantiansoft.linkmod.kaiheiloft.UserSignInLinkMod;
import com.hantiansoft.spring.framework.annotation.OpenAPI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户注册
 *
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/")
public class SignController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @OpenAPI
    @PostMapping("/sign_up")
    public R<Void> sign_up(@RequestBody @Valid UserSignUpModx userSignUpModx) {
        userService.sign_up(userSignUpModx);
        return R.ok();
    }

    /**
     * 用户登录接口（不对外暴露）
     */
    @OpenAPI
    @PostMapping("/nopen/sign_in/private")
    public R<UserInfoLinkMod> sign_in(@RequestBody @Valid UserSignInLinkMod userSignInLinkMod) {
        var user = userService.login(userSignInLinkMod.getUsername(), userSignInLinkMod.getPassword());
        if (user == null)
            return R.fail("登陆失败，用户名或密码错误！");

        return R.ok(BeanUtils.copyProperties(user, UserInfoLinkMod.class));
    }

}
