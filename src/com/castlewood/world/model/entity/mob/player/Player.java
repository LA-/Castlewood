package com.castlewood.world.model.entity.mob.player;

import com.castlewood.Constants;
import com.castlewood.io.event.inbound.InboundEventManager;
import com.castlewood.io.event.outbound.OutboundEvent;
import com.castlewood.io.event.outbound.impl.UpdatePlayerEvent;
import com.castlewood.io.event.outbound.impl.UpdateRegionEvent;
import com.castlewood.io.service.game.packet.inbound.InboundPacket;
import com.castlewood.world.model.entity.mob.ChatBlock;
import com.castlewood.world.model.entity.mob.Mob;

public class Player extends Mob
{

	private Client client;

	public Player(Client client)
	{
		this.client = client;
		send(new UpdateRegionEvent());
		send(new UpdatePlayerEvent(this));
	}

	public void add(ChatBlock block)
	{
		getBlocks().add(block);
		getMask().add(Constants.MASK_PLAYER_CHAT);
	}

	@Override
	public void process()
	{
		InboundPacket packet;
		while ((packet = client.next()) != null)
		{
			InboundEventManager.decode(this, packet);
		}
	}

	public void send(OutboundEvent event)
	{
		client.send(event);
	}

	public Client getClient()
	{
		return client;
	}

}
