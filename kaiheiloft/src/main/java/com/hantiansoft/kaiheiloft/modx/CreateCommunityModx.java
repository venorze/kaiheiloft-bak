package com.hantiansoft.kaiheiloft.modx;

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

/* Creates on 2023/1/13. */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 创建社区请求对象
 *
 * @author Vincent Luo
 */
@Data
public class CreateCommunityModx {

    /**
     * 社区名称
     */
    @NotBlank(message = "社区名称是必填项")
    @Length(min = 2, max = 8, message = "社区名称范围 2 - 8 个字符")
    private String name;

    /**
     * 社区头像
     */
    private String avatar;

    /**
     * 社区标签
     */
    @Size(max = 5, message = "标签最多可选择5个")
    private List<String> tags;

}
