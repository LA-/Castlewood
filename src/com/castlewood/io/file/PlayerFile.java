package com.castlewood.io.file;

import com.castlewood.world.model.entity.Location;
import com.castlewood.world.model.entity.mob.player.CharacterDesign;

public interface PlayerFile
{

	public String getUsername();

	public String getPassword();

	public byte getRights();

	public Location getLocation();

	public CharacterDesign getDesign();

}
