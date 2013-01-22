package com.castlewood.service.world.model.entity.event;

import java.util.Hashtable;
import java.util.Map;

import com.castlewood.service.world.model.entity.Entity;
import com.castlewood.service.world.model.entity.event.handlers.LogoutEventHandler;

public class EventManager
{

	private static Map<Class<? extends EventHandler<?, ?>>, EventHandler<?, ?>> handlers = new Hashtable<>();

	static
	{
		handlers.put(LogoutEventHandler.class, new LogoutEventHandler());
	}

	@SuppressWarnings("unchecked")
	public static <E extends Entity, T extends Event> EventHandler<E, T> getHandler(Event event)
	{
		return (EventHandler<E, T>) handlers.get(event.getHandler());
	}
	
}
