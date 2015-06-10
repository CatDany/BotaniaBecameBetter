package catdany.bbb.items;

import catdany.bbb.misc.ArmorType;

public class ItemRepo
{
	public static ItemElvenTablet elvenTablet;
	public static ItemCanvas canvas;
	public static ItemBag bag;
	public static ItemNovasteel novasteel;
	public static ItemNovasteelArmor novasteelHelm;
	public static ItemNovasteelArmor novasteelHelmRevealing;
	public static ItemNovasteelArmor novasteelChest;
	public static ItemNovasteelArmor novasteelLegs;
	public static ItemNovasteelArmor novasteelBoots;
	public static ItemTerraGaia terraGaia;
	public static ItemSpecialTerraSword specialTerraSword;
	public static ItemHolyRing holyRing;
	public static ItemGaiaShard gaiaShard;
	
	public static void init()
	{
		elvenTablet = new ItemElvenTablet();
		canvas = new ItemCanvas();
		bag = new ItemBag();
		novasteel = new ItemNovasteel();
		novasteelHelm = new ItemNovasteelArmor(ArmorType.HELM, false);
		novasteelHelmRevealing = new ItemNovasteelArmor(ArmorType.HELM, true);
		novasteelChest = new ItemNovasteelArmor(ArmorType.CHEST, false);
		novasteelLegs = new ItemNovasteelArmor(ArmorType.LEGS, false);
		novasteelBoots = new ItemNovasteelArmor(ArmorType.BOOTS, false);
		terraGaia = new ItemTerraGaia();
		specialTerraSword = new ItemSpecialTerraSword();
		holyRing = new ItemHolyRing();
		gaiaShard = new ItemGaiaShard();
	}
}