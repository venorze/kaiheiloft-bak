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
import com.amaoai.kaiheiloft.enties.GroupAdmin;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
public interface GroupAdminService extends IService<GroupAdmin> {

    /**
     * 添加管理员
     */
    void addAdmin(Long groupId, Long userId);

    /**
     * 添加超级管理员
     */
    void addSuperAdmin(Long groupId, Long userId);

    /**
     * 获取管理员标志位
     */
    int adminFlags(Long groupId, Long userId);

    /**
     * 是否是管理员
     */
    boolean isAdmin(Long groupId, Long userId);

    /**
     * 是否是管理员，传入管理员Flags标志位
     */
    boolean isAdmin(int flags);

    /**
     * 是否是超级管理员
     */
    boolean isSuperAdmin(Long groupId, Long userId);

    /**
     * 是否是超级管理员，传入管理员Flags标志位
     */
    boolean isSuperAdmin(int flags);

    /**
     * 移除所有管理员
     */
    void removeAllAdmin(Long groupId);

    /**
     * 移除管理员
     */
    void removeAdmin(Long groupId, Long userId);

    /**
     * 转让超级管理员
     */
    void transfer(Long groupId, Long srcSuperAdminId, Long destSuperAdminId);
}
