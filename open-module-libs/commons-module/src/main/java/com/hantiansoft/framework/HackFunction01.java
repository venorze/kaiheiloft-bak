package com.hantiansoft.framework;

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

/**
 * 标志位解释(NOTE)：
 *
 *    假设函数接口名叫做 HackFunction01 第一位表示有无返回值。0 表示无返回值，1表示有返回值。
 *  第二位表示参数长度，0 - 9
 *
 * @author Vincent Luo
 */
public interface HackFunction01<R> {
    R call();
}
