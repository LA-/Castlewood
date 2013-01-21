package com.castlewood.service.net;

public abstract class Service<T, R extends Request<T>> implements Runnable
{

	private int delay;

	public Service()
	{
		this(1);
	}

	public Service(int delay)
	{
		this.delay = delay;
	}

	public abstract boolean setup();

	@Override
	public void run()
	{
		synchronized (this)
		{
			try
			{
				R request;
				while ((request = next()) != null)
				{
					service(request);
				}
				reset();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public abstract void push(R request) throws Exception;

	protected abstract R next() throws Exception;

	protected abstract void service(R request) throws Exception;
	
	protected abstract void reset();

	public int getDelayTime()
	{
		return delay;
	}

}
