package com.hantiansoft.msrv.socket

import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

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

/* Creates on 2023/2/18. */

/**
 * socket事件处理
 *
 * @author Vincent Luo
 */
class MsrvNioSocketChannel extends ChannelInboundHandlerAdapter {

  /**
   * 触发读事件
   */
  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    println(msg)
  }

}
