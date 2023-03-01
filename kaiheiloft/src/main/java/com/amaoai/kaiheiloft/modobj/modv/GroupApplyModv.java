package com.amaoai.kaiheiloft.modobj.modv;

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

/* Creates on 2023/3/1. */

import lombok.Data;

/**
 * @author Vincent Luo
 */
@Data
public class GroupApplyModv {

    /**
     * 申请ID
     */
    private Long applyId;

    /**
     * 俱乐部名称
     */
    private String groupName;

    /**
     * 俱乐部头像
     */
    private String groupAvatar;

    /**
     * 申请用户
     */
    private String userNickname;

    /**
     * 申请备注
     */
    private String requestRemark;

    /**
     * 是否同意
     */
    private String allowedStatus;

}
