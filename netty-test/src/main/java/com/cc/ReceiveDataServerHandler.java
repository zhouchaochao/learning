package com.cc;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 */


/**
 * Title: com.cc.ReceiveDataServerHandler
 * Description: com.cc.ReceiveDataServerHandler
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2017/3/4
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class ReceiveDataServerHandler extends ChannelInboundHandlerAdapter { // (1)
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        ByteBuf in = (ByteBuf) msg;
        try {
            //打印出结果
/*            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }*/
            //或者下面这样
            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));

        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
