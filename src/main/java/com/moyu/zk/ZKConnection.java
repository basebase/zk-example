package com.moyu.zk;

import org.apache.zookeeper.ZooKeeper;
import java.io.IOException;

public class ZKConnection {

    private ZooKeeper zoo ;

    /***
     * 创建链接
     * @param host ZK服务地址
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ZooKeeper connect(String host) throws IOException, InterruptedException {
        /**
         * 在创建ZooKeeper对象时, 需要提供三个参数
         * connectString: ZK主机地址信息
         * sessionTimeout: 会话超时时间
         * watcher: 监听器, 由于zk的创建过程是异步的，这里可以通过注册一个监视器, 监听zk状态
         */

        zoo = new ZooKeeper(host, 3000, (watchedEvent) -> {
            System.out.println("State: " + watchedEvent.getState()); // 事件状态
            System.out.println("Type: " + watchedEvent.getType()); // 事件类型
            System.out.println("Path: " + watchedEvent.getPath()); // 事件发生的路径
            System.out.println("================================");
        });

        return zoo ;
    }

    // 关闭链接
    public void close() throws InterruptedException {
        zoo.close();
    }
}
