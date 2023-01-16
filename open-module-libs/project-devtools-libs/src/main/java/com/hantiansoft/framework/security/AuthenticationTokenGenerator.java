package com.hantiansoft.framework.security;

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

import com.hantiansoft.framework.collections.Maps;
import com.hantiansoft.framework.time.DateUtils;
import com.hantiansoft.framework.time.TimeUnits;
import io.jsonwebtoken.*;
import lombok.Getter;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TOKEN生成校验
 *
 * @author Vincent Luo
 */
public class AuthenticationTokenGenerator {
    private final AuthenticationSignatureAlgorithm authenticationSignatureAlgorithm;  /* 加密方式 */
    private final long expireTimeSeconds;            /* 过期时间 */
    private final String SECRET;                   /* 秘钥 */
    private final PublicKey PUBLIC_KEY;               /* 公钥 */
    private final PrivateKey PRIVATE_KEY;              /* 私钥 */

    /**
     * jwt过期时间
     */
    private static final String JWTMAP_EXPIRE = "VEXP";

    /**
     * 支持的加密算法列表
     */
    enum AuthenticationSignatureAlgorithm {
        RS256(SignatureAlgorithm.RS256),
        HS256(SignatureAlgorithm.HS256),
        ;

        @Getter
        private final SignatureAlgorithm signatureAlgorithm;

        AuthenticationSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
        }
    }

    /**
     * 创建Token生成器，默认使用HS256加密方式
     */
    public AuthenticationTokenGenerator(String secret, long seconds) {
        this("HS256", (null), (null), secret, seconds);
    }

    /**
     * 创建Token生成器，默认使用RS256加密方式
     */
    public AuthenticationTokenGenerator(PublicKey publicKey, PrivateKey privateKey, long seconds) {
        this("RS256", publicKey, privateKey, (null), seconds);
    }

    public AuthenticationTokenGenerator(String algorithm, PublicKey publicKey, PrivateKey privateKey, String secret, long seconds) {
        this.authenticationSignatureAlgorithm = AuthenticationSignatureAlgorithm.valueOf(algorithm);
        this.SECRET = secret;
        this.PUBLIC_KEY = publicKey;
        this.PRIVATE_KEY = privateKey;
        this.expireTimeSeconds = seconds;
    }

    public String create_token(String k0, Object v0) {
        return create_token(Maps.ofMap(k0, v0));
    }

    public String create_token(String k0, Object v0, String k1, Object v1) {
        return create_token(Maps.ofMap(k0, v0, k1, v1));
    }

    public String create_token(String k0, Object v0, String k1, Object v1, String k2, Object v2) {
        return create_token(Maps.ofMap(k0, v0, k1, v1, k2, v2));
    }

    /**
     * @return 创建后的 token 字符串
     */
    public String create_token(Map<String, Object> tokenClaims) {
        JwtBuilder builder = Jwts.builder().setExpiration(calculate_expire_time());

        // 设置过期时间
        Date exp = calculate_expire_time();
        builder.setExpiration(exp);

        if (tokenClaims == null)
            tokenClaims = new HashMap<>();

        tokenClaims.put(JWTMAP_EXPIRE, DateUtils.vfmt(exp)); // 设置过期时间
        builder.setClaims(tokenClaims);

        if (authenticationSignatureAlgorithm == AuthenticationSignatureAlgorithm.RS256)
            builder.signWith(authenticationSignatureAlgorithm.getSignatureAlgorithm(), PRIVATE_KEY);

        if (authenticationSignatureAlgorithm == AuthenticationSignatureAlgorithm.HS256)
            builder.signWith(authenticationSignatureAlgorithm.getSignatureAlgorithm(), SECRET);

        return builder.compact();
    }

    /**
     * @return 获取 token 数据
     */
    @SuppressWarnings("unchecked")
    public <T> T value_of(String token, String key) {
        JwtParser jwtParser = Jwts.parser();

        /* 使用不同的算法解析token */
        if (authenticationSignatureAlgorithm == AuthenticationSignatureAlgorithm.RS256)
            jwtParser.setSigningKey(PUBLIC_KEY);

        if (authenticationSignatureAlgorithm == AuthenticationSignatureAlgorithm.HS256)
            jwtParser.setSigningKey(SECRET);

        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        return (T) claims.get(key);
    }

    /**
     * @return token是否过期
     */
    public boolean validate(String token) {
        try {
            value_of(token, "_");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取token过期时间
     */
    public Date expire(String token) {
        return DateUtils.parse(value_of(token, JWTMAP_EXPIRE));
    }

    /* 获取过期时间 */
    private Date calculate_expire_time() {
        return TimeUnits.SECONDS.plus((int) expireTimeSeconds);
    }

}
