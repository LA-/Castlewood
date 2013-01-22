package com.castlewood.service.world.model.entity.event;

import com.castlewood.service.world.model.entity.Entity;

public interface EventHandler<E extends Entity, T extends Event>
{

	public void handle(E entity, T event);

}
