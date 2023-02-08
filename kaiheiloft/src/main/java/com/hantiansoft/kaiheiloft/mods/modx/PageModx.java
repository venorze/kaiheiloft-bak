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

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 分页查询
 *
 * @author Vincent Luo
 */
@Data
public class PageModx {

    /**
     * 索引
     */
    @Min(value = 1, message = "索引能小于1")
    private Integer pageNo;

    /**
     * 条数
     */
    @Max(value = 100, message = "每页条数不能大于100")
    private Integer pageSize;

}
