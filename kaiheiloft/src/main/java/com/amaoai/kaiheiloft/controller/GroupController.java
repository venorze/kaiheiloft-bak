package com.amaoai.kaiheiloft.controller;

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

import com.amaoai.framework.R;
import com.amaoai.kaiheiloft.enties.GroupApplyIdModx;
import com.amaoai.kaiheiloft.enties.GroupApplyRefuseModx;
import com.amaoai.kaiheiloft.mods.modx.*;
import com.amaoai.kaiheiloft.mods.modv.InviteModv;
import com.amaoai.kaiheiloft.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/group")
public class GroupController extends SuperController {

    @Autowired
    private GroupService groupService;

    /**
     * 创建俱乐部
     */
    @PostMapping("/create")
    public R<Long> create(@RequestBody @Valid CreateGroupModx createGroupModx) {
        return R.ok(groupService.create(createGroupModx, getCurrentUserId()));
    }

    /**
     * 创建俱乐部频道
     */
    @PostMapping("/new/channel")
    public R<Void> channelCreate(@RequestBody @Valid CreateGroupChannelModx createGroupChannelModx) {
        groupService.createChannel(createGroupChannelModx.getGroupId(), createGroupChannelModx.getName(),
                createGroupChannelModx.getType(), getCurrentUserId());
        return R.ok();
    }

    /**
     * 获取俱乐部全量信息
     */
    @GetMapping("/aquery")
    public R<Long> completeQuery(@RequestBody @Valid GroupIdModx groupIdModx) {
        return R.ok(groupService.queryAllGroupInformation(groupIdModx.getGroupId()));
    }

    /**
     * 编辑俱乐部基本信息
     */
    @PostMapping("/edit")
    public R<Void> edit(@RequestBody @Valid EditGroupModx editGroupModx) {
        groupService.edit(editGroupModx, getCurrentUserId());
        return R.ok();
    }

    /**
     * 解散俱乐部
     */
    @PostMapping("/disband")
    public R<Void> disband(@RequestBody @Valid GroupIdModx groupIdModx) {
        groupService.disband(groupIdModx.getGroupId(), getCurrentUserId());
        return R.ok();
    }

    /**
     * 申请加入俱乐部
     */
    @PostMapping("/join")
    public R<Void> join(@RequestBody @Valid GroupApplyJoinModx groupApplyJoinModx) {
        groupService.join(groupApplyJoinModx, getCurrentUserId());
        return R.ok();
    }

    /**
     * 同意加入俱乐部申请请求
     */
    @PostMapping("/apply/agree")
    public R<Void> applyAgree(@RequestBody @Valid GroupApplyIdModx groupApplyIdModx) {
        groupService.agreeJoin(groupApplyIdModx.getId(), getCurrentUserId());
        return R.ok();
    }

    /**
     * 拒绝加入俱乐部申请请求
     */
    @PostMapping("/apply/refuse")
    public R<Void> applyRefuse(@RequestBody @Valid GroupApplyRefuseModx groupApplyRefuseModx) {
        groupService.refuseJoin(groupApplyRefuseModx.getId(), groupApplyRefuseModx.getReason(), getCurrentUserId());
        return R.ok();
    }

    /**
     * 邀请加入俱乐部
     */
    @PostMapping("/invite")
    public R<Void> invite(@RequestBody @Valid InviteModx inviteModx) {
        groupService.invite(inviteModx.getGroupId(), inviteModx.getUserId(), getCurrentUserId());
        return R.ok();
    }

    /**
     * 查询用户邀请列表
     */
    @GetMapping("/invite/query")
    public R<List<InviteModv>> queryInvites() {
        return R.ok(groupService.queryInvites(getCurrentUserId()));
    }

    /**
     * 同意邀请加入俱乐部
     */
    @PostMapping("/invite/agree")
    public R<Void> inviteAgree(@RequestBody @Valid InviteIdModx inviteIdModx) {
        groupService.agreeInvite(inviteIdModx.getInviteId(), inviteIdModx.getUserId(), getCurrentUserId());
        return R.ok();
    }

    /**
     * 拒绝邀请加入俱乐部
     */
    @PostMapping("/invite/refuse")
    public R<Void> inviteRefuse(@RequestBody @Valid InviteIdModx inviteIdModx) {
        groupService.refuseInvite(inviteIdModx.getInviteId(), inviteIdModx.getUserId(), getCurrentUserId());
        return R.ok();
    }

}
