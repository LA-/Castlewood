package com.castlewood.world.model.entity.mob.player;

import com.castlewood.Constants;
import com.castlewood.io.event.inbound.InboundEventManager;
import com.castlewood.io.event.outbound.OutboundEvent;
import com.castlewood.io.event.outbound.impl.UpdatePlayerEvent;
import com.castlewood.io.event.outbound.impl.UpdateRegionEvent;
import com.castlewood.io.service.game.packet.inbound.InboundPacket;
import com.castlewood.util.misc.TextUtils;
import com.castlewood.world.model.entity.mob.AppearanceBlock;
import com.castlewood.world.model.entity.mob.ChatBlock;
import com.castlewood.world.model.entity.mob.Mob;

public class Player extends Mob
{

	private Client client;

	private CharacterDesign design = new CharacterDesign();

	public Player(Client client)
	{
		this.client = client;
		send(new UpdateRegionEvent());
		send(new UpdatePlayerEvent(this));
	}

	public void addAppearanceBlock()
	{
		AppearanceBlock block = new AppearanceBlock(design, -1, 126,
				TextUtils.encodeBase37("Mopar"));
		getBlocks().add(block);
		getMask().add(Constants.MASK_PLAYER_APPEARANCE);
	}

	public void add(ChatBlock block)
	{
		getBlocks().add(block);
		getMask().add(Constants.MASK_PLAYER_CHAT);
	}

	@Override
	public void register()
	{

	}

	@Override
	public void unregister()
	{

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

	public CharacterDesign getDesign()
	{
		return design;
	}

}
