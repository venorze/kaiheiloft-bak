package com.hantiansoft.framework.mail;

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

import lombok.Data;

/**
 * 邮件发送消息对象
 *
 * @author Vincent Luo
 */
@Data
public class MailMessage {

    /**
     * 接收人
     */
    private String to;

    /**
     * 标题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

}
