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
import com.hantiansoft.distance.mod.EditMailMod;
import com.hantiansoft.distance.mod.EditPasswdMod;
import com.hantiansoft.distance.mod.UserSignUpMod;
import com.hantiansoft.distance.mod.UserProfileMod;

/**
 * qcx 前缀为 QueryCurrentServiceObject 简写，qc，x代表未知，也就是查询参数。
 * 理解为 Query_Current_Service_Object_By_XXXX。
 *
 * qcxe 前缀前面的 qcx 和上面介绍一样，e表示异常，如果查询为空就会抛出异常。所以查询当前对象如果为空
 * 或其他会抛异常，就使用 qcxe 作为查询前缀。
 *
 * @author Vincent Luo
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户ID查询用户
     */
    User qcxe_user_id(Long userid);

    /**
     * 根据用户名查询用户
     */
    User qcx_user_name(String username);

    /**
     * 用户注册
     */
    void sign_up(UserSignUpMod userSignUpMod);

    /**
     * 个人信息获取
     */
    UserProfileMod profile(Long userid);

    /**
     * 个人信息修改
     */
    void profile_edit(Long userid, UserProfileMod userProfileMod);

    /**
     * 密码修改
     */
    void passwd_edit(Long userid, EditPasswdMod editPasswdMod);

    /**
     * 邮箱修改
     */
    void mail_edit(Long userid, EditMailMod editMailMod);
}
