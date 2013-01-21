package com.castlewood.io.file.binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

import com.castlewood.io.file.PlayerFileManager;
import com.castlewood.io.file.binary.annotation.BinaryEncode;
import com.castlewood.world.model.entity.Location;
import com.castlewood.world.model.entity.mob.player.CharacterDesign;
import com.castlewood.world.model.entity.mob.player.Gender;

public class BinaryFileManager implements PlayerFileManager<BinaryFile>
{

	public static final byte OPCODE_USERNAME = 1, OPCODE_PASSWORD = 2,
			OPCODE_RIGHTS = 3, OPCODE_LOCATION = 4, OPCODE_DESIGN = 5;

	private static Map<String, BinaryFile> files = new Hashtable<>();

	@Override
	public boolean checkCredentials(String username, String password)
	{
		BinaryFile profile;
		if (files.containsKey(username))
		{
			profile = files.get(username);
		}
		else
		{
			profile = new BinaryFile();
			File file = new File("./data/saves/" + username.toLowerCase()
					+ ".sav");
			if (!file.exists())
			{
				profile.setUsername(username);
				profile.setPassword(password);
				files.put(username, profile);
				save(profile);
				return true;
			}
			try
			{
				DataInputStream stream = new DataInputStream(
						new FileInputStream(file));
				while (true)
				{
					byte opcode = stream.readByte();
					if (opcode == 0)
					{
						break;
					}
					switch (opcode)
					{
					case OPCODE_USERNAME:
						profile.setUsername(stream.readUTF());
						break;
					case OPCODE_PASSWORD:
						profile.setPassword(stream.readUTF());
						break;
					case OPCODE_RIGHTS:
						profile.setRights(stream.readByte());
						break;
					case OPCODE_LOCATION:
						short x = -1;
						short y = -1;
						byte height = -1;
						while (x == -1 || y == -1 || height == -1)
						{
							opcode = stream.readByte();
							switch (opcode)
							{
							case 1:
								x = stream.readShort();
								break;
							case 2:
								y = stream.readShort();
								break;
							case 3:
								height = stream.readByte();
								break;
							}
						}
						Location location = new Location(x, y, height);
						profile.setLocation(location);
						break;
					case OPCODE_DESIGN:
						Gender gender = null;
						int[] style = null;
						int[] colour = null;
						while (gender == null || style == null
								|| colour == null)
						{
							opcode = stream.readByte();
							switch (opcode)
							{
							case 1:
								gender = Gender.values()[stream.readShort()];
								break;
							case 2:
								style = new int[stream.readShort()];
								for (int i = 0; i < style.length; i++)
								{
									style[i] = stream.readInt();
								}
								break;
							case 3:
								colour = new int[stream.readShort()];
								for (int i = 0; i < colour.length; i++)
								{
									colour[i] = stream.readInt();
								}
								break;
							}
						}
						CharacterDesign design = new CharacterDesign(gender,
								style, colour);
						profile.setDesign(design);
						break;
					}
				}

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (profile.getUsername().equalsIgnoreCase(username)
				&& profile.getPassword().equalsIgnoreCase(password))
		{
			files.put(username, profile);
			return true;
		}
		return false;
	}

	@Override
	public boolean save(BinaryFile file)
	{
		try
		{
			DataOutputStream stream = new DataOutputStream(
					new FileOutputStream(new File("./data/saves/"
							+ file.getUsername().toLowerCase() + ".sav")));
			for (Method method : file.getClass().getMethods())
			{
				if (!method.isAnnotationPresent(BinaryEncode.class))
				{
					continue;
				}
				BinaryEncode encode = method.getAnnotation(BinaryEncode.class);
				encode.type().write(stream, encode.opcode(),
						method.invoke(file), encode.type());
			}
			stream.writeByte(0);
			stream.flush();
			stream.close();
			return true;
		}
		catch (IOException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public BinaryFile load(String username)
	{
		return files.get(username);
	}
}
