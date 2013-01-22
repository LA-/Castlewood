package com.castlewood.service.world.model.entity.event;

public interface Event
{
	
	public Class<? extends EventHandler<?, ?>> getHandler();

}
