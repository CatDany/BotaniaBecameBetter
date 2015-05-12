package catdany.bbb.libs;

import org.lwjgl.opengl.GL11;

public class RenderUtils
{
	/**
	 * Tint color of GL11
	 * Color depends on time (1 cycle: from red to red)
	 * @param source Milliseconds (use {@link java.lang.System#currentTimeMillis() System.currentTimeMillis()})
	 * @param fullCycleLength Length of full cycle (from red to red) in milliseconds
	 */
	public static void tintRainbow(long source, int fullCycleLength)
	{
		int t = (int)(source % fullCycleLength);
		float r,g,b;
		// fade in: (t-(max-500))/500
		// fade out: (max-t)/500
		if (t < 500) // RED -> YELLOW
		{
			r = 1f;
			g = t / 500f;
			b = 0f;
		}
		else if (t < 1000) // YELLOW -> GREEN
		{
			r = (1000 - t) / 500f;
			g = 1f;
			b = 0f;
		}
		else if (t < 1500) // GREEN -> CYAN
		{
			r = 0f;
			g = 1f;
			b = (t - 1000) / 500f;
		}
		else if (t < 2000) // CYAN -> BLUE
		{
			r = 0f;
			g = (2000 - t) / 500f;
			b = 1f;
		}
		else if (t < 2500) // BLUE -> PURPLE
		{
			r = (t - 2000) / 500f;
			g = 0f;
			b = 1f;
		}
		else // (t < 3000) // PURPLE -> RED
		{
			r = 1f;
			g = 0f;
			b = (3000 - t) / 500f;
		}
		GL11.glColor4f(r, g, b, 1f);
	}
}