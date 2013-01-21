package com.castlewood.io.event.outbound.impl;

import com.castlewood.io.event.outbound.OutboundEvent;
import com.castlewood.io.service.game.packet.outbound.OutboundPacket;

public class ViewTabEvent implements OutboundEvent
{

	private int tab;

	public ViewTabEvent(int tab)
	{
		this.tab = tab;
	}

	@Override
	public OutboundPacket encode()
	{
		OutboundPacket packet = new OutboundPacket(106);
		packet.getBuffer().writeByte(tab);
		return packet;
	}

}
