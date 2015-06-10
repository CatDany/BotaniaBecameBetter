package catdany.bbb.crafting;

import vazkii.botania.api.item.IRelic;
import vazkii.botania.common.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import catdany.bbb.CraftingRecipes;
import catdany.bbb.items.ItemNovasteelArmor;
import catdany.bbb.items.ItemRepo;
import catdany.bbb.libs.ItemUtils;

public class RecipeGaiaShard implements IRecipe
{
	private final ItemNovasteelArmor armor;
	private final ItemStack result;
	
	public RecipeGaiaShard(ItemNovasteelArmor armor)
	{
		this.armor = armor;
		this.result = new ItemStack(armor);
	}
	
	@Override
	public boolean matches(InventoryCrafting craft, World world)
	{
		ItemStack armorStack = null;
		int gaiaShards = 0;
		for (int i = 0; i < craft.getSizeInventory(); i++)
		{
			if (craft.getStackInSlot(i) != null && craft.getStackInSlot(i).getItem() == armor)
			{
				if (armorStack == null)
				{
					armorStack = craft.getStackInSlot(i);
				}
				else
				{
					return false;
				}
			}
			else if (craft.getStackInSlot(i) != null && craft.getStackInSlot(i).getItem() == ItemRepo.gaiaShard)
			{
				gaiaShards++;
			}
		}
		if (armorStack != null && gaiaShards == 1 && !armor.hasGaiaShard(armorStack))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting craft)
	{
		ItemStack armorStack = null;
		for (int i = 0; i < craft.getSizeInventory(); i++)
		{
			if (craft.getStackInSlot(i) != null && craft.getStackInSlot(i).getItem() == armor)
			{
				armorStack = craft.getStackInSlot(i);
				break;
			}
		}
		ItemStack out = armorStack.copy();
		armor.setGaiaShard(out, true);
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
