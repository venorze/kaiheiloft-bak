package com.hantiansoft.distance.controller;

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

/* Creates on 2023/1/10. */

import com.hantiansoft.distance.reqmod.UserSignUpReqmod;
import com.hantiansoft.distance.model.UserProfile;
import com.hantiansoft.distance.service.UserService;
import com.hantiansoft.framework.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/sign_up")
    public R<Void> sign_up(@RequestBody @Valid UserSignUpReqmod userSignUpReqmod) {
        userService.sign_up(userSignUpReqmod);
        return R.ok();
    }

    /**
     * 获取用户个人信息
     */
    @GetMapping("/profile/{userid}")
    public R<UserProfile> profile(@PathVariable("userid") Long userid) {
        return R.ok(userService.profile(userid));
    }

    /**
     * 修改用户个人信息
     */
    @GetMapping("/profile/{userid}/edit")
    public R<Void> profile_edit(@PathVariable("userid") Long userid, @RequestBody @Valid UserProfile userProfile) {
        userService.profile_edit(userid, userProfile);
        return R.ok();
    }

}
