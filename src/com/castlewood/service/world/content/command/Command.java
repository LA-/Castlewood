package com.castlewood.service.world.content.command;

import com.castlewood.service.world.model.entity.mob.player.Player;

public interface Command
{

	public void handle(Player player, String[] args);
	
	public boolean able(Player player);
	
}
