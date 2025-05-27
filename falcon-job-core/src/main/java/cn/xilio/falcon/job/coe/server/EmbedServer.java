package cn.xilio.falcon.job.coe.server;

import cn.xilio.falcon.job.coe.LifeCycle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EmbedServer implements LifeCycle {
    private Thread serverThread;

    @Override
    public void init() {

    }

    @Override
    @SuppressWarnings("all")
    public void start() {
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                      final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                            0,
                            200,
                            60L,
                            TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(2000),
                            r -> new Thread(r, "FalconJob-server-" + r.hashCode()),
                            (r, executor) -> {
                                throw new RuntimeException("falcon-job, EmbedServer threadPool is EXHAUSTED!");
                            });
                    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
                    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
                    ServerBootstrap serverBootstrap = new ServerBootstrap();
                    serverBootstrap
                            .group(bossGroup, workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel channel) throws Exception {
                                    channel.pipeline().addLast(new IdleStateHandler(0, 0, 30 * 3, TimeUnit.SECONDS))
                                            .addLast(new HttpServerCodec())
                                            .addLast(new HttpObjectAggregator(1024 * 1024 * 5))
                                            .addLast(new EmbedHttpServerHandler(threadPoolExecutor,null));
                                }
                            }).childOption(ChannelOption.SO_KEEPALIVE, true);

                    ChannelFuture future = serverBootstrap.bind(7100).sync();
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    @Override
    public void stop() {
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.interrupt();
        }
    }
}
