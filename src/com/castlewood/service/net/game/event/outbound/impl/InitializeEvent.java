package com.castlewood.service.net.game.event.outbound.impl;

import com.castlewood.service.net.game.event.outbound.OutboundEvent;
import com.castlewood.service.net.game.packet.outbound.OutboundPacket;

public class InitializeEvent implements OutboundEvent
{

	private boolean members;

	private int index;

	public InitializeEvent(boolean members, int index)
	{
		this.members = members;
		this.index = index;
	}

	@Override
	public OutboundPacket encode()
	{
		OutboundPacket packet = new OutboundPacket(249);
		packet.getBuffer().writeBoolean(members);
		packet.getBuffer().writeShort(index);
		return packet;
	}

}
