package com.castlewood.world.model.entity.mob.player;

import com.castlewood.io.file.binary.Binary;
import com.castlewood.io.file.binary.annotation.BinaryEncode;

public class CharacterDesign
{

	private int transform = -1;

	private Gender gender;

	private int[] style = new int[7];

	private int[] colour = new int[5];

	public CharacterDesign(Gender gender, int[] style, int[] colour)
	{
		this.gender = gender;
		this.style = style;
		this.colour = colour;
	}

	public void setTransform(int id)
	{
		this.transform = id;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public void setStyle(int index, int style)
	{
		this.style[index] = style;
	}

	public void setColour(int index, int colour)
	{
		this.colour[index] = colour;
	}

	public int getTransform()
	{
		return transform;
	}

	@BinaryEncode(opcode = 1, type = Binary.ENUM)
	public Gender getGender()
	{
		return gender;
	}

	@BinaryEncode(opcode = 2, type = Binary.NUMBER_ARRAY)
	public int[] getStyle()
	{
		return style;
	}

	@BinaryEncode(opcode = 3, type = Binary.NUMBER_ARRAY)
	public int[] getColour()
	{
		return colour;
	}

}
