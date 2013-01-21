package com.castlewood.io.service.game.login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.castlewood.Constants;
import com.castlewood.io.service.game.packet.inbound.InboundPacketDecoder;
import com.castlewood.io.service.game.packet.inbound.InboundPacketHandler;
import com.castlewood.io.service.game.packet.outbound.OutboundPacketEncoder;

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
			context.pipeline().addLast(new InboundPacketDecoder(),
					new InboundPacketHandler(), new OutboundPacketEncoder());
		}
		context.pipeline().remove(LoginResponseEncoder.class);
		context.pipeline().remove(LoginRequestDecoder.class);
		context.pipeline().remove(LoginRequestHandler.class);
	}

}
