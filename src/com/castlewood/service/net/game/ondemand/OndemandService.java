package com.castlewood.service.net.game.ondemand;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import com.castlewood.Constants;
import com.castlewood.io.fs.FileStore;
import com.castlewood.service.net.ChannelRequest;
import com.castlewood.service.net.Service;
import com.castlewood.util.comparator.OndemandChannelRequestComparator;

public class OndemandService extends
		Service<OndemandRequest, ChannelRequest<OndemandRequest>>
{

	private BlockingQueue<ChannelRequest<OndemandRequest>> requests = new PriorityBlockingQueue<>(
			1, new OndemandChannelRequestComparator());

	@Override
	public boolean setup()
	{
		return true;
	}

	@Override
	public void push(ChannelRequest<OndemandRequest> request) throws Exception
	{
		requests.offer(request);
	}

	@Override
	protected ChannelRequest<OndemandRequest> next() throws Exception
	{
		return requests.take();
	}

	@Override
	protected void service(ChannelRequest<OndemandRequest> request)
			throws Exception
	{
		ByteBuffer buffer = FileStore
				.get(request.getRequest().getDescription()).asReadOnlyBuffer();
		int length = buffer.remaining();
		int block = 0;
		while (buffer.hasRemaining())
		{
			int size = buffer.remaining();
			if (size > Constants.ONDEMAND_BLOCK_SIZE)
			{
				size = Constants.ONDEMAND_BLOCK_SIZE;
			}
			byte[] data = new byte[size];
			buffer.get(data);
			request.getChannel().write(
					new OndemandResponse(request.getRequest().getDescription(),
							length, block++, data));
		}
	}

	@Override
	protected void reset()
	{
		
	}

}
