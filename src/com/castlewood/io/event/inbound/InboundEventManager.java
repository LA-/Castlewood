package com.castlewood.io.event.inbound;

import java.util.Hashtable;
import java.util.Map;

import com.castlewood.io.event.inbound.impl.ChatEvent;
import com.castlewood.io.event.inbound.impl.UnhandledEvent;
import com.castlewood.io.event.inbound.impl.UnusedEvent;
import com.castlewood.io.service.game.packet.inbound.InboundPacket;
import com.castlewood.world.model.entity.mob.player.Player;

public class InboundEventManager
{

	private static InboundEvent unhandled = new UnhandledEvent();

	private static InboundEvent unused = new UnusedEvent();

	private static Map<Integer, InboundEvent> events = new Hashtable<>();

	static
	{
		events.put(0, unused);
		events.put(3, unused);
		events.put(4, new ChatEvent());
		events.put(86, unused);
	}

	public static void decode(Player player, InboundPacket packet)
	{
		InboundEvent event = events.get(packet.getId());
		if (event == null)
		{
			event = unhandled;
			events.put(packet.getId(), event);
		}
		event.decode(player, packet);
	}

}
