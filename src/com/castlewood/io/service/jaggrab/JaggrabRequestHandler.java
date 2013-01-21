package com.castlewood.io.service.jaggrab;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import com.castlewood.Castlewood;
import com.castlewood.io.service.ChannelRequest;

public class JaggrabRequestHandler extends
		ChannelInboundMessageHandlerAdapter<JaggrabRequest>
{

	@Override
	public void messageReceived(ChannelHandlerContext context,
			JaggrabRequest message) throws Exception
	{
		if (context.channel().isOpen())
		{
			Castlewood.getServiceManager().getService(JaggrabService.class)
					.push(new ChannelRequest<>(context.channel(), message));
		}
	}

}
