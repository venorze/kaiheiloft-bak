package com.hantiansoft.kaiheiloft.mods.modx;

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

/* Creates on 2023/2/6. */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * @author Vincent Luo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateClubChannelModx extends ClubIdModx {

    /**
     * 频道名称
     */
    @NotBlank(message = "频道名称不能为空")
    @Length(min = 2, max = 20, message = "频道名称范围 2 - 8 个字符")
    private String name;

    @NotBlank(message = "频道类型不能为空")
    @Pattern(regexp = "^|T|V|$", message = "频道类型不合法")
    private String type;

}
