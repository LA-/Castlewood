package com.castlewood.io.service.game.packet.inbound;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.castlewood.Constants;
import com.castlewood.io.service.ChannelRequest;
import com.castlewood.io.service.Service;

public class InboundPacketService extends
		Service<InboundPacket, ChannelRequest<InboundPacket>>
{

	private BlockingQueue<ChannelRequest<InboundPacket>> requests = new LinkedBlockingQueue<>();

	@Override
	public boolean setup()
	{
		return true;
	}

	@Override
	public void push(ChannelRequest<InboundPacket> request) throws Exception
	{
		requests.offer(request);
	}

	@Override
	protected ChannelRequest<InboundPacket> next() throws Exception
	{
		return requests.take();
	}

	@Override
	protected void service(ChannelRequest<InboundPacket> request)
			throws Exception
	{
		request.getChannel().attr(Constants.KEY_CLIENT).get()
				.push(request.getRequest());
	}

	@Override
	protected void reset()
	{
		
	}

}
