package com.castlewood.service.net.game.event.inbound;

import com.castlewood.service.net.game.packet.inbound.InboundPacket;
import com.castlewood.service.world.model.entity.mob.player.Player;

public interface InboundEvent
{

	public void decode(Player player, InboundPacket packet);
	
}
