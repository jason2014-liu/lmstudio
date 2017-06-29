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

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmstudio.rpc.model.RpcRequest;
import com.lmstudio.rpc.model.RpcResponse;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
* TODO 
* 发送数据和接收数据不是同一个线程，但是一次请求响应，用的是同一个channel(客户端服务端都是一样)；
* 发送：对一个服务器，客户端可以有多个channel，connect一次创建一个channel，一个channel对应一套 handler对象；
* 也就是说一个handler对象只属于一个channel，rpc请求的返回数据最终是在业务处理handler中接收，，故可以把发送操作也写在同一个handler中（调用channel的对应方法）
* ，便于将一个请求的请求和响应数据集中处理，也便于异步操作处理
* @ClassName: RpcClientHandler
* @author jason
*/
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
	


	private static Logger logger = LoggerFactory.getLogger(RpcClientHandler.class);
	
	/**
	 * 一个channel的所有待处理请求，在发送的时候记录请求ID、请求数据，返回结果时记录对应请求ID的结果数据，并从map中移除，，对map的容量处理暂时未考虑
	 */
	 private ConcurrentHashMap<String, RpcResult> pendingRpc = new ConcurrentHashMap<String, RpcResult>();
	 
	 /**
	  * 关联的channel
	  */
	 private volatile Channel channel;
	 
	 /**
	  * 服务端信息
	  */
	 private SocketAddress remotePeer;
	 
	 

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
		this.channel = ctx.channel();
		
	}



	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		this.remotePeer = ctx.channel().remoteAddress();
		
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		logger.error("clientHandler cause exception", cause);
		ctx.close();
		//super.exceptionCaught(ctx, cause);
	}



	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
		// TODO Auto-generated method stub
		
		RpcResult rpcResult = pendingRpc.get(msg.getRequestId());
		if(rpcResult != null){
			logger.debug("请求ID为{}的返回结果是：{}",msg.getRequestId(),msg.getResult());
			rpcResult.setResponse(msg);
		}else{
			logger.debug("没有找到请求ID={}的结果数据,出现异常",msg.getRequestId());
		}
			
		
		//super.channelRead(ctx, msg);
	}
	
	
	public RpcResult sendRequest(final RpcRequest request){
		RpcResult rpcResult = new RpcResult(request,System.currentTimeMillis());
		pendingRpc.put(request.getRequestId(), rpcResult);
		try {
			channel.writeAndFlush(request).sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
//			
//			@Override
//			public void operationComplete(ChannelFuture future) throws Exception {
//				// TODO Auto-generated method stub
//				logger.debug("请求发送成功，请求ID={}",request.getRequestId());
//			}
//		});
		return rpcResult;
	}
	
	
	

	
}
