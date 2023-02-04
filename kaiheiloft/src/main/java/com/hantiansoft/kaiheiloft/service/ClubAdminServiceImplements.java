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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hantiansoft.framework.Bits;
import com.hantiansoft.kaiheiloft.enties.ClubAdmin;
import com.hantiansoft.kaiheiloft.mapper.ClubAdminMapper;
import com.hantiansoft.kaiheiloft.system.KaiheiloftApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Luo
 */
@Service
public class ClubAdminServiceImplements extends ServiceImpl<ClubAdminMapper, ClubAdmin>
        implements ClubAdminService {

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
    private ClubAdmin queryClubAdmin(Long clubId, Long userId) {
        return getOne(
                new LambdaQueryWrapper<ClubAdmin>()
                        .eq(ClubAdmin::getClubId, clubId)
                        .eq(ClubAdmin::getUserId, userId)
        );
    }

    @Override
    public void addAdmin(Long clubId, Long userId) {
        addAnyAdmin(clubId, userId, KaiheiloftApplicationContext.DB_BOOL_OF_FALSE);
    }

    @Override
    public void addSuperAdmin(Long clubId, Long userId) {
        addAnyAdmin(clubId, userId, KaiheiloftApplicationContext.DB_BOOL_OF_TRUE);
    }

    private void addAnyAdmin(Long clubId, Long userId, String superadmin) {
        var clubAdmin = new ClubAdmin();
        clubAdmin.setClubId(clubId);
        clubAdmin.setUserId(userId);
        clubAdmin.setSuperadmin(superadmin); // 创建人默认为超级管理员
        save(clubAdmin);
    }

    @Override
    public boolean isAdmin(Long clubId, Long userId) {
        return Bits.compare(adminFlag(clubId, userId) & ADMIN_FLAG_BIT);
    }

    @Override
    public boolean isSuperAdmin(Long clubId, Long userId) {
        return Bits.compare(adminFlag(clubId, userId) & SUPER_ADMIN_FLAG_BIT);
    }

    /**
     * 使用标志位判断
     */
    private int adminFlag(Long clubId, Long userId) {
        int bit = MEMBER_FLAG_BIT;

        ClubAdmin clubAdmin = queryClubAdmin(clubId, userId);
        if (clubAdmin == null)
            return bit;

        // 如果 clubAdmin 对象不为空，那么至少是个管理员
        bit |= ADMIN_FLAG_BIT;

        // 如果 superadmin 成员为 true，那么就代表是个超级管理员
        if (clubAdmin.getSuperadmin().equals(KaiheiloftApplicationContext.DB_BOOL_OF_TRUE))
            bit |= SUPER_ADMIN_FLAG_BIT;

        return bit;
    }

    @Override
    public void removeAllAdmin(Long clubId) {
        UpdateWrapper<ClubAdmin> wrapper = new UpdateWrapper<>();
        wrapper.eq("club_id", clubId);
        remove(wrapper);
    }

}
