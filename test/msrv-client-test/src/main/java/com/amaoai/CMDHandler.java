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

    public static void login(ChannelHandlerContext ctx, String attach) {
        ctx.writeAndFlush(new UMCProtocol(new UserAuthorization(attach), UMCPCMD.SIGN_IN_SEND));
    }

    public static void send(ChannelHandlerContext ctx, String attach) {
        UserMessage userMessage = new UserMessage();
        userMessage.setReceiver("R00001");
        userMessage.setType(UserMessageType.TEXT);
        userMessage.setMessage(attach);
        ctx.writeAndFlush(new UMCProtocol(userMessage, UMCPCMD.SEND));
    }

}
