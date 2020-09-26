package com.master.danmu.channel;

import com.master.danmu.codec.DouyuEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

import java.nio.ByteOrder;

public class DouyuClientInitializer extends ChannelInitializer<SocketChannel> {

    public static WebSocketClientHandler webSocketClientHandler;
    public static SslContext sslCtx;
    public static String host;
    public static int port;
    public DouyuClientInitializer(WebSocketClientHandler webSocketClientHandler, SslContext sslCtx,String host,int port){
        DouyuClientInitializer.webSocketClientHandler = webSocketClientHandler;
        DouyuClientInitializer.sslCtx = sslCtx;
        DouyuClientInitializer.host = host;
        DouyuClientInitializer.port = port;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        if(sslCtx != null){
            pipeline.addLast("ssl",sslCtx.newHandler(socketChannel.alloc(),host,port));
        }
        pipeline.addLast("httpClientCodec",new HttpClientCodec());
        pipeline.addLast("aggregator",new HttpObjectAggregator(8192));
        pipeline.addLast("comprssion", WebSocketClientCompressionHandler.INSTANCE);
        //pipeline.addLast("logging",new LoggingHandler(LogLevel.INFO));
        pipeline.addLast("websocket",webSocketClientHandler);
        pipeline.addLast("framer",new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN,1024*1024*1024,0,4,0,12,true));
        pipeline.addLast("decode",new StringDecoder());
        pipeline.addLast("encode",new DouyuEncoder());
        pipeline.addLast("echo",new EchoChannel());

    }
}
