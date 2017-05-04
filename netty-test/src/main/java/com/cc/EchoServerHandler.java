package com.cc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 */

/**
 * Title: com.cc.EchoServerHandler
 * Description: com.cc.EchoServerHandler
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2017/3/4
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter { // (1)
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        ByteBuf in = (ByteBuf) msg;

/*            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }*/
            //或者下面这样
            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));

            ctx.write(msg); // (1)
            ctx.flush(); // (2)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
