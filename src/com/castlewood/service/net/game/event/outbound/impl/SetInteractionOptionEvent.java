package com.castlewood.service.net.game.event.outbound.impl;

import com.castlewood.service.net.game.event.outbound.OutboundEvent;
import com.castlewood.service.net.game.packet.outbound.OutboundPacket;
import com.castlewood.service.net.game.packet.outbound.OutboundPacketHeader;
import com.castlewood.util.buffer.DataBuffer;

public class SetInteractionOptionEvent implements OutboundEvent
{

	private int slot;

	private boolean top;

	private String option;

	public SetInteractionOptionEvent(int slot, boolean top, String option)
	{
		this.slot = slot;
		this.top = top;
		this.option = option;
	}

	@Override
	public OutboundPacket encode()
	{
		OutboundPacket packet = new OutboundPacket(104,
				OutboundPacketHeader.BYTE);
		packet.getBuffer().writeByte(slot);
		packet.getBuffer().writeBoolean(top);
		DataBuffer.writeString(packet.getBuffer(), option == null ? "null"
				: option);
		return packet;
	}

}
