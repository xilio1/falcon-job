package cn.xilio.falcon.job.coe.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuratorConfig {
    private static final String ZK_CONNECTION_STRING = "localhost:2181,localhost:2182,localhost:2183";

    @Bean
    public CuratorFramework curatorFramework() {
        CuratorFramework client = CuratorFrameworkFactory.
                builder().connectString(ZK_CONNECTION_STRING).
                sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("")
                .build();
        client.start();
        return client;
    }

    @Bean
    public DisposableBean curatorFrameworkCleaner(CuratorFramework curatorFramework) {
        return () -> {
            if (curatorFramework != null) {
                curatorFramework.close();
            }
        };
    }
}
