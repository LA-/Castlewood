package com.castlewood.util.comparator;

import java.util.Comparator;

import com.castlewood.service.net.ChannelRequest;
import com.castlewood.service.net.game.ondemand.OndemandRequest;

public class OndemandChannelRequestComparator implements
		Comparator<ChannelRequest<OndemandRequest>>
{

	@Override
	public int compare(ChannelRequest<OndemandRequest> request,
			ChannelRequest<OndemandRequest> compare)
	{
		return request.getRequest().compareTo(compare.getRequest());
	}

}
