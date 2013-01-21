package com.castlewood.world.model.entity.mob.player;

import java.util.ArrayList;
import java.util.List;

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

	private List<Player> localPlayers = new ArrayList<>();

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
		AppearanceBlock block = new AppearanceBlock(design, 0, 126,
				TextUtils.encodeBase37(username));
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
		set("teleporting", true);
		addAppearanceBlock();
	}

	@Override
	public void unregister()
	{
		getRegion().unregister(this);
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
		advance();
		if (hasRegionChanged())
		{
			if (getRegion() != null)
			{
				getRegion().unregister(this);
			}
			setRegion(RegionManager.getForLocation(getLocation()));
			getRegion().register(this);
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
		set("teleporting", false);
		resetDirections();
		client.flush();
	}

	public void send(OutboundEvent event)
	{
		client.send(event);
	}

	public void disconnect()
	{
		client.close();
	}

	public boolean isDisconnected()
	{
		return !client.isConnected();
	}

	public boolean isUpdateRequired()
	{
		return getMask().getMask() != 0;
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

	public List<Player> getLocalPlayers()
	{
		return localPlayers;
	}
}
