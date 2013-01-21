package com.castlewood;

import java.io.File;

import com.castlewood.io.file.PlayerFileManager;
import com.castlewood.io.file.binary.BinaryFile;
import com.castlewood.io.file.binary.BinaryFileManager;
import com.castlewood.io.fs.FileStore;
import com.castlewood.io.service.ServiceManager;
import com.castlewood.io.service.game.handshake.HandshakeService;
import com.castlewood.io.service.game.login.LoginService;
import com.castlewood.io.service.game.ondemand.OndemandService;
import com.castlewood.io.service.game.packet.inbound.InboundPacketService;
import com.castlewood.io.service.jaggrab.JaggrabService;
import com.castlewood.task.TaskRequest;
import com.castlewood.world.World;
import com.castlewood.world.WorldService;

public class Castlewood
{

	private static ServiceManager manager = new ServiceManager(6);

	private static World world = new World();

	private static PlayerFileManager<BinaryFile> files = new BinaryFileManager();

	public static void main(String... args)
	{
		FileStore.open(new File(Constants.CACHE_PATH));
		manager.register(JaggrabService.class, new JaggrabService());
		manager.register(HandshakeService.class, new HandshakeService());
		manager.register(OndemandService.class, new OndemandService());
		manager.register(LoginService.class, new LoginService());
		manager.register(InboundPacketService.class, new InboundPacketService());
		manager.register(WorldService.class, new WorldService());
		try
		{
			manager.getService(WorldService.class).push(new TaskRequest(world));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		manager.run();
	}

	public static ServiceManager getServiceManager()
	{
		return manager;
	}

	public static World getWorld()
	{
		return world;
	}

	public static PlayerFileManager<BinaryFile> getFileManager()
	{
		return files;
	}

}
