package com.castlewood.service.world.model.entity;

import com.castlewood.service.world.model.entity.region.Region;

public class Entity extends AttributeMap
{

	private Location location;

	private Region region;

	public Entity(Location location)
	{
		this.location = location;
	}

	public void setRegion(Region region)
	{
		this.region = region;
	}

	public Location getLocation()
	{
		return location;
	}

	public Region getRegion()
	{
		return region;
	}

}
