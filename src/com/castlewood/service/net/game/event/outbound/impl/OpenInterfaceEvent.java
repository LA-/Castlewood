package com.castlewood.service.net.game.event.outbound.impl;

import com.castlewood.service.net.game.event.outbound.OutboundEvent;
import com.castlewood.service.net.game.packet.outbound.OutboundPacket;

public class OpenInterfaceEvent implements OutboundEvent
{

	private int id;

	public OpenInterfaceEvent(int id)
	{
		this.id = id;
	}

	@Override
	public OutboundPacket encode()
	{
		OutboundPacket packet = new OutboundPacket(97);
		packet.getBuffer().writeShort(id);
		return packet;
	}

}
