package catdany.bbb.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import vazkii.botania.common.item.ModItems;
import catdany.bbb.CraftingRecipes;
import catdany.bbb.Refs;
import catdany.bbb.items.ItemRepo;

public class RecipeSpecialTerraSword implements IRecipe
{
	private final ItemStack resultSpecial = new ItemStack(ItemRepo.specialTerraSword);
	private final ItemStack resultRegular = new ItemStack(ModItems.terraSword);
	
	@Override
	public boolean matches(InventoryCrafting craft, World world)
	{
		int a = 0;
		for (int i = 0; i < craft.getSizeInventory(); i++)
		{
			if (craft.getStackInSlot(i) != null && craft.getStackInSlot(i).getItem() == ModItems.terraSword)
			{
				a++;
			}
		}
		return a == 1;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting craft)
	{
		EntityPlayer player = CraftingRecipes.getCraftingPlayer(craft);
		if (player != null && player.getGameProfile().getId().toString().equals(Refs.T3H_31337_UUID))
		{
			return resultSpecial.copy();
		}
		else
		{
			return getRecipeOutput().copy();
		}
	}
	
	@Override
	public ItemStack getRecipeOutput()
	{
		return resultRegular;
	}
	
	@Override
	public int getRecipeSize()
	{
		return 1;
	}
}