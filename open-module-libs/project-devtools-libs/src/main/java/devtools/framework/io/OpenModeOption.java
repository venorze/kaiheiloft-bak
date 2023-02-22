package devtools.framework.io;

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

import lombok.Getter;

/**
 * @author Amaoai
 */
public enum OpenModeOption {

    OPEN_ONLY_READ("r"),

    OPEN_ONLY_WRITE("w"),

    OPEN_READ_AND_WRITE("rws"),
    ;

    @Getter
    private final String value;

    OpenModeOption(String value) {
        this.value = value;
    }

}
