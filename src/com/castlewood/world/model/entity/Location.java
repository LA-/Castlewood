package com.castlewood.world.model.entity;

import com.castlewood.io.file.binary.Binary;
import com.castlewood.io.file.binary.annotation.BinaryEncode;

public class Location
{

	private int x;

	private int y;

	private int height;

	public Location(int x, int y, int height)
	{
		this.x = x;
		this.y = y;
		this.height = height;
	}

	public boolean withinDistance(Location location)
	{
		return withinDistance(location, 8);
	}

	public boolean withinDistance(Location location, int distance)
	{
		int deltaX = Math.abs(location.x - x);
		int deltaY = Math.abs(location.y - y);
		return deltaX <= distance && deltaY <= distance;
	}

	public int getRegionX()
	{
		return x / 8;
	}

	public int getRegionY()
	{
		return y / 8;
	}

	@BinaryEncode(opcode = 1, type = Binary.SHORT)
	public short getX()
	{
		return (short) x;
	}

	@BinaryEncode(opcode = 2, type = Binary.SHORT)
	public short getY()
	{
		return (short) y;
	}

	@BinaryEncode(opcode = 3, type = Binary.BYTE)
	public byte getHeight()
	{
		return (byte) height;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (height != other.height)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
