package com.castlewood.world.model.entity.mob;

import java.util.LinkedList;

import com.castlewood.world.model.entity.Location;

public class MovementHandler extends Location
{

	private LinkedList<Location> points = new LinkedList<Location>();

	private boolean run = false;

	private boolean running = false;

	public MovementHandler(Location location)
	{
		super(location.getX(), location.getY(), location.getHeight());
	}

	public Direction next()
	{
		Direction direction = Direction.NONE;
		Location next = null;
		while (next == null && points.size() > 0)
		{
			next = points.poll();
		}
		if (next != null)
		{
			Location delta = delta(next);
			direction = Direction.getDirection(delta.getX(), delta.getY());
			translate(direction);
		}
		if (points.size() == 0)
		{
			run = false;
		}
		return direction;
	}

	public void add(Direction direction)
	{
		Location point = points.getLast().clone();
		point.translate(direction);
		points.add(point);
	}

	public void add(Location location)
	{
		points.add(location);
	}

	public void queue(Location location)
	{
		Location last = this;
		if (points.size() > 0)
		{
			last = points.getLast();
		}
		int deltaX = location.getX() - last.getX();
		int deltaY = location.getY() - last.getY();
		int steps = Math.max(Math.abs(deltaX), Math.abs(deltaY));
		for (int i = 0; i < steps; i++)
		{
			if (deltaX < 0)
			{
				deltaX++;
			}
			else if (deltaX > 0)
			{
				deltaX--;
			}

			if (deltaY < 0)
			{
				deltaY++;
			}
			else if (deltaY > 0)
			{
				deltaY--;
			}
			add(new Location(location.getX() - deltaX, location.getY() - deltaY));
		}
	}

	public void reset()
	{
		points.clear();
	}

	private Location delta(Location location)
	{
		return new Location(location.getX() - getX(), location.getY() - getY());
	}

	public void forceRun(boolean force)
	{
		this.run = force;
	}

	public void setRunning(boolean running)
	{
		this.running = running;
	}

	public boolean forceRun()
	{
		return run;
	}

	public boolean isRunning()
	{
		return running;
	}

}
