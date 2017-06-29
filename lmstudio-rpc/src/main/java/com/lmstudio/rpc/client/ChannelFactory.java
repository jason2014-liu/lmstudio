/**
* TODO
* @Project: lmstudio-rpc
* @Title: ChannelFactory.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月27日 下午3:26:04
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmstudio.rpc.model.RpcRequest;
import com.lmstudio.rpc.model.RpcResponse;
import com.lmstudio.rpc.serialize.RpcDecoder;
import com.lmstudio.rpc.serialize.RpcEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * TODO
 * 
 * @ClassName: ChannelFactory
 * @author jason
 */
public class ChannelFactory {

	private static Logger logger = LoggerFactory.getLogger(ChannelFactory.class);

	private static ChannelFactory channeFactory = new ChannelFactory();// 单例

	public  InetSocketAddress default_node = null;

	private Map<InetSocketAddress, RpcClientHandler> connectedServerNodes = new ConcurrentHashMap<>();

	private Map<InetSocketAddress, Channel> channels = new ConcurrentHashMap<InetSocketAddress, Channel>();

	public ChannelFactory() {
		default_node = new InetSocketAddress("127.0.0.1", 18888);
		/**
		 * 初始化连接
		 */
		init();
	}

	public static ChannelFactory getInstance() {
		return channeFactory;
	}

	private void init() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workerGroup);
		b.channel(NioSocketChannel.class);
		// b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline cp = ch.pipeline();
				cp.addLast(new LoggingHandler(LogLevel.DEBUG));
				cp.addLast(new RpcEncoder(RpcRequest.class));
				cp.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
				cp.addLast(new RpcDecoder(RpcResponse.class));
				cp.addLast(new RpcClientHandler());
			}
		});
		
		try {
			ChannelFuture f = b.connect(default_node).sync();
			connectedServerNodes.put(default_node, f.channel().pipeline().get(RpcClientHandler.class));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

//		ChannelFuture f = b.connect(DEFAULT_NODE);
//
//		f.addListener(new ChannelFutureListener() {
//			@Override
//			public void operationComplete(ChannelFuture cfuture) throws Exception {
//
//				if (cfuture.isSuccess()) {
//					logger.debug("客户端成功连接到服务端{}", DEFAULT_NODE.toString());
//					channels.put(DEFAULT_NODE, cfuture.channel());
//					connectedServerNodes.put(DEFAULT_NODE, cfuture.channel().pipeline().get(RpcClientHandler.class));
//				} else {
//					logger.debug("客户端连接到服务端{}出现异常", DEFAULT_NODE.toString());
//					cfuture.channel().pipeline().fireExceptionCaught(cfuture.cause());
//				}
//
//			}
//
//		});
//
//		f.addListener(ChannelFutureListener.CLOSE);

	}

	/**
	 * TODO
	 * 
	 * @Title: getChannel
	 * @param ip
	 * @return
	 */
	public Channel getChannel(InetSocketAddress addr) {
		return channels.get(addr);
	}

	public RpcClientHandler getClientHandler(InetSocketAddress addr) {

		return connectedServerNodes.get(addr);
	}

}
