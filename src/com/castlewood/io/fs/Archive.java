package com.castlewood.io.fs;

import java.nio.ByteBuffer;
import java.util.Hashtable;
import java.util.Map;

public class Archive
{

	private Map<Integer, ArchiveEntry> entries = new Hashtable<>();

	public Archive(ByteBuffer buffer)
	{
		buffer.position(0);
		boolean compressed = false;
		int uncompressed = ((buffer.get() & 0xFF) << 16)
				| ((buffer.get() & 0xFF) << 8) | (buffer.get() & 0xFF);
		int size = ((buffer.get() & 0xFF) << 16) | ((buffer.get() & 0xFF) << 8)
				| (buffer.get() & 0xFF);
		if (uncompressed != size)
		{
			byte[] data = new byte[size];
			buffer.get(data);
			buffer = ByteBuffer.wrap(FileStore.unzip(data));
			compressed = true;
		}
		int count = buffer.getShort() & 0xFFFF;
		int offset = buffer.position() + (count * 10);
		for (int i = 0; i < count; i++)
		{
			ArchiveEntry entry = new ArchiveEntry(buffer.getInt(),
					((buffer.get() & 0xFF) << 16)
							| ((buffer.get() & 0xFF) << 8)
							| (buffer.get() & 0xFF),
					((buffer.get() & 0xFF) << 16)
							| ((buffer.get() & 0xFF) << 8)
							| (buffer.get() & 0xFF), offset, buffer, compressed);
			entries.put(entry.getHash(), entry);
			offset += entry.getSize();
		}
	}

	public ArchiveEntry getArchiveEntry(String name)
	{
		int hash = 0;
		name = name.toUpperCase();
		for (int i = 0; i < name.length(); i++)
		{
			hash = (hash * 61 + name.charAt(i)) - 32;
		}
		return getArchiveEntry(hash);
	}

	public ArchiveEntry getArchiveEntry(int hash)
	{
		return entries.get(hash);
	}
	
}
