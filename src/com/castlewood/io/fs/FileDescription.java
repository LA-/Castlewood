package com.castlewood.io.fs;

public class FileDescription
{

	private int index;

	private int archive;

	public FileDescription(int index, int archive)
	{
		this.index = index;
		this.archive = archive;
	}

	public int getIndex()
	{
		return index;
	}

	public int getArchive()
	{
		return archive;
	}

	@Override
	public int hashCode()
	{
		int hash = 1234;
		hash = (hash << 1) + index * 31;
		hash = (hash << 1) + archive * 31;
		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		if (object.getClass() != FileDescription.class)
		{
			return false;
		}
		FileDescription description = (FileDescription) object;
		return description.index == index && description.archive == archive;
	}

}
