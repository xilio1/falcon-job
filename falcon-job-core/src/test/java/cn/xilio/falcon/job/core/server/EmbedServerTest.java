package cn.xilio.falcon.job.core.server;

import cn.xilio.falcon.job.coe.server.EmbedServer;

public class EmbedServerTest {
    public static void main(String[] args) throws InterruptedException {
        EmbedServer embedServer = new EmbedServer();
        embedServer.start();
        Thread.sleep(1000000000);
    }
}
