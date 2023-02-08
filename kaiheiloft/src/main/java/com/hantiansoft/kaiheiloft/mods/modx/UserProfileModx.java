package com.hantiansoft.kaiheiloft.mods.modx;

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

/* Creates on 2023/1/10. */

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author Vincent Luo
 */
@Data
public class UserProfileModx {

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 16, message = "用户昵称超出规定长度")
    private String nickname;

    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像不能为空")
    private String avatar;

    /**
     * 用户生日
     */
    @NotNull(message = "用户生日不能为空")
    private Date birthday;

    /**
     * 自我介绍
     */
    private String bio;

    /**
     * 用户性别, M 男， W 女
     */
    @Pattern(regexp = "^|M|W|$", message = "用户性别不正确")
    private String gender;

}
