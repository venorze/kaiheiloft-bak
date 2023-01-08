package com.hantiansoft.spring.framework.configuration;

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

/* Creates on 2022/12/23. */

import com.hantiansoft.framework.R;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常拦截器。
 *
 * @author Vincent Luo
 */
@Configuration
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ExceptionHandlerExceptionResolver {

    /**
     * 业务异常捕获
     */
    @ExceptionHandler(value = Exception.class)
    public R<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();

        // 返回异常信息
        String message = e.getMessage();
        if (message.startsWith("-C")) {
            int space = message.indexOf(" ");
            String code = message.substring(2, space);
            return R.fail(code, message.substring(space));
        }

        return R.fail(message);
    }

    /**
     * 参数校验异常捕获
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R<String> methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        String errorMessage = "";
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        for (ObjectError objectError : errors) {
            errorMessage = objectError.getDefaultMessage();
        }
        return R.fail(errorMessage);
    }

}
