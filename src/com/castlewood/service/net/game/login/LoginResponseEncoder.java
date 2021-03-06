package com.castlewood.service.net.game.login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.castlewood.Constants;

public class LoginResponseEncoder extends MessageToByteEncoder<LoginResponse>
{

	@Override
	public void encode(ChannelHandlerContext context, LoginResponse message,
			ByteBuf out) throws Exception
	{
		out.writeByte(message.getStatus());
		if (message.getStatus() == Constants.STATUS_OK)
		{
			out.writeByte(message.getRights());
			out.writeBoolean(message.isFlagged());
			out.writeInt(Constants.ANTI_RANDOM);
		}
		context.channel().pipeline().remove(LoginResponseEncoder.class);
		context.channel().pipeline().remove(LoginRequestDecoder.class);
		context.channel().pipeline().remove(LoginRequestHandler.class);
	}

}
