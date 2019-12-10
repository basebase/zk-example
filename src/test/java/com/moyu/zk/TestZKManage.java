package com.moyu.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class TestZKManage {

    private ZKManage zkManage ;

    @Before
    public void initZKManage() throws IOException, InterruptedException {
        zkManage = new ZKManageImpl() ;
    }


    @Test
    public void createConn() throws IOException, InterruptedException {
        ZKConnection zkConn = new ZKConnection();
        ZooKeeper connect = zkConn.connect("localhost:2181");
        System.out.println("conn -> " + connect);
    }


    @Test
    public void createNodes() throws KeeperException, InterruptedException {
        String p1 = "/xiaomoyu3";
        zkManage.create(p1, null);

        String p2 = "/xiaomoyu3/testdb";
        zkManage.create(p2, "yoho".getBytes());
    }

    @Test
    public void getNodeData() throws InterruptedException, UnsupportedEncodingException, KeeperException {
//        String p1 = "/xiaomoyu3" ;
//        Object zNodeData = zkManage.getZNodeData(p1, true);
//        System.out.println(zNodeData);

        String p2 = "/xiaomoyu3/testdb" ;
        Object zNodeData2 = zkManage.getZNodeData(p2, true);
        System.out.println(zNodeData2);
    }

    @Test
    public void setNodeData() throws KeeperException, InterruptedException, UnsupportedEncodingException {
        String p1 = "/xiaomoyu3/testdb" ;

        Object changeBefore = zkManage.getZNodeData(p1, true);
        System.out.println("数据修改之前 -> " + changeBefore);

        zkManage.update(p1, "i love you forever".getBytes());
        Object changeAfter = zkManage.getZNodeData(p1, true);
        System.out.println("数据修改之后 -> " + changeAfter);

        zkManage.update(p1, "爱你直到永远".getBytes());
        changeAfter = zkManage.getZNodeData(p1, true);
        System.out.println("数据修改之后 -> " + changeAfter);

    }

    @Test
    public void deleteNode() throws KeeperException, InterruptedException {
        String p1 = "/xiaomoyu3/testdb" ;
        zkManage.delete(p1);
    }

    @Test
    public void childNodes() throws KeeperException, InterruptedException {
        String p1 = "/" ;
        List<String> childs = zkManage.getChildren(p1);
        for (String child : childs) {
            System.out.println("|- " + child);
        }
    }
}
