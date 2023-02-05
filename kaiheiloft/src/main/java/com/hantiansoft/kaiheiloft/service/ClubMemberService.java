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
import com.hantiansoft.kaiheiloft.enties.ClubMember;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Luo
 */
@Service
public interface ClubMemberService extends IService<ClubMember> {

    /**
     * 精准查询俱乐部成员
     */
    ClubMember queryMember(Long clubId, Long userId);

    /**
     * 添加俱乐部成员
     */
    void addMember(Long clubId, Long userId);

    /**
     * 删除所有成员
     */
    void removeAllMember(Long clubId);

    /**
     * 移除用户
     */
    void removeMember(Long clubId, Long userId);

    /**
     * 判断成员是否在俱乐部
     */
    boolean isExist(Long clubId, Long userId);
}