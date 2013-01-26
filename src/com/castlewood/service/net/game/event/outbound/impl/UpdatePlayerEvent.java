package com.castlewood.service.net.game.event.outbound.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

import com.castlewood.Castlewood;
import com.castlewood.Constants;
import com.castlewood.service.net.game.event.outbound.OutboundEvent;
import com.castlewood.service.net.game.packet.outbound.OutboundPacket;
import com.castlewood.service.net.game.packet.outbound.OutboundPacketHeader;
import com.castlewood.service.world.model.entity.mob.AppearanceBlock;
import com.castlewood.service.world.model.entity.mob.ChatBlock;
import com.castlewood.service.world.model.entity.mob.Direction;
import com.castlewood.service.world.model.entity.mob.UpdateMask;
import com.castlewood.service.world.model.entity.mob.player.Player;
import com.castlewood.service.world.model.entity.region.Region;

public class UpdatePlayerEvent implements OutboundEvent
{

	private Player player;

	public UpdatePlayerEvent(Player player)
	{
		this.player = player;
	}

	@Override
	public OutboundPacket encode()
	{
		OutboundPacket block = new OutboundPacket(-1);
		OutboundPacket packet = new OutboundPacket(81,
				OutboundPacketHeader.SHORT);
		packet.switchBit();
		updatePlayerMovement(packet, player, true);
		updatePlayer(block, player, false, true);
		packet.writeBits(8, player.getLocalPlayers().size());
		for (Iterator<Player> iterator = player.getLocalPlayers().iterator(); iterator
				.hasNext();)
		{
			Player other = iterator.next();
			if (Castlewood.getWorld().contains(other.getUsername())
					&& other.getLocation().withinDistance(player.getLocation()))
			{
				updatePlayerMovement(packet, other, false);
				if (other.isUpdateRequired())
				{
					updatePlayer(block, other, false, false);
				}
			}
			else
			{
				iterator.remove();
				packet.writeBits(1, 1);
				packet.writeBits(2, 3);
			}
		}
		for (Region region : player.getRegion().getSurrounding())
		{
			for (Player other : region.getPlayers())
			{
				if (player.getLocalPlayers().size() > 254)
				{
					break;
				}
				if (other == player || player.getLocalPlayers().contains(other))
				{
					continue;
				}
				player.getLocalPlayers().add(other);
				addPlayer(packet, other, player);
				if (!player.getMask().has(Constants.MASK_PLAYER_APPEARANCE))
				{
					player.addAppearanceBlock();
				}
				if (!other.getMask().has(Constants.MASK_PLAYER_APPEARANCE))
				{
					other.addAppearanceBlock();
				}
				updatePlayer(block, other, true, false);
			}
		}
		if (block.getLength() > 0)
		{
			packet.writeBits(11, 2047);
			packet.switchByte();
			packet.getBuffer().writeBytes(block.getBuffer());
		}
		else
		{
			packet.switchByte();
		}
		return packet;
	}

	private static void addPlayer(OutboundPacket packet, Player other, Player player)
	{
		packet.writeBits(11, other.getIndex() + 1);
		packet.writeBits(1, 1);
		packet.writeBits(1, 1);
		int deltaY = other.getLocation().getY() - player.getLocation().getY();
		int deltaX = other.getLocation().getX() - player.getLocation().getX();
		packet.writeBits(5, deltaY);
		packet.writeBits(5, deltaX);
	}

	private static void updatePlayer(OutboundPacket packet, Player other,
			boolean force, boolean current)
	{
		if (other.isUpdateRequired())
		{
			UpdateMask mask = other.getMask().clone();
			if (current)
			{
				if (mask.has(Constants.MASK_PLAYER_CHAT))
				{
					mask.remove(Constants.MASK_PLAYER_CHAT);
				}
			}
			if (mask.getMask() >= 0x100)
			{
				mask.add(0x40);
				packet.getBuffer().writeShort(mask.getMask());
			}
			else
			{
				packet.getBuffer().writeByte(mask.getMask());
			}
			if (mask.has(Constants.MASK_PLAYER_CHAT))
			{
				appendChatBlock(other.getBlocks().get(ChatBlock.class), packet);
			}
			if (mask.has(Constants.MASK_PLAYER_APPEARANCE))
			{
				appendAppearanceBlock(
						other.getBlocks().get(AppearanceBlock.class), packet);
			}
		}
	}

	private static void updatePlayerMovement(OutboundPacket packet, Player player,
			boolean current)
	{
		if (player.<Boolean> get("teleporting") && current)
		{
			packet.writeBits(1, 1);
			packet.writeBits(2, 3);
			packet.writeBits(2, player.getLocation().getHeight());
			packet.writeBits(1, 1);
			packet.writeBits(1, player.isUpdateRequired() ? 1 : 0);
			packet.writeBits(7, player.getLocation().getLocalY());
			packet.writeBits(7, player.getLocation().getLocalX());
		}
		else
		{
			if (player.getWalkingDirection() == Direction.NONE)
			{
				if (player.isUpdateRequired())
				{
					packet.writeBits(1, 1);
					packet.writeBits(2, 0);
				}
				else
				{
					packet.writeBits(1, 0);
				}
			}
			else if (player.getRunningDirection() == Direction.NONE)
			{
				packet.writeBits(1, 1);
				packet.writeBits(2, 1);
				packet.writeBits(3, player.getWalkingDirection().getValue());
				packet.writeBits(1, player.isUpdateRequired() ? 1 : 0);
			}
			else
			{
				packet.writeBits(1, 1);
				packet.writeBits(2, 2);
				packet.writeBits(3, player.getWalkingDirection().getValue());
				packet.writeBits(3, player.getRunningDirection().getValue());
				packet.writeBits(1, player.isUpdateRequired() ? 1 : 0);
			}
		}
	}

	private static void appendChatBlock(ChatBlock block, OutboundPacket packet)
	{
		packet.getBuffer().writeShort(
				(block.getColour() << 8) + block.getEffect());
		packet.getBuffer().writeByte(block.getRights());
		packet.getBuffer().writeByte(block.getMessage().length);
		packet.getBuffer().writeBytes(block.getMessage());
	}

	private static void appendAppearanceBlock(AppearanceBlock block,
			OutboundPacket packet)
	{
		ByteBuf properties = Unpooled.buffer();
		int[] equipment = new int[12];
		int[] style = block.getDesign().getStyle();
		int[] colour = block.getDesign().getColour();
		int[] movement =
		{
				0x328, 0x337, 0x333, 0x334, 0x335, 0x336, 0x338
		};
		properties.writeByte(block.getDesign().getGender().ordinal());
		properties.writeByte(block.getOverhead());
		for (int i = 0; i < 12; i++)
		{
			if (equipment[i] > 0)
			{
				if (i == 6 || i == 8 || i == 11)
				{
					properties.writeByte(0);
					continue;
				}

				properties.writeShort(0x200 + equipment[i]);
			}
			else if (i > 3 && i != 5)
			{
				properties.writeShort(0x100 + style[i - 4 - (i > 5 ? 1 : 0)]);
			}
			else
			{
				properties.writeByte(0);
			}
		}

		for (int i = 0; i < colour.length; i++)
		{
			properties.writeByte(colour[i]);
		}

		for (int i = 0; i < movement.length; i++)
		{
			properties.writeShort(movement[i]);
		}
		properties.writeLong(block.getUsername());
		properties.writeByte(block.getCombat());
		properties.writeShort(0);
		packet.getBuffer().writeByte(properties.writerIndex());
		packet.getBuffer().writeBytes(properties);
	}

}
