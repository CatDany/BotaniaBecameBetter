package catdany.bbb.items;

import catdany.bbb.misc.ArmorType;
import vazkii.botania.api.BotaniaAPI;

public class ItemRepo
{
	public static ItemElvenTablet elvenTablet;
	public static ItemCanvas canvas;
	public static ItemBag bag;
	public static ItemNovasteel novasteel;
	public static ItemNovasteelArmor novasteelHelm;
	public static ItemNovasteelArmor novasteelChest;
	public static ItemNovasteelArmor novasteelLegs;
	public static ItemNovasteelArmor novasteelBoots;
	
	public static void init()
	{
		elvenTablet = new ItemElvenTablet();
		canvas = new ItemCanvas();
		bag = new ItemBag();
		novasteel = new ItemNovasteel();
		novasteelHelm = new ItemNovasteelArmor(ArmorType.HELM);
		novasteelChest = new ItemNovasteelArmor(ArmorType.CHEST);
		novasteelLegs = new ItemNovasteelArmor(ArmorType.LEGS);
		novasteelBoots = new ItemNovasteelArmor(ArmorType.BOOTS);
	}
}