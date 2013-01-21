package com.castlewood.util.misc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;

public class NumberUtils
{

	public static void main(String... args)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(
					"./data/average.txt"));
			String line;
			int lastPlayerCount = 0;
			long total = 0;
			long cycles = 0;
			while ((line = reader.readLine()) != null)
			{
				if (line.length() < 1)
				{
					continue;
				}
				String[] parts = line.split(" ");
				long nanos = Long.parseLong(parts[2]);
				int count = Integer.parseInt(parts[parts.length - 1]);
				if (lastPlayerCount == 0)
				{
					lastPlayerCount = count;
				}
				if (lastPlayerCount != count)
				{

					double average = (((total / 1000000D) / cycles));
					System.out.println("Average time: "
							+ NumberFormat.getInstance().format(average)
							+ " for " + lastPlayerCount);
					lastPlayerCount = count;
					total = 0;
					cycles = 0;
				}
				total += nanos;
				cycles++;
			}
			double average = (((total / 1000000D) / cycles));
			System.out.println("Average time: "
					+ NumberFormat.getInstance().format(average)
					+ " for " + lastPlayerCount);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
