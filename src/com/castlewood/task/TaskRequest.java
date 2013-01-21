package com.castlewood.task;

import com.castlewood.io.service.Request;

public class TaskRequest implements Request<Task>
{

	private Task task;

	public TaskRequest(Task task)
	{
		this.task = task;
	}

	@Override
	public Task getRequest()
	{
		return task;
	}

}
