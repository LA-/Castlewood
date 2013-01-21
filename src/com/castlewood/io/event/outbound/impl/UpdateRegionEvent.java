package com.castlewood.io.event.outbound.impl;

import com.castlewood.io.event.outbound.OutboundEvent;
import com.castlewood.io.service.game.packet.outbound.OutboundPacket;

public class UpdateRegionEvent implements OutboundEvent
{

	@Override
	public OutboundPacket encode()
	{
		OutboundPacket packet = new OutboundPacket(73);
		packet.getBuffer().writeShort(400);
		packet.getBuffer().writeShort(400);
		return packet;
	}

}
