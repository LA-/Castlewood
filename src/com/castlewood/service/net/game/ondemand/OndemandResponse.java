package com.castlewood.service.net.game.ondemand;

import com.castlewood.io.fs.FileDescription;

public class OndemandResponse
{

	private FileDescription description;

	private int length;

	private int block;

	private byte[] data;

	public OndemandResponse(FileDescription description, int length, int block,
			byte[] data)
	{
		this.description = description;
		this.length = length;
		this.block = block;
		this.data = data;
	}

	public FileDescription getDescription()
	{
		return description;
	}

	public int getLength()
	{
		return length;
	}

	public int getBlock()
	{
		return block;
	}

	public byte[] getData()
	{
		return data;
	}
	
}
