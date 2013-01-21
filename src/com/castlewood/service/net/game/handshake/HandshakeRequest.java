package com.castlewood.service.net.game.handshake;

import io.netty.channel.ChannelHandlerContext;

import com.castlewood.Constants;
import com.castlewood.service.net.game.login.LoginRequestDecoder;
import com.castlewood.service.net.game.login.LoginRequestHandler;
import com.castlewood.service.net.game.login.LoginResponseEncoder;
import com.castlewood.service.net.game.ondemand.OndemandRequestDecoder;
import com.castlewood.service.net.game.ondemand.OndemandRequestHandler;
import com.castlewood.service.net.game.ondemand.OndemandResponseEncoder;

public class HandshakeRequest
{

	private ChannelHandlerContext context;

	private int id;

	public HandshakeRequest(ChannelHandlerContext context, int id)
	{
		this.context = context;
		this.id = id;
	}

	public void addDecoders()
	{
		switch (id)
		{
		case Constants.SERVICE_LOGIN:
			context.channel()
					.pipeline()
					.addFirst(LoginRequestHandler.class.getSimpleName(),
							new LoginRequestHandler());
			context.channel()
					.pipeline()
					.addFirst(LoginRequestDecoder.class.getSimpleName(),
							new LoginRequestDecoder());
			break;
		case Constants.SERVICE_ONDEMAND:
			context.channel()
					.pipeline()
					.addFirst(OndemandRequestHandler.class.getSimpleName(),
							new OndemandRequestHandler());
			context.channel()
					.pipeline()
					.addFirst(OndemandRequestDecoder.class.getSimpleName(),
							new OndemandRequestDecoder());
			break;
		default:
			throw new UnsupportedOperationException();
		}
	}

	public void addEncoder()
	{
		switch (id)
		{
		case Constants.SERVICE_LOGIN:
			context.channel()
					.pipeline()
					.addFirst(LoginResponseEncoder.class.getSimpleName(),
							new LoginResponseEncoder());
			break;
		case Constants.SERVICE_ONDEMAND:
			context.channel()
					.pipeline()
					.addFirst(OndemandResponseEncoder.class.getSimpleName(),
							new OndemandResponseEncoder());
			break;
		default:
			throw new UnsupportedOperationException();
		}
	}

	public int getId()
	{
		return id;
	}
}
