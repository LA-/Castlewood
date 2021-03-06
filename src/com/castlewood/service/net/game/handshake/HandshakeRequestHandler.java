package com.castlewood.service.net.game.handshake;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import com.castlewood.Castlewood;
import com.castlewood.service.net.ChannelRequest;

public class HandshakeRequestHandler extends
		ChannelInboundMessageHandlerAdapter<HandshakeRequest>
{

	@Override
	public void messageReceived(ChannelHandlerContext context,
			HandshakeRequest message) throws Exception
	{
		Castlewood.getServiceManager().getService(HandshakeService.class)
				.push(new ChannelRequest<>(context.channel(), message));
		context.channel().pipeline().remove(this);
	}

}
