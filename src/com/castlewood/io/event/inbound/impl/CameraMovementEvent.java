package com.castlewood.io.event.inbound.impl;

import com.castlewood.io.event.inbound.InboundEvent;
import com.castlewood.io.service.game.packet.inbound.InboundPacket;
import com.castlewood.world.model.entity.mob.player.Player;

public class CameraMovementEvent implements InboundEvent
{

	@Override
	public void decode(Player player, InboundPacket packet)
	{
		int x = packet.getBuffer().readShort();
		int y = packet.getBuffer().readShort();
	}

}
