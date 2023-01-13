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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hantiansoft.distance.enties.User;
import com.hantiansoft.distance.mapper.UserMapper;
import com.hantiansoft.distance.mod.EditMailMod;
import com.hantiansoft.distance.mod.EditPasswdMod;
import com.hantiansoft.distance.mod.UserProfileMod;
import com.hantiansoft.distance.mod.UserSignUpMod;
import com.hantiansoft.framework.Asserts;
import com.hantiansoft.framework.BeanUtils;
import com.hantiansoft.framework.generators.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Luo
 */
@Service
public class UserServiceImplements extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private SnowflakeGenerator snowflakeGenerator;

    @Override
    public User qcxe_user_id(Long userid) {
        var wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getId, userid);

        // 根据ID查询用户信息
        var user = getOne(wrapper);
        Asserts.throwIfNull(user, "用户不存在");

        return user;
    }

    @Override
    public User qcx_user_name(String username) {
        return getOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );
    }

    @Override
    public void sign_up(UserSignUpMod userSignUpMod) {
        // 判断用户名是否已被注册
        Asserts.throwIfBool(qcx_user_name(userSignUpMod.getUsername()) == null, "当前用户名已被注册");
        // 注册成功
        save(BeanUtils.copyProperties(userSignUpMod, User.class));
    }

    @Override
    public UserProfileMod profile(Long userid) {
        // 根据ID查询用户信息
        return BeanUtils.copyProperties(qcxe_user_id(userid), UserProfileMod.class);
    }

    @Override
    public void profile_edit(Long userid, UserProfileMod userProfileMod) {
        User user = qcxe_user_id(userid);
        BeanUtils.copyProperties(userProfileMod, user);
        // 更新用户信息
        updateById(user);
    }

    @Override
    public void passwd_edit(Long userid, EditPasswdMod editPasswdMod) {

    }

    @Override
    public void mail_edit(Long userid, EditMailMod editMailMod) {

    }
}