/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcClientHandler.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月26日 下午4:24:41
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmstudio.rpc.model.RpcRequest;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
* TODO
* @ClassName: RpcClientHandler
* @author jason
*/
public class RpcClientHandler extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(RpcClientHandler.class);
	
	private Channel channel;
	
	/**
	 * 接收服务端数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
		
		logger.error("client caught exception:",cause);
	}
	
	
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
		this.channel = ctx.channel();
	}

	public void sendRequest(RpcRequest request){
		
		ChannelFuture future =  channel.writeAndFlush(request);
		
	}

	
}
