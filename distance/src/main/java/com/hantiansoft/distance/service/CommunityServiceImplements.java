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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hantiansoft.distance.enties.Community;
import com.hantiansoft.distance.mapper.CommunityAnnouncementMapper;
import com.hantiansoft.distance.mapper.CommunityMapper;
import com.hantiansoft.distance.mapper.CommunityMemberMapper;
import com.hantiansoft.distance.modx.CreateCommunityModx;
import com.hantiansoft.distance.modx.EditCommunityModx;
import com.hantiansoft.framework.BeanUtils;
import com.hantiansoft.framework.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Luo
 */
@Service
public class CommunityServiceImplements extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Autowired
    private CommunityAnnouncementMapper communityAnnouncementMapper;

    @Autowired
    private CommunityMemberMapper communityMemberMapper;

    @Override
    public Community queryByCommunityId(Long communityId) {
        return null;
    }

    @Override
    public void create(CreateCommunityModx createCommunityModx) {
        // 创建社区对象
        var community = BeanUtils.copyProperties(createCommunityModx, Community.class);
        community.setTags(StringUtils.listMerge(createCommunityModx.getTags(), " ")); // 添加社区标签

        save(community);
    }

    @Override
    public void edit(EditCommunityModx editCommunityModx) {

    }

    @Override
    public void disband(Long communityId) {

    }

    @Override
    public void join(Long userId, Long communityId) {

    }

    @Override
    public void kick(Long userId, Long communityId) {

    }

    @Override
    public void quit(Long userId, Long communityId) {

    }

    @Override
    public void publishAnnouncement(Long communityId, String announcement) {

    }

}