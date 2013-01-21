package com.castlewood.service.net.game.event.inbound.impl;

import com.castlewood.service.net.game.event.inbound.InboundEvent;
import com.castlewood.service.net.game.packet.inbound.InboundPacket;
import com.castlewood.service.world.model.entity.mob.player.Player;
import com.castlewood.util.buffer.DataBuffer;

public class CommandEvent implements InboundEvent
{

	@Override
	public void decode(Player player, InboundPacket packet)
	{
		String command = DataBuffer.readString(packet.getBuffer());
	}

}
