package com.castlewood.service.net;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceManager implements Runnable
{

	private ScheduledExecutorService servicer;

	private Map<Class<? extends Service<?, ? extends Request<?>>>, Service<?, ?>> services = new Hashtable<>();

	private BlockingQueue<Service<?, ?>> waiting = new LinkedBlockingQueue<>();

	public ServiceManager(int services)
	{
		servicer = Executors.newScheduledThreadPool(services);
	}

	public <T> void register(
			Class<? extends Service<T, ? extends Request<T>>> key,
			Service<T, ? extends Request<T>> service)
	{
		services.put(key, service);
		waiting.offer(service);
		System.out
				.println("Registering " + key.getSimpleName() + " service...");
	}

	public <T> void remove(Class<? extends Service<T, ? extends Request<T>>> key)
	{
		services.remove(key);
	}

	@SuppressWarnings("unchecked")
	public <T, R extends Request<T>> Service<T, R> getService(
			Class<? extends Service<T, R>> key)
	{
		return (Service<T, R>) services.get(key);
	}

	@Override
	public void run()
	{
		while (true)
		{
			Service<?, ?> service;
			try
			{
				service = waiting.take();
			}
			catch (Exception e)
			{
				continue;
			}
			if (service.setup())
			{
				System.out.println("\t-> Successfully registered "
						+ service.getClass().getSimpleName());
			}
			servicer.scheduleAtFixedRate(service, 0, service.getDelayTime(),
					TimeUnit.MILLISECONDS);
		}
	}

}
