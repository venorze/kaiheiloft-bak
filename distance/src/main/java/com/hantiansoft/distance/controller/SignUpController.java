package com.hantiansoft.distance.controller;

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

/* Creates on 2023/1/16. */

import com.hantiansoft.distance.modx.UserSignUpModx;
import com.hantiansoft.distance.service.UserService;
import com.hantiansoft.framework.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册
 *
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/")
public class SignUpController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/sign_up")
    public R<Void> sign_up(@RequestBody @Valid UserSignUpModx userSignUpModx) {
        userService.sign_up(userSignUpModx);
        return R.ok();
    }

}
