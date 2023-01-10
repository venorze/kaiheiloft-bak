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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hantiansoft.distance.enties.User;
import com.hantiansoft.distance.mapper.UserMapper;
import com.hantiansoft.distance.reqmod.EditMailReqmod;
import com.hantiansoft.distance.reqmod.EditPasswdReqmod;
import com.hantiansoft.distance.reqmod.UserSignUpReqmod;
import com.hantiansoft.distance.model.UserProfile;
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
    public User of_user_id(Long userid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userid);

        // 根据ID查询用户信息
        User user = getOne(wrapper);
        Asserts.throwIfNull(user, "用户不存在");

        return user;
    }

    @Override
    public void sign_up(UserSignUpReqmod userSignUpReqmod) {
        // 属性拷贝
        User user = BeanUtils.copyProperties(userSignUpReqmod, User.class);
        user.setId(snowflakeGenerator.nextId());

        // 注册成功
        save(user);
    }

    @Override
    public UserProfile profile(Long userid) {
        // 根据ID查询用户信息
        return BeanUtils.copyProperties(of_user_id(userid), UserProfile.class);
    }

    @Override
    public void profile_edit(Long userid, UserProfile userProfile) {
        User user = of_user_id(userid);
        BeanUtils.copyProperties(userProfile, user);
        // 更新用户信息
        updateById(user);
    }

    @Override
    public void passwd_edit(Long userid, EditPasswdReqmod editPasswdReqmod) {

    }

    @Override
    public void mail_edit(Long userid, EditMailReqmod editMailReqmod) {

    }
}