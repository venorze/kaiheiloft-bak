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

/* Creates on 2023/2/4. */

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 俱乐部成员邀请新成员加入表
 *
 * @author Vincent Luo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("khl_group_invite")
public class GroupInvite extends SuperModel<GroupInvite> {

    /**
     * 俱乐部ID
     */
    private Long groupId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 邀请人ID
     */
    private Long inviterId;

    /**
     * 是否同意加入
     */
    private String allowedStatus;

}
