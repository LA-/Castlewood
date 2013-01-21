package com.castlewood.io.service.game.ondemand;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import com.castlewood.Castlewood;
import com.castlewood.io.service.ChannelRequest;

public class OndemandRequestHandler extends
		ChannelInboundMessageHandlerAdapter<OndemandRequest>
{

	@Override
	public void messageReceived(ChannelHandlerContext context,
			OndemandRequest message) throws Exception
	{
		Castlewood.getServiceManager().getService(OndemandService.class)
				.push(new ChannelRequest<>(context.channel(), message));
	}

}
