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

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hantiansoft.framework.Asserts;
import com.hantiansoft.framework.BeanUtils;
import com.hantiansoft.framework.StringUtils;
import com.hantiansoft.framework.exception.BusinessException;
import com.hantiansoft.kaiheiloft.enties.Club;
import com.hantiansoft.kaiheiloft.enties.ClubApplyJoin;
import com.hantiansoft.kaiheiloft.enties.ClubInvite;
import com.hantiansoft.kaiheiloft.enties.User;
import com.hantiansoft.kaiheiloft.mapper.ClubAnnouncementMapper;
import com.hantiansoft.kaiheiloft.mapper.ClubMapper;
import com.hantiansoft.kaiheiloft.modx.ClubApplyJoinModx;
import com.hantiansoft.kaiheiloft.modx.CreateClubModx;
import com.hantiansoft.kaiheiloft.modx.EditClubModx;
import com.hantiansoft.kaiheiloft.modx.InviteModv;
import com.hantiansoft.kaiheiloft.system.KaiheiloftApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vincent Luo
 */
@Service
public class ClubServiceImplements extends ServiceImpl<ClubMapper, Club> implements ClubService {

    @Autowired
    private ClubAnnouncementMapper clubAnnouncementMapper;

    @Autowired
    private ClubMemberService clubMemberService;

    @Autowired
    private ClubAdminService clubAdminService;

    @Autowired
    private ClubApplyJoinService clubApplyJoinService;

    @Autowired
    private ClubInviteService clubInviteService;

    @Autowired
    private UserService userService;

    @Override
    public Club queryByClubId(Long clubId) {
        Club club = getById(clubId);
        Asserts.throwIfNull(club, "俱乐部不存在");
        return club;
    }

    @Override
    public List<Club> queryClubsByUserId(Long userId) {
        return baseMapper.queryClubsByUserId(userId);
    }

    @Override
    @Transactional
    public Long create(CreateClubModx createClubModx, Long operatorId) {
        // 创建俱乐部对象
        var club = BeanUtils.copyProperties(createClubModx, Club.class);
        save(club);
        // 添加成员表
        clubMemberService.addMember(club.getId(), operatorId);
        // 添加管理员表
        clubAdminService.addSuperAdmin(club.getId(), operatorId);

        return club.getId();
    }

    @Override
    public void edit(EditClubModx editClubModx, Long operatorId) {
        Club club = queryByClubId(editClubModx.getId());
        // 管理员有权限修改
        Asserts.throwIfBool(clubAdminService.isAdmin(editClubModx.getId(), operatorId), "用户无权限修改");
        BeanUtils.copyProperties(editClubModx, club);
        updateById(club);
    }

    @Override
    @Transactional
    public void disband(Long clubId, Long operatorId) {
        // 判断俱乐部ID是否正确
        queryByClubId(clubId);
        Asserts.throwIfBool(clubAdminService.isSuperAdmin(clubId, operatorId), "用户无权限解散");
        // 删除所有成员
        clubMemberService.removeAllMember(clubId);
        // 删除所有管理员
        clubAdminService.removeAllAdmin(clubId);
        // 删除俱乐部
        removeById(clubId);
    }

    @Override
    public void invite(Long clubId, Long userId, Long inviterId) {
        // 判断俱乐部是否存在
        queryByClubId(clubId);
        // 邀请成员加入
        clubInviteService.invite(clubId, userId, inviterId);
    }

    @Override
    public void join(ClubApplyJoinModx clubApplyJoinModx, Long userId) {
        // 判断俱乐部是否存在
        queryByClubId(clubApplyJoinModx.getClubId());
        // 提交请求
        clubApplyJoinService.submit(clubApplyJoinModx, userId, null);
    }

