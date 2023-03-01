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
import com.amaoai.framework.Bits;
import com.amaoai.kaiheiloft.enties.GroupAdmin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amaoai.kaiheiloft.mapper.GroupAdminMapper;
import com.amaoai.kaiheiloft.system.KaiheiloftSystemConsts;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Luo
 */
@Service
public class GroupAdminServiceImplements extends ServiceImpl<GroupAdminMapper, GroupAdmin>
        implements GroupAdminService {

    /**
     * 表示成员
     */
    private static final int MEMBER_FLAG_BIT = 0x0001;

    /**
     * 表示管理员
     */
    private static final int ADMIN_FLAG_BIT = 0x0010;

    /**
     * 表示超级管理员
     */
    private static final int SUPER_ADMIN_FLAG_BIT = 0x0100;

    /**
     * 查询俱乐部管理员
     */
    private GroupAdmin queryGroupAdmin(Long groupId, Long userId) {
        return getOne(
                new LambdaQueryWrapper<GroupAdmin>()
                        .eq(GroupAdmin::getGroupId, groupId)
                        .eq(GroupAdmin::getUserId, userId)
        );
    }

    @Override
    public void addAdmin(Long groupId, Long userId) {
        addAnyAdmin(groupId, userId, KaiheiloftSystemConsts.DB_BOOL_OF_FALSE);
    }

    @Override
    public void addSuperAdmin(Long groupId, Long userId) {
        addAnyAdmin(groupId, userId, KaiheiloftSystemConsts.DB_BOOL_OF_TRUE);
    }

    @Override
    public int adminFlags(Long groupId, Long userId) {
        return adminFlags(queryGroupAdmin(groupId, userId));
    }

    /**
     * 使用标志位判断
     */
    private int adminFlags(GroupAdmin groupAdmin) {
        int bit = MEMBER_FLAG_BIT;

        if (groupAdmin == null)
            return bit;

        // 如果 groupAdmin 对象不为空，那么至少是个管理员
        bit |= ADMIN_FLAG_BIT;

        // 如果 superadmin 成员为 true，那么就代表是个超级管理员
        if (groupAdmin.getSuperadmin().equals(KaiheiloftSystemConsts.DB_BOOL_OF_TRUE))
            bit |= SUPER_ADMIN_FLAG_BIT;

        return bit;
    }

    private void addAnyAdmin(Long groupId, Long userId, String superadmin) {
        var groupAdmin = new GroupAdmin();
        groupAdmin.setGroupId(groupId);
        groupAdmin.setUserId(userId);
        groupAdmin.setSuperadmin(superadmin); // 创建人默认为超级管理员
        save(groupAdmin);
    }

    @Override
    public boolean isAdmin(Long groupId, Long userId) {
        return Bits.compare(adminFlags(groupId, userId) & ADMIN_FLAG_BIT);
    }

    @Override
    public boolean isAdmin(int flags) {
        return Bits.compare(flags & ADMIN_FLAG_BIT);
    }

    @Override
    public boolean isSuperAdmin(int flags) {
        return Bits.compare(flags & SUPER_ADMIN_FLAG_BIT);
    }

    @Override
    public boolean isSuperAdmin(Long groupId, Long userId) {
        return Bits.compare(adminFlags(groupId, userId) & SUPER_ADMIN_FLAG_BIT);
    }

    @Override
    public void removeAllAdmin(Long groupId) {
        remove(new UpdateWrapper<GroupAdmin>().eq("group_id", groupId));
    }

    @Override
    public void removeAdmin(Long groupId, Long userId) {
        remove(new UpdateWrapper<GroupAdmin>()
                .eq("group_id", groupId)
                .eq("user_id", userId)
        );
    }

    @Override
    public void transfer(Long groupId, Long srcSuperAdminId, Long destSuperAdminId) {
        var groupAdmin = queryGroupAdmin(groupId, srcSuperAdminId);
        Assert.throwIfFalse(Bits.compare(adminFlags(groupAdmin) & SUPER_ADMIN_FLAG_BIT), "用户非超级管理员，无转让权限");
        groupAdmin.setUserId(destSuperAdminId);
        updateById(groupAdmin);
    }
}
