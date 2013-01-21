package com.castlewood.util.buffer;

import io.netty.buffer.ByteBuf;

public class DataBuffer
{

	public static String readString(ByteBuf in)
	{
		StringBuilder builder = new StringBuilder();
		char character;
		while ((character = (char) in.readByte()) != 10)
		{
			builder.append(character);
		}
		return builder.toString();
	}

	public static void writeString(ByteBuf out, String message)
	{
		out.writeBytes(message.getBytes());
		out.writeByte(10);
	}

}
