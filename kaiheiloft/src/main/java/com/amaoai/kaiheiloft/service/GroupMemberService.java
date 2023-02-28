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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.amaoai.kaiheiloft.enties.GroupMember;
import com.amaoai.kaiheiloft.modobj.modv.GroupMemberInfoModv;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Luo
 */
@Service
public interface GroupMemberService extends IService<GroupMember> {

    /**
     * 精准查询俱乐部成员
     */
    GroupMember queryMember(Long groupId, Long userId);

    /**
     * 添加俱乐部成员
     */
    void addMember(Long groupId, Long userId);

    /**
     * 删除所有成员
     */
    void removeAllMember(Long groupId);

    /**
     * 移除用户
     */
    void removeMember(Long groupId, Long userId);

    /**
     * 判断成员是否在俱乐部
     */
    boolean hasMember(Long groupId, Long userId);

    /**
     * 分页查询俱乐部成员
     */
    IPage<GroupMemberInfoModv> queryPageMember(Long groupId, int pageNo, int pageSize);

}
