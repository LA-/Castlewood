package com.castlewood.service.world.model.entity.mob;

import com.castlewood.service.world.Processable;
import com.castlewood.service.world.Updateable;
import com.castlewood.service.world.model.entity.Entity;
import com.castlewood.service.world.model.entity.Location;

public abstract class Mob extends Entity implements Processable, Updateable
{

	private int index;

	private UpdateMask mask = new UpdateMask();

	private Blocks blocks = new Blocks();

	private Direction walk = Direction.NONE;

	private Direction run = Direction.NONE;

	public Mob(Location location)
	{
		super(new MovementHandler(location));
	}

	public abstract void register();

	public abstract void unregister();

	public void advance()
	{
		MovementHandler movement = (MovementHandler) getLocation();
		walk = movement.next();
		if (movement.forceRun() || movement.isRunning())
		{
			run = movement.next();
		}
	}

	public boolean hasRegionChanged()
	{
		if (getRegion() == null)
		{
			return true;
		}
		int deltaX = Math.abs(getLocation().getRegionX() - getRegion().getLocation().getX());
		int deltaY = Math.abs(getLocation().getRegionY() - getRegion().getLocation().getY());
		if (deltaX >= 4 || deltaY >= 4)
		{
			return true;
		}
		return false;
	}

	public boolean isMovementRequired()
	{
		return walk != Direction.NONE;
	}

	public void resetDirections()
	{
		walk = Direction.NONE;
		run = Direction.NONE;
	}

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

	public Direction getWalkingDirection()
	{
		return walk;
	}

	public Direction getRunningDirection()
	{
		return run;
	}

}
