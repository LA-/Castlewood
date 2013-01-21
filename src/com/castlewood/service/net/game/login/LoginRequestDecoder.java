package com.castlewood.service.net.game.login;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.math.BigInteger;

import com.castlewood.Constants;
import com.castlewood.util.buffer.DataBuffer;

import net.burtleburtle.bob.rand.IsaacRandom;

public class LoginRequestDecoder extends ByteToMessageDecoder<LoginRequest>
{

	private LoginRequestDecoderState state = LoginRequestDecoderState.HEADER;

	@Override
	public LoginRequest decode(ChannelHandlerContext context, ByteBuf in)
			throws Exception
	{
		if (!context.channel().isOpen())
		{
			return null;
		}
		if (state == LoginRequestDecoderState.HEADER)
		{
			if (in.readableBytes() >= 2)
			{
				int type = in.readUnsignedByte();
				if (type != Constants.LOGIN_TYPE_CONNECTING
						&& type != Constants.LOGIN_TYPE_RECONNECTING)
				{
					return null;
				}
				int length = in.readUnsignedByte();
				context.channel().attr(Constants.KEY_LENGTH).set(length);
				state = LoginRequestDecoderState.DECODE;
			}
		}
		if (state == LoginRequestDecoderState.DECODE)
		{
			if (in.readableBytes() >= context.channel()
					.attr(Constants.KEY_LENGTH).get() - 41)
			{
				int check = in.readUnsignedByte();
				if (check != 0xFF)
				{
					return null;
				}
				int revision = in.readUnsignedShort();
				if (revision != Constants.CLIENT_REVISION)
				{
					return null;
				}
				in.skipBytes(37);
				int length = in.readUnsignedByte();
				if (length != context.channel().attr(Constants.KEY_LENGTH)
						.get() - 41)
				{
					return null;
				}
				if (Constants.ENABLE_RSA)
				{
					byte[] buffer = new byte[length];
					in.readBytes(buffer);
					BigInteger rsa = new BigInteger(buffer).modPow(
							Constants.RSA_KEYS[0], Constants.RSA_KEYS[1]);
					in = Unpooled.wrappedBuffer(rsa.toByteArray());
				}
				int rsa = in.readUnsignedByte();
				if (rsa != 10)
				{
					return null;
				}
				long client = in.readLong();
				long server = in.readLong();
				int[] seeds =
				{
						(int) (client >> 32), (int) client,
						(int) (server >> 32), (int) server
				};
				IsaacRandom decoder = new IsaacRandom(seeds);
				for (int i = 0; i < seeds.length; i++)
				{
					seeds[i] += 50;
				}
				IsaacRandom encoder = new IsaacRandom(seeds);
				in.skipBytes(4);
				String username = DataBuffer.readString(in);
				String password = DataBuffer.readString(in);
				return new LoginRequest(username, password, decoder, encoder);
			}
		}
		return null;
	}
}
