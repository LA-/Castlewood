package com.castlewood.io.service.game.handshake;

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
import com.castlewood.io.service.ChannelRequest;
import com.castlewood.io.service.Service;

public class HandshakeService extends
		Service<HandshakeRequest, ChannelRequest<HandshakeRequest>>
{

	private BlockingQueue<ChannelRequest<HandshakeRequest>> requests = new LinkedBlockingQueue<>();

	@Override
	public boolean setup()
	{
		if (new ServerBootstrap().localAddress(Constants.PORT_GAME)
				.channel(NioServerSocketChannel.class)
				.group(new NioEventLoopGroup())
				.childHandler(new ChannelInitializer<SocketChannel>()
				{

					@Override
					public void initChannel(SocketChannel channel)
							throws Exception
					{
						channel.pipeline().addLast(
								new HandshakeRequestDecoder(),
								new HandshakeRequestHandler());
					}

				}).bind().syncUninterruptibly().isSuccess())
		{
			return true;
		}
		return false;
	}

	@Override
	public void push(ChannelRequest<HandshakeRequest> request) throws Exception
	{
		requests.offer(request);
	}

	@Override
	protected ChannelRequest<HandshakeRequest> next() throws Exception
	{
		return requests.take();
	}

	@Override
	protected void service(ChannelRequest<HandshakeRequest> request)
			throws Exception
	{
		ByteBuf buffer;
		switch (request.getRequest().getId())
		{
		case Constants.SERVICE_LOGIN:
			buffer = Unpooled.buffer(17);
			buffer.writeByte(Constants.STATUS_EXCHANGE_DATA);
			buffer.writeLong(0);
			buffer.writeLong(0);
			break;
		case Constants.SERVICE_ONDEMAND:
			buffer = Unpooled.buffer(8);
			buffer.writeLong(Constants.STATUS_EXCHANGE_DATA);
			break;
		default:
			throw new UnsupportedOperationException();
		}
		request.getChannel().write(buffer);
		request.getRequest().transform();
	}

	@Override
	protected void reset()
	{
		
	}

}
