package com.castlewood.service.net.game.ondemand;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class OndemandResponseEncoder extends
		MessageToByteEncoder<OndemandResponse>
{

	@Override
	public void encode(ChannelHandlerContext context, OndemandResponse message,
			ByteBuf out) throws Exception
	{
		out.writeByte(message.getDescription().getIndex() - 1);
		out.writeShort(message.getDescription().getArchive());
		out.writeShort(message.getLength());
		out.writeByte(message.getBlock());
		out.writeBytes(message.getData());
	}

}
