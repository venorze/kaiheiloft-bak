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

/* Creates on 2023/2/4. */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hantiansoft.framework.Asserts;
import com.hantiansoft.kaiheiloft.enties.ClubMember;
import com.hantiansoft.kaiheiloft.mapper.ClubMemberMapper;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Luo
 */
@Service
public class ClubMemberServiceImplements extends ServiceImpl<ClubMemberMapper, ClubMember>
        implements ClubMemberService {

    @Override
    public ClubMember queryMember(Long clubId, Long userId) {
        return getOne(new LambdaQueryWrapper<ClubMember>()
                .eq(ClubMember::getClubId, clubId)
                .eq(ClubMember::getUserId, userId)
        );
    }

    @Override
    public void addMember(Long clubId, Long userId) {
        Asserts.throwIfBool(!hasMember(clubId, userId), "用户已经在俱乐部内了");
        var clubMember = new ClubMember();
        clubMember.setClubId(clubId);
        clubMember.setUserId(userId);
        save(clubMember);
    }

    @Override
    public void removeAllMember(Long clubId) {
        remove(new LambdaUpdateWrapper<ClubMember>().eq(ClubMember::getClubId, clubId));
    }

    @Override
    public void removeMember(Long clubId, Long userId) {
        remove(new LambdaUpdateWrapper<ClubMember>()
                .eq(ClubMember::getClubId, clubId)
                .eq(ClubMember::getUserId, userId)
        );
    }

    @Override
    public boolean hasMember(Long clubId, Long userId) {
        return queryMember(clubId, userId) != null;
    }

}
