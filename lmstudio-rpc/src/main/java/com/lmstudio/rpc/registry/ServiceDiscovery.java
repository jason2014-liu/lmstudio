/**
* TODO
* @Project: lmstudio-rpc
* @Title: ServiceDiscovery.java
* @Package com.lmstudio.rpc.registry
* @author jason
* @Date 2017年7月17日 下午3:07:08
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.registry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* TODO
* @ClassName: ServiceDiscovery
* @author jason
*/
public class ServiceDiscovery {

	private static final Logger logger = LoggerFactory.getLogger(ServiceDiscovery.class);
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	//服务列表
	private volatile List<String> dataList = new ArrayList<String>();
	
	private String registryAddress;

	public ServiceDiscovery(String registryAddress) {
		this.registryAddress = registryAddress;
		ZooKeeper zk = connectServer();
		if(zk != null){
			watchNode(zk);
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
			zk = new ZooKeeper(registryAddress, ServiceRegistry.ZK_SESSION_TIMEOUT, new Watcher() {
				
				@Override
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
					if(event.getState() == Event.KeeperState.SyncConnected){
						latch.countDown();
						logger.debug("已连上zookeeper {}",registryAddress);
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
	
	private void watchNode(final ZooKeeper zk){
		try {
			List<String> nodeList = zk.getChildren(ServiceRegistry.ZK_REGISTRY_PATH, new Watcher() {
				
				@Override
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
					if(event.getType() == Event.EventType.NodeChildrenChanged){
						watchNode(zk);
					}
				}
			});
			
			List<String> dataList = new ArrayList<String>();
			if(nodeList != null){
				for(String node : nodeList){
					byte[] bytes = zk.getData(ServiceRegistry.ZK_REGISTRY_PATH+"/"+node, false, null);
					dataList.add(new String(bytes));
				}
			}
			logger.debug("node data:{}",dataList);
			this.dataList = dataList;
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> getDataList() {
		return dataList;
	}

	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}
	
	
	
}
