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

import com.hantiansoft.framework.BeanUtils;
import com.hantiansoft.framework.R;
import com.hantiansoft.kaiheiloft.enties.Club;
import com.hantiansoft.kaiheiloft.modx.ClubModv;
import com.hantiansoft.kaiheiloft.modx.EditMailModx;
import com.hantiansoft.kaiheiloft.modx.EditPasswordModx;
import com.hantiansoft.kaiheiloft.modx.UserProfileModx;
import com.hantiansoft.kaiheiloft.service.ClubService;
import com.hantiansoft.kaiheiloft.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private ClubService clubService;

    /**
     * 获取用户个人信息
     */
    @GetMapping("/profile")
    public R<UserProfileModx> profile() {
        return R.ok(userService.profile(getCurrentUsername()));
    }

    /**
     * 修改用户个人信息
     */
    @PostMapping("/edit/profile")
    public R<Void> editProfile(@RequestBody @Valid UserProfileModx userProfileModx) {
        userService.editProfile(getCurrentUserId(), userProfileModx);
        return R.ok();
    }

    /**
     * 用户密码修改
     */
    @PostMapping("/edit/passwd")
    public R<Void> editPassword(@RequestBody @Valid EditPasswordModx editPasswordModx) {
        userService.editPassword(getCurrentUserId(), editPasswordModx);
        return R.ok();
    }

    /**
     * 用户邮箱修改
     */
    @PostMapping("/edit/mail")
    public R<Void> editMail(@RequestBody @Valid EditMailModx editMailModx) {
        userService.editMail(getCurrentUserId(), editMailModx);
        return R.ok();
    }

    /**
     * @return 查询用户加入的俱乐部列表
     */
    @GetMapping("/clubs")
    public R<List<ClubModv>> queryClubsByUserId() {
        List<Club> clubs = clubService.queryClubsByUserId(getCurrentUserId());
        return R.ok(BeanUtils.copyProperties(clubs, ClubModv.class));
    }

}
