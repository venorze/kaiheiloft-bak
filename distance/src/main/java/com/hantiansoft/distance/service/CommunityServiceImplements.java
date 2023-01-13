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
import com.hantiansoft.distance.mod.CreateCommunityMod;
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
    public void create(CreateCommunityMod createCommunityMod) {
    }

}
