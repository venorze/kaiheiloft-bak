package com.hantiansoft.kaiheiloft.remotecall;

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

/* Creates on 2023/1/22. */

import com.hantiansoft.framework.R;
import com.hantiansoft.export.opensso.TokenPayloadExportMod;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 认证服务调用
 *
 * @author Vincent Luo
 */
@FeignClient(name = "OPENSSO-SRV")
public interface OpenSSORemoteCall {

    /**
     * 验证Token是否正确
     */
    @PostMapping("/nopen/verifier/private")
    R<TokenPayloadExportMod> verifier(@RequestHeader("Authorization") String authorization);

}
