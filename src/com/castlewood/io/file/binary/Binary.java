package com.castlewood.io.file.binary;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.castlewood.io.file.binary.annotation.BinaryEncode;

public enum Binary
{

	BYTE,

	SHORT,

	INTEGER,

	LONG,

	BOOLEAN,

	STRING,

	OBJECT,

	OBJECT_ARRAY,

	NUMBER_ARRAY,

	ENUM;

	public void write(DataOutputStream stream, int opcode, Object value,
			Binary type) throws IOException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		if (opcode != -1)
		{
			stream.writeByte(opcode);
		}
		switch (type)
		{
		case BYTE:
			stream.writeByte((byte) value);
			break;
		case SHORT:
			stream.writeShort((short) value);
			break;
		case INTEGER:
			stream.writeInt((int) value);
			break;
		case LONG:
			stream.writeLong((long) value);
			break;
		case BOOLEAN:
			stream.writeBoolean((boolean) value);
			break;
		case STRING:
			stream.writeUTF((String) value);
			break;
		case OBJECT:
			for (Method method : value.getClass().getDeclaredMethods())
			{
				if (!method.isAnnotationPresent(BinaryEncode.class))
				{
					continue;
				}
				BinaryEncode info = method.getAnnotation(BinaryEncode.class);
				write(stream, info.opcode(), method.invoke(value), info.type());
			}
			break;
		case OBJECT_ARRAY:
			Object[] objects = (Object[]) value;
			if (objects.length > Short.MAX_VALUE)
			{
				throw new IOException(new Integer(objects.length).toString());
			}
			stream.writeShort(objects.length);
			for (Object object : objects)
			{
				if (!object.getClass().isAnnotationPresent(BinaryEncode.class))
				{
					continue;
				}
				BinaryEncode info = object.getClass().getAnnotation(
						BinaryEncode.class);
				write(stream, info.opcode(), object, info.type());
			}
			break;
		case NUMBER_ARRAY:
			int[] numbers = (int[]) value;
			if (numbers.length > Short.MAX_VALUE)
			{
				throw new IOException(new Integer(numbers.length).toString());
			}
			stream.writeShort(numbers.length);
			for (int i = 0; i < numbers.length; i++)
			{
				write(stream, -1, numbers[i], INTEGER);
			}
			break;
		case ENUM:
			stream.writeShort(((Enum<?>) value).ordinal());
			break;
		}
	}
}
