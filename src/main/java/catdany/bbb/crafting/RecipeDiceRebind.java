package catdany.bbb.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.common.item.ModItems;
import catdany.bbb.CraftingRecipes;
import catdany.bbb.libs.ItemUtils;

public class RecipeDiceRebind implements IRecipe
{
	private final ItemStack result = new ItemStack(ModItems.dice);
	
	@Override
	public boolean matches(InventoryCrafting craft, World world)
	{
		ItemStack dice = null;
		ItemStack tag = null;
		for (int i = 0; i < craft.getSizeInventory(); i++)
		{
			if (craft.getStackInSlot(i) != null && craft.getStackInSlot(i).getItem() == ModItems.dice)
			{
				if (dice == null)
				{
					dice = craft.getStackInSlot(i);
				}
				else
				{
					return false;
				}
			}
			else if (craft.getStackInSlot(i) != null && craft.getStackInSlot(i).getItem() == Items.name_tag)
			{
				if (tag == null)
				{
					tag = craft.getStackInSlot(i);
				}
				else
				{
					return false;
				}
			}
		}
		if (dice != null && tag != null)
		{
			EntityPlayer player = CraftingRecipes.getCraftingPlayer(craft);
			if (player != null)
			{
				String namePlayer = player.getCommandSenderName();
				String nameDice = ((IRelic)dice.getItem()).getSoulbindUsername(dice);
				String nameTag = ItemUtils.getCustomName(tag);
				if (nameTag != null && nameDice.equals(namePlayer) && !nameTag.equals(nameDice))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting craft)
	{
		ItemStack dice = null;
		ItemStack tag = null;
		for (int i = 0; i < craft.getSizeInventory(); i++)
		{
			if (craft.getStackInSlot(i) != null && craft.getStackInSlot(i).getItem() == ModItems.dice)
			{
				dice = craft.getStackInSlot(i);
			}
			else if (craft.getStackInSlot(i) != null && craft.getStackInSlot(i).getItem() == Items.name_tag)
			{
				tag = craft.getStackInSlot(i);
			}
		}
		ItemStack out = dice.copy();
		((IRelic)out.getItem()).bindToUsername(ItemUtils.getCustomName(tag), out);
		return out;
	}
	
	@Override
	public ItemStack getRecipeOutput()
	{
		return result;
	}
	
	@Override
	public int getRecipeSize()
	{
		return 2;
	}
}
