package com.castlewood.io.service.game.handshake;

import io.netty.channel.ChannelHandlerContext;

import com.castlewood.Constants;
import com.castlewood.io.service.game.login.LoginRequestDecoder;
import com.castlewood.io.service.game.login.LoginRequestHandler;
import com.castlewood.io.service.game.login.LoginResponseEncoder;
import com.castlewood.io.service.game.ondemand.OndemandRequestDecoder;
import com.castlewood.io.service.game.ondemand.OndemandRequestHandler;
import com.castlewood.io.service.game.ondemand.OndemandResponseEncoder;

public class HandshakeRequest
{

	private ChannelHandlerContext context;

	private int id;

	public HandshakeRequest(ChannelHandlerContext context, int id)
	{
		this.context = context;
		this.id = id;
	}

	public void transform()
	{
		switch (id)
		{
		case Constants.SERVICE_LOGIN:
			context.channel()
					.pipeline()
					.addLast(LoginRequestDecoder.class.getSimpleName(),
							new LoginRequestDecoder());
			context.channel()
					.pipeline()
					.addLast(LoginRequestHandler.class.getSimpleName(),
							new LoginRequestHandler());
			context.channel()
					.pipeline()
					.addLast(LoginResponseEncoder.class.getSimpleName(),
							new LoginResponseEncoder());
			break;
		case Constants.SERVICE_ONDEMAND:
			context.channel()
					.pipeline()
					.addLast(OndemandRequestDecoder.class.getSimpleName(),
							new OndemandRequestDecoder());
			context.channel()
					.pipeline()
					.addLast(OndemandRequestHandler.class.getSimpleName(),
							new OndemandRequestHandler());
			context.channel()
					.pipeline()
					.addLast(OndemandResponseEncoder.class.getSimpleName(),
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
