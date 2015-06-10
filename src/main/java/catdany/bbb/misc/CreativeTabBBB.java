package catdany.bbb.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import catdany.bbb.Refs;
import cpw.mods.fml.common.registry.GameRegistry;

public class CreativeTabBBB extends CreativeTabs
{
	private final Item tabIconItem;
	
	public CreativeTabBBB()
	{
		super(Refs.NAME);
		tabIconItem = GameRegistry.findItem("Botania", "spark");
	}
	
	@Override
	public Item getTabIconItem()
	{
		return tabIconItem;
	}
	
	@Override
	public String getTranslatedTabLabel()
	{
		return Refs.NAME;
	}
}
