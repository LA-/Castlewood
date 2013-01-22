package com.castlewood.util.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CycleTimeAverage
{

	public static void main(String... args)
	{
		File file = new File("./data/average.txt");
		try
		{
			FileReader stream = new FileReader(file);
			BufferedReader reader = new BufferedReader(stream);
			String line;
			double total = 0;
			double count = 0;
			int last = 0;
			while ((line = reader.readLine()) != null)
			{
				String[] parts = line.split(" ");
				double time = Long.parseLong(parts[0]) / 1000000D;
				int player = Integer.parseInt(parts[1]);
				count++;
				total += time;
				if (last == 0)
				{
					last = player;
				}
				if (player != last)
				{
					System.out.println((double) (total / count) + ", " + last);
					last = player;
					total = 0;
					count = 0;
				}
			}
			System.out.println((double) (total / count) + ", " + last);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
