package com.castlewood.service.world.model.entity.region;

import java.util.ArrayList;
import java.util.List;

import com.castlewood.service.world.model.entity.Entity;
import com.castlewood.service.world.model.entity.Location;
import com.castlewood.service.world.model.entity.mob.player.Player;

public class Region extends Entity
{

	private List<Player> players = new ArrayList<>();

	private List<Region> surrounding;

	public Region(Location location)
	{
		super(location);
	}

	public void register(Player player)
	{
		players.add(player);
	}

	public void unregister(Player player)
	{
		players.remove(player);
	}

	public List<Region> getSurrounding()
	{
		if (surrounding == null)
		{
			surrounding = new ArrayList<>();
			for (int i = -1; i <= 1; i++)
			{
				for (int j = -1; j <= 1; j++)
				{
					Location location = new Location(getLocation().getX() + i,
							getLocation().getY() + j, 0);
					surrounding.add(RegionManager.get(location));
				}
			}
		}
		return surrounding;
	}

	public List<Player> getPlayers()
	{
		return players;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getLocation() == null) ? 0 : getLocation().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Region other = (Region) obj;
		if (getLocation() == null)
		{
			if (other.getLocation() != null)
				return false;
		}
		else if (!getLocation().equals(other.getLocation()))
			return false;
		return true;
	}

}
