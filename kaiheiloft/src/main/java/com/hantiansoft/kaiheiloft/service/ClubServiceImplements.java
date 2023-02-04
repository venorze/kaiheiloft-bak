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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hantiansoft.framework.Asserts;
import com.hantiansoft.framework.BeanUtils;
import com.hantiansoft.kaiheiloft.enties.Club;
import com.hantiansoft.kaiheiloft.mapper.ClubAnnouncementMapper;
import com.hantiansoft.kaiheiloft.mapper.ClubMapper;
import com.hantiansoft.kaiheiloft.modx.CreateClubModx;
import com.hantiansoft.kaiheiloft.modx.EditClubModx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vincent Luo
 */
@Service
public class ClubServiceImplements extends ServiceImpl<ClubMapper, Club> implements ClubService {

    @Autowired
    private ClubAnnouncementMapper clubAnnouncementMapper;

    @Autowired
    private ClubMemberService clubMemberService;

    @Autowired
    private ClubAdminService clubAdminService;

    @Override
    public Club queryByClubId(Long clubId) {
        return null;
    }

    @Override
    @Transactional
    public void create(CreateClubModx createClubModx, Long operatorId) {
        // 创建俱乐部对象
        var club = BeanUtils.copyProperties(createClubModx, Club.class);
        save(club);

        // 添加成员表
        clubMemberService.addMember(club.getId(), operatorId);

        // 添加管理员表
        clubAdminService.addSuperAdmin(club.getId(), operatorId);
    }

    @Override
    public void edit(EditClubModx editClubModx, Long operatorId) {
        Club club = getById(editClubModx.getId());
        Asserts.throwIfNull(club, "俱乐部不存在");

        // 管理员有权限修改
        Asserts.throwIfBool(clubAdminService.isAdmin(editClubModx.getId(), operatorId), "用户无权限");
        BeanUtils.copyProperties(editClubModx, club);
        updateById(club);
    }

    @Override
    public void disband(Long clubId, Long operatorId) {

    }

    @Override
    public void join(Long clubId, Long userId, Long inviteId) {

    }

    @Override
    public void kick(Long clubId, Long userId, Long operatorId) {

    }

    @Override
    public void quit(Long clubId, Long userId) {

    }

    @Override
    public void publishAnnouncement(Long clubId, String announcement, Long operatorId) {

    }

}
