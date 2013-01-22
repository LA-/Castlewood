package com.castlewood.service.world.model.entity.mob.player;

import java.util.ArrayList;
import java.util.List;

import com.castlewood.Castlewood;
import com.castlewood.Constants;
import com.castlewood.io.file.PlayerFile;
import com.castlewood.io.file.binary.BinaryFile;
import com.castlewood.service.net.game.event.inbound.InboundEventManager;
import com.castlewood.service.net.game.event.outbound.OutboundEvent;
import com.castlewood.service.net.game.event.outbound.impl.InitializeEvent;
import com.castlewood.service.net.game.event.outbound.impl.SetInteractionOptionEvent;
import com.castlewood.service.net.game.event.outbound.impl.SetTabInterfaceEvent;
import com.castlewood.service.net.game.event.outbound.impl.UpdatePlayerEvent;
import com.castlewood.service.net.game.event.outbound.impl.UpdateRegionEvent;
import com.castlewood.service.net.game.packet.inbound.InboundPacket;
import com.castlewood.service.world.model.entity.mob.AppearanceBlock;
import com.castlewood.service.world.model.entity.mob.ChatBlock;
import com.castlewood.service.world.model.entity.mob.Mob;
import com.castlewood.service.world.model.entity.region.Region;
import com.castlewood.service.world.model.entity.region.RegionManager;
import com.castlewood.util.misc.TextUtils;

public class Player extends Mob
{

	private static final int[] tabs =
	{
			2423, 3917, 638, 3213, 1644, 5608, 1151, -1, 5065, 5715, 2449, 904,
			147, 962
	};

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
		client.send(new InitializeEvent(true, getIndex() + 1));
		for (int i = 0; i < tabs.length; i++)
		{
			client.send(new SetTabInterfaceEvent(tabs[i], i));
		}
		client.send(new SetInteractionOptionEvent(1, false, "Follow"));
		client.send(new SetInteractionOptionEvent(2, false, "Trade with"));
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
			Region region = RegionManager.getForLocation(getLocation());
			region.register(this);
			setRegion(region);
			set("teleporting", true);
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
