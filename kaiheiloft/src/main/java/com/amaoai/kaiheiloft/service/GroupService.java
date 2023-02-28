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

/* Creates on 2023/1/13. */

import com.baomidou.mybatisplus.extension.service.IService;
import com.amaoai.kaiheiloft.enties.Group;
import com.amaoai.kaiheiloft.modobj.modv.GroupInfoModv;
import com.amaoai.kaiheiloft.modobj.modx.GroupApplyModx;
import com.amaoai.kaiheiloft.modobj.modx.CreateGroupModx;
import com.amaoai.kaiheiloft.modobj.modx.EditGroupModx;
import com.amaoai.kaiheiloft.modobj.modv.InviteModv;

import java.util.List;

/**
 * @author Vincent Luo
 */
public interface GroupService extends IService<Group> {

    /**
     * 通过ID查询俱乐部对象
     */
    Group queryByGroupId(Long groupId);

    /**
     * 查询俱乐部全量信息
     */
    GroupInfoModv queryAllGroupInformation(Long groupId);

    /**
     * 判断俱乐部是否存在
     */
    boolean hasGroup(Long groupId);

    /**
     * 获取用户加入的俱乐部列表
     */
    List<Group> queryGroupsByUserId(Long userId);

    /**
     * 创建俱乐部
     */
    Long create(CreateGroupModx createGroupModx, Long operatorId);

    /**
     * 编辑俱乐部信息
     */
    void edit(EditGroupModx editGroupModx, Long operatorId);

    /**
     * 解散俱乐部
     */
    void disband(Long groupId, Long operatorId);

    /**
     * 邀请加入俱乐部
     */
    void invite(Long groupId, Long userId, Long inviterId);

    /**
     * 申请加入俱乐部
     */
    void join(GroupApplyModx groupApplyModx, Long userId);

    /**
     * 同意新成员吧加入俱乐部
     *
     * @param applyId 申请ID
     */
    void allow(Long applyId, Long operatorId);

    /**
     * 拒绝新成员吧加入俱乐部
     *
     * @param applyId 申请ID
     */
    void refuse(Long applyId, String reason, Long operatorId);

    /**
     * 踢出俱乐部
     */
    void kick(Long groupId, Long userId, Long operatorId);

    /**
     * 退出俱乐部
     */
    void quit(Long groupId, Long userId);

    /**
     * 转让俱乐部超级管理员
     */
    void transfer(Long groupId, Long srcSuperAdminId, Long destSuperAdminId);

    /**
     * 发布俱乐部公告
     */
    void publishAnnouncement(Long groupId, String announcement, Long operatorId);

    /**
     * 查询用户邀请列表
     */
    List<InviteModv> queryInvites(Long userId);

    /**
     * 同意邀请
     */
    void allowInvite(Long inviteId, Long userId, Long operatorId);

    /**
     * 拒绝邀请
     */
    void refuseInvite(Long inviteId, Long userId, Long operatorId);

    /**
     * 创建俱乐部频道
     */
    void createChannel(Long groupId, String channelName, String channelType, Long operatorId);

}
