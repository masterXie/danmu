package com.master.danmu.codec;

import com.master.danmu.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class DouyuEncoder extends MessageToByteEncoder<String> {

    protected void encode(ChannelHandlerContext channelHandlerContext, String message, ByteBuf byteBuf) throws Exception {
        System.out.println("out--"+message);
        Message msg = new Message(message);
        channelHandlerContext.write(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg.getBytes())));
    }

}
