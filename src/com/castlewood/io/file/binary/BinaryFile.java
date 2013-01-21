package com.castlewood.io.file.binary;

import com.castlewood.io.file.PlayerFile;
import com.castlewood.io.file.binary.annotation.BinaryEncode;
import com.castlewood.world.model.entity.Location;
import com.castlewood.world.model.entity.mob.player.CharacterDesign;
import com.castlewood.world.model.entity.mob.player.Gender;
import com.castlewood.world.model.entity.mob.player.Player;

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

	public BinaryFile(Player player)
	{
		this.username = player.getUsername();
		this.password = player.getPassword();
		this.rights = player.getRights();
		this.location = player.getLocation();
		this.design = player.getDesign();
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

}
