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

/* Creates on 2023/2/6. */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amaoai.kaiheiloft.enties.GroupChannel;
import com.amaoai.kaiheiloft.mapper.GroupChannelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vincent Luo
 */
@Service
public class GroupChannelServiceImplements extends ServiceImpl<GroupChannelMapper, GroupChannel>
        implements GroupChannelService {

    @Override
    public void create(Long groupId, String channelName, String channelType) {
        var channel = new GroupChannel();
        channel.setName(channelName);
        channel.setGroupId(groupId);
        channel.setType(channelType);
        save(channel);
    }

    @Override
    public List<GroupChannel> queryChannels(Long groupId) {
        return list(new LambdaQueryWrapper<GroupChannel>().eq(GroupChannel::getGroupId, groupId));
    }
}
