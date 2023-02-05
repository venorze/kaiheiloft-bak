package com.hantiansoft.kaiheiloft.fullobj;

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

/* Creates on 2023/2/6. */

import lombok.Data;

/**
 * @author Vincent Luo
 */
@Data
public class MemberCompleteObject {

    /**
     * 成员ID
     */
    private Long id;

    /**
     * 成员用户名
     */
    private String username;

    /**
     * 成员昵称
     */
    private String nickname;

    /**
     * 成员头像
     */
    private String avatar;

    /**
     * 是否是管理员, Y超级管理员，N普通管理员，M普通成员
     */
    private String superadmin;

}
