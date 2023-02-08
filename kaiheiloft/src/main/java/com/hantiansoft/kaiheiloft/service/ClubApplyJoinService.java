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

import com.baomidou.mybatisplus.extension.service.IService;
import com.hantiansoft.kaiheiloft.enties.ClubApplyJoin;
import com.hantiansoft.kaiheiloft.mods.modx.ClubApplyJoinModx;

import java.util.List;

/**
 * @author Vincent Luo
 */
public interface ClubApplyJoinService extends IService<ClubApplyJoin> {

    /**
     * @return 根据ID查询申请对象
     */
    ClubApplyJoin queryByApplyId(Long applyId);

    /**
     * 查询申请请求
     */
    ClubApplyJoin queryJoinRequest(Long clubId, Long userId);

    /**
     * 提交申请请求
     */
    void submit(ClubApplyJoinModx clubApplyJoinModx, Long userId, Long inviterId);

    /**
     * 提交申请请求
     *
     * @param clubId        俱乐部ID
     * @param requestRemark 申请备注
     * @param userId        用户ID
     * @param inviterId     邀请人ID（如果没有可以为空）
     */
    void submit(Long clubId, String requestRemark, Long userId, Long inviterId);

    /**
     * @return 获取所有待处理申请请求
     */
    List<ClubApplyJoin> pendingRequests(Long clubId);

    /**
     * 同意成员加入
     */
    void agree(ClubApplyJoin clubApplyJoin);

    /**
     * 拒绝成员加入
     */
    void refuse(ClubApplyJoin clubApplyJoin);

}
