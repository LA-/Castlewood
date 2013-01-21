package com.castlewood.io.service.game.packet.inbound;

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
