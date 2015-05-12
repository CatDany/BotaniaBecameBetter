package catdany.bbb.libs;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.util.StatCollector;

public class CooldownHelper
{
	public static ExtendedTimeSpan parseCooldownTime(int ticks)
	{
		int h = (int)(ticks / 20 / 60 / 60);
		int m = (int)(ticks / 20 / 60) - (h * 60);
		int s = (int)(ticks / 20) - (m * 60);
		if (h == 0 && m == 0 && s == 0)
		{
			s++;
		}
		
		return new ExtendedTimeSpan.Builder()
					.setHours(h)
					.setMinutes(m)
					.setSeconds(s)
					.build();
	}
	
	public static String getDisplayCooldown(int ticks)
	{
		ExtendedTimeSpan span = parseCooldownTime(ticks);
		
		String s = "";
		if (span.days > 0)
		{
			s += " " + span.days + " d";
		}
		if (span.hours > 0)
		{
			s += " " + span.hours + " h";
		}
		if (span.minutes > 0)
		{
			s += " " + span.minutes + " min";
		}
		if (span.seconds > 0)
		{
			s += " " + span.seconds + " sec";
		}
		if (!s.isEmpty())
		{
			s = s.substring(1);
		}
		return s;
	}
	
	public static String getShortDisplayCooldown(int ticks)
	{
		ExtendedTimeSpan span = parseCooldownTime(ticks);
		String s = "";
		if (span.hours > 0)
		{
			s = StatCollector.translateToLocalFormatted("info.misc.cooldown.hours", span.hours);
		}
		else if (span.minutes > 0)
		{
			s = StatCollector.translateToLocalFormatted("info.misc.cooldown.minutes", span.minutes);
		}
		else if (span.seconds > 0)
		{
			s = StatCollector.translateToLocalFormatted("info.misc.cooldown.seconds", span.seconds);
		}
		return s;
	}
}