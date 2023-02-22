package com.amaoai.kaiheiloft.service;

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

import devtools.framework.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amaoai.kaiheiloft.enties.ClubMember;
import com.amaoai.kaiheiloft.mods.modv.ClubMemberInfoModv;
import com.amaoai.kaiheiloft.mapper.ClubMemberMapper;
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
        Assert.throwIfBool(!hasMember(clubId, userId), "用户已经在俱乐部内了");
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

    @Override
    public IPage<ClubMemberInfoModv> queryPageMember(Long clubId, int pageNo, int pageSize) {
        return baseMapper.queryPageMember(new Page<>(pageNo, pageSize), clubId);
    }
}
