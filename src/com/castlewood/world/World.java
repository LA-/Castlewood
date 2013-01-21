package com.castlewood.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.castlewood.Castlewood;
import com.castlewood.Constants;
import com.castlewood.task.Task;
import com.castlewood.task.TaskRequest;
import com.castlewood.world.model.entity.mob.player.Player;

public class World extends Task
{

	private Map<Integer, Player> players = new ConcurrentHashMap<>();

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
		player.setIndex(index);
		player.register();
	}

	public void unregister(Player player)
	{
		player.unregister();
		players.remove(player.getIndex());
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
		for (Player player : players.values())
		{
			if (player == null)
			{
				continue;
			}
			if (player.isDisconnected())
			{
				unregister(player);
				continue;
			}
			player.prepare();
		}
		for (Player player : players.values())
		{
			if (player == null)
			{
				continue;
			}
			player.update();
		}
		for (Player player : players.values())
		{
			if (player == null)
			{
				continue;
			}
			player.post();
		}
		for (Iterator<Player> iterator = register.iterator(); iterator
				.hasNext();)
		{
			register(iterator.next());
			iterator.remove();
		}
	}

	public boolean contains(Player player)
	{
		for (Player online : players.values())
		{
			if (online.getUsername().equalsIgnoreCase(player.getUsername()))
			{
				return true;
			}
		}
		return false;
	}

}
