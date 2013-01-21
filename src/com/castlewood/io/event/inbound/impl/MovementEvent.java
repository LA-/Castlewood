package com.castlewood.io.event.inbound.impl;

import java.util.LinkedList;
import java.util.List;

import com.castlewood.io.event.inbound.InboundEvent;
import com.castlewood.io.service.game.packet.inbound.InboundPacket;
import com.castlewood.world.model.entity.Location;
import com.castlewood.world.model.entity.mob.player.Player;

public class MovementEvent implements InboundEvent
{

	@Override
	public void decode(Player player, InboundPacket packet)
	{
		int type = packet.getBuffer().readByte();
		boolean running = packet.getBuffer().readBoolean();
		Location first = new Location(packet.getBuffer().readShort(), packet
				.getBuffer().readShort());
		List<Location> steps = new LinkedList<Location>();
		steps.add(first);
		while (packet.getBuffer().readable())
		{
			steps.add(new Location(
					first.getX() + packet.getBuffer().readByte(), first.getY()
							+ packet.getBuffer().readByte()));
		}
		System.out.println(steps);
	}
}
