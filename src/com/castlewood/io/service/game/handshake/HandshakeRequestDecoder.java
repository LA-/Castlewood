package com.castlewood.io.service.game.handshake;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class HandshakeRequestDecoder extends ByteToMessageDecoder<Object>
{

	@Override
	public Object decode(ChannelHandlerContext context, ByteBuf in)
			throws Exception
	{
		int id = in.readUnsignedByte();
		HandshakeRequest request = new HandshakeRequest(context, id);
		context.channel().pipeline().remove(this);
		if (in.readable())
		{
			in.readBytes(in.readableBytes());
		}
		return request;
	}

}
