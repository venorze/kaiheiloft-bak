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

/* Creates on 2023/2/5. */

import com.amaoai.framework.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amaoai.kaiheiloft.enties.GroupInvite;
import com.amaoai.kaiheiloft.mapper.GroupInviteMapper;
import com.amaoai.kaiheiloft.modobj.modv.InviteModv;
import com.amaoai.kaiheiloft.system.KaiheiloftApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vincent Luo
 */
@Service
public class GroupInviteServiceImplements extends ServiceImpl<GroupInviteMapper, GroupInvite>
        implements GroupInviteService {

    @Override
    public GroupInvite queryUserInvite(Long inviteId, Long userId) {
        // 查询邀请
        var groupInvite = getOne(
                new LambdaQueryWrapper<GroupInvite>()
                        .eq(GroupInvite::getId, inviteId)
                        .eq(GroupInvite::getUserId, userId)
        );

        Assert.throwIfNull(groupInvite, "邀请不存在");
        return groupInvite;
    }

    @Override
    public void invite(Long groupId, Long userId, Long inviterId) {
        var groupInvite = new GroupInvite();
        groupInvite.setGroupId(groupId);
        groupInvite.setUserId(userId);
        groupInvite.setInviterId(inviterId);
        save(groupInvite);
    }

    @Override
    public List<InviteModv> queryInvitesByUserId(Long userId) {
        return baseMapper.queryInvitesByUserId(userId);
    }

    @Override
    public void allow(Long inviteId) {
        var invite = getById(inviteId);
        invite.setAllowedStatus(KaiheiloftApplicationContext.CLUB_AGREE_STATUS_YES);
        updateById(invite);
    }

    @Override
    public void refuse(Long inviteId) {
        var invite = getById(inviteId);
        invite.setAllowedStatus(KaiheiloftApplicationContext.CLUB_AGREE_STATUS_NO);
        updateById(invite);
    }
}
