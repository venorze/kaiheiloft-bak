package com.hantiansoft.kaiheiloft.service;

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
import com.hantiansoft.kaiheiloft.enties.User;
import com.hantiansoft.kaiheiloft.mapper.UserMapper;
import com.hantiansoft.kaiheiloft.modx.EditMailModx;
import com.hantiansoft.kaiheiloft.modx.EditPasswordModx;
import com.hantiansoft.kaiheiloft.modx.UserProfileModx;
import com.hantiansoft.kaiheiloft.modx.UserSignUpModx;
import com.hantiansoft.framework.Asserts;
import com.hantiansoft.framework.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Luo
 */
@Service
public class UserServiceImplements extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User queryByUserId(Long userid) {
        // 根据ID查询用户信息
        return getOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getId, userid)
        );
    }

    @Override
    public User queryByUsername(String username) {
        // 根据用户名查询用户信息
        return getOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );
    }

    @Override
    public User login(String username, String password) {
        return getOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
                        .eq(User::getPassword, password)
        );
    }

    @Override
    public void sign_up(UserSignUpModx userSignUpModx) {
        // 判断用户名是否已被注册
        Asserts.throwIfBool(queryByUsername(userSignUpModx.getUsername()) == null, "当前用户名已被注册");
        // 注册成功
        save(BeanUtils.copyProperties(userSignUpModx, User.class));
    }

    @Override
    public UserProfileModx profile(String username) {
        // 根据ID查询用户信息
        var user = queryByUsername(username);
        Asserts.throwIfNull(user, "用户不存在");
        return BeanUtils.copyProperties(user, UserProfileModx.class);
    }

    @Override
    public void editProfile(Long userid, UserProfileModx userProfileModx) {
        User user = queryByUserId(userid);
        BeanUtils.copyProperties(userProfileModx, user);
        // 更新用户信息
        updateById(user);
    }

    @Override
    public void editPassword(Long userid, EditPasswordModx editPasswordModx) {

    }

    @Override
    public void editMail(Long userid, EditMailModx editMailModx) {

    }
}