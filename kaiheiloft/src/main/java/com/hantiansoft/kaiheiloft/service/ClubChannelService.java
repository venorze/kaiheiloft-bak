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
import com.hantiansoft.kaiheiloft.enties.ClubChannel;

import java.util.List;

/**
 * @author Vincent Luo
 */
public interface ClubChannelService extends IService<ClubChannel> {

    /**
     * 创建频道
     */
    void create(Long clubId, String channelName);

    /**
     * 查询所有频道
     */
    List<ClubChannel> queryChannels(Long clubId);
}
