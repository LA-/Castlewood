package com.castlewood.io.event.inbound.impl;

import com.castlewood.io.event.inbound.InboundEvent;
import com.castlewood.io.service.game.packet.inbound.InboundPacket;
import com.castlewood.world.model.entity.Location;
import com.castlewood.world.model.entity.mob.player.Player;

public class MovementEvent implements InboundEvent
{

	@Override
	public void decode(Player player, InboundPacket packet)
	{
		boolean running = packet.getBuffer().readBoolean();
		Location first = new Location(packet.getBuffer().readShort(), packet
				.getBuffer().readShort());
		player.getMovement().reset();
		player.getMovement().queue(first);
		while (packet.getBuffer().readable())
		{
			player.getMovement().queue(
					new Location(first.getX() + packet.getBuffer().readByte(),
							first.getY() + packet.getBuffer().readByte()));
		}
		player.getMovement().forceRun(running);
	}

}
