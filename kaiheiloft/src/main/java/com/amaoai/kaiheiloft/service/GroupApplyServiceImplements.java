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
import com.amaoai.kaiheiloft.enties.GroupApply;
import com.amaoai.kaiheiloft.mapper.GroupApplyJoinMapper;
import com.amaoai.kaiheiloft.modobj.modx.GroupApplyModx;
import com.amaoai.kaiheiloft.system.KaiheiloftSystemConsts;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vincent Luo
 */
@Service
public class GroupApplyServiceImplements extends ServiceImpl<GroupApplyJoinMapper, GroupApply>
        implements GroupApplyService {

    @Override
    public GroupApply queryByApplyId(Long applyId) {
        return getById(applyId);
    }

    @Override
    public GroupApply queryApplyRequest(Long groupId, Long userId) {
        QueryWrapper<GroupApply> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId);
        wrapper.eq("user_id", userId);
        return getOne(wrapper);
    }

    @Override
    public void submit(GroupApplyModx groupApplyModx, Long userId, Long inviterId) {
        submit(groupApplyModx.getGroupId(), groupApplyModx.getRequestRemark(), userId, inviterId);
    }

    @Override
    public void submit(Long groupId, String requestRemark, Long userId, Long inviterId) {
        // 是否重复申请
        Assert.throwIfNotNull(queryApplyRequest(groupId, userId), "您已申请过加入该俱乐部，请勿重复申请");
        // 构建申请加入对象
        GroupApply groupApply = new GroupApply();
        groupApply.setGroupId(groupId);
        groupApply.setRequestRemark(requestRemark);
        groupApply.setUserId(userId);

        save(groupApply);
    }

    @Override
    public List<GroupApply> pendingRequests(Long groupId) {
        QueryWrapper<GroupApply> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId);
        wrapper.eq("allowed_status", KaiheiloftSystemConsts.GROUP_ALLOW_STATUS_WAIT);
        return list(wrapper);
    }

    @Override
    public void allow(GroupApply groupApply) {
        groupApply.setAllowedStatus(KaiheiloftSystemConsts.GROUP_ALLOW_STATUS_YES);
        updateById(groupApply);
    }

    @Override
    public void refuse(GroupApply groupApply) {
        groupApply.setAllowedStatus(KaiheiloftSystemConsts.GROUP_ALLOW_STATUS_NO);
        updateById(groupApply);
    }

}
