package com.castlewood.io.player.binary;

import com.castlewood.io.player.PlayerFile;
import com.castlewood.io.player.binary.annotation.BinaryEncode;
import com.castlewood.service.world.model.entity.Location;
import com.castlewood.service.world.model.entity.mob.player.CharacterDesign;
import com.castlewood.service.world.model.entity.mob.player.Gender;
import com.castlewood.service.world.model.entity.mob.player.Player;
import com.castlewood.service.world.model.entity.mob.player.PrivacySettings;

public class BinaryFile implements PlayerFile
{

	private String username;

	private String password;

	private byte rights = 0;

	private Location location = new Location(3200, 3200, 0);

	private CharacterDesign design = new CharacterDesign(Gender.MALE, new int[]
	{
			0, 10, 18, 26, 33, 36, 42
	}, new int[]
	{
			0, 0, 0, 0, 0
	});

	private PrivacySettings settings = new PrivacySettings(0, 0, 0);

	public BinaryFile(Player player)
	{
		this.username = player.getUsername();
		this.password = player.getPassword();
		this.rights = player.getRights();
		this.location = player.getLocation();
		this.design = player.getDesign();
		this.settings = player.getSettings();
	}

	public BinaryFile()
	{

	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setRights(byte rights)
	{
		this.rights = rights;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public void setDesign(CharacterDesign design)
	{
		this.design = design;
	}

	public void setSettings(PrivacySettings settings)
	{
		this.settings = settings;
	}

	@Override
	@BinaryEncode(opcode = BinaryFileManager.OPCODE_USERNAME, type = Binary.STRING)
	public String getUsername()
	{
		return username;
	}

	@Override
	@BinaryEncode(opcode = BinaryFileManager.OPCODE_PASSWORD, type = Binary.STRING)
	public String getPassword()
	{
		return password;
	}

	@Override
	@BinaryEncode(opcode = BinaryFileManager.OPCODE_RIGHTS, type = Binary.BYTE)
	public byte getRights()
	{
		return rights;
	}

	@Override
	@BinaryEncode(opcode = BinaryFileManager.OPCODE_LOCATION, type = Binary.OBJECT)
	public Location getLocation()
	{
		return location;
	}

	@Override
	@BinaryEncode(opcode = BinaryFileManager.OPCODE_DESIGN, type = Binary.OBJECT)
	public CharacterDesign getDesign()
	{
		return design;
	}

	@Override
	@BinaryEncode(opcode = BinaryFileManager.OPCODE_SETTINGS, type = Binary.OBJECT)
	public PrivacySettings getSettings()
	{
		return settings;
	}

	@Override
	public String toString()
	{
		return username + ".sav";
	}

}
