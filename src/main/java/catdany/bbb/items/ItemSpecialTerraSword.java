package catdany.bbb.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import vazkii.botania.common.entity.EntityManaBurst;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.terrasteel.ItemTerraSword;
import catdany.bbb.Refs;

public class ItemSpecialTerraSword extends ItemTerraSword
{
	public ItemSpecialTerraSword()
	{
		super();
	}
	
	@Override
	public EntityManaBurst getBurst(EntityPlayer player, ItemStack stack)
	{
		EntityManaBurst burst = super.getBurst(player, stack);
		if (player.getGameProfile().getId().toString().equals(Refs.T3H_31337_UUID))
		{
			burst.setColor(0x6666ff);
		}
		return burst;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(ModItems.terraSword));
	}
}
