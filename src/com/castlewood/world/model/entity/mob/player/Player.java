package com.castlewood.world.model.entity.mob.player;

import com.castlewood.Castlewood;
import com.castlewood.Constants;
import com.castlewood.io.event.inbound.InboundEventManager;
import com.castlewood.io.event.outbound.OutboundEvent;
import com.castlewood.io.event.outbound.impl.UpdatePlayerEvent;
import com.castlewood.io.event.outbound.impl.UpdateRegionEvent;
import com.castlewood.io.file.PlayerFile;
import com.castlewood.io.file.binary.BinaryFile;
import com.castlewood.io.service.game.packet.inbound.InboundPacket;
import com.castlewood.util.misc.TextUtils;
import com.castlewood.world.model.entity.mob.AppearanceBlock;
import com.castlewood.world.model.entity.mob.ChatBlock;
import com.castlewood.world.model.entity.mob.Mob;
import com.castlewood.world.model.entity.region.RegionManager;

public class Player extends Mob
{

	private String username;

	private String password;

	private Client client;

	private int rights;

	private CharacterDesign design;

	public Player(Client client, PlayerFile file)
	{
		super(file.getLocation());
		this.client = client;
		this.username = file.getUsername();
		this.password = file.getPassword();
		this.rights = file.getRights();
		this.design = file.getDesign();
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
		Castlewood.getFileManager().save(new BinaryFile(this));
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

	@Override
	public void prepare()
	{
		process();
		if (hasRegionChanged())
		{
			setRegion(RegionManager.getForLocation(getLocation()));
			client.send(new UpdateRegionEvent(getLocation()));
		}
	}

	@Override
	public void update()
	{
		client.send(new UpdatePlayerEvent(this));
	}

	@Override
	public void post()
	{
		getMask().clear();
		getBlocks().clear();
		client.flush();
	}

	public void send(OutboundEvent event)
	{
		client.send(event);
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public byte getRights()
	{
		return (byte) rights;
	}

	public CharacterDesign getDesign()
	{
		return design;
	}

}