    @Override
    @Transactional
    public void agreeJoin(Long applyId, Long operatorId) {
        ClubApplyJoin clubApply = clubApplyJoinService.queryByApplyId(applyId);
        Asserts.throwIfBool(clubAdminService.isSuperAdmin(clubApply.getClubId(), operatorId), "用户无权限同意/拒绝");
        clubApplyJoinService.agree(clubApply);
        // 添加成员
        clubMemberService.addMember(clubApply.getClubId(), clubApply.getUserId());
    }

    @Override
    public void refuseJoin(Long applyId, String reason, Long operatorId) {
        ClubApplyJoin clubApply = clubApplyJoinService.queryByApplyId(applyId);
        Asserts.throwIfBool(clubAdminService.isSuperAdmin(clubApply.getClubId(), operatorId), "用户无权限同意/拒绝");
        clubApply.setRefusalReason(reason);
        clubApplyJoinService.refuse(clubApply);
    }

    @Override
    @Transactional
    public void kick(Long clubId, Long userId, Long operatorId) {
        // 判断成员是否在俱乐部
        checkMember(clubId, userId);

        // 判断用户是否是管理员, 如果是管理员需要超级管理员踢出
        if (clubAdminService.isAdmin(clubId, userId)) {
            Asserts.throwIfBool(clubAdminService.isSuperAdmin(clubId, operatorId), "用户无权踢出管理员");
            clubAdminService.removeAdmin(clubId, userId);
        }

        clubMemberService.removeMember(clubId, userId); // 移除成员
    }

    @Override
    public void quit(Long clubId, Long userId) {
        // 判断成员是否在俱乐部
        checkMember(clubId, userId);

        // 判断用户是否是管理员, 如果是管理员需要移除管理员权限
        if (clubAdminService.isAdmin(clubId, userId))
            clubAdminService.removeAdmin(clubId, userId);

        clubMemberService.removeMember(clubId, userId); // 移除成员
    }

    @Override
    public void publishAnnouncement(Long clubId, String announcement, Long operatorId) {

    }

    @Override
    public List<InviteModv> queryInvites(Long userId) {
        return clubInviteService.queryInvitesByUserId(userId);
    }

    @Override
    @Transactional
    public void agreeInvite(Long inviteId, Long userId, Long operatorId) {
        // 查询邀请信息
        ClubInvite clubInvite = clubInviteService.queryUserInvite(inviteId, userId);
        Long clubId = clubInvite.getClubId();
        Long inviterId = clubInvite.getInviterId();

        // 判断用户是否有权限同意
        Asserts.throwIfBool(clubAdminService.isAdmin(clubId, operatorId), "用户无权限同意/拒绝");
        if (clubInvite.getAgreeStatus().equals(KaiheiloftApplicationContext.CLUB_AGREE_STATUS_YES))
            throw new BusinessException("已同意邀请请求，请勿重复点击");

        // 同意邀请
        clubInviteService.agree(inviteId);

        // 加入申请列表
        User inviter = userService.queryByUserId(inviterId);
        clubApplyJoinService.submit(clubId,
                StringUtils.vfmt("来自{}用户邀请", inviter.getNickname()), userId, inviterId);
    }

    @Override
    public void refuseInvite(Long inviteId, Long userId, Long operatorId) {
        ClubInvite clubInvite = clubInviteService.queryUserInvite(inviteId, userId);
        // 判断用户是否有权限拒绝
        Asserts.throwIfBool(clubAdminService.isAdmin(clubInvite.getClubId(), operatorId), "用户无权限同意/拒绝");
        if (clubInvite.getAgreeStatus().equals(KaiheiloftApplicationContext.CLUB_AGREE_STATUS_NO))
            throw new BusinessException("已拒绝邀请请求，请勿重复点击");

        // 拒绝邀请
        clubInviteService.refuse(inviteId);
    }

    void checkMember(Long clubId, Long userId) {
        Asserts.throwIfBool(!clubMemberService.isExist(clubId, userId), "成员不在该俱乐部");
    }

}
