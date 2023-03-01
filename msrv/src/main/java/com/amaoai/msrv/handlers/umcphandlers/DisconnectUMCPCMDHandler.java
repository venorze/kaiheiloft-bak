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

import com.amaoai.msrv.handlers.UMCPCMDHandlerAdapter;
import com.amaoai.msrv.handlers.UMCPCMDHandlerMark;
import com.amaoai.msrv.handlers.contxt.SessionConnectionHandlerContext;
import com.amaoai.msrv.protocol.umcp.UMCPCMD;
import com.amaoai.msrv.protocol.umcp.UMCProtocol;

/**
 * 处理客户端连接断开
 *
 * @author Vincent Luo
 */
@UMCPCMDHandlerMark(cmd = UMCPCMD.DISCONNECT)
public class DisconnectUMCPCMDHandler extends UMCPCMDHandlerAdapter {

    @Override
    public void handler(UMCProtocol umcp, SessionConnectionHandlerContext session) {
        // 删除redis缓存中的内容
        session.executeRedisOperation(ops -> ops.delByte(session.owner()));
        // 断开与客户端的连接
        SessionConnectionHandlerContext.markUnValidSessionChannelHandlerContext(session);
    }

}
