package com.castlewood.world.model.entity.mob;

public class UpdateMask
{

	private int mask;

	public UpdateMask()
	{
		mask = 0;
	}

	public UpdateMask(int mask)
	{
		this.mask = mask;
	}

	public void set(int mask)
	{
		this.mask = mask;
	}

	public void add(int flag)
	{
		mask |= flag;
	}

	public void remove(int flag)
	{
		mask &= ~flag;
	}

	public boolean has(int flag)
	{
		return (mask & flag) != 0;
	}

	public void clear()
	{
		mask = 0;
	}

	public int getMask()
	{
		return mask;
	}

}