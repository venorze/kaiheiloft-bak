package com.hantiansoft.qiniu;

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

/* Creates on 2023/1/9. */

import com.alibaba.fastjson.JSON;
import com.hantiansoft.adapter.PutPolicy;
import com.hantiansoft.framework.StringUtils;
import com.hantiansoft.framework.io.ByteBuf;
import com.hantiansoft.framework.io.FileUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.InputStream;
import java.util.Properties;

/**
 * 七牛云，云储存SDK适配
 *
 * @author Vincent Luo
 */
public class QiniuPutPolicy implements PutPolicy {

    /**
     * 上传Token
     */
    private final String uploadToken;

    /**
     * 初始化七牛云服务
     *
     * @param props 配置属性，属性列表如下：
     *              - access KEY
     *              - secret 密钥
     *              - bucket 空间名称
     */
    public QiniuPutPolicy(Properties props) {
        // 创建认证对象
        String access = props.getProperty("access");
        String secret = props.getProperty("secret");
        Auth auth = Auth.create(access, secret);

        String bucket = props.getProperty("bucket");
        this.uploadToken = auth.uploadToken(bucket);
    }

    @Override
    public void putFile(String filepath, String fmt, Object... args) {
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);

        try {
            Response response = uploadManager.put(filepath, StringUtils.vfmt(fmt, args), uploadToken);
            System.out.println(JSON.toJSONString(response));
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putBytes(ByteBuf byteBuf, String fmt, Object... args) {

    }

    @Override
    public void putInputStream(InputStream inputStream, String fmt, Object... args) {

    }

}
