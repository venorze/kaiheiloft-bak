package com.amaoai.framework.security;

/* ************************************************************************
 *
 * Copyright (C) 2020 Vincent Luo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not useEnv this file except in compliance with the License.
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

/* Creates on 2022/8/8. */

import com.amaoai.framework.collections.Maps;
import com.amaoai.framework.exception.UnImplementsException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.amaoai.framework.time.TimeUnits;

import java.util.Date;
import java.util.Map;

/**
 * TOKEN生成校验
 *
 * @author Vincent Luo
 */
public class AuthenticationTokenGenerator {

    /**
     * 过期时间
     */
    private final long expireTimeSeconds;

    /**
     * 加密算法
     */
    private final Algorithm algorithm;

    /**
     * jwt过期时间
     */
    private static final String JWTMAP_EXPIRE = "VEXP";

    /**
     * HS256加密
     */
    public static final String SIGNATURE_ALGORITHM_OF_HS256 = "HS256";

    /**
     * RS256加密
     */
    public static final String SIGNATURE_ALGORITHM_OF_RS256 = "HS256";

    /**
     * token发行者
     */
    private static final String TOKEN_ISSUER = "auth0";

    /**
     * 创建Token生成器，默认使用HS256加密方式
     */
    public AuthenticationTokenGenerator(String secret, long seconds) {
        // 设置加密算法
        this.algorithm = switch (SIGNATURE_ALGORITHM_OF_HS256) {
            case SIGNATURE_ALGORITHM_OF_HS256 -> Algorithm.HMAC256(secret);
            default -> throw new UnImplementsException("不支持的加密算法");
        };

        this.expireTimeSeconds = seconds;
    }

    public String createToken(String k0, Object v0) {
        return createToken(Maps.ofMap(k0, v0));
    }

    public String createToken(String k0, Object v0, String k1, Object v1) {
        return createToken(Maps.ofMap(k0, v0, k1, v1));
    }

    public String createToken(String k0, Object v0, String k1, Object v1, String k2, Object v2) {
        return createToken(Maps.ofMap(k0, v0, k1, v1, k2, v2));
    }

    /**
     * @return 创建后的 token 字符串
     */
    public String createToken(Map<String, Object> tokenClaims) {
        return JWT.create()
                .withIssuer(TOKEN_ISSUER)
                .withExpiresAt(calculateExpireTime())
                .withClaim("claims", tokenClaims)
                .sign(this.algorithm);
    }

    /**
     * @return 获取 token 数据
     */
    public Map<String, Object> getClaims(String token) {
        return JWT.decode(token)
                .getClaim("claims")
                .asMap();
    }

    /**
     * @return token是否过期
     */
    public boolean verifier(String token) {
        try {
            JWTVerifier verifierBuilder = JWT.require(this.algorithm)
                    .withIssuer(TOKEN_ISSUER)
                    .build();
            verifierBuilder.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /* 获取过期时间 */
    private Date calculateExpireTime() {
        return TimeUnits.SECONDS.plus((int) expireTimeSeconds);
    }

}
