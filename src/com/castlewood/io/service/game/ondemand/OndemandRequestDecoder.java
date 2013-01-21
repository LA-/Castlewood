package com.castlewood.io.service.game.ondemand;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import com.castlewood.io.fs.FileDescription;

public class OndemandRequestDecoder extends
		ByteToMessageDecoder<OndemandRequest>
{

	@Override
	public OndemandRequest decode(ChannelHandlerContext context, ByteBuf in)
			throws Exception
	{
		if (!context.channel().isOpen())
		{
			return null;
		}
		int index = in.readUnsignedByte() + 1;
		int archive = in.readUnsignedShort();
		int priority = in.readUnsignedByte();
		return new OndemandRequest(new FileDescription(index, archive),
				priority);
	}

}
