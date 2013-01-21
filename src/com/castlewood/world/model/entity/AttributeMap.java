package com.castlewood.world.model.entity;

import java.util.HashMap;
import java.util.Map;

public class AttributeMap
{

	private Map<String, Object> attributes = new HashMap<>();

	public void set(String attribute, Object value)
	{
		attributes.put(attribute, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String attribute)
	{
		return (T) attributes.get(attribute);
	}

	public void remove(String attribute)
	{
		attributes.remove(attribute);
	}

}
