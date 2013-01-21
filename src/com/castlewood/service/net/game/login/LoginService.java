package com.castlewood.service.net.game.login;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.castlewood.Castlewood;
import com.castlewood.Constants;
import com.castlewood.io.file.PlayerFile;
import com.castlewood.service.net.ChannelRequest;
import com.castlewood.service.net.Service;
import com.castlewood.service.net.game.packet.inbound.InboundPacketDecoder;
import com.castlewood.service.net.game.packet.inbound.InboundPacketHandler;
import com.castlewood.service.net.game.packet.outbound.OutboundPacketEncoder;
import com.castlewood.service.world.model.entity.mob.player.Client;
import com.castlewood.service.world.model.entity.mob.player.Player;

public class LoginService extends
		Service<LoginRequest, ChannelRequest<LoginRequest>>
{

	private BlockingQueue<ChannelRequest<LoginRequest>> requests = new LinkedBlockingQueue<>();

	public LoginService()
	{
		super(600);
	}

	@Override
	public boolean setup()
	{
		return true;
	}

	@Override
	public void push(ChannelRequest<LoginRequest> request) throws Exception
	{
		requests.offer(request);
	}

	@Override
	protected ChannelRequest<LoginRequest> next() throws Exception
	{
		return requests.take();
	}

	@Override
	protected void service(ChannelRequest<LoginRequest> request)
			throws Exception
	{
		int status = Constants.STATUS_OK;
		if (!Castlewood.getFileManager().checkCredentials(
				request.getRequest().getUsername(),
				request.getRequest().getPassword()))
		{
			status = Constants.STATUS_INVALID_CREDENTIALS;
		}
		if (Castlewood.getWorld().contains(request.getRequest().getUsername()))
		{
			status = Constants.STATUS_ACCOUNT_ONLINE;
		}
		PlayerFile file = Castlewood.getFileManager().load(
				request.getRequest().getUsername());
		request.getChannel().write(
				new LoginResponse(status, file.getRights(), false));
		if (status == Constants.STATUS_OK)
		{
			Client client = new Client(request.getChannel(), request
					.getRequest().getDecoder(), request.getRequest()
					.getEncoder());
			Player player = new Player(client, file);
			request.getChannel().attr(Constants.KEY_CLIENT).set(client);
			request.getChannel().attr(Constants.KEY_PLAYER).set(player);
			Castlewood.getWorld().register(player);
			request.getChannel()
					.pipeline()
					.addFirst(OutboundPacketEncoder.class.getSimpleName(),
							new OutboundPacketEncoder());
			request.getChannel()
					.pipeline()
					.addFirst(InboundPacketHandler.class.getSimpleName(),
							new InboundPacketHandler());
			request.getChannel()
					.pipeline()
					.addFirst(InboundPacketDecoder.class.getSimpleName(),
							new InboundPacketDecoder());
		}
	}

	@Override
	protected void reset()
	{

	}

}
