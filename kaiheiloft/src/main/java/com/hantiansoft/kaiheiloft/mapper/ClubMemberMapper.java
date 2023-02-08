package com.hantiansoft.kaiheiloft.mapper;

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

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hantiansoft.kaiheiloft.enties.ClubMember;
import com.hantiansoft.kaiheiloft.mods.modv.ClubMemberInfoModv;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Vincent Luo
 */
@Mapper
public interface ClubMemberMapper extends BaseMapper<ClubMember> {

    /**
     * 分页查询俱乐部成员
     */
    IPage<ClubMemberInfoModv> queryPageMember(@Param("page") IPage<?> page, @Param("clubId") Long clubId);

}
