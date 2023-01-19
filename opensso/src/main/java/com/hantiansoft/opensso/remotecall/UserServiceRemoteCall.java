package com.hantiansoft.opensso.remotecall;

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

/* Creates on 2023/1/18. */

import com.hantiansoft.framework.R;
import com.hantiansoft.linkmod.distance.UserInfoLinkMod;
import com.hantiansoft.linkmod.distance.UserSignInLinkMod;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Vincent Luo
 */
@Service
@FeignClient("DISTANCE-SRV")
public interface UserServiceRemoteCall {

    /**
     * 用户登录接口
     */
    @GetMapping("/nopen/sign_in/private")
    R<UserInfoLinkMod> sign_in(@RequestBody @Valid UserSignInLinkMod userSignInLinkMod);

}
