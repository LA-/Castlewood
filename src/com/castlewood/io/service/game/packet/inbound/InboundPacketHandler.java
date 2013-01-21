package com.castlewood.io.service.game.packet.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import com.castlewood.Castlewood;
import com.castlewood.io.service.ChannelRequest;

public class InboundPacketHandler extends
		ChannelInboundMessageHandlerAdapter<InboundPacket>
{

	@Override
	public void messageReceived(ChannelHandlerContext context,
			InboundPacket message) throws Exception
	{
		Castlewood.getServiceManager().getService(InboundPacketService.class)
				.push(new ChannelRequest<>(context.channel(), message));
	}

}
