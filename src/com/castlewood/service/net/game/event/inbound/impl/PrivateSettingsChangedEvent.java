package com.castlewood.service.net.game.event.inbound.impl;

import com.castlewood.service.net.game.event.inbound.InboundEvent;
import com.castlewood.service.net.game.packet.inbound.InboundPacket;
import com.castlewood.service.world.model.entity.mob.player.Player;

public class PrivateSettingsChangedEvent implements InboundEvent
{

	@Override
	public void decode(Player player, InboundPacket packet)
	{
		int privateSetting = packet.getBuffer().readByte();
		int publicSetting = packet.getBuffer().readByte();
		int tradeSetting = packet.getBuffer().readByte();
		System.out.println(privateSetting + ", " + publicSetting + ", "
				+ tradeSetting);
	}

}
