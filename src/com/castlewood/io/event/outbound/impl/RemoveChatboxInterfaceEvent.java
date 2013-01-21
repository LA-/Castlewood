package com.castlewood.io.event.outbound.impl;

import com.castlewood.io.event.outbound.OutboundEvent;
import com.castlewood.io.service.game.packet.outbound.OutboundPacket;

public class RemoveChatboxInterfaceEvent implements OutboundEvent
{

	@Override
	public OutboundPacket encode()
	{
		return new OutboundPacket(130);
	}

}
