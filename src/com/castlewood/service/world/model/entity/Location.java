package com.castlewood.service.world.model.entity;

import com.castlewood.io.file.binary.Binary;
import com.castlewood.io.file.binary.annotation.BinaryEncode;
import com.castlewood.service.world.model.entity.mob.Direction;

public class Location implements Cloneable
{

	private int x;

	private int y;

	private int height;

	public Location(int x, int y)
	{
		this(x, y, 0);
	}

	public Location(int x, int y, int height)
	{
		this.x = x;
		this.y = y;
		this.height = height;
	}

	public void translate(int x, int y)
	{
		this.x += x;
		this.y += y;
	}

	public void translate(Direction direction)
	{
		int x = 0;
		int y = 0;
		switch (direction)
		{
		case NORTH_WEST:
			x--;
			y++;
			break;
		case NORTH_EAST:
			x++;
			y++;
			break;
		case NORTH:
			y++;
			break;
		case EAST:
			x++;
			break;
		case SOUTH_WEST:
			x--;
			y--;
			break;
		case SOUTH_EAST:
			x++;
			y--;
			break;
		case SOUTH:
			y--;
			break;
		case WEST:
			x--;
			break;
		default:
			break;
		}
		translate(x, y);
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

	public int getLocalX()
	{
		return x - (getRegionX() * 8);
	}

	public int getLocalY()
	{
		return y - (getRegionY() * 8);
	}

	public int getRegionX()
	{
		return (x >> 3) - 6;
	}

	public int getRegionY()
	{
		return (y >> 3) - 6;
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
	public Location clone()
	{
		return new Location(x, y, height);
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

	@Override
	public String toString()
	{
		return "Location [x=" + x + ", y=" + y + ", height=" + height + "]";
	}

}
