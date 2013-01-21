package com.castlewood.service.net.game.event.inbound;

import java.util.Hashtable;
import java.util.Map;

import com.castlewood.service.net.game.event.inbound.impl.ButtonClickEvent;
import com.castlewood.service.net.game.event.inbound.impl.CameraMovementEvent;
import com.castlewood.service.net.game.event.inbound.impl.ChatEvent;
import com.castlewood.service.net.game.event.inbound.impl.CommandEvent;
import com.castlewood.service.net.game.event.inbound.impl.FocusChangedEvent;
import com.castlewood.service.net.game.event.inbound.impl.IdleEvent;
import com.castlewood.service.net.game.event.inbound.impl.InteractionSettingsChangedEvent;
import com.castlewood.service.net.game.event.inbound.impl.KeepAliveEvent;
import com.castlewood.service.net.game.event.inbound.impl.MouseClickEvent;
import com.castlewood.service.net.game.event.inbound.impl.MovementEvent;
import com.castlewood.service.net.game.event.inbound.impl.RegionChangedEvent;
import com.castlewood.service.net.game.event.inbound.impl.RegionLoadedEvent;
import com.castlewood.service.net.game.event.inbound.impl.UnhandledEvent;
import com.castlewood.service.net.game.packet.inbound.InboundPacket;
import com.castlewood.service.world.model.entity.mob.player.Player;

public class InboundEventManager
{

	private static InboundEvent unhandled = new UnhandledEvent();
	
	private static Map<Integer, InboundEvent> events = new Hashtable<>();

	static
	{
		events.put(0, new KeepAliveEvent());
		events.put(3, new FocusChangedEvent());
		events.put(4, new ChatEvent());
		events.put(86, new CameraMovementEvent());
		events.put(95, new InteractionSettingsChangedEvent());
		events.put(98, new MovementEvent());
		events.put(103, new CommandEvent());
		events.put(121, new RegionLoadedEvent());
		events.put(185, new ButtonClickEvent());
		events.put(202, new IdleEvent());
		events.put(210, new RegionChangedEvent());
		events.put(241, new MouseClickEvent());
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
