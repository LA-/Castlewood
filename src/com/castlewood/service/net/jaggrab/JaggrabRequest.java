package com.castlewood.service.net.jaggrab;

import com.castlewood.Constants;
import com.castlewood.io.fs.FileDescription;

public class JaggrabRequest
{

	private FileDescription description;

	public JaggrabRequest(String root)
	{
		if (root.startsWith("crc"))
		{
			description = new FileDescription(255, 255);
		}
		else
		{
			for (int i = 0; i < Constants.JAGGRAB_ROOT.length; i++)
			{
				if (root.startsWith(Constants.JAGGRAB_ROOT[i]))
				{
					description = new FileDescription(0, i);
					break;
				}
			}
		}
	}

	public FileDescription getDescription()
	{
		return description;
	}

}
