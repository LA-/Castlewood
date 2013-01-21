package com.castlewood.io.service.game;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelStateHandlerAdapter;

import com.castlewood.Castlewood;
import com.castlewood.Constants;
import com.castlewood.world.model.entity.mob.player.Player;

public class RS2ChannelHandler extends ChannelStateHandlerAdapter
{

	@Override
	public void channelUnregistered(ChannelHandlerContext context)
	{
		Player player = context.channel().attr(Constants.KEY_PLAYER).get();
		if (player != null)
		{
			Castlewood.getWorld().unregister(player);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext context, Throwable cause)
			throws Exception
	{

	}

}
