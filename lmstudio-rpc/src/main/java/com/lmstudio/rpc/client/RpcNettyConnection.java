/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcNettyConnection.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月26日 下午5:05:55
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

import java.net.InetSocketAddress;

import com.lmstudio.rpc.model.RpcRequest;

import io.netty.channel.Channel;

/**
 * TODO
 * 
 * @ClassName: RpcNettyConnection
 * @author jason
 */
public class RpcNettyConnection implements RpcConnection {

	private InetSocketAddress inetAddr;

	private volatile Channel channel;

	private RpcClientHandler handler;

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object send(RpcRequest request, boolean async) {
		// TODO Auto-generated method stub
		return null;
	}

}
