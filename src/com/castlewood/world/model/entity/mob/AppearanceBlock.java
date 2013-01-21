package com.castlewood.world.model.entity.mob;

import com.castlewood.world.model.entity.mob.player.CharacterDesign;

public class AppearanceBlock implements Block
{

	private CharacterDesign design;

	private int overhead;

	private int combat;

	private long username;

	public AppearanceBlock(CharacterDesign design, int overhead, int combat,
			long username)
	{
		this.design = design;
		this.overhead = overhead;
		this.combat = combat;
		this.username = username;
	}

	public CharacterDesign getDesign()
	{
		return design;
	}

	public int getOverhead()
	{
		return overhead;
	}

	public int getCombat()
	{
		return combat;
	}

	public long getUsername()
	{
		return username;
	}
	
}
