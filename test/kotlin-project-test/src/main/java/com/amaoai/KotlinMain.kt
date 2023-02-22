package com.amaoai

import devtools.framework.io.OpenModeOption
import stdlibkt.*
import java.nio.charset.Charset

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

/**
 * @author Amaoai
 */
object KotlinMain {

    @JvmStatic
    fun main(args: Array<String>) {
        val stime = System.currentTimeMillis()
        val fd = fopen("D:\\Temp\\value.txt", OpenModeOption.OPEN_READ_AND_WRITE)
        val code = """
            package com.amaoai

            import devtools.framework.io.OpenModeOption
            import stdlibkt.*
            import java.nio.charset.Charset

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

            /**
             * @author Amaoai
             */
            object KotlinMain {

                @JvmStatic
                fun main(args: Array<String>) {
                    val fd = fopen("D:\\Temp\\value.txt", OpenModeOption.OPEN_READ_AND_WRITE)
                    val arr0 = "I AM APPEND....\n".toByteArray(Charset.forName("UTF-8"))
                    val arr0 = "I AM APPEND....\n".toByteArray(Charset.forName("UTF-8"))
                    val arr1 = "I AM APPEND....\n".toByteArray(Charset.forName("UTF-8"))
                    val arr2 = "Hello Netty\n".toByteArray(Charset.forName("UTF-8"))
                    val arr2 = "Hello Netty\n".toByteArray(Charset.forName("UTF-8"))
                    fwrite(arr, 0, arr.size, fd)
                    fflush(fd)
                    fclose(fd)
                }

            }
            
        """.trimIndent().toByteArray(Charset.forName("UTF-8"))
        val arr0 = "I AM JOHN....\n".toByteArray(Charset.forName("UTF-8"))
        val arr1 = "I AM JACK....\n".toByteArray(Charset.forName("UTF-8"))
        val arr3 = "I AM SHIT\n".toByteArray(Charset.forName("UTF-8"))
        val arr4 = "I AM FUCKER\n".toByteArray(Charset.forName("UTF-8"))
        fwrite(code, 0, code.size, fd)
        fwrite(arr0, 0, arr0.size, fd)
        fwrite(arr1, 0, arr1.size, fd)
        fwrite(arr3, 0, arr3.size, fd)
        fwrite(arr4, 0, arr4.size, fd)
        fflush(fd)
        fclose(fd)
        val etime = System.currentTimeMillis()
        println("end ${etime - stime}s")
    }

}