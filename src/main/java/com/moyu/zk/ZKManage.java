package com.moyu.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ZKManage {
    public void create(String path, byte[] data) throws KeeperException, InterruptedException;
    public Object getZNodeData(String path, boolean watchFlag) throws KeeperException, InterruptedException, UnsupportedEncodingException;
    public Stat exits(String path, boolean watch) throws KeeperException, InterruptedException;
    public void update(String path, byte[] data) throws KeeperException, InterruptedException;
    public void delete(String path) throws KeeperException, InterruptedException;
    public List<String> getChildren(String path) throws KeeperException, InterruptedException;
    public void closeConnection() throws InterruptedException;
}
