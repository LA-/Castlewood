package com.castlewood.io.player;

import com.castlewood.service.world.model.entity.Location;
import com.castlewood.service.world.model.entity.mob.player.CharacterDesign;
import com.castlewood.service.world.model.entity.mob.player.PrivacySettings;

public interface PlayerFile
{

	public String getUsername();

	public String getPassword();

	public byte getRights();

	public Location getLocation();

	public CharacterDesign getDesign();
	
	public PrivacySettings getSettings();

}
