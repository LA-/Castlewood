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
			context.pipeline().addLast(new LoginRequestDecoder(),
					new LoginRequestHandler(), new LoginResponseEncoder());
			break;
		case Constants.SERVICE_ONDEMAND:
			context.pipeline()
					.addLast(new OndemandRequestDecoder(),
							new OndemandRequestHandler(),
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
