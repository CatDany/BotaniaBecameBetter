package catdany.bbb.libs;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientUtils
{
	public static int getFPS()
	{
		// minecraft.debugFPS/fpsCounter is private, making a workaround; access transformers for suckers :|
		String debug = Minecraft.getMinecraft().debug;
		int j = debug.indexOf('(');
		int k = debug.indexOf(' ', j);
		return Integer.parseInt(debug.substring(j + 1, k));
	}
}