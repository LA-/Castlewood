package com.castlewood.io.service.game.packet.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.castlewood.Constants;

import net.burtleburtle.bob.rand.IsaacRandom;

public class OutboundPacketEncoder extends MessageToByteEncoder<OutboundPacket>
{

	@Override
	public void encode(ChannelHandlerContext context, OutboundPacket message,
			ByteBuf out) throws Exception
	{
		IsaacRandom encoder = context.channel().attr(Constants.KEY_CLIENT)
				.get().getEncoder();
		out.writeByte(message.getId() + encoder.nextInt());
		if (message.getHeader() != OutboundPacketHeader.NONE)
		{
			if (message.getHeader() == OutboundPacketHeader.BYTE)
			{
				out.writeByte(message.getLength());
			}
			else
			{
				out.writeShort(message.getLength());
			}
		}
		out.writeBytes(message.getBuffer());
	}

}
