package com.castlewood.service.net.game.event.outbound.impl;

import com.castlewood.service.net.game.event.outbound.OutboundEvent;
import com.castlewood.service.net.game.packet.outbound.OutboundPacket;
import com.castlewood.service.net.game.packet.outbound.OutboundPacketHeader;
import com.castlewood.util.buffer.DataBuffer;

public class SendMessageEvent implements OutboundEvent
{

	private String message;

	public SendMessageEvent(String message)
	{
		this.message = message;
	}

	@Override
	public OutboundPacket encode()
	{
		OutboundPacket packet = new OutboundPacket(253,
				OutboundPacketHeader.BYTE);
		DataBuffer.writeString(packet.getBuffer(), message);
		return packet;
	}

}
