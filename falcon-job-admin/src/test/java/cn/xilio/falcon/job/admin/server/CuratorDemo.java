package cn.xilio.falcon.job.admin.server;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

public class CuratorDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.
                builder().connectString("localhost:2181," + "localhost:2182,localhost:2183").
                sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("")
                .build();
        client.start();
        Stat stat = new Stat();
        InterProcessMutex lock = new InterProcessMutex(client, "/distributed-lock");
        // 模拟 5 个客户端（线程）竞争锁
        for (int i = 0; i < 5000; i++) {
            final int clientId = i;
            new Thread(() -> {
                try {
                    System.out.println("客户端 " + clientId + " 尝试获取锁...");
                    // 尝试获取锁，最大等待 10 秒
                    if (lock.acquire(10, TimeUnit.SECONDS)) {
                        try {
                            System.out.println("客户端 " + clientId + " 获取到锁，进入临界区");
                            // 模拟任务执行
                            Thread.sleep(2000);
                            System.out.println("客户端 " + clientId + " 完成任务");
                        } finally {
                            // 释放锁
                            lock.release();
                            System.out.println("客户端 " + clientId + " 释放锁");
                        }
                    } else {
                        System.out.println("客户端 " + clientId + " 获取锁超时");
                    }
                } catch (Exception e) {
                    System.err.println("客户端 " + clientId + " 发生异常: " + e.getMessage());
                }
            }, "Thread-" + i).start();
        }

        // 等待所有线程完成
        Thread.sleep(15000);


        client.close();
    }
}
