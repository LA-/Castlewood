package com.castlewood.world.model.entity.region;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.castlewood.world.model.entity.Entity;
import com.castlewood.world.model.entity.Location;
import com.castlewood.world.model.entity.mob.player.Player;

public class Region extends Entity
{

	private List<Player> players = new ArrayList<>();

	private Set<Region> surrounding;

	public Region(Location location)
	{
		super(location);
	}

	public void add(Player player)
	{
		players.add(player);
	}

	public Set<Region> getSurrounding()
	{
		if (surrounding == null)
		{
			surrounding = new HashSet<>();
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
