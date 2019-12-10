package com.moyu.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ZKManageImpl implements ZKManage {

    private static final String HOST = "localhost:2181,localhost:2182,localhost:2183";

    private static ZooKeeper zoo ;
    private static ZKConnection zkConnection ;

    public ZKManageImpl() {
        initialize();
    }

    private void initialize() {
        try {
            zkConnection = new ZKConnection() ;
            zoo = zkConnection.connect(HOST) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() throws InterruptedException {
        zkConnection.close();
    }

    @Override
    public void create(String path, byte[] data) throws KeeperException, InterruptedException {
        /***
         * zk创建一个节点有4个参数
         * path: 创建的路径
         * data: 节点数据, 字节数组
         * acl: 权限控制, 使用Ids.OPEN_ACL_UNSAFE开发权限即可
         * createMode: 节点类型, 持久化(PERSISTENT)|临时节点(EPHEMRAL)|持久化顺序节点(PERSISTENT_SEQUENTIAL)|临时顺序节点(EPHEMRAL_SEQUENTIAL)
         */
        zoo.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Override
    public Stat exits(String path, boolean watch) throws KeeperException, InterruptedException {
        return zoo.exists(path, watch) ;
    }

    @Override
    public Object getZNodeData(String path, boolean watchFlag) throws KeeperException, InterruptedException, UnsupportedEncodingException {

        // 判断节点是否存在
        if (exits(path, watchFlag) == null) throw new KeeperException.NoNodeException("节点不存在");

        /***
         * path: 节点路径
         * watch: 是否监听, true|false
         * stat: 一般不用管
         */
        byte[] b = zoo.getData(path, watchFlag, null) ;
        String data = new String(b, "UTF-8") ;
        return data ;
    }



    @Override
    public void update(String path, byte[] data) throws KeeperException, InterruptedException {

        Stat stat = exits(path, true);

        if (stat != null) {
            int version = zoo.exists(path, true).getVersion();
            /***
             * path: 节点路径
             * data: 更新的数据, 字节数组
             * version: 对应的版本, 如果是-1则忽略版本
             */
            zoo.setData(path, data, version);
        }
    }

    @Override
    public void delete(String path) throws KeeperException, InterruptedException {
        Stat stat = exits(path, true);
        if (stat != null) {
            int version = stat.getVersion();
            /***
             * path: 节点路径
             * version: 版本
             */
            zoo.delete(path, version);
        }
    }

    @Override
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        Stat stat = exits(path, true);
        if (stat == null) throw new KeeperException.NoNodeException("节点不存在");

        List<String> children = zoo.getChildren(path, true);
        return children;
    }
}
