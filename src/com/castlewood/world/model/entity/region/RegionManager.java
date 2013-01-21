package com.castlewood.world.model.entity.region;

import java.util.Hashtable;
import java.util.Map;

import com.castlewood.world.model.entity.Location;

public class RegionManager
{

	private static Map<Location, Region> regions = new Hashtable<>();

	public static Region getForLocation(Location location)
	{
		Location position = new Location(location.getRegionX(),
				location.getRegionY(), 0);
		return get(position);
	}

	public static Region get(Location location)
	{
		Region region = regions.get(location);
		if (region == null)
		{
			region = new Region(location);
			regions.put(location, region);
		}
		return region;
	}

}
