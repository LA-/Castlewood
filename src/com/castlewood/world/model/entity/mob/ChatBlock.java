package com.castlewood.world.model.entity.mob;

public class ChatBlock implements Block
{

	private int effect;

	private int colour;

	private byte[] message;

	public ChatBlock(int effect, int colour, byte[] message)
	{
		this.effect = effect;
		this.colour = colour;
		this.message = message;
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

}
