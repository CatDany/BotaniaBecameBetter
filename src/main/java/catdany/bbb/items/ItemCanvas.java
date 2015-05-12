package catdany.bbb.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import catdany.bbb.BBB;
import catdany.bbb.Refs;
import catdany.bbb.libs.IconRegHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemCanvas extends Item
{
	public static final String NAME = "Canvas";
	
	public ItemCanvas()
	{
		super();
		setUnlocalizedName(Refs.MODID + ":" + NAME);
		setCreativeTab(BBB.modTab);
		
		GameRegistry.registerItem(this, NAME);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = IconRegHelper.regItem(this, reg);
	}
}