package com.amaoai.qiniu;

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
import com.amaoai.adapter.FILE;
import com.amaoai.adapter.SourcePolicy;
import com.amaoai.framework.StringUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

import java.io.InputStream;
import java.util.Properties;

/**
 * 七牛云，云储存SDK适配
 *
 * @author Vincent Luo
 */
public class QiniuSourcePolicy implements SourcePolicy {

    /**
     * 上传Token
     */
    private final String uploadToken;

    /**
     * 上传管理对象
     */
    private final UploadManager uploadManager;

    /**
     * 空间名称
     */
    private final String bucket;

    /**
     * 文件管理器
     */
    private final BucketManager bucketManager;

    /**
     * 初始化七牛云服务
     *
     * @param props 配置属性，属性列表如下：
     *              - access KEY
     *              - secret 密钥
     *              - bucket 空间名称
     */
    public QiniuSourcePolicy(Properties props) {
        // 创建认证对象
        String access = props.getProperty("access");
        String secret = props.getProperty("secret");
        Auth auth = Auth.create(access, secret);

        // 获取上传Token
        this.bucket = props.getProperty("bucket");
        this.uploadToken = auth.uploadToken(this.bucket);

        // 创建上传管理对象
        Configuration um_cfg = new Configuration();
        this.uploadManager = new UploadManager(um_cfg);

        // 创建文件管理对象
        Configuration bm_cfg = new Configuration();
        this.bucketManager = new BucketManager(auth, bm_cfg);
    }

    @Override
    public String putFile(String filepath, String fmt, Object... args) {
        String finalFilePath = StringUtils.vfmt(fmt, args);

        try {
            Response response =
                    this.uploadManager.put(filepath, finalFilePath, this.uploadToken);
            System.out.println(JSON.toJSONString(response));
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }

        return finalFilePath;
    }

    @Override
    public String putBytes(byte[] byteBuf, String fmt, Object... args) {
        String finalFilePath = StringUtils.vfmt(fmt, args);

        try {
            Response response =
                    this.uploadManager.put(byteBuf, finalFilePath, this.uploadToken);
            System.out.println(JSON.toJSONString(response));
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }

        return finalFilePath;
    }

    @Override
    public String putInputStream(InputStream inputStream, String fmt, Object... args) {
        String finalFilePath = StringUtils.vfmt(fmt, args);

        try {
            Response response =
                    this.uploadManager.put(inputStream, finalFilePath, this.uploadToken, null, null);
            System.out.println(JSON.toJSONString(response));
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }

        return finalFilePath;
    }

    @Override
    public FILE stat(String filepath) {
        FILE file = null;

        try {
            FileInfo stat = this.bucketManager.stat(this.bucket, filepath);
            if (stat != null)
                file = new QiniuFILEInfo(stat);
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    @Override
    public String move(String srcPath, String distPath) {
        try {
            Response response =
                    this.bucketManager.move(this.bucket, srcPath, this.bucket, distPath);
            System.out.println(JSON.toJSONString(response));
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }

        return distPath;
    }

}
