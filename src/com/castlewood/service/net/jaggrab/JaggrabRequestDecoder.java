package com.castlewood.service.net.jaggrab;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class JaggrabRequestDecoder extends ByteToMessageDecoder<JaggrabRequest>
{

	@Override
	public JaggrabRequest decode(ChannelHandlerContext context, ByteBuf in)
			throws Exception
	{
		if (!context.channel().isOpen())
		{
			return null;
		}
		byte[] characters = Unpooled.buffer(in.readableBytes()).writeBytes(in)
				.array();
		String root = new String(characters);
		if (root.startsWith("JAGGRAB"))
		{
			return new JaggrabRequest(root.substring(9));
		}
		return null;
	}

}
