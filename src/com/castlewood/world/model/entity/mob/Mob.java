package com.castlewood.world.model.entity.mob;

import com.castlewood.world.Processable;

public abstract class Mob implements Processable
{

	private int index;

	private UpdateMask mask = new UpdateMask();

	private Blocks blocks = new Blocks();

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int getIndex()
	{
		return index;
	}

	public UpdateMask getMask()
	{
		return mask;
	}

	public Blocks getBlocks()
	{
		return blocks;
	}

}
