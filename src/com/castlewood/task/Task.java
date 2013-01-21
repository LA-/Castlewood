package com.castlewood.task;

public abstract class Task
{

	private boolean running = true;

	private int delay;

	private int current;

	public Task(int delay)
	{
		this(delay, false);
	}

	public Task(int delay, boolean immediate)
	{
		this.delay = this.current = delay;
	}

	public abstract void execute();

	public void pulse()
	{
		if (running && --current == 0)
		{
			current = delay;
			execute();
		}
	}

	public void stop()
	{
		running = false;
	}

	public boolean isRunning()
	{
		return running;
	}

}
