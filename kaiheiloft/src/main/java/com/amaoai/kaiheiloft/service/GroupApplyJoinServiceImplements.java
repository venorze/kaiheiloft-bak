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

import com.amaoai.framework.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amaoai.kaiheiloft.enties.GroupApplyJoin;
import com.amaoai.kaiheiloft.mapper.GroupApplyJoinMapper;
import com.amaoai.kaiheiloft.mods.modx.GroupApplyJoinModx;
import com.amaoai.kaiheiloft.system.KaiheiloftApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vincent Luo
 */
@Service
public class GroupApplyJoinServiceImplements extends ServiceImpl<GroupApplyJoinMapper, GroupApplyJoin>
        implements GroupApplyJoinService {

    @Override
    public GroupApplyJoin queryByApplyId(Long applyId) {
        return getById(applyId);
    }

    @Override
    public GroupApplyJoin queryJoinRequest(Long groupId, Long userId) {
        QueryWrapper<GroupApplyJoin> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId);
        wrapper.eq("user_id", userId);
        return getOne(wrapper);
    }

    @Override
    public void submit(GroupApplyJoinModx groupApplyJoinModx, Long userId, Long inviterId) {
        submit(groupApplyJoinModx.getGroupId(), groupApplyJoinModx.getRequestRemark(), userId, inviterId);
    }

    @Override
    public void submit(Long groupId, String requestRemark, Long userId, Long inviterId) {
        // 是否重复申请
        Assert.throwIfNotNull(queryJoinRequest(groupId, userId), "您已申请过加入该俱乐部，请勿重复申请");
        // 构建申请加入对象
        GroupApplyJoin groupApplyJoin = new GroupApplyJoin();
        groupApplyJoin.setGroupId(groupId);
        groupApplyJoin.setRequestRemark(requestRemark);
        groupApplyJoin.setUserId(userId);

        save(groupApplyJoin);
    }

    @Override
    public List<GroupApplyJoin> pendingRequests(Long groupId) {
        QueryWrapper<GroupApplyJoin> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId);
        wrapper.eq("agree_status", KaiheiloftApplicationContext.CLUB_AGREE_STATUS_WAIT);
        return list(wrapper);
    }

    @Override
    public void agree(GroupApplyJoin groupApply) {
        groupApply.setAgreeStatus(KaiheiloftApplicationContext.CLUB_AGREE_STATUS_YES);
        updateById(groupApply);
    }

    @Override
    public void refuse(GroupApplyJoin groupApply) {
        groupApply.setAgreeStatus(KaiheiloftApplicationContext.CLUB_AGREE_STATUS_NO);
        updateById(groupApply);
    }

}
