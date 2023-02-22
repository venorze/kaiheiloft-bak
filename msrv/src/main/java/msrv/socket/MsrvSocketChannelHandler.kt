package msrv.socket

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandler
import stdlibkt.toLong

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
class MsrvSocketChannelHandler : ChannelInboundHandler {

    override fun handlerAdded(ctx: ChannelHandlerContext) {
    }

    override fun handlerRemoved(ctx: ChannelHandlerContext) {
        
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        
    }

    override fun channelRegistered(ctx: ChannelHandlerContext) {

    }

    override fun channelUnregistered(ctx: ChannelHandlerContext) {
        toLong(111)
    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        println("客户端连接")
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any?) {
        println("收到来自客户端的消息：$msg")
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        
    }

    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        
    }

    override fun channelWritabilityChanged(ctx: ChannelHandlerContext) {
        
    }

}