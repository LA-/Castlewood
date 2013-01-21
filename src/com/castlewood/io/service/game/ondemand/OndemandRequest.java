package com.castlewood.io.service.game.ondemand;

import com.castlewood.io.fs.FileDescription;

public class OndemandRequest implements Comparable<OndemandRequest>
{

	private FileDescription description;

	private int priority;

	public OndemandRequest(FileDescription description, int priority)
	{
		this.description = description;
		this.priority = priority;
	}

	public FileDescription getDescription()
	{
		return description;
	}

	@Override
	public int compareTo(OndemandRequest request)
	{
		return priority - request.priority;
	}

}
