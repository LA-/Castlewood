package com.castlewood.io.fs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Hashtable;
import java.util.Map;

import com.castlewood.Constants;

public class FileMeta
{

	private Map<Integer, ArchiveMeta> meta = new Hashtable<>();

	public FileMeta(FileChannel channel)
	{
		try
		{
			if (channel.size() % Constants.ARCHIVE_META_SIZE != 0)
			{
				throw new IOException();
			}
			for (int i = 0; i < channel.size(); i += 6)
			{
				int archive = i / 6;
				ByteBuffer buffer = channel.map(MapMode.PRIVATE, archive
						* Constants.ARCHIVE_META_SIZE,
						Constants.ARCHIVE_META_SIZE);
				int size = (buffer.get() & 0xFF) << 16
						| (buffer.get() & 0xFF) << 8 | (buffer.get() & 0xFF);
				int block = (buffer.get() & 0xFF) << 16
						| (buffer.get() & 0xFF) << 8 | (buffer.get() & 0xFF);
				meta.put(archive, new ArchiveMeta(size, block));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public ArchiveMeta getArchiveMeta(int archive)
	{
		return meta.get(archive);
	}

	public int getArchiveCount()
	{
		return meta.size();
	}

}
