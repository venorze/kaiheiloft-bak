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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hantiansoft.kaiheiloft.enties.Club;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 俱乐部全量信息对象
 *
 * @author Vincent Luo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClubCompleteObject extends Club {

    /**
     * 俱乐部频道
     */
    @Data
    public static class Channel {
        // 频道ID
        private Long id;
        // 频道名称
        private String name;
    }

    /**
     * 更新时间（这个字段不需要json序列化）
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 俱乐部频道列表
     */
    private List<Channel> channels;

    /**
     * 俱乐部成员列表（分页数据）
     */
    private IPage<MemberCompleteObject> members;

}
