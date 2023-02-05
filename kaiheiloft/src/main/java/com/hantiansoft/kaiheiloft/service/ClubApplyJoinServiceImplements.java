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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hantiansoft.framework.Asserts;
import com.hantiansoft.framework.BeanUtils;
import com.hantiansoft.kaiheiloft.enties.ClubApplyJoin;
import com.hantiansoft.kaiheiloft.mapper.ClubApplyJoinMapper;
import com.hantiansoft.kaiheiloft.modx.ClubApplyJoinModx;
import com.hantiansoft.kaiheiloft.system.KaiheiloftApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vincent Luo
 */
@Service
public class ClubApplyJoinServiceImplements extends ServiceImpl<ClubApplyJoinMapper, ClubApplyJoin>
        implements ClubApplyJoinService {

    @Override
    public ClubApplyJoin queryByApplyId(Long applyId) {
        return getById(applyId);
    }

    @Override
    public ClubApplyJoin queryJoinRequest(Long clubId, Long userId) {
        QueryWrapper<ClubApplyJoin> wrapper = new QueryWrapper<>();
        wrapper.eq("club_id", clubId);
        wrapper.eq("user_id", userId);
        return getOne(wrapper);
    }

    @Override
    public void submit(ClubApplyJoinModx clubApplyJoinModx, Long userId, Long inviterId) {
        submit(clubApplyJoinModx.getClubId(), clubApplyJoinModx.getRequestRemark(), userId, inviterId);
    }

    @Override
    public void submit(Long clubId, String requestRemark, Long userId, Long inviterId) {
        // 是否重复申请
        Asserts.throwIfNotNull(queryJoinRequest(clubId, userId), "您已申请过加入该俱乐部，请勿重复申请");
        // 构建申请加入对象
        ClubApplyJoin clubApplyJoin = new ClubApplyJoin();
        clubApplyJoin.setClubId(clubId);
        clubApplyJoin.setRequestRemark(requestRemark);
        clubApplyJoin.setUserId(userId);

        save(clubApplyJoin);
    }

    @Override
    public List<ClubApplyJoin> pendingRequests(Long clubId) {
        QueryWrapper<ClubApplyJoin> wrapper = new QueryWrapper<>();
        wrapper.eq("club_id", clubId);
        wrapper.eq("agree_status", KaiheiloftApplicationContext.CLUB_AGREE_STATUS_WAIT);
        return list(wrapper);
    }

    @Override
    public void agree(ClubApplyJoin clubApply) {
        clubApply.setAgreeStatus(KaiheiloftApplicationContext.CLUB_AGREE_STATUS_YES);
        updateById(clubApply);
    }

    @Override
    public void refuse(ClubApplyJoin clubApply) {
        clubApply.setAgreeStatus(KaiheiloftApplicationContext.CLUB_AGREE_STATUS_NO);
        updateById(clubApply);
    }

}
