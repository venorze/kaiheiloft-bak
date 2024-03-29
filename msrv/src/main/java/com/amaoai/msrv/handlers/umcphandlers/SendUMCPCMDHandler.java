package com.amaoai.msrv.handlers.umcphandlers;

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

import com.amaoai.framework.logging.Logger;
import com.amaoai.framework.logging.LoggerFactory;
import com.amaoai.msrv.handlers.UMCPCMDHandlerAdapter;
import com.amaoai.msrv.handlers.UMCPCMDHandlerMark;
import com.amaoai.msrv.handlers.contxt.SessionConnectionHandlerContext;
import com.amaoai.msrv.protocol.umcp.UMCPCMD;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;
import com.amaoai.msrv.protocol.umcp.attch.UserMessage;

/**
 * 消息接收处理器
 *
 * @author Vincent Luo
 */
@UMCPCMDHandlerMark(cmd = UMCPCMD.SEND)
public class SendUMCPCMDHandler extends UMCPCMDHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(SendUMCPCMDHandler.class);

    @Override
    public void handler(UMCProtocol umcp, SessionConnectionHandlerContext session) {
        try {
            // 拿到聊天消息
            UserMessage message = umcp.attach();
            message.setSender(session.owner());
            // 获取在线的客户端
            var onlinesession = SessionConnectionHandlerContext.online(message.getReceiver());
            if (onlinesession != null)
                onlinesession.writeAndFlush(umcp).sync();
            // 回复客户端服务器已收到消息
            autoack(umcp, UMCPCMD.ACK, session);
        } catch (Throwable e) {
            LOG.error("SendUMCPCMDHandler", e);
        }
    }

}
