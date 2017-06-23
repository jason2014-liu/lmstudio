/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcEncoder.java
* @Package com.lmstudio.rpc.serialize
* @author jason
* @Date 2017年6月23日 下午3:14:32
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.serialize;

import com.lmstudio.rpc.model.RpcResponse;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
* TODO
* @ClassName: RpcEncoder
* @author jason
*/
public class RpcEncoder extends MessageToByteEncoder<RpcResponse> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RpcResponse msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		byte[] data = SerializeUtils.serialize(msg);
        //byte[] data = JsonUtil.serialize(in); // Not use this, have some bugs
        out.writeInt(data.length);
        out.writeBytes(data);
	}

}
