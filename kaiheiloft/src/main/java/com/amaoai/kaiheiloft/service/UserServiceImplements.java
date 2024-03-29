package com.amaoai.kaiheiloft.service;

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

import com.amaoai.framework.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amaoai.kaiheiloft.enties.User;
import com.amaoai.kaiheiloft.mapper.UserMapper;
import com.amaoai.kaiheiloft.modobj.modx.EditMailModx;
import com.amaoai.kaiheiloft.modobj.modx.EditPasswordModx;
import com.amaoai.kaiheiloft.modobj.modx.UserProfileModx;
import com.amaoai.kaiheiloft.modobj.modx.UserSignUpModx;
import com.amaoai.framework.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Luo
 */
@Service
public class UserServiceImplements extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User queryByUserId(Long userId) {
        // 根据ID查询用户信息
        return getOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getId, userId)
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
        Assert.throwIfFalse(queryByUsername(userSignUpModx.getUsername()) == null, "当前用户名已被注册");
        // 注册成功
        save(BeanUtils.copyProperties(userSignUpModx, User.class));
    }

    @Override
    public UserProfileModx profile(String username) {
        // 根据ID查询用户信息
        var user = queryByUsername(username);
        Assert.throwIfNull(user, "用户不存在");
        return BeanUtils.copyProperties(user, UserProfileModx.class);
    }

    @Override
    public void editProfile(Long userId, UserProfileModx userProfileModx) {
        User user = queryByUserId(userId);
        BeanUtils.copyProperties(userProfileModx, user);
        // 更新用户信息
        updateById(user);
    }

    @Override
    public void editPassword(Long userId, EditPasswordModx editPasswordModx) {

    }

    @Override
    public void editMail(Long userId, EditMailModx editMailModx) {

    }
}