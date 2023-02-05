package com.hantiansoft.kaiheiloft.controller;

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

import com.hantiansoft.framework.R;
import com.hantiansoft.kaiheiloft.enties.ClubApplyIdModx;
import com.hantiansoft.kaiheiloft.enties.ClubApplyRefuseModx;
import com.hantiansoft.kaiheiloft.modx.*;
import com.hantiansoft.kaiheiloft.service.ClubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/club")
public class ClubController extends SuperController {

    @Autowired
    private ClubService clubService;

    /**
     * 创建俱乐部
     */
    @PostMapping("/create")
    public R<Long> create(@RequestBody @Valid CreateClubModx createClubModx) {
        return R.ok(clubService.create(createClubModx, getCurrentUserId()));
    }

    /**
     * 获取俱乐部全量信息
     */
    @GetMapping("/fquery")
    public R<Long> completeQuery(@RequestBody @Valid ClubIdModx clubIdModx) {
        return R.ok(clubService.queryCompleteClub(clubIdModx.getId()));
    }


    /**
     * 编辑俱乐部基本信息
     */
    @PostMapping("/edit")
    public R<Void> edit(@RequestBody @Valid EditClubModx editClubModx) {
        clubService.edit(editClubModx, getCurrentUserId());
        return R.ok();
    }

    /**
     * 解散俱乐部
     */
    @PostMapping("/disband")
    public R<Void> disband(@RequestBody @Valid ClubIdModx clubIdModx) {
        clubService.disband(clubIdModx.getId(), getCurrentUserId());
        return R.ok();
    }

    /**
     * 申请加入俱乐部
     */
    @PostMapping("/join")
    public R<Void> join(@RequestBody @Valid ClubApplyJoinModx clubApplyJoinModx) {
        clubService.join(clubApplyJoinModx, getCurrentUserId());
        return R.ok();
    }

    /**
     * 申请加入俱乐部
     */
    @PostMapping("/apply/agree")
    public R<Void> applyAgree(@RequestBody @Valid ClubApplyIdModx clubApplyIdModx) {
        clubService.agreeJoin(clubApplyIdModx.getId(), getCurrentUserId());
        return R.ok();
    }


    /**
     * 申请加入俱乐部
     */
    @PostMapping("/apply/refuse")
    public R<Void> applyRefuse(@RequestBody @Valid ClubApplyRefuseModx clubApplyRefuseModx) {
        clubService.refuseJoin(clubApplyRefuseModx.getId(), clubApplyRefuseModx.getReason(), getCurrentUserId());
        return R.ok();
    }

    /**
     * 邀请加入俱乐部
     */
    @PostMapping("/invite")
    public R<Void> invite(@RequestBody @Valid InviteModx inviteModx) {
        clubService.invite(inviteModx.getClubId(), inviteModx.getUserId(), getCurrentUserId());
        return R.ok();
    }

    /**
     * 查询用户邀请列表
     */
    @GetMapping("/invite/query")
    public R<List<InviteModv>> queryInvites() {
        return R.ok(clubService.queryInvites(getCurrentUserId()));
    }

    /**
     * 同意邀请加入俱乐部
     */
    @PostMapping("/invite/agree")
    public R<Void> inviteAgree(@RequestBody @Valid InviteIdModx inviteIdModx) {
        clubService.agreeInvite(inviteIdModx.getInviteId(), inviteIdModx.getUserId(), getCurrentUserId());
        return R.ok();
    }

    /**
     * 拒绝邀请加入俱乐部
     */
    @PostMapping("/invite/refuse")
    public R<Void> inviteRefuse(@RequestBody @Valid InviteIdModx inviteIdModx) {
        clubService.refuseInvite(inviteIdModx.getInviteId(), inviteIdModx.getUserId(), getCurrentUserId());
        return R.ok();
    }

}
