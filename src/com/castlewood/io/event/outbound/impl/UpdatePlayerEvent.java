package com.castlewood.io.event.outbound.impl;

import com.castlewood.io.event.outbound.OutboundEvent;
import com.castlewood.io.service.game.packet.outbound.OutboundPacket;
import com.castlewood.io.service.game.packet.outbound.OutboundPacketHeader;
import com.castlewood.world.model.entity.mob.player.Player;

public class UpdatePlayerEvent implements OutboundEvent
{

	private Player player;

	public UpdatePlayerEvent(Player player)
	{
		this.player = player;
	}

	@Override
	public OutboundPacket encode()
	{
		OutboundPacket packet = new OutboundPacket(81,
				OutboundPacketHeader.SHORT);
		packet.switchBit();
		packet.writeBits(1, 1);
		packet.writeBits(2, 3);
		packet.writeBits(2, 0);
		packet.writeBits(1, 1);
		packet.writeBits(1, 1);
		packet.writeBits(7, 48);
		packet.writeBits(7, 48);
		packet.writeBits(8, 0);
		packet.writeBits(11, 2047);
		packet.switchByte();
		packet.getBuffer().writeByte(0);
		return packet;
	}

}
