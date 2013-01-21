package com.castlewood.service.net.game.event.outbound;

import com.castlewood.service.net.game.packet.outbound.OutboundPacket;

public interface OutboundEvent
{

	public OutboundPacket encode();
	
}
