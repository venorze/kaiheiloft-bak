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

import com.amaoai.framework.Assert;
import com.amaoai.kaiheiloft.modobj.modv.GroupApplyModv;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amaoai.framework.BeanUtils;
import com.amaoai.framework.StringUtils;
import com.amaoai.framework.exception.BusinessException;
import com.amaoai.kaiheiloft.enties.Group;
import com.amaoai.kaiheiloft.enties.GroupApply;
import com.amaoai.kaiheiloft.enties.GroupInvite;
import com.amaoai.kaiheiloft.enties.User;
import com.amaoai.kaiheiloft.modobj.modv.GroupInfoModv;
import com.amaoai.kaiheiloft.mapper.GroupAnnouncementMapper;
import com.amaoai.kaiheiloft.mapper.GroupMapper;
import com.amaoai.kaiheiloft.modobj.modx.GroupApplyModx;
import com.amaoai.kaiheiloft.modobj.modx.CreateGroupModx;
import com.amaoai.kaiheiloft.modobj.modx.EditGroupModx;
import com.amaoai.kaiheiloft.modobj.modv.InviteModv;
import com.amaoai.kaiheiloft.system.KaiheiloftSystemConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vincent Luo
 */
@Service
public class GroupServiceImplements extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Autowired
    private GroupAnnouncementMapper groupAnnouncementMapper;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private GroupAdminService groupAdminService;

    @Autowired
    private GroupApplyService groupApplyService;

    @Autowired
    private GroupInviteService groupInviteService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupChannelService groupChannelService;

    @Override
    public Group queryByGroupId(Long groupId) {
        Group group = getById(groupId);
        Assert.throwIfNull(group, "俱乐部不存在");
        return group;
    }

    @Override
    public GroupInfoModv queryAllGroupInformation(Long groupId) {
        var completeGroup = new GroupInfoModv();
        // 查询俱乐部基本信息
        BeanUtils.copyProperties(queryByGroupId(groupId), completeGroup);

        // 查询俱乐部频道
        completeGroup.setChannels(BeanUtils.copyProperties(groupChannelService.queryChannels(groupId), GroupInfoModv.Channel.class));

        // 查询俱乐部用户
        var memberPage = groupMemberService.queryPageMember(groupId, 1, 20);
        completeGroup.setMembers(memberPage);

        return completeGroup;
    }

    @Override
    public boolean hasGroup(Long groupId) {
        return queryByGroupId(groupId) != null;
    }

    @Override
    public List<Group> queryGroupsByUserId(Long userId) {
        return baseMapper.queryGroupsByUserId(userId);
    }

    @Override
    @Transactional
    public Long create(CreateGroupModx createGroupModx, Long operatorId) {
        // 创建俱乐部对象
        var group = BeanUtils.copyProperties(createGroupModx, Group.class);
        save(group);
        // 添加成员表
        groupMemberService.addMember(group.getId(), operatorId);
        // 添加管理员表
        groupAdminService.addSuperAdmin(group.getId(), operatorId);

        return group.getId();
    }

    @Override
    public void edit(EditGroupModx editGroupModx, Long operatorId) {
        Group group = queryByGroupId(editGroupModx.getId());
        // 管理员有权限修改
        Assert.throwIfBool(groupAdminService.isAdmin(editGroupModx.getId(), operatorId), "用户无权限修改");
        BeanUtils.copyProperties(editGroupModx, group);
        updateById(group);
    }

    @Override
    @Transactional
    public void disband(Long groupId, Long operatorId) {
        // 判断俱乐部ID是否正确
        queryByGroupId(groupId);
        Assert.throwIfBool(groupAdminService.isSuperAdmin(groupId, operatorId), "用户无权限解散");
        // 删除所有成员
        groupMemberService.removeAllMember(groupId);
        // 删除所有管理员
        groupAdminService.removeAllAdmin(groupId);
        // 删除俱乐部
        removeById(groupId);
    }

    @Override
    public void invite(Long groupId, Long userId, Long inviterId) {
        // 判断用户是否已经在俱乐部
        checkAlreadyExist(groupId, userId);
        // 判断是否已经邀请过了
        Assert.throwIfNotNull(groupInviteService.queryWaitAllowInvite(groupId, userId, inviterId),
              "您已邀请过该用户了，请勿重复邀请");
        // 邀请成员加入
        groupInviteService.invite(groupId, userId, inviterId);
    }

    @Override
    public void join(GroupApplyModx groupApplyModx, Long userId) {
        Long groupId = groupApplyModx.getGroupId();
        // 判断用户是否已经在俱乐部
        checkAlreadyExist(groupId, userId);
        // 提交请求
        groupApplyService.submit(groupApplyModx, userId, null);
    }

    @Override
    @Transactional
    public void allow(Long applyId, Long operatorId) {
        GroupApply groupApply = groupApplyService.queryByApplyId(applyId);
        Assert.throwIfBool(groupAdminService.isSuperAdmin(groupApply.getGroupId(), operatorId), "用户无权限同意/拒绝");
        groupApplyService.allow(groupApply);
        // 添加成员
        groupMemberService.addMember(groupApply.getGroupId(), groupApply.getUserId());
    }

    @Override
    public void refuse(Long applyId, String reason, Long operatorId) {
        GroupApply groupApply = groupApplyService.queryByApplyId(applyId);
        Assert.throwIfBool(groupAdminService.isSuperAdmin(groupApply.getGroupId(), operatorId), "用户无权限同意/拒绝");
        groupApply.setRefusalReason(reason);
        groupApplyService.refuse(groupApply);
    }

    @Override
    @Transactional
    public void kick(Long groupId, Long userId, Long operatorId) {
        // 判断成员是否在俱乐部
        checkMemberExist(groupId, userId);

        // 判断用户是否是管理员, 如果是管理员需要超级管理员踢出
        if (groupAdminService.isAdmin(groupId, userId)) {
            Assert.throwIfBool(groupAdminService.isSuperAdmin(groupId, operatorId), "用户无权踢出管理员");
            groupAdminService.removeAdmin(groupId, userId);
        }

        groupMemberService.removeMember(groupId, userId); // 移除成员
    }

    @Override
    public void quit(Long groupId, Long userId) {
        // 判断成员是否在俱乐部
        checkMemberExist(groupId, userId);

        // 判断用户是否是管理员, 如果是管理员需要移除管理员权限
        if (groupAdminService.isAdmin(groupId, userId))
            groupAdminService.removeAdmin(groupId, userId);

        groupMemberService.removeMember(groupId, userId); // 移除成员
    }

    @Override
    public void transfer(Long groupId, Long srcSuperAdminId, Long destSuperAdminId) {
        // 判断用户是否在俱乐部内
        Assert.throwIfNull(groupMemberService.queryMember(groupId, destSuperAdminId), "转让用户不在俱乐部内");
        groupAdminService.transfer(groupId, srcSuperAdminId, destSuperAdminId);
    }

    @Override
    public void publishAnnouncement(Long groupId, String announcement, Long operatorId) {

    }

    @Override
    public List<InviteModv> queryInvites(Long userId) {
        return groupInviteService.queryInvitesByUserId(userId);
    }

    @Override
    @Transactional
    public void allowInvite(Long inviteId, Long userId) {
        // 查询邀请信息
        GroupInvite groupInvite = groupInviteService.queryInvite(inviteId, userId);
        Long groupId = groupInvite.getGroupId();
        Long inviterId = groupInvite.getInviterId();
        if (groupInvite.getAllowedStatus().equals(KaiheiloftSystemConsts.GROUP_ALLOW_STATUS_YES))
            throw new BusinessException("已同意邀请请求，请勿重复点击");

        // 同意邀请
        groupInviteService.allow(inviteId);

        // 加入申请列表
        User inviter = userService.queryByUserId(inviterId);
        groupApplyService.submit(groupId,
                StringUtils.vfmt("来自{}用户邀请", inviter.getNickname()), userId, inviterId);
    }

    @Override
    public void refuseInvite(Long inviteId, Long userId) {
        GroupInvite groupInvite = groupInviteService.queryInvite(inviteId, userId);
        if (groupInvite.getAllowedStatus().equals(KaiheiloftSystemConsts.GROUP_ALLOW_STATUS_NO))
            throw new BusinessException("已拒绝邀请请求，请勿重复点击");

        // 拒绝邀请
        groupInviteService.refuse(inviteId);
    }

    /**
     * 检查用户是否在俱俱乐部
     */
    void checkMemberExist(Long groupId, Long userId) {
        Assert.throwIfBool(!groupMemberService.hasMember(groupId, userId), "成员不在该俱乐部");
    }

    /**
     * 检查用户是否已经在俱乐部内了
     */
    void checkAlreadyExist(Long groupId, Long userId) {
        // 检查俱乐部是否存在
        Assert.throwIfBool(hasGroup(groupId), "俱乐部不存在");
        // 检查成员是否已经在俱乐部内
        Assert.throwIfBool(!groupMemberService.hasMember(groupId, userId), "成员已经在俱乐部内了");
    }

    @Override
    public void createChannel(Long groupId, String channelName, String channelType, Long operatorId) {
        // 判断是不是管理员
        Assert.throwIfBool(groupAdminService.isAdmin(groupId, operatorId), "用户无权限创建频道");
        groupChannelService.create(groupId, channelName, channelType);
    }

    @Override
    public IPage<GroupApplyModv> pageQueryApplys(Long groupId, Integer pageNo, Integer pageSize) {
        // TODO
        return null;
    }

}
