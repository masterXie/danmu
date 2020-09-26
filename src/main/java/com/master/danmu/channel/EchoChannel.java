package com.master.danmu.channel;

import com.master.danmu.utils.STTUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

public class EchoChannel extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        Map<String, Object> message = STTUtil.toMap(msg);
        if("chatmsg".equals(message.get("type").toString())){
            System.out.println(String.format("%s-->%s",message.get("nn").toString(),message.get("txt").toString()));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active...");
        super.channelActive(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active close");
        super.channelInactive(ctx);
    }
}
