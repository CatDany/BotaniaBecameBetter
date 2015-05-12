package catdany.bbb.libs;

import net.minecraft.util.StatCollector;

public class LocalizationHelper
{
	public static String get(String string)
	{
		return StatCollector.translateToLocal(string);
	}
	
	public static String get(String string, Object... format)
	{
		return StatCollector.translateToLocalFormatted(string, format);
	}
}