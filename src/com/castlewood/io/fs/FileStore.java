package com.castlewood.io.fs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Hashtable;
import java.util.Map;
import java.util.zip.CRC32;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import com.castlewood.Constants;

public class FileStore
{

	private static FileChannel datum;

	private static ByteBuffer meta;

	private static Map<Integer, FileMeta> cache = new Hashtable<>();

	private static Map<FileDescription, ByteBuffer> data = new Hashtable<>();

	private static Map<FileDescription, Archive> archives = new Hashtable<>();

	public static void open(File root)
	{
		System.out.println("Loading cache from " + root + "...");
		System.out.println("----------------------------------");
		try
		{
			File file = new File(root, "main_file_cache.dat");
			datum = new RandomAccessFile(file, "rw").getChannel();
			System.out.println("\tCache found " + file);
			for (int i = 0; i < 254; i++)
			{
				file = new File(root, "main_file_cache.idx" + i);
				if (!file.exists())
				{
					break;
				}
				cache.put(
						i,
						new FileMeta(new RandomAccessFile(file, "rw")
								.getChannel()));
				System.out.println("\t\tFound index " + i + " and built with "
						+ cache.get(i).getArchiveCount() + " archives");
			}
			FileMeta meta = cache.get(0);
			System.out.println("\tBuilding CRC32 table with "
					+ meta.getArchiveCount() + " entries...");
			CRC32 crc = new CRC32();
			int[] checksums = new int[meta.getArchiveCount()];
			checksums[0] = Constants.CLIENT_REVISION;
			for (int i = 1; i < checksums.length; i++)
			{
				FileDescription description = new FileDescription(0, i);
				ByteBuffer buffer = get(description);
				crc.reset();
				crc.update(buffer.array());
				checksums[i] = (int) crc.getValue();
				System.out.println("\t\tEntry " + i
						+ " generated a crc value of " + checksums[i]);
			}
			int hash = 1234;
			for (int i = 0; i < checksums.length; i++)
			{
				hash = (hash << 1) + checksums[i];
			}
			System.out.println("\tUp to date cache hash is " + hash);
			FileStore.meta = ByteBuffer.allocate(4 * (checksums.length + 1));
			for (int i = 0; i < checksums.length; i++)
			{
				FileStore.meta.putInt(checksums[i]);
			}
			FileStore.meta.putInt(hash);
			FileStore.meta.flip();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		System.out.println("----------------------------------");
	}

	public static Archive getArchive(FileDescription description)
	{
		if (archives.containsKey(description))
		{
			return archives.get(description);
		}
		ByteBuffer buffer = get(description).duplicate();
		Archive archive = new Archive(buffer);
		archives.put(description, archive);
		return archive;
	}

	public static ByteBuffer get(FileDescription description)
	{
		if (description.getIndex() == 255 && description.getArchive() == 255)
		{
			return meta;
		}
		if (data.containsKey(description))
		{
			return data.get(description);
		}
		ArchiveMeta meta = cache.get(description.getIndex()).getArchiveMeta(
				description.getArchive());
		ByteBuffer buffer = ByteBuffer.allocate(meta.getSize());
		int remaining = meta.getSize();
		int block = meta.getBlock();
		int cycles = 0;
		while (remaining > 0)
		{
			int read = Constants.BLOCK_SIZE;
			if (read > remaining)
			{
				read = remaining;
			}
			try
			{
				ByteBuffer data = datum.map(MapMode.PRIVATE, block
						* Constants.BLOCK_DATA, read + Constants.BLOCK_HEADER);
				int expectedArchive = data.getShort() & 0xFFFF;
				int expectedCycle = data.getShort() & 0xFFFF;
				int nextBlock = (data.get() & 0xFF) << 16
						| (data.get() & 0xFF) << 8 | (data.get() & 0xFF);
				int expectedIndex = data.get() & 0xFF;
				if (expectedArchive != description.getArchive()
						|| expectedCycle != cycles++
						|| expectedIndex != description.getIndex() + 1)
				{
					throw new IOException();
				}
				remaining -= read;
				block = nextBlock;
				buffer.put(data);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		buffer.flip();
		data.put(description, buffer);
		return buffer;
	}

	public static byte[] unzip(byte[] compressed)
	{
		byte[] buffer = null;
		byte[] unzipped = new byte[compressed.length + 4];
		unzipped[0] = 'B';
		unzipped[1] = 'Z';
		unzipped[2] = 'h';
		unzipped[3] = '1';
		System.arraycopy(compressed, 0, unzipped, 4, compressed.length);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try
		{
			InputStream stream = new BZip2CompressorInputStream(
					new ByteArrayInputStream(unzipped));
			while (true)
			{
				byte[] read = new byte[512];
				int position = stream.read(read, 0, read.length);
				if (position == -1)
				{
					break;
				}
				out.write(read, 0, position);
			}
			buffer = out.toByteArray();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return buffer;
	}

}
