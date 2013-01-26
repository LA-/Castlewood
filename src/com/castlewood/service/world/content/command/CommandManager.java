package com.castlewood.service.world.content.command;

import java.util.Hashtable;
import java.util.Map;

import com.castlewood.service.net.game.event.outbound.impl.SendMessageEvent;
import com.castlewood.service.world.model.entity.mob.player.Player;

public class CommandManager
{

	private static Map<String, Command> commands = new Hashtable<>();

	static
	{

	}

	public static void handle(Player player, String input)
	{
		String[] args = input.toLowerCase().split(" ");
		if (commands.containsKey(args[0]))
		{
			Command command = commands.get(args[0]);
			if (command.able(player))
			{
				command.handle(player, args);
			}
			else
			{
				player.send(new SendMessageEvent(
						"This command is unavailable to you."));
			}
		}
		else
		{
			player.send(new SendMessageEvent("This command does not exist."));
		}
	}
	
}
