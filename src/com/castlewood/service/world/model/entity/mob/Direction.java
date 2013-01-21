package com.castlewood.service.world.model.entity.mob;

import com.castlewood.service.world.model.entity.Location;

public enum Direction
{

	NONE,

	NORTH_WEST,

	NORTH,

	NORTH_EAST,

	WEST,

	EAST,

	SOUTH_WEST,

	SOUTH,

	SOUTH_EAST;

	public static Direction getDirection(int value)
	{
		return Direction.values()[value + 1];
	}

	public static Direction getDirection(Location start, Location destination)
	{
		double deltaX = start.getX() - destination.getY();
		double deltaY = start.getY() - destination.getY();
		double angle = Math.atan(deltaY / deltaX) * 180 / Math.PI;
		if (Double.isNaN(angle))
		{
			return Direction.NONE;
		}
		if (Math.signum(deltaX) < 0)
		{
			angle += 180;
		}
		int direction = (int) ((((90 - angle) / 22.5) + 16) % 16);
		if (direction > -1)
		{
			direction >>= 1;
		}
		return getDirection(direction);
	}

	public static Direction getDirection(int deltaX, int deltaY)
	{
		if (deltaY == 1)
		{
			if (deltaX == 1)
			{
				return Direction.NORTH_EAST;
			}
			else if (deltaX == 0)
			{
				return Direction.NORTH;
			}
			else
			{
				return Direction.NORTH_WEST;
			}
		}
		else if (deltaY == -1)
		{
			if (deltaX == 1)
			{
				return Direction.SOUTH_EAST;
			}
			else if (deltaX == 0)
			{
				return Direction.SOUTH;
			}
			else
			{
				return Direction.SOUTH_WEST;
			}
		}
		else
		{
			if (deltaX == 1)
			{
				return Direction.EAST;
			}
			else if (deltaX == -1)
			{
				return Direction.WEST;
			}
		}
		return Direction.NONE;
	}

	public static boolean isConnectable(int deltaX, int deltaY)
	{
		return Math.abs(deltaX) == Math.abs(deltaY) || deltaX == 0
				|| deltaY == 0;
	}

	public int getValue()
	{
		return ordinal() - 1;
	}

}
