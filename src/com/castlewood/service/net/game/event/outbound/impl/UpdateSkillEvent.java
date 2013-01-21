package com.castlewood.service.net.game.event.outbound.impl;

import com.castlewood.service.net.game.event.outbound.OutboundEvent;
import com.castlewood.service.net.game.packet.outbound.OutboundPacket;

public class UpdateSkillEvent implements OutboundEvent
{

	private int id;

	private int xp;

	private int level;

	public UpdateSkillEvent(int id, int xp, int level)
	{
		this.id = id;
		this.xp = xp;
		this.level = level;
	}

	@Override
	public OutboundPacket encode()
	{
		OutboundPacket packet = new OutboundPacket(134);
		packet.getBuffer().writeByte(id);
		packet.getBuffer().writeInt(xp);
		packet.getBuffer().writeByte(level);
		return null;
	}

}
