package com.castlewood.service.net.game.event.outbound.impl;

import com.castlewood.service.net.game.event.outbound.OutboundEvent;
import com.castlewood.service.net.game.packet.outbound.OutboundPacket;

public class LogoutEvent implements OutboundEvent
{

	@Override
	public OutboundPacket encode()
	{
		return new OutboundPacket(109);
	}

}
