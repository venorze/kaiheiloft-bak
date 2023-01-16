package com.hantiansoft.distance.service;

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

/* Creates on 2023/1/13. */

import com.baomidou.mybatisplus.extension.service.IService;
import com.hantiansoft.distance.enties.Community;
import com.hantiansoft.distance.mod.CreateCommunityMod;
import com.hantiansoft.distance.mod.EditCommunityMod;

/**
 * @author Vincent Luo
 */
public interface CommunityService extends IService<Community> {

    /**
     * 通过ID查询社区对象
     */
    Community queryByCommunityId(Long communityId);

    /**
     * 创建社区
     */
    void create(CreateCommunityMod createCommunityMod);

    /**
     * 编辑社区信息
     */
    void edit(EditCommunityMod editCommunityMod);

    /**
     * 解散社区
     */
    void disband(Long communityId);

    /**
     * 加入社区
     */
    void join(Long userId, Long communityId);

    /**
     * 踢出社区
     */
    void kick(Long userId, Long communityId);

    /**
     * 退出社区
     */
    void quit(Long userId, Long communityId);

    /**
     * 发布社区公告
     */
    void publish_announcement(Long communityId, String announcement);

}
