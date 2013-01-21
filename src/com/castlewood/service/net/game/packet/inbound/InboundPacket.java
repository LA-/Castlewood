package com.castlewood.service.net.game.packet.inbound;

import io.netty.buffer.ByteBuf;

public class InboundPacket
{

	private int id;

	private int length;

	private ByteBuf buffer;

	public InboundPacket(int id, int length, ByteBuf buffer)
	{
		this.id = id;
		this.length = length;
		this.buffer = buffer;
	}

	public void readBytesReversed(byte[] destination, int length)
	{
		for (int i = length - 1; i >= 0; i--)
		{
			destination[i] = buffer.readByte();
		}
	}

	public int getId()
	{
		return id;
	}

	public int getLength()
	{
		return length;
	}

	public ByteBuf getBuffer()
	{
		return buffer;
	}
	
}
