package catdany.bbb;



public class Log
{
	public static void info(String format, Object... args)
	{
		BBB.log.info(String.format(format, args));
	}
	
	public static void warn(String format, Object... args)
	{
		BBB.log.info(String.format(format, args));
	}
	
	public static void error(String format, Object... args)
	{
		BBB.log.info(String.format(format, args));
	}
	
	public static void debug(String format, Object... args)
	{
		if (BBBCfg.debugOutput)
		{
			BBB.log.info(">>>[DEBUG] " + String.format(format, args));
		}
	}
	
	public static <T>void dump(String log, T[] array)
	{
		String str = "";
		for (T i : array)
		{
			str += "\n" + str.toString();
		}
		debug(log + (str.isEmpty() ? "<No data to dump>" : str));
	}
	
	public static void printStackTrace(Throwable t, boolean fatal)
	{
		if (fatal)
		{
			BBB.log.throwing(t);
		}
		else
		{
			BBB.log.catching(t);
		}
	}
}