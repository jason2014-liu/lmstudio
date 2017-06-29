/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcClientProxy.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月27日 下午2:46:31
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmstudio.rpc.model.RpcRequest;

/**
* TODO
* @ClassName: RpcClientProxy
* @author jason
*/
public class RpcClientProxy<T> implements InvocationHandler {
	
	private static Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);
	
	private Class<T> interfaceClass;
	
	private static long DEFAULT_TIMEOUT = 30000L;
	
	public RpcClientProxy(Class<T> interfaceClass) {
		super();
		this.interfaceClass = interfaceClass;
	}



	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setInterfaceName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        
        logger.debug("客户端发起请求，参数为 interfaceName={},methodName={}",request.getInterfaceName(),request.getMethodName());
        
//        Channel channel = ChannelFactory.getInstance().getChannel(ChannelFactory.DEFAULT_NODE);
        
//        if(channel == null){
//        	logger.debug("channel is null");
//        }
        
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//			Bootstrap b = new Bootstrap();
//			b.group(workerGroup);
//			b.channel(NioSocketChannel.class);
//			//b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
//			b.option(ChannelOption.SO_KEEPALIVE, true);
//			b.handler(new ChannelInitializer<SocketChannel>() {
//				@Override
//				public void initChannel(SocketChannel ch) throws Exception {
//					ChannelPipeline cp = ch.pipeline();
//					cp.addLast(new LoggingHandler(LogLevel.DEBUG));
//					cp.addLast(new RpcEncoder(RpcRequest.class));
//					cp.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
//					cp.addLast(new RpcDecoder(RpcResponse.class));
//					cp.addLast(new RpcClientHandler());
//				}
//			});
//        
//			ChannelFuture f = b.connect("127.0.0.1", 18888).sync();
//        
			
			
        //ChannelFuture cfuture = f.channel().writeAndFlush(request);
        InetSocketAddress node = new InetSocketAddress("127.0.0.1", 18888);
        
        RpcClientHandler hander =ChannelFactory.getInstance().getClientHandler(node);
        
		RpcResult result = hander.sendRequest(request);
		return result.getResult();
	}
	

}
