package com.castlewood.io.event.outbound.impl;

import com.castlewood.io.event.outbound.OutboundEvent;
import com.castlewood.io.service.game.packet.outbound.OutboundPacket;

public class SetTabInterfaceEvent implements OutboundEvent
{

	private int id;

	private int tab;

	public SetTabInterfaceEvent(int id, int tab)
	{
		this.id = id;
		this.tab = tab;
	}

	@Override
	public OutboundPacket encode()
	{
		OutboundPacket packet = new OutboundPacket(71);
		packet.getBuffer().writeShort(id);
		packet.getBuffer().writeByte(tab);
		return packet;
	}

}
