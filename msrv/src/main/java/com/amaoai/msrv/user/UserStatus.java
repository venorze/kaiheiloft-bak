package com.amaoai.msrv.user;

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

/* Creates on 2023/2/28. */

import com.amaoai.framework.time.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * 用户状态
 *
 * @author Vincent Luo
 */
@Data
public class UserStatus {

    /**
     * 用户名
     */
    private String username;

    /**
     * 上线时间
     */
    private Date onlineTime = DateUtils.currentDate();

    /**
     * 状态信息，0离线，1在线
     */
    private Integer status;

    public static final int USER_STATUS_ONLINE = 1;
    public static final int USER_STATUS_OFFLINE = 0;

    public UserStatus(String username, Integer status) {
        this.username = username;
        this.status = status;
    }

}
