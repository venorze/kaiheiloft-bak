package com.amaoai.kaiheiloft.enties;

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

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户信息表
 *
 * @author Vincent Luo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("khl_user_info")
public class User extends SuperModel<User> {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 自我介绍
     */
    private String bio;

    /**
     * 性别
     */
    private String gender;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 电子邮箱
     */
    private String email;

}
