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

import com.hantiansoft.distance.mod.EditMailMod;
import com.hantiansoft.distance.mod.EditPasswdMod;
import com.hantiansoft.distance.mod.UserSignUpMod;
import com.hantiansoft.distance.mod.UserProfileMod;
import com.hantiansoft.distance.service.UserService;
import com.hantiansoft.framework.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户个人信息
     */
    @GetMapping("/profile/{username}")
    public R<UserProfileMod> profile(@PathVariable("username") String username) {
        return R.ok(userService.profile(username));
    }

    /**
     * 修改用户个人信息
     */
    @PostMapping("/edit/{userid}/profile")
    public R<Void> profile_edit(@PathVariable("userid") Long userid, @RequestBody @Valid UserProfileMod userProfileMod) {
        userService.profile_edit(userid, userProfileMod);
        return R.ok();
    }

    /**
     * 用户密码修改
     */
    @PostMapping("/edit/{userid}/passwd")
    public R<Void> passwd_edit(@PathVariable("userid") Long userid, @RequestBody @Valid EditPasswdMod editPasswdMod) {
        userService.passwd_edit(userid, editPasswdMod);
        return R.ok();
    }

    /**
     * 用户邮箱修改
     */
    @PostMapping("/edit/{userid}/mail")
    public R<Void> mail_edit(@PathVariable("userid") Long userid, @RequestBody @Valid EditMailMod editMailMod) {
        userService.mail_edit(userid, editMailMod);
        return R.ok();
    }

}
