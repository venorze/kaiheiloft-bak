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

/* Creates on 2023/1/9. */

import com.hantiansoft.adapter.SourcePolicy;
import com.hantiansoft.framework.Asserts;
import com.hantiansoft.framework.R;
import com.hantiansoft.framework.exception.BusinessException;
import com.hantiansoft.framework.generators.VGenerator;
import com.hantiansoft.framework.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件上传相关的控制器
 *
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/put")
public class UploadController {

    @Autowired
    private SourcePolicy sourcePolicy;

    /**
     * 上传头像
     */
    @PostMapping("/avatar")
    public R<String> putAvatar(@RequestPart MultipartFile avatarFile) {
        if (avatarFile.getSize() > FileUtils.SIZE_OF_MB_2)
            throw new BusinessException("文件大小不能超过2MB");

        try {
            byte[] byteBuf = avatarFile.getBytes();
            return R.ok(sourcePolicy.putBytes(byteBuf, "avatar/{}", VGenerator.vsha256(byteBuf)));
        } catch (Exception e) {
            throw new BusinessException("文件上传失败，请重试", e);
        }
    }

}
