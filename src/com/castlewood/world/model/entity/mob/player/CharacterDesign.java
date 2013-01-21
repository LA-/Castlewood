package com.castlewood.world.model.entity.mob.player;

public class CharacterDesign
{

	private int transform = -1;

	private Gender gender;

	private int[] style = new int[7];

	private int[] colour = new int[5];

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

	public Gender getGender()
	{
		return gender;
	}

	public int[] getStyle()
	{
		return style;
	}

	public int[] getColour()
	{
		return colour;
	}

}
