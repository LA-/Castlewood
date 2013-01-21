package com.castlewood.service.world;

import java.util.LinkedList;

import com.castlewood.service.net.Service;
import com.castlewood.task.Task;
import com.castlewood.task.TaskRequest;

public class WorldService extends Service<Task, TaskRequest>
{

	private LinkedList<TaskRequest> requests = new LinkedList<>();

	private LinkedList<TaskRequest> renew = new LinkedList<>();

	public WorldService()
	{
		super(600);
	}

	@Override
	public boolean setup()
	{
		return true;
	}

	@Override
	public void push(TaskRequest request) throws Exception
	{
		requests.addLast(request);
	}

	@Override
	protected TaskRequest next() throws Exception
	{
		return requests.poll();
	}

	@Override
	protected void service(TaskRequest request) throws Exception
	{
		if (request.getRequest().isRunning())
		{
			renew.addLast(request);
		}
		request.getRequest().pulse();
	}

	@Override
	protected void reset()
	{
		TaskRequest request;
		while ((request = renew.poll()) != null)
		{
			requests.addLast(request);
		}
	}

}
