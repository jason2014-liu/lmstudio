/**
* TODO
* @Project: lmstudio-rpc
* @Title: ServiceRegistry.java
* @Package com.lmstudio.rpc.registry
* @author jason
* @Date 2017年7月17日 上午11:52:26
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.registry;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* TODO
* @ClassName: ServiceRegistry
* @author jason
*/
public class ServiceRegistry{

	private static final Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);
	
	public static final int ZK_SESSION_TIMEOUT = 5000;
	public static final String ZK_REGISTRY_PATH = "/registry";
	public static final String ZK_DATA_PATH = "/registry/data";
	
	private CountDownLatch latch = new CountDownLatch(1);

	private String registryAddress;

	public ServiceRegistry(String registryAddress) {
		this.registryAddress = registryAddress;
	}
	
	public void register(String data){
		
		if(data != null){
			ZooKeeper zk = connectServer();
			if(zk != null){
				addRootNode(zk);
				createNode(zk, data);
			}
		}
	}
	
	/**
	* TODO
	* @Title: connectServer
	* @return
	 */
	private ZooKeeper connectServer(){
		
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(registryAddress, ZK_SESSION_TIMEOUT, new Watcher() {
				
				@Override
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
					if(event.getState() == Event.KeeperState.SyncConnected){
						latch.countDown();
					}
				}
			});
			
			latch.await();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("连接zookeeper 异常",e);
		}
		return zk;
	}
	
	private void addRootNode(ZooKeeper zk){
		try {
			Stat stat = zk.exists(ZK_REGISTRY_PATH, false);
			if(stat == null){
				zk.create(ZK_REGISTRY_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	private void createNode(ZooKeeper zk, String data){
		try {
			String path = zk.create(ZK_DATA_PATH, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			logger.debug("create zookeeper node {}=>{}",path,data);
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

}
