package com.amaoai.kaiheiloft.controller;

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

import com.amaoai.kaiheiloft.modobj.modx.UserSignUpModx;
import com.amaoai.kaiheiloft.service.UserService;
import com.amaoai.framework.BeanUtils;
import com.amaoai.framework.R;
import com.amaoai.export.kaiheiloft.modx.UserInfo;
import com.amaoai.export.kaiheiloft.modx.UserSign;
import com.amaoai.spring.framework.annotation.OpenAPI;
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
    public R<UserInfo> sign_in(@RequestBody @Valid UserSign userSign) {
        var user = userService.login(userSign.getUsername(), userSign.getPassword());
        if (user == null)
            return R.fail("登陆失败，用户名或密码错误！");

        return R.ok(BeanUtils.copyProperties(user, UserInfo.class));
    }

}
