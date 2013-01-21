package com.castlewood.util.misc;

public class TextUtils
{
	
	public static long encodeBase37(String name)
	{
		long encode = 0;
		for (int i = 0; i < name.length(); i++)
		{
			char character = name.charAt(i);
			encode *= 37;
			if (character >= 'A' && character <= 'Z')
			{
				encode += 1 + character - 65;
			}
			else if (character >= 'a' && character <= 'z')
			{
				encode += 1 + character - 97;
			}
			else if (character >= '0' && character <= '9')
			{
				encode += 27 + character - 48;
			}
		}
		for (; encode % 37 == 9 && encode != 0; encode /= 37);
		return encode;
	}
	
}
