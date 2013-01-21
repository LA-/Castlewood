package com.castlewood.service.net.game.event.inbound.impl;

import com.castlewood.Constants;
import com.castlewood.service.net.game.event.inbound.InboundEvent;
import com.castlewood.service.net.game.packet.inbound.InboundPacket;
import com.castlewood.service.world.model.entity.mob.player.Player;

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
