package com.castlewood.io.service.game.packet.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import com.castlewood.Constants;

public class OutboundPacket
{

	private int id;

	private OutboundPacketHeader header;

	private ByteBuf buffer = Unpooled.buffer();

	private int index = 0;

	public OutboundPacket(int id)
	{
		this(id, OutboundPacketHeader.NONE);
	}

	public OutboundPacket(int id, OutboundPacketHeader header)
	{
		this.id = id;
		this.header = header;
	}

	public void switchBit()
	{
		index = buffer.writerIndex() * 8;
	}

	public void switchByte()
	{
		buffer.writerIndex((index + 7) / 8);
	}

	public void writeBits(int amount, int value)
	{
		if (!buffer.hasArray())
		{
			System.out.println("Error");
		}
		int position = index >> 3;
		int offset = 8 - (index & 7);
		index += amount;
		int requiredSpace = position - buffer.writerIndex() + 1;
		requiredSpace += (amount + 7) / 8;
		buffer.ensureWritableBytes(requiredSpace);
		for (; amount > offset; offset = 8)
		{
			int tmp = buffer.getByte(position);
			tmp &= ~Constants.BIT_MASK[offset];
			tmp |= (value >> (amount - offset)) & Constants.BIT_MASK[offset];
			buffer.setByte(position++, tmp);
			amount -= offset;
		}
		if (amount == offset)
		{
			int tmp = buffer.getByte(position);
			tmp &= ~Constants.BIT_MASK[offset];
			tmp |= value & Constants.BIT_MASK[offset];
			buffer.setByte(position, tmp);
		}
		else
		{
			int data = buffer.getByte(position);
			data &= ~(Constants.BIT_MASK[amount] << (offset - amount));
			data |= (value & Constants.BIT_MASK[amount]) << (offset - amount);
			buffer.setByte(position, data);
		}
	}

	public int getId()
	{
		return id;
	}

	public OutboundPacketHeader getHeader()
	{
		return header;
	}

	public int getLength()
	{
		return buffer.writerIndex();
	}

	public ByteBuf getBuffer()
	{
		return buffer;
	}

}
