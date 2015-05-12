package catdany.bbb.libs;

import net.minecraft.client.Minecraft;

public class KeyBoardHelper
{
	public static boolean isShiftDown()
	{
		return Minecraft.getMinecraft().currentScreen.isShiftKeyDown();
	}
	
	public static boolean isCtrlDown()
	{
		return Minecraft.getMinecraft().currentScreen.isCtrlKeyDown();
	}
}