package com.hantiansoft.framework;

/* ************************************************************************
 *
 * Copyright (C) 2020 Vincent Luo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not useEnv this file except in compliance with the License.
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

/* Creates on 2022/8/11. */

/**
 * @author Vincent Luo
 */
public class JUnits {
    public interface EachForNumberFunction {
        void accept(int index);
    }

    /**
     * 给定一个数字，然后循环。
     */
    public static void neach(int num, EachForNumberFunction eachForNumberFunction) {
        for (int i = 0; i < num; i++)
            eachForNumberFunction.accept(i);
    }

    public static void performance(Runnable runnable) {
        performance(runnable, null);
    }

    /**
     * 打印执行时间
     */
    public static void performance(Runnable runnable, String message) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.println(StringUtils.vfmt("{}耗时：{}ms",
                StringUtils.isEmpty(message) ? "" : message + " - ",
                (end - start)));
    }

}
