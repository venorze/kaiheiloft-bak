package com.amaoai.export.kaiheiloft.modx;

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

/* Creates on 2023/1/18. */

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户登录模块
 *
 * @author Vincent Luo
 */
@Data
public class UserSign {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名是必填项")
    @Length(min = 6, max = 16, message = "用户长度需要 6 - 16 位字母或数字组成")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码是必填项")
    @Length(min = 8, max = 32, message = "用户密码长度为 8 - 16 位字母、数字、符号组成")
    private String password;

}
