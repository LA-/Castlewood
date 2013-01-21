package com.castlewood.world.model.entity.mob;

import java.util.Hashtable;
import java.util.Map;

public class Blocks
{

	private Map<Class<? extends Block>, Block> blocks = new Hashtable<>();

	public void add(Block block)
	{
		blocks.put(block.getClass(), block);
	}

	public void remove(Block block)
	{
		blocks.remove(block.getClass());
	}

	@SuppressWarnings("unchecked")
	public <T extends Block> T get(Class<T> block)
	{
		return (T) blocks.get(block);
	}

	public void clear()
	{
		blocks.clear();
	}

	public boolean isUpdateRequired()
	{
		return !blocks.isEmpty();
	}

}
