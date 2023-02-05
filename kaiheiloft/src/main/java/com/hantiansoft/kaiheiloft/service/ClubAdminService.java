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
import com.hantiansoft.kaiheiloft.enties.ClubAdmin;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
public interface ClubAdminService extends IService<ClubAdmin> {

    /**
     * 添加管理员
     */
    void addAdmin(Long clubId, Long userId);

    /**
     * 添加超级管理员
     */
    void addSuperAdmin(Long clubId, Long userId);

    /**
     * 是否是管理员
     */
    boolean isAdmin(Long clubId, Long userId);

    /**
     * 是否是超级管理员
     */
    boolean isSuperAdmin(Long clubId, Long userId);

    /**
     * 移除所有管理员
     */
    void removeAllAdmin(Long clubId);

    /**
     * 移除管理员
     */
    void removeAdmin(Long clubId, Long userId);

    /**
     * 转让超级管理员
     */
    void transfer(Long clubId, Long srcSuperAdminId, Long destSuperAdminId);
}
