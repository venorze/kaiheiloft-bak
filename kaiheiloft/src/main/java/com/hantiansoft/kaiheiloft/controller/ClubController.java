package com.hantiansoft.kaiheiloft.controller;

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

import com.hantiansoft.kaiheiloft.modx.ClubIdModx;
import com.hantiansoft.kaiheiloft.modx.CreateClubModx;
import com.hantiansoft.kaiheiloft.modx.EditClubModx;
import com.hantiansoft.kaiheiloft.service.ClubService;
import com.hantiansoft.framework.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/club")
public class ClubController extends SuperController {

    @Autowired
    private ClubService clubService;

    /**
     * 创建俱乐部
     */
    @PostMapping("/create")
    public R<Long> create(@RequestBody @Valid CreateClubModx createClubModx) {
        return R.ok(clubService.create(createClubModx, getUserId()));
    }

    /**
     * 编辑俱乐部基本信息
     */
    @PostMapping("/edit")
    public R<Void> edit(@RequestBody @Valid EditClubModx editClubModx) {
        clubService.edit(editClubModx, getUserId());
        return R.ok();
    }

    /**
     * 解散俱乐部
     */
    @PostMapping("/disband")
    public R<Void> disband(@RequestBody @Valid ClubIdModx clubIdModx) {
        clubService.disband(clubIdModx.getId(), getUserId());
        return R.ok();
    }

}
