/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcDecoder.java
* @Package com.lmstudio.rpc.serialize
* @author jason
* @Date 2017年6月23日 下午2:25:04
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.serialize;

import java.util.List;

import com.lmstudio.rpc.model.RpcRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * TODO
 * 
 * @ClassName: RpcDecoder
 * @author jason
 */
public class RpcDecoder extends ByteToMessageDecoder {

	private Class<?> genericClass;

	public RpcDecoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub

		if (in.readableBytes() < 4) {
			return;
		}
		in.markReaderIndex();
		int dataLength = in.readInt();
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}
		byte[] data = new byte[dataLength];
		in.readBytes(data);

		Object obj = SerializeUtils.deserialize(data, genericClass);
		out.add(obj);
	}

}
