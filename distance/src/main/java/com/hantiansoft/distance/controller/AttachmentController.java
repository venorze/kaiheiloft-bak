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
import com.hantiansoft.distance.system.DistanceApplicationContext;
import com.hantiansoft.framework.R;
import com.hantiansoft.framework.exception.BusinessException;
import com.hantiansoft.framework.generators.VGenerator;
import com.hantiansoft.framework.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传相关的控制器
 *
 * @author Vincent Luo
 */
@RestController
@RequestMapping("/upload/attachment")
public class AttachmentController {

    @Autowired
    private SourcePolicy sourcePolicy;

    /**
     * 上传头像
     */
    @PutMapping("/avatar")
    public R<String> uploadAvatar(@RequestPart MultipartFile avatarFile) {
        if (avatarFile.getSize() > FileUtils.SIZE_OF_MB_2)
            throw new BusinessException("文件大小不能超过2MB");

        try {
            byte[] byteBuf = avatarFile.getBytes();
            String fileSha256 = VGenerator.vsha256(byteBuf);

            // 计算文件存放在哪个位置，随机取SHA256中的两个字符作为保存区域
            int len = fileSha256.length();
            int i = VGenerator.random_of_number(len - 2); // len - 2 避免随机到名称末尾

            // 上传文件
            String region = fileSha256.substring(i, i + 2);
            return R.ok(sourcePolicy.putBytes(byteBuf, "{}/{}/{}", DistanceApplicationContext.ATTACHMENT_OF_AVATAR, region, fileSha256));
        } catch (Exception e) {
            throw new BusinessException("文件上传失败，请重试", e);
        }
    }

}
