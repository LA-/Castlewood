package com.castlewood.service.world.model.entity.mob.player;

import com.castlewood.io.player.binary.Binary;
import com.castlewood.io.player.binary.annotation.BinaryEncode;

public class PrivacySettings
{

	private int privateSetting = 0;

	private int publicSetting = 0;

	private int tradeSetting = 0;

	public PrivacySettings(int privateSetting, int publicSetting,
			int tradeSetting)
	{
		this.privateSetting = privateSetting;
		this.publicSetting = publicSetting;
		this.tradeSetting = tradeSetting;
	}

	public void setPrivateSetting(int setting)
	{
		this.privateSetting = setting;
	}

	public void setPublicSetting(int setting)
	{
		this.publicSetting = setting;
	}

	public void setTradeSetting(int setting)
	{
		this.tradeSetting = setting;
	}

	@BinaryEncode(opcode = 1, type = Binary.BYTE)
	public byte getPrivateSetting()
	{
		return (byte) privateSetting;
	}

	@BinaryEncode(opcode = 2, type = Binary.BYTE)
	public byte getPublicSetting()
	{
		return (byte) publicSetting;
	}

	@BinaryEncode(opcode = 3, type = Binary.BYTE)
	public byte getTradeSetting()
	{
		return (byte) tradeSetting;
	}
	
}
