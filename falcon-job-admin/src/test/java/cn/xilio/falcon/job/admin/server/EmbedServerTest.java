package cn.xilio.falcon.job.admin.server;

import cn.xilio.falcon.job.admin.utils.RemoteUtils;

public class EmbedServerTest {
    public static void main(String[] args) {
        Object o = RemoteUtils.postBody("http://localhost:7100/run",
                null,
                10000, null,
                null);
    }
}
