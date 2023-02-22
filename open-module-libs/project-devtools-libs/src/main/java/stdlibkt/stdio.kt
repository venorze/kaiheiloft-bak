/* SuppressWarnings */
@file:Suppress("HasPlatformType", "NOTHING_TO_INLINE")

package stdlibkt

import devtools.framework.io.IOUtils

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

/* Creates on 2023/2/22. */

/** @author Amaoai */

/** 标准输入流 */
val stdin = IOUtils.stdin
/** 标准输出流 */
val stdout = IOUtils.stdout
/** 标准错误流 */
val stderr = IOUtils.stderr
