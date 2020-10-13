package com.master.danmu;

import com.master.danmu.channel.DouyuClientInitializer;
import com.master.danmu.channel.WebSocketClientHandler;
import com.master.danmu.constants.DouYuApi;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;
import java.net.URI;
import java.net.URISyntaxException;

public class Application {

    static final String URL = System.getProperty("url", "wss://danmuproxy.douyu.com:8503/");

    public static void main(String[] args) throws InterruptedException, URISyntaxException, SSLException {
        URI uri = new URI(URL);
        String scheme = uri.getScheme() == null? "ws" : uri.getScheme();
        final String host = uri.getHost() == null? "127.0.0.1" : uri.getHost();
        final int port;
        if (uri.getPort() == -1) {
            if ("ws".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("wss".equalsIgnoreCase(scheme)) {
                port = 443;
            } else {
                port = -1;
            }
        } else {
            port = uri.getPort();
        }

        if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
            System.err.println("Only WS(S) is supported.");
            return;
        }

        final boolean ssl = "wss".equalsIgnoreCase(scheme);
        final SslContext sslCtx;
        if (ssl) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup boss = new NioEventLoopGroup();
        try {

            final WebSocketClientHandler handler = new WebSocketClientHandler(
                    WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13,null,true,new DefaultHttpHeaders()));
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(boss)
                    .channel(NioSocketChannel.class)
                    //.option(ChannelOption.TCP_NODELAY, true)
                    .handler(new DouyuClientInitializer(handler,sslCtx,host,port));

            Channel channel = bootstrap.connect(uri.getHost(), port).sync().channel();
            handler.handshakeFuture().sync();
            System.out.println("login");
            channel.writeAndFlush(String.format(DouYuApi.LOGIN_REQ,"1221923"));
            System.out.println("join_group");
            channel.writeAndFlush(String.format(DouYuApi.JOIN_GROUP,"1221923","-9999"));
            while (true) {
                //发送心跳
                //System.out.println("发送心跳包");
                Thread.sleep(30000);
                channel.writeAndFlush(DouYuApi.KEEP_LIVE);
            }
        } finally {
            boss.shutdownGracefully();
        }

    }
}
