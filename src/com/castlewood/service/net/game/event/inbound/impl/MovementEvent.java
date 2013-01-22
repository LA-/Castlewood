package com.castlewood.service.net.game.event.inbound.impl;

import com.castlewood.service.net.game.event.inbound.InboundEvent;
import com.castlewood.service.net.game.packet.inbound.InboundPacket;
import com.castlewood.service.world.model.entity.Location;
import com.castlewood.service.world.model.entity.mob.MovementHandler;
import com.castlewood.service.world.model.entity.mob.player.Player;

public class MovementEvent implements InboundEvent
{

	@Override
	public void decode(Player player, InboundPacket packet)
	{
		MovementHandler movement = (MovementHandler) player.getLocation();
		boolean running = packet.getBuffer().readBoolean();
		Location first = new Location(packet.getBuffer().readShort(), packet
				.getBuffer().readShort());
		movement.reset();
		movement.queue(first);
		while (packet.getBuffer().readable())
		{
			movement.queue(new Location(first.getX()
					+ packet.getBuffer().readByte(), first.getY()
					+ packet.getBuffer().readByte()));
		}
		movement.forceRun(running);
	}

}
