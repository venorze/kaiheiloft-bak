package com.hantiansoft.distance.service;

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

/* Creates on 2022/12/22. */

import com.baomidou.mybatisplus.extension.service.IService;
import com.hantiansoft.distance.enties.User;
import com.hantiansoft.distance.modx.EditMailModx;
import com.hantiansoft.distance.modx.EditPasswordModx;
import com.hantiansoft.distance.modx.UserSignUpModx;
import com.hantiansoft.distance.modx.UserProfileModx;

/**
 * @author Vincent Luo
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户ID查询用户
     */
    User queryByUserId(Long userid);

    /**
     * 根据用户名查询用户
     */
    User queryByUsername(String username);

    /**
     * 用户注册
     */
    void sign_up(UserSignUpModx userSignUpModx);

    /**
     * 个人信息获取
     */
    UserProfileModx profile(String username);

    /**
     * 个人信息修改
     */
    void editProfile(Long userid, UserProfileModx userProfileModx);

    /**
     * 密码修改
     */
    void editPassword(Long userid, EditPasswordModx editPasswordModx);

    /**
     * 邮箱修改
     */
    void editMail(Long userid, EditMailModx editMailModx);
}
