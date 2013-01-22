package com.castlewood.service.world.model.entity.event.handlers;

import com.castlewood.service.net.game.event.outbound.impl.SendLogoutEvent;
import com.castlewood.service.world.model.entity.event.EventHandler;
import com.castlewood.service.world.model.entity.event.events.LogoutEvent;
import com.castlewood.service.world.model.entity.mob.player.Player;

public class LogoutEventHandler implements EventHandler<Player, LogoutEvent>
{

	@Override
	public void handle(Player entity, LogoutEvent event)
	{
		entity.send(new SendLogoutEvent());
	}

}
