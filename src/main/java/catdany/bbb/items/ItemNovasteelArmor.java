package catdany.bbb.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import catdany.bbb.BBB;
import catdany.bbb.Refs;
import catdany.bbb.libs.IconRegHelper;
import catdany.bbb.misc.ArmorType;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemNovasteelArmor extends Item
{
	public static final String NAME = "Novasteel";
	
	public ItemNovasteelArmor(ArmorType type)
	{
		super();
		setUnlocalizedName(Refs.MODID + ":" + NAME + type.getTypeName());
		setCreativeTab(BBB.modTab);
		
		GameRegistry.registerItem(this, NAME + type.getTypeName());
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = IconRegHelper.regItem(this, reg);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack, int pass)
	{
		return true;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {}
}
