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
import com.hantiansoft.distance.reqmod.EditMailReqmod;
import com.hantiansoft.distance.reqmod.EditPasswdReqmod;
import com.hantiansoft.distance.reqmod.UserSignUpReqmod;
import com.hantiansoft.distance.model.UserProfile;

/**
 * @author Vincent Luo
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户ID查询用户
     */
    User of_user_id(Long userid);

    /**
     * 用户注册
     */
    void sign_up(UserSignUpReqmod userSignUpReqmod);

    /**
     * 个人信息获取
     */
    UserProfile profile(Long userid);

    /**
     * 个人信息修改
     */
    void profile_edit(Long userid, UserProfile userProfile);

    /**
     * 密码修改
     */
    void passwd_edit(Long userid, EditPasswdReqmod editPasswdReqmod);

    /**
     * 邮箱修改
     */
    void mail_edit(Long userid, EditMailReqmod editMailReqmod);
}