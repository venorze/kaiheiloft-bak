package com.amaoai.kaiheiloft.modobj.modx;

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

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Date;

/**
 * 用户注册请求体
 *
 * @author Vincent Luo
 */
@Data
public class UserSignUpModx {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名是必填项")
    @Length(min = 6, max = 16, message = "用户长度需要 6 - 16 位字母或数字组成")
    private String username;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称是必填项")
    @Length(max = 16, message = "用户昵称超出规定长度")
    private String nickname;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码是必填项")
    @Length(min = 8, max = 32, message = "用户密码长度为 8 - 16 位字母、数字、符号组成")
    private String password;

    /**
     * 用户生日
     */
    @NotNull(message = "用户生日是必填项")
    private Date birthday;

    /**
     * 用户性别, M 男， W 女
     */
    @Pattern(regexp = "^|M|W|$", message = "用户性别不正确")
    private String gender;

}
