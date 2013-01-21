package com.castlewood.io.service;

import io.netty.channel.Channel;

public class ChannelRequest<T> implements Request<T>
{

	private Channel channel;

	private T request;

	public ChannelRequest(Channel channel, T request)
	{
		this.channel = channel;
		this.request = request;
	}

	public Channel getChannel()
	{
		return channel;
	}

	@Override
	public T getRequest()
	{
		return request;
	}
	
}
