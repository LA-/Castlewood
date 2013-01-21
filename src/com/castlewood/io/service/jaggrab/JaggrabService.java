package com.castlewood.io.service.jaggrab;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.castlewood.Constants;
import com.castlewood.io.fs.FileStore;
import com.castlewood.io.service.ChannelRequest;
import com.castlewood.io.service.Service;

public class JaggrabService extends
		Service<JaggrabRequest, ChannelRequest<JaggrabRequest>>
{

	private BlockingQueue<ChannelRequest<JaggrabRequest>> requests = new LinkedBlockingQueue<>();

	@Override
	public boolean setup()
	{
		if (new ServerBootstrap().localAddress(Constants.PORT_JAGGRAB)
				.channel(NioServerSocketChannel.class)
				.group(new NioEventLoopGroup())
				.childHandler(new ChannelInitializer<SocketChannel>()
				{

					@Override
					public void initChannel(SocketChannel channel)
							throws Exception
					{
						channel.pipeline().addLast(new JaggrabRequestDecoder(),
								new JaggrabRequestHandler());
					}

				}).bind().syncUninterruptibly().isSuccess())
		{
			return true;
		}
		return false;
	}

	@Override
	public void push(ChannelRequest<JaggrabRequest> request) throws Exception
	{
		requests.offer(request);
	}

	@Override
	protected ChannelRequest<JaggrabRequest> next() throws Exception
	{
		return requests.take();
	}

	@Override
	protected void service(ChannelRequest<JaggrabRequest> request)
			throws Exception
	{
		ByteBuf buffer = Unpooled.wrappedBuffer(FileStore.get(request
				.getRequest().getDescription()));
		request.getChannel().write(buffer);
	}

	@Override
	protected void reset()
	{
		
	}

}
