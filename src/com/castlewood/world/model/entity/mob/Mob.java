package com.castlewood.world.model.entity.mob;

import com.castlewood.world.Processable;
import com.castlewood.world.Updateable;
import com.castlewood.world.model.entity.Entity;
import com.castlewood.world.model.entity.Location;

public abstract class Mob extends Entity implements Processable, Updateable
{

	private int index;

	private UpdateMask mask = new UpdateMask();

	private Blocks blocks = new Blocks();

	public Mob(Location location)
	{
		super(location);
	}

	public abstract void register();

	public abstract void unregister();

	public void setIndex(int index)
	{
		this.index = index;
	}

	public boolean hasRegionChanged()
	{
		return getRegion() == null
				|| getLocation().getRegionX() != getRegion().getLocation()
						.getX()
				|| getLocation().getRegionY() != getRegion().getLocation()
						.getY();
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
