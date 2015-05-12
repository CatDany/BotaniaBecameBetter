package catdany.bbb.libs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.output.FileWriterWithEncoding;

import cpw.mods.fml.common.FMLLog;

public class FileHelper
{
	public static void writeToFile(File file, List<String> list, Charset charset)
	{
		try
		{
			createFiles(file);
			FileWriterWithEncoding fw = new FileWriterWithEncoding(file, charset);
			for (String i : list)
			{
				fw.write(i);
				if (list.indexOf(i) + 1 < list.size())
				{
					fw.write("\n");
				}
			}
			fw.close();
		}
		catch (Throwable t)
		{
			FMLLog.warning("Unable to write to file. Path: " + file.getAbsolutePath());
			t.printStackTrace();
		}
	}
	
	public static void createFiles(File... files) throws IOException
	{
		for (File i : files)
		{
			if (!i.exists())
			{
				i.createNewFile();
			}
		}
	}
}