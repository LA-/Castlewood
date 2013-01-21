package com.castlewood.io.service.game.packet.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.LinkedList;
import java.util.List;

import com.castlewood.Constants;

import net.burtleburtle.bob.rand.IsaacRandom;

public class InboundPacketDecoder extends ByteToMessageDecoder<InboundPacket[]>
{

	@Override
	public InboundPacket[] decode(ChannelHandlerContext context, ByteBuf in)
			throws Exception
	{
		List<InboundPacket> requests = new LinkedList<>();
		IsaacRandom decoder = context.channel().attr(Constants.KEY_CLIENT)
				.get().getDecoder();
		do
		{
			if (in.readable())
			{
				int id = (in.readUnsignedByte() - decoder.nextInt()) & 0xFF;
				int length = Constants.PACKET_LENGTHS[id];
				if (length == -1)
				{
					if (in.readable())
					{
						length = in.readUnsignedByte();
					}
					else
					{
						return null;
					}
				}
				if (in.readableBytes() >= length)
				{
					ByteBuf buffer = Unpooled.buffer(length);
					in.readBytes(buffer);
					requests.add(new InboundPacket(id, length, buffer));
				}
			}
		}
		while (in.readable());
		return requests.toArray(new InboundPacket[requests.size()]);
	}

}
