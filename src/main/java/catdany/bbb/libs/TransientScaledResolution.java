package catdany.bbb.libs;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;


// Copy of ScaledResolution with 1.7.2 and 1.7.10 support. Will drop once 1.7.10 is in full use
public class TransientScaledResolution
{
	private static int scaledWidthStatic;
	private static int scaledHeightStatic;
	
	private static void scaledResolution()
	{
		int scaledWidth = Minecraft.getMinecraft().displayWidth;
		int scaledHeight = Minecraft.getMinecraft().displayHeight;
		int scaleFactor = 1;
		int k = Minecraft.getMinecraft().gameSettings.guiScale;
		
		if (k == 0)
			k = 1000;
		
		while (scaleFactor < k && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240)
			++scaleFactor;
		
		double scaledWidthD = (double)scaledWidth / (double)scaleFactor;
		double scaledHeightD = (double)scaledHeight / (double)scaleFactor;
		scaledWidthStatic = MathHelper.ceiling_double_int(scaledWidthD);
		scaledHeightStatic = MathHelper.ceiling_double_int(scaledHeightD);
	}
	
	public static int scaledWidth()
	{
		scaledResolution();
		return scaledWidthStatic;
	}
	
	public static int scaledHeight()
	{
		scaledResolution();
		return scaledHeightStatic;
	}
}