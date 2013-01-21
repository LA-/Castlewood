package com.castlewood.world.model.entity.mob;

public class ChatBlock implements Block
{

	private int effect;

	private int colour;

	private byte[] message;

	private int rights;

	public ChatBlock(int effect, int colour, byte[] message, int rights)
	{
		this.effect = effect;
		this.colour = colour;
		this.message = message;
		this.rights = rights;
	}

	public int getEffect()
	{
		return effect;
	}

	public int getColour()
	{
		return colour;
	}

	public byte[] getMessage()
	{
		return message;
	}

	public int getRights()
	{
		return rights;
	}
	
}
