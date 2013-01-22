package com.castlewood.service.world.model.entity.event.events;

import com.castlewood.service.world.model.entity.event.Event;
import com.castlewood.service.world.model.entity.event.EventHandler;
import com.castlewood.service.world.model.entity.event.handlers.LogoutEventHandler;

public class LogoutEvent implements Event
{

	@Override
	public Class<? extends EventHandler<?, ?>> getHandler()
	{
		return LogoutEventHandler.class;
	}

}
