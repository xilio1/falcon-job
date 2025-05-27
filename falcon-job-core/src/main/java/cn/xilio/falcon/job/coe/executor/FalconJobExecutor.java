package cn.xilio.falcon.job.coe.executor;

import cn.xilio.falcon.job.coe.server.EmbedServer;

public class FalconJobExecutor {
    private EmbedServer embedServer;

    public void start() {
        embedServer = new EmbedServer();
    }


    public void destroy() {
        embedServer.stop();
    }
}
