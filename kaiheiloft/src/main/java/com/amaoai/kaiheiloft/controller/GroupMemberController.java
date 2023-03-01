package com.amaoai.kaiheiloft.controller;

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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.amaoai.framework.R;
import com.amaoai.kaiheiloft.modobj.modv.GroupMemberInfoModv;
import com.amaoai.kaiheiloft.modobj.modx.GroupMemberPageModx;
import com.amaoai.kaiheiloft.service.GroupMemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/group-member")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    /**
     * 分页查询俱乐部成员列表
     */
    @GetMapping("/pquery")
    public R<IPage<GroupMemberInfoModv>> page(@RequestBody @Valid GroupMemberPageModx pageModx) {
        return R.ok(groupMemberService.queryPageMember(pageModx.getGroupId(), pageModx.getPageNo(), pageModx.getPageSize()));
    }

}
