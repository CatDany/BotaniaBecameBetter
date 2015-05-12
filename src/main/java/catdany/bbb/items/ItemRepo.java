package catdany.bbb.items;

import vazkii.botania.api.BotaniaAPI;

public class ItemRepo
{
	public static ItemElvenTablet elvenTablet;
	public static ItemCanvas canvas;
	public static ItemBag bag;
	
	public static void init()
	{
		elvenTablet = new ItemElvenTablet();
		canvas = new ItemCanvas();
		bag = new ItemBag();
	}
}