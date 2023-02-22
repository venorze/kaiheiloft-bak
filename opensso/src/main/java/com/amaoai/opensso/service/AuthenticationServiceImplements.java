package com.amaoai.opensso.service;

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

/* Creates on 2023/1/20. */

import devtools.framework.security.AuthenticationTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Vincent Luo
 */
@Service
public class AuthenticationServiceImplements implements AuthenticationService {

    @Autowired
    private AuthenticationTokenGenerator authenticationTokenGenerator;

    @Override
    public String createToken(Map<String, Object> payload) {
        return authenticationTokenGenerator.createToken(payload);
    }

    @Override
    public Map<String, Object> getClaims(String token) {
        return authenticationTokenGenerator.getClaims(token);
    }

    @Override
    public boolean verifier(String token) {
        return authenticationTokenGenerator.verifier(token);
    }

}
