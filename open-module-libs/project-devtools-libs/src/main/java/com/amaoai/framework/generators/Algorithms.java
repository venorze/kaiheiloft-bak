package com.amaoai.framework.generators;

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

/* Creates on 2022/9/19. */

import com.amaoai.framework.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 加密解密算法，以及常用算法整合
 *
 * @author Vincent Luo
 */
public class Algorithms {

    ////////////////////////////////////////////////////////////
    /// 编码算法、加密/解密
    ////////////////////////////////////////////////////////////

    /**
     * @return BASE64 加密
     */
    public static String BASE64(byte[] src) {
        return new String(Base64.getEncoder().encode(src));
    }

    /**
     * @return BASE64 解密
     */
    public static byte[] XBASE64(String base64text) {
        return Base64.getDecoder().decode(base64text.getBytes());
    }

    /**
     * @return 将文本加密成md5
     */
    public String MD5(String plaintext) {
        String ciphertext = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(plaintext.getBytes(StandardCharsets.UTF_8));
            ciphertext = new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ciphertext;
    }

    /**
     * 去除密钥中的开头和结尾以及换行符，并转成byte[]
     */
    private static byte[] INITKEY(String key) {
        if (key.contains("-----BEGIN PRIVATE KEY-----")) {
            key = key.substring(key.indexOf("-----BEGIN PRIVATE KEY-----") + 27);
        }
        if (key.contains("-----BEGIN PUBLIC KEY-----")) {
            key = key.substring(key.indexOf("-----BEGIN PUBLIC KEY-----") + 26);
        }
        if (key.contains("-----END PRIVATE KEY-----")) {
            key = key.substring(0, key.indexOf("-----END PRIVATE KEY-----"));
        }
        if (key.contains("-----END PUBLIC KEY-----")) {
            key = key.substring(0, key.indexOf("-----END PUBLIC KEY-----"));
        }
        key = key.replaceAll("\r\n", "");
        key = key.replaceAll("\n", "");

        return XBASE64(StringUtils.toline(key));
    }

    /**
     * 将文本反序列化成RS256公钥
     */
    public static PublicKey PUBLIC_KEY_RSA256(String key) {
        try {
            /* 序列化公钥 */
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(INITKEY(key));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(pubKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将文本反序列化成RS256私钥
     */
    public static PrivateKey PRIVATE_KEY_RSA256(String key) {
        try {
            /* 序列化私钥 */
            PKCS8EncodedKeySpec pubKeySpec = new PKCS8EncodedKeySpec(INITKEY(key));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePrivate(pubKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
