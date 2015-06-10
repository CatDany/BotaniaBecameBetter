package catdany.bbb.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import catdany.bbb.BBB;
import catdany.bbb.Refs;
import catdany.bbb.entity.EntityGaia;
import catdany.bbb.libs.IconRegHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemNovasteel extends Item
{
	public static final String NAME = "Novasteel";
	
	public ItemNovasteel()
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
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		EntityGaia.spawn(player, stack, world, x, y, z);
		return true;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack, int pass)
	{
		return true;
	}
}
