package com.castlewood.io.fs;

import java.nio.ByteBuffer;

public class ArchiveEntry
{

	private int hash;

	private int size;

	private ByteBuffer buffer;

	public ArchiveEntry(int hash, int uncompressed, int size, int offset,
			ByteBuffer buffer, boolean compressed)
	{
		buffer.mark();
		this.hash = hash;
		this.size = size;
		byte[] data = new byte[size];
		buffer.position(offset);
		buffer.get(data);
		buffer.reset();
		if (!compressed)
		{
			buffer = ByteBuffer.wrap(FileStore.unzip(data));
		}
		else
		{
			buffer = ByteBuffer.wrap(data);
		}
		this.buffer = buffer;
	}

	public int getHash()
	{
		return hash;
	}

	public int getSize()
	{
		return size;
	}

	public ByteBuffer getBuffer()
	{
		return buffer;
	}

}
