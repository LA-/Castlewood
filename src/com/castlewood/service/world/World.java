package com.castlewood.service.world;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.castlewood.Castlewood;
import com.castlewood.Constants;
import com.castlewood.service.world.model.entity.mob.player.Player;
import com.castlewood.service.world.task.Task;
import com.castlewood.service.world.task.TaskRequest;

public class World extends Task
{

	private Map<Integer, Player> players = new ConcurrentHashMap<>();

	private Map<String, Integer> usernameToIndex = new LinkedHashMap<>();

	private LinkedList<Player> register = new LinkedList<>();

	private LinkedList<Player> unregister = new LinkedList<>();

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
		int registered = 0;
		for (Player player : players.values())
		{
			if (player.isDisconnected())
			{
				unregister.add(player);
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
		Player player;
		while ((player = register.poll()) != null)
		{
			register(player);
			registered++;
			if (registered > 10)
			{
				break;
			}
		}
		while ((player = unregister.poll()) != null)
		{
			unregister(player);
		}
	}

	public boolean contains(String username)
	{
		return usernameToIndex.get(username) != null;
	}

	public Map<Integer, Player> getPlayers()
	{
		return Collections.unmodifiableMap(players);
	}

}
