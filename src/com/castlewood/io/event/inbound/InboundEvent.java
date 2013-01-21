package com.castlewood.io.event.inbound;

import com.castlewood.io.service.game.packet.inbound.InboundPacket;
import com.castlewood.world.model.entity.mob.player.Player;

public interface InboundEvent
{

	public void decode(Player player, InboundPacket packet);
	
}
