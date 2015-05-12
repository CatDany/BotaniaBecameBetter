package catdany.bbb.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemManaSpawner extends ItemBlock
{
	public ItemManaSpawner(Block block)
	{
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if (!stack.hasTagCompound())
		{
			list.add("<error>");
		}
		else
		{
			String type = stack.getTagCompound().getCompoundTag("SpawnerData").getString("EntityId");
			list.add(type);	
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {}
}
