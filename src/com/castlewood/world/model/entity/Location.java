package com.castlewood.world.model.entity;

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

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getHeight()
	{
		return height;
	}
	
}
