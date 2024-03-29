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

/**
 * 俱乐部公告表
 *
 * @author Vincent Luo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("khl_group_announcement")
public class GroupAnnouncement extends SuperModel<GroupAnnouncement> {

    /**
     * 俱乐部ID
     */
    private String groupId;

    /**
     * 公告内容
     */
    private String content;

}
