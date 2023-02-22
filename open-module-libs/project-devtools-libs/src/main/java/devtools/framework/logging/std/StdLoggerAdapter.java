package devtools.framework.logging.std;

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

/* Creates on 2022/8/18. */

import devtools.framework.logging.Logger;
import devtools.framework.logging.LoggerAdapter;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
public class StdLoggerAdapter implements LoggerAdapter {
    @Override
    public Logger getLogger(String key) {
        return new StdLogger(key);
    }

    @Override
    public Logger getLogger(Class<?> key) {
        return new StdLogger(key.getName());
    }
}
