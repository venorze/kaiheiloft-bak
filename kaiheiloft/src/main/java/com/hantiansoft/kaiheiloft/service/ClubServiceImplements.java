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
import com.hantiansoft.kaiheiloft.enties.Club;
import com.hantiansoft.kaiheiloft.enties.ClubAdmin;
import com.hantiansoft.kaiheiloft.mapper.ClubAdminMapper;
import com.hantiansoft.kaiheiloft.mapper.ClubAnnouncementMapper;
import com.hantiansoft.kaiheiloft.mapper.ClubMapper;
import com.hantiansoft.kaiheiloft.mapper.ClubMemberMapper;
import com.hantiansoft.kaiheiloft.modx.CreateClubModx;
import com.hantiansoft.kaiheiloft.modx.EditClubModx;
import com.hantiansoft.framework.BeanUtils;
import com.hantiansoft.framework.StringUtils;
import com.hantiansoft.kaiheiloft.system.KaiheiloftApplicationContext;
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
    private ClubMemberMapper clubMemberMapper;

    @Autowired
    private ClubAdminMapper clubAdminMapper;

    @Override
    public Club queryByClubId(Long clubId) {
        return null;
    }

    @Override
    @Transactional
    public void create(CreateClubModx createClubModx, Long userid) {
        // 创建俱乐部对象
        var club = BeanUtils.copyProperties(createClubModx, Club.class);
        club.setTags(StringUtils.listMerge(createClubModx.getTags(), " ")); // 添加俱乐部标签
        save(club);

        // 添加管理员表
        ClubAdmin clubAdmin = new ClubAdmin();
        clubAdmin.setClubId(club.getId());
        clubAdmin.setUserId(userid);
        clubAdmin.setSuperadmin(KaiheiloftApplicationContext.DB_BOOL_OF_TRUE); // 创建人默认为超级管理员
        clubAdminMapper.insert(clubAdmin);
    }

    @Override
    public void edit(EditClubModx editClubModx) {

    }

    @Override
    public void disband(Long clubId) {

    }

    @Override
    public void join(Long userId, Long clubId) {

    }

    @Override
    public void kick(Long userId, Long clubId) {

    }

    @Override
    public void quit(Long userId, Long clubId) {

    }

    @Override
    public void publishAnnouncement(Long clubId, String announcement) {

    }

}
