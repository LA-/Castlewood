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

	private MovementHandler movement;

	private Direction walk = Direction.NONE;

	private Direction run = Direction.NONE;

	public Mob(Location location)
	{
		super(location);
		movement = new MovementHandler(location);
	}

	public abstract void register();

	public abstract void unregister();

	public void advance()
	{
		walk = movement.next();
		if (movement.forceRun() || movement.isRunning())
		{
			run = movement.next();
		}
	}

	public boolean hasRegionChanged()
	{
		return getRegion() == null
				|| getLocation().getRegionX() != getRegion().getLocation()
						.getX()
				|| getLocation().getRegionY() != getRegion().getLocation()
						.getY();
	}

	public boolean movementRequired()
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

	public MovementHandler getMovement()
	{
		return movement;
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
