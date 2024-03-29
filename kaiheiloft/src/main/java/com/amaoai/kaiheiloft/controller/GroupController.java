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
import com.amaoai.kaiheiloft.modobj.modv.GroupApplyModv;
import com.amaoai.kaiheiloft.modobj.modx.GroupApplyIdModx;
import com.amaoai.kaiheiloft.modobj.modx.GroupApplyRefuseModx;
import com.amaoai.kaiheiloft.modobj.modx.*;
import com.amaoai.kaiheiloft.modobj.modv.InviteModv;
import com.amaoai.kaiheiloft.service.GroupService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
        return R.ok(groupService.create(createGroupModx, currentUserId()));
    }

    /**
     * 创建俱乐部频道
     */
    @PostMapping("/new/channel")
    public R<Void> channelCreate(@RequestBody @Valid CreateGroupChannelModx createGroupChannelModx) {
        groupService.createChannel(createGroupChannelModx.getGroupId(), createGroupChannelModx.getName(),
                createGroupChannelModx.getType(), currentUserId());
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
        groupService.edit(editGroupModx, currentUserId());
        return R.ok();
    }

    /**
     * 解散俱乐部
     */
    @PostMapping("/disband")
    public R<Void> disband(@RequestBody @Valid GroupIdModx groupIdModx) {
        groupService.disband(groupIdModx.getGroupId(), currentUserId());
        return R.ok();
    }

    /**
     * 申请加入俱乐部
     */
    @PostMapping("/join")
    public R<Void> join(@RequestBody @Valid GroupApplyModx groupApplyModx) {
        groupService.join(groupApplyModx, currentUserId());
        return R.ok();
    }

    /**
     * 查询申请列表
     */
    @PostMapping("/pquery/apply")
    public R<IPage<GroupApplyModv>> applyQuery(@RequestBody @Valid GroupIdPageModx groupIdPageModx) {
        return R.ok(groupService.pageQueryApplys(groupIdPageModx.getGroupId(), groupIdPageModx.getPageNo(),
              groupIdPageModx.getPageSize()));
    }

    /**
     * 同意加入俱乐部申请请求
     */
    @PostMapping("/allow/apply")
    public R<Void> allow(@RequestBody @Valid GroupApplyIdModx groupApplyIdModx) {
        groupService.allow(groupApplyIdModx.getApplyId(), currentUserId());
        return R.ok();
    }

    /**
     * 拒绝加入俱乐部申请请求
     */
    @PostMapping("/refuse/apply")
    public R<Void> refuse(@RequestBody @Valid GroupApplyRefuseModx groupApplyRefuseModx) {
        groupService.refuse(groupApplyRefuseModx.getApplyId(), groupApplyRefuseModx.getReason(), currentUserId());
        return R.ok();
    }

    /**
     * 邀请加入俱乐部
     */
    @PostMapping("/invite")
    public R<Void> invite(@RequestBody @Valid InviteModx inviteModx) {
        groupService.invite(inviteModx.getGroupId(), inviteModx.getUserId(), currentUserId());
        return R.ok();
    }

    /**
     * 查询用户邀请列表
     */
    @GetMapping("/query/invite")
    public R<List<InviteModv>> queryInvites() {
        return R.ok(groupService.queryInvites(currentUserId()));
    }

    /**
     * 同意邀请加入俱乐部
     */
    @PostMapping("/allow/invite")
    public R<Void> allowInvite(@RequestBody @Valid InviteIdModx inviteIdModx) {
        groupService.allowInvite(inviteIdModx.getInviteId(), currentUserId());
        return R.ok();
    }

    /**
     * 拒绝邀请加入俱乐部
     */
    @PostMapping("/refuse/invite")
    public R<Void> inviteRefuse(@RequestBody @Valid InviteIdModx inviteIdModx) {
        groupService.refuseInvite(inviteIdModx.getInviteId(), currentUserId());
        return R.ok();
    }

    /**
     * 退出俱乐部
     */
    @PostMapping("/quit")
    public R<Void> quit(@RequestBody @Valid GroupIdModx groupIdModx) {
        groupService.quit(groupIdModx.getGroupId(), currentUserId());
        return R.ok();
    }

}
