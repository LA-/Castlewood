package com.castlewood.io.fs;

public class ArchiveMeta
{

	private int size;

	private int block;

	public ArchiveMeta(int size, int block)
	{
		this.size = size;
		this.block = block;
	}

	public int getSize()
	{
		return size;
	}

	public int getBlock()
	{
		return block;
	}
	
}
