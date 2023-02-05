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

/* Creates on 2023/2/5. */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hantiansoft.kaiheiloft.enties.ClubInvite;
import com.hantiansoft.kaiheiloft.mapper.ClubInviteMapper;
import com.hantiansoft.kaiheiloft.modx.InviteModv;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vincent Luo
 */
@Service
public class ClubInviteServiceImplements extends ServiceImpl<ClubInviteMapper, ClubInvite>
        implements ClubInviteService {

    @Override
    public void invite(Long clubId, Long userId, Long inviterId) {
        var clubInvite = new ClubInvite();
        clubInvite.setClubId(clubId);
        clubInvite.setUserId(userId);
        clubInvite.setInviterId(inviterId);
        save(clubInvite);
    }

    @Override
    public List<InviteModv> queryInvitesByUserId(Long userId) {
        return baseMapper.queryInvitesByUserId(userId);
    }

}
