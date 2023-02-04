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

/* Creates on 2023/1/10. */

import com.hantiansoft.kaiheiloft.modx.EditMailModx;
import com.hantiansoft.kaiheiloft.modx.EditPasswordModx;
import com.hantiansoft.kaiheiloft.modx.UserProfileModx;
import com.hantiansoft.kaiheiloft.service.UserService;
import com.hantiansoft.framework.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/user")
public class UserController extends SuperController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户个人信息
     */
    @GetMapping("/profile")
    public R<UserProfileModx> profile() {
        return R.ok(userService.profile(getUsername()));
    }

    /**
     * 修改用户个人信息
     */
    @PostMapping("/edit/profile")
    public R<Void> editProfile(@RequestBody @Valid UserProfileModx userProfileModx) {
        userService.editProfile(getUserId(), userProfileModx);
        return R.ok();
    }

    /**
     * 用户密码修改
     */
    @PostMapping("/edit/passwd")
    public R<Void> editPassword(@RequestBody @Valid EditPasswordModx editPasswordModx) {
        userService.editPassword(getUserId(), editPasswordModx);
        return R.ok();
    }

    /**
     * 用户邮箱修改
     */
    @PostMapping("/edit/mail")
    public R<Void> editMail(@RequestBody @Valid EditMailModx editMailModx) {
        userService.editMail(getUserId(), editMailModx);
        return R.ok();
    }

}
