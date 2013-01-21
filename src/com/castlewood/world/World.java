package com.castlewood.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.castlewood.Castlewood;
import com.castlewood.Constants;
import com.castlewood.task.Task;
import com.castlewood.task.TaskRequest;
import com.castlewood.world.model.entity.mob.player.Player;

public class World extends Task
{

	private Map<Integer, Player> players = new Hashtable<>();

	private Map<String, Integer> usernameToIndex = new LinkedHashMap<>();

	private List<Player> register = new ArrayList<>();

	public World()
	{
		super(1);
	}

	public void register(Player player)
	{
		int index = -1;
		for (int i = 0; i < Constants.MAXIMUM_PLAYERS; i++)
		{
			if (players.get(i) == null)
			{
				index = i;
				break;
			}
		}
		players.put(index, player);
		usernameToIndex.put(player.getUsername(), index);
		player.setIndex(index);
		player.register();
	}

	public void unregister(Player player)
	{
		player.unregister();
		players.remove(player.getIndex());
		usernameToIndex.remove(player.getUsername());
	}

	public static void push(Task task)
	{
		try
		{
			Castlewood.getServiceManager().getService(WorldService.class)
					.push(new TaskRequest(task));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void execute()
	{
		Map<Integer, Player> players = Collections
				.unmodifiableMap(this.players);
		for (Player player : players.values())
		{
			if (player.isDisconnected())
			{
				unregister(player);
				continue;
			}
			player.prepare();
		}
		for (Player player : players.values())
		{
			player.update();
		}
		for (Player player : players.values())
		{
			player.post();
		}
		for (Iterator<Player> iterator = register.iterator(); iterator
				.hasNext();)
		{
			register(iterator.next());
			iterator.remove();
		}
	}

	public boolean contains(String username)
	{
		return usernameToIndex.get(username) != null;
	}

}
