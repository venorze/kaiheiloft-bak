package com.hantiansoft.kaiheiloft.service;

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
import com.hantiansoft.kaiheiloft.enties.Club;
import com.hantiansoft.kaiheiloft.modx.CreateClubModx;
import com.hantiansoft.kaiheiloft.modx.EditClubModx;

/**
 * @author Vincent Luo
 */
public interface ClubService extends IService<Club> {

    /**
     * 通过ID查询俱乐部对象
     */
    Club queryByClubId(Long clubId);

    /**
     * 创建俱乐部
     */
    void create(CreateClubModx createClubModx, Long userid);

    /**
     * 编辑俱乐部信息
     */
    void edit(EditClubModx editClubModx);

    /**
     * 解散俱乐部
     */
    void disband(Long clubId);

    /**
     * 加入俱乐部
     */
    void join(Long userId, Long clubId);

    /**
     * 踢出俱乐部
     */
    void kick(Long userId, Long clubId);

    /**
     * 退出俱乐部
     */
    void quit(Long userId, Long clubId);

    /**
     * 发布俱乐部公告
     */
    void publishAnnouncement(Long clubId, String announcement);

}
