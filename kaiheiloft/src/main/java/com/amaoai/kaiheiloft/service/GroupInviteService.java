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

import com.baomidou.mybatisplus.extension.service.IService;
import com.amaoai.kaiheiloft.enties.GroupInvite;
import com.amaoai.kaiheiloft.modobj.modv.InviteModv;

import java.util.List;

/**
 * @author Vincent Luo
 */
public interface GroupInviteService extends IService<GroupInvite> {

    /**
     * 查询用户邀请列表
     */
    GroupInvite queryInvite(Long inviteId, Long userId);

    /**
     * 查询等待处理的邀请数据
     */
    GroupInvite queryWaitAllowInvite(Long groupId, Long userId, Long inviterId);

    /**
     * 邀请新成员加入
     */
    void invite(Long groupId, Long userId, Long inviterId);

    /**
     * 查询用户邀请列表
     */
    List<InviteModv> queryInvitesByUserId(Long userId);

    /**
     * 同意邀请
     */
    void allow(Long inviteId);

    /**
     * 拒绝邀请
     */
    void refuse(Long inviteId);
}
