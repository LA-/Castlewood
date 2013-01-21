package com.castlewood.io.event.outbound.impl;

import com.castlewood.io.event.outbound.OutboundEvent;
import com.castlewood.io.service.game.packet.outbound.OutboundPacket;

public class RemoveInterfaceEvent implements OutboundEvent
{

	@Override
	public OutboundPacket encode()
	{
		return new OutboundPacket(219);
	}

}
