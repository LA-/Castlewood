package com.castlewood.io.event.outbound;

import com.castlewood.io.service.game.packet.outbound.OutboundPacket;

public interface OutboundEvent
{

	public OutboundPacket encode();
	
}
