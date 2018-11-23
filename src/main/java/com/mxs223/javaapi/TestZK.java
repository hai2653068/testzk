package com.mxs223.javaapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName TestZK
 * @AUTHOR MXS
 * @Date 2018/11/23 18:59
 * @Version 1.0
 **/
public class TestZK  implements Watcher {

    private String connectString = "www.mxs223.com:2181";
    private int sessionTimeout = 1000;
    private ZooKeeper zk = null;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public TestZK() {
    }

    public TestZK(String connectString,int sessionTimeout){
        this.connectString = connectString;
        this.sessionTimeout = sessionTimeout;
    }
    public static void main(String[] args){
        TestZK testZK = new TestZK();
        testZK.connection();
    }

    public void connection(){
        try {
            zk = new ZooKeeper(connectString,sessionTimeout,this);
            System.out.println("zk 状态"+zk.getState());
            countDownLatch.await();
            System.out.println("zk 状态"+zk.getState());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println("监听事件："+watchedEvent.getState());
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            countDownLatch.countDown();
        }

    }
}
