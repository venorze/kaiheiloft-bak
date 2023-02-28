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

import com.baomidou.mybatisplus.extension.service.IService;
import com.amaoai.kaiheiloft.enties.GroupApplyJoin;
import com.amaoai.kaiheiloft.mods.modx.GroupApplyJoinModx;

import java.util.List;

/**
 * @author Vincent Luo
 */
public interface GroupApplyJoinService extends IService<GroupApplyJoin> {

    /**
     * @return 根据ID查询申请对象
     */
    GroupApplyJoin queryByApplyId(Long applyId);

    /**
     * 查询申请请求
     */
    GroupApplyJoin queryJoinRequest(Long groupId, Long userId);

    /**
     * 提交申请请求
     */
    void submit(GroupApplyJoinModx groupApplyJoinModx, Long userId, Long inviterId);

    /**
     * 提交申请请求
     *
     * @param groupId        俱乐部ID
     * @param requestRemark 申请备注
     * @param userId        用户ID
     * @param inviterId     邀请人ID（如果没有可以为空）
     */
    void submit(Long groupId, String requestRemark, Long userId, Long inviterId);

    /**
     * @return 获取所有待处理申请请求
     */
    List<GroupApplyJoin> pendingRequests(Long groupId);

    /**
     * 同意成员加入
     */
    void agree(GroupApplyJoin groupApplyJoin);

    /**
     * 拒绝成员加入
     */
    void refuse(GroupApplyJoin groupApplyJoin);

}
