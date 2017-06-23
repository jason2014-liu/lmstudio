/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcServer.java
* @Package com.lmstudio.rpc.server
* @author jason
* @Date 2017年6月23日 上午11:16:06
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.lmstudio.rpc.model.RpcRequest;
import com.lmstudio.rpc.serialize.RpcDecoder;
import com.lmstudio.rpc.serialize.RpcEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
* TODO
* @ClassName: RpcServer
* @author jason
*/
public class RpcServer implements ApplicationContextAware,InitializingBean{
	
	private static Logger logger = LoggerFactory.getLogger(RpcServer.class);
	
	private static int port = 8099;
	
	/**
	 * 接口名与实例Bean对应关系
	 */
	private Map<String, Object> handlerMap = new HashMap<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		 EventLoopGroup bossGroup = new NioEventLoopGroup();
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            ServerBootstrap bootstrap = new ServerBootstrap();
	            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
	                    .childHandler(new ChannelInitializer<SocketChannel>() {
	                        @Override
	                        public void initChannel(SocketChannel channel) throws Exception {
	                            channel.pipeline()
	                                    .addLast(new LengthFieldBasedFrameDecoder(65536,0,4,0,0))
	                                    .addLast(new RpcDecoder(RpcRequest.class))
	                                    .addLast(new RpcEncoder())
	                                    .addLast(new RpcHandler(handlerMap));
	                        }
	                    })
	                    .option(ChannelOption.SO_BACKLOG, 128)
	                    .childOption(ChannelOption.SO_KEEPALIVE, true);


	            ChannelFuture future = bootstrap.bind(port).sync();
	            logger.debug("Server started on port {}", port);

	            future.channel().closeFuture().sync();
	        } finally {
	            workerGroup.shutdownGracefully();
	            bossGroup.shutdownGracefully();
	        }
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// TODO Auto-generated method stub
		Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
        if (serviceBeanMap!=null) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                handlerMap.put(interfaceName, serviceBean);
            }
        }
	}

}
