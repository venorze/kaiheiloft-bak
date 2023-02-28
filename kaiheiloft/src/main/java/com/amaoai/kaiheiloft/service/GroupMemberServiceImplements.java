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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amaoai.kaiheiloft.enties.GroupMember;
import com.amaoai.kaiheiloft.mods.modv.GroupMemberInfoModv;
import com.amaoai.kaiheiloft.mapper.GroupMemberMapper;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Luo
 */
@Service
public class GroupMemberServiceImplements extends ServiceImpl<GroupMemberMapper, GroupMember>
        implements GroupMemberService {

    @Override
    public GroupMember queryMember(Long groupId, Long userId) {
        return getOne(new LambdaQueryWrapper<GroupMember>()
                .eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getUserId, userId)
        );
    }

    @Override
    public void addMember(Long groupId, Long userId) {
        Assert.throwIfBool(!hasMember(groupId, userId), "用户已经在俱乐部内了");
        var groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setUserId(userId);
        save(groupMember);
    }

    @Override
    public void removeAllMember(Long groupId) {
        remove(new LambdaUpdateWrapper<GroupMember>().eq(GroupMember::getGroupId, groupId));
    }

    @Override
    public void removeMember(Long groupId, Long userId) {
        remove(new LambdaUpdateWrapper<GroupMember>()
                .eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getUserId, userId)
        );
    }

    @Override
    public boolean hasMember(Long groupId, Long userId) {
        return queryMember(groupId, userId) != null;
    }

    @Override
    public IPage<GroupMemberInfoModv> queryPageMember(Long groupId, int pageNo, int pageSize) {
        return baseMapper.queryPageMember(new Page<>(pageNo, pageSize), groupId);
    }
}
