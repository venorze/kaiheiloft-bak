package com.amaoai.kaiheiloft.mods.modx;

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

/* Creates on 2023/2/4. */

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Vincent Luo
 */
@Data
public class GroupApplyJoinModx {

    /**
     * 俱乐部ID
     */
    @NotNull(message = "俱乐部ID不能为空")
    private Long groupId;

    /**
     * 申请备注
     */
    @Length(max = 50, message = "申请备注字符长度不能大于50个字符")
    private String requestRemark;

}
