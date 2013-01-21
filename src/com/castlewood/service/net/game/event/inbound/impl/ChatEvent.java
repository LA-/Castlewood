package com.castlewood.service.net.game.event.inbound.impl;

import com.castlewood.service.net.game.event.inbound.InboundEvent;
import com.castlewood.service.net.game.packet.inbound.InboundPacket;
import com.castlewood.service.world.model.entity.mob.ChatBlock;
import com.castlewood.service.world.model.entity.mob.player.Player;

public class ChatEvent implements InboundEvent
{

	@Override
	public void decode(Player player, InboundPacket packet)
	{
		int effect = packet.getBuffer().readUnsignedByte();
		int colour = packet.getBuffer().readUnsignedByte();
		int length = packet.getLength() - 2;
		byte[] message = new byte[length];
		packet.readBytesReversed(message, length);
		player.add(new ChatBlock(effect, colour, message, player.getRights()));
	}

}
