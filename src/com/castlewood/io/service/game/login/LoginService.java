package com.castlewood.io.service.game.login;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.castlewood.Castlewood;
import com.castlewood.Constants;
import com.castlewood.io.service.ChannelRequest;
import com.castlewood.io.service.Service;
import com.castlewood.world.model.entity.mob.player.Client;
import com.castlewood.world.model.entity.mob.player.Player;

public class LoginService extends
		Service<LoginRequest, ChannelRequest<LoginRequest>>
{

	private BlockingQueue<ChannelRequest<LoginRequest>> requests = new LinkedBlockingQueue<>();

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
		if (status == Constants.STATUS_OK)
		{
			Client client = new Client(request.getChannel(), request
					.getRequest().getDecoder(), request.getRequest()
					.getEncoder());
			request.getChannel().attr(Constants.KEY_CLIENT).set(client);
			Castlewood.getWorld().register(new Player(client));
		}
		request.getChannel().write(new LoginResponse(status, 2, false));
	}

	@Override
	protected void reset()
	{

	}

}
