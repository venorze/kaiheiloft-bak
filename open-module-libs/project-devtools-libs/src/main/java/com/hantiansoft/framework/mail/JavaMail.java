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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * TODO 邮件发送
 *
 * @author Vincent Luo
 */
public class JavaMail {

    private final Session session;

    private final MailSessionProperties mailSessionProperties;

    /**
     * 创建Mail Session
     */
    private static Session createMailSession(MailSessionProperties mailSessionProperties) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailSessionProperties.getSmtpHost());
        properties.put("mail.smtp.port", mailSessionProperties.getPort());
        properties.put("mail.smtp.auth", String.valueOf(mailSessionProperties.getAuth()));
        properties.put("mail.smtp.starttls.enable", String.valueOf(mailSessionProperties.getEnableTLS()));

        Session sessionInstance = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSessionProperties.getUser(), mailSessionProperties.getPasswd());
            }
        });

        // 开启Debug模式
        if (mailSessionProperties.getEnableDebug())
            sessionInstance.setDebug(true);

        return sessionInstance;
    }

    public JavaMail(MailSessionProperties mailSessionProperties) {
        this.session = createMailSession(mailSessionProperties);
        this.mailSessionProperties = mailSessionProperties;
    }

    /**
     * 发送邮件
     */
    public void send(MailMessage mailMessage) {

        try (Transport transport = this.session.getTransport()) {
            transport.connect(
                    mailSessionProperties.getSmtpHost(),
                    mailSessionProperties.getUser(),
                    mailSessionProperties.getPasswd()
            );

            // 创建邮件对象
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailSessionProperties.getUser()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailMessage.getTo()));
            message.setSubject(mailMessage.getSubject());
            message.setContent(mailMessage.getContent(), "text/html;charset=UTF-8");

            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        MailSessionProperties msp = new MailSessionProperties();
        msp.setSmtpHost("smtp.office365.com");
        msp.setUser("silverfirework@outlook.com");
        msp.setPasswd("XXXXXXX");
        msp.setPort(587);
        msp.setEnableTLS(true);
        msp.setEnableDebug(true);

        JavaMail javaMail = new JavaMail(msp);

        MailMessage mm = new MailMessage();
        mm.setTo("silverfirework@foxmail.com");
        mm.setSubject("Authorize邮件注册认证");
        mm.setContent("验证码【12093】请不要告诉他人");

        javaMail.send(mm);
    }

}
