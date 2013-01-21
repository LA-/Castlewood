package com.castlewood.io.service.game.login;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import com.castlewood.Castlewood;
import com.castlewood.io.service.ChannelRequest;

public class LoginRequestHandler extends
		ChannelInboundMessageHandlerAdapter<LoginRequest>
{

	@Override
	public void messageReceived(ChannelHandlerContext context,
			LoginRequest message) throws Exception
	{
		if (context.channel().isOpen())
		{
			Castlewood.getServiceManager().getService(LoginService.class)
					.push(new ChannelRequest<>(context.channel(), message));
		}
	}

}
