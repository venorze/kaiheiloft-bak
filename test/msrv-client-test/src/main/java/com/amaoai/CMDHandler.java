package com.amaoai;

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

/* Creates on 2023/2/27. */

import com.amaoai.msrv.protocol.umcp.UMCPCMD;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;
import com.amaoai.msrv.protocol.umcp.attch.UserAuthorization;
import com.amaoai.msrv.protocol.umcp.attch.UserMessage;
import com.amaoai.msrv.protocol.umcp.attch.UserMessageType;
import io.netty.channel.ChannelHandlerContext;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
public class CMDHandler {

    static UserAuthorization kaiheiloftAuthorization =
          new UserAuthorization("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImNsYWltcyI6eyJ1aWQiOjI4ODEwMDc4MDg4MjE2NTc2MCwidW5hbWUiOiJrYWloZWlsb2Z0In0sImV4cCI6MTY3Nzc5MDMxNH0.d_RJmuNuUd-olF8R9t0BgMdLElKbQ5srgb-m0LIXUnY");

    static UserAuthorization vincentAuthorization =
          new UserAuthorization("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImNsYWltcyI6eyJ1aWQiOjI4ODEwMDcxMjU2NTM0MjIwOCwidW5hbWUiOiJ2aW5jZW50In0sImV4cCI6MTY3Nzc5MDMxMn0.ZumkMaoetG1UX-AP4t1k1ZOWTSNVYPWM8tbRc09qHuk");

    public static void login(ChannelHandlerContext ctx, String attach) {
        UserAuthorization authorization = null;
        if ("k".equals(attach))
            authorization = kaiheiloftAuthorization;
        else if ("v".equals(attach))
            authorization = vincentAuthorization;

        ctx.writeAndFlush(new UMCProtocol(authorization, UMCPCMD.SIGN_IN_SEND));
    }

    public static void send(ChannelHandlerContext ctx, String attach) {
        String rec = attach.substring(0, attach.indexOf(" "));
        String msg = attach.substring(attach.indexOf(" ") + 1);
        UserMessage userMessage = new UserMessage();
        userMessage.setReceiver(Long.valueOf(rec));
        userMessage.setType(UserMessageType.TEXT);
        userMessage.setMessage(msg);
        ctx.writeAndFlush(new UMCProtocol(userMessage, UMCPCMD.SEND));
    }

}
