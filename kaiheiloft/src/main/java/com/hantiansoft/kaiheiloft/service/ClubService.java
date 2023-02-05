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

/* Creates on 2023/1/13. */

import com.baomidou.mybatisplus.extension.service.IService;
import com.hantiansoft.kaiheiloft.enties.Club;
import com.hantiansoft.kaiheiloft.fullobj.ClubCompleteObject;
import com.hantiansoft.kaiheiloft.modx.ClubApplyJoinModx;
import com.hantiansoft.kaiheiloft.modx.CreateClubModx;
import com.hantiansoft.kaiheiloft.modx.EditClubModx;
import com.hantiansoft.kaiheiloft.modx.InviteModv;

import java.util.List;

/**
 * @author Vincent Luo
 */
public interface ClubService extends IService<Club> {

    /**
     * 通过ID查询俱乐部对象
     */
    Club queryByClubId(Long clubId);

    /**
     * 查询俱乐部全量信息
     */
    ClubCompleteObject queryCompleteClub(Long clubId);

    /**
     * 判断俱乐部是否存在
     */
    boolean hasClub(Long clubId);

    /**
     * 获取用户加入的俱乐部列表
     */
    List<Club> queryClubsByUserId(Long userId);

    /**
     * 创建俱乐部
     */
    Long create(CreateClubModx createClubModx, Long operatorId);

    /**
     * 编辑俱乐部信息
     */
    void edit(EditClubModx editClubModx, Long operatorId);

    /**
     * 解散俱乐部
     */
    void disband(Long clubId, Long operatorId);

    /**
     * 邀请加入俱乐部
     */
    void invite(Long clubId, Long userId, Long inviterId);

    /**
     * 申请加入俱乐部
     */
    void join(ClubApplyJoinModx clubApplyJoinModx, Long userId);

    /**
     * 同意新成员吧加入俱乐部
     *
     * @param applyId 申请ID
     */
    void agreeJoin(Long applyId, Long operatorId);

    /**
     * 拒绝新成员吧加入俱乐部
     *
     * @param applyId 申请ID
     */
    void refuseJoin(Long applyId, String reason, Long operatorId);

    /**
     * 踢出俱乐部
     */
    void kick(Long clubId, Long userId, Long operatorId);

    /**
     * 退出俱乐部
     */
    void quit(Long clubId, Long userId);

    /**
     * 转让俱乐部超级管理员
     */
    void transfer(Long clubId, Long srcSuperAdminId, Long destSuperAdminId);

    /**
     * 发布俱乐部公告
     */
    void publishAnnouncement(Long clubId, String announcement, Long operatorId);

    /**
     * 查询用户邀请列表
     */
    List<InviteModv> queryInvites(Long userId);

    /**
     * 同意邀请
     */
    void agreeInvite(Long inviteId, Long userId, Long operatorId);

    /**
     * 拒绝邀请
     */
    void refuseInvite(Long inviteId, Long userId, Long operatorId);

    /**
     * 创建俱乐部频道
     */
    void createChannel(Long clubId, String channelName);

}
