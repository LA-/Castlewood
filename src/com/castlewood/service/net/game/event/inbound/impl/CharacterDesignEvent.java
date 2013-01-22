package com.castlewood.service.net.game.event.inbound.impl;

import com.castlewood.service.net.game.event.inbound.InboundEvent;
import com.castlewood.service.net.game.packet.inbound.InboundPacket;
import com.castlewood.service.world.model.entity.mob.player.Gender;
import com.castlewood.service.world.model.entity.mob.player.Player;

public class CharacterDesignEvent implements InboundEvent
{

	@Override
	public void decode(Player player, InboundPacket packet)
	{
		player.getDesign().setGender(
				Gender.values()[packet.getBuffer().readUnsignedByte()]);
		for (int i = 0; i < 7; i++)
		{
			player.getDesign().setStyle(i,
					packet.getBuffer().readUnsignedByte());
		}
		for (int i = 0; i < 5; i++)
		{
			player.getDesign().setColour(i,
					packet.getBuffer().readUnsignedByte());
		}
		player.addAppearanceBlock();
	}

}
