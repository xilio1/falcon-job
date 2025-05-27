package cn.xilio.falcon.job.coe.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.concurrent.*;

public class EmbedHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final ThreadPoolExecutor threadPoolExecutor;
    private final String accessToken;


    public EmbedHttpServerHandler(ThreadPoolExecutor threadPoolExecutor, String accessToken) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.accessToken = accessToken;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println(msg);
    }
}
