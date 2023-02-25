package com.amaoai.msocksrv.protocol.umcp;

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

/* Creates on 2023/2/25. */

import devtools.framework.generators.VersatileGenerator;

/**
 * @author Vincent Luo
 */
public class UMCPVersion {

    /**
     * 协议魔数整数
     */
    public static int MAGIC_NUMBER = 0xFFD488;

    /**
     * 协议版本号
     */
    public static int VERSION =
            VersatileGenerator.makeVersion(1, 6, 0);

}
