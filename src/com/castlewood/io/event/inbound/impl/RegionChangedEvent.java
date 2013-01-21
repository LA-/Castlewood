package com.castlewood.io.event.inbound.impl;

import com.castlewood.Constants;
import com.castlewood.io.event.inbound.InboundEvent;
import com.castlewood.io.service.game.packet.inbound.InboundPacket;
import com.castlewood.world.model.entity.mob.player.Player;

public class RegionChangedEvent implements InboundEvent
{

	@Override
	public void decode(Player player, InboundPacket packet)
	{
		int anti = packet.getBuffer().readInt();
		if (anti != Constants.ANTI_RANDOM)
		{
			player.disconnect();
		}
	}

}
