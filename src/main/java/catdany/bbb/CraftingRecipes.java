package catdany.bbb;

import java.util.Arrays;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import catdany.bbb.blocks.BlockRepo;
import catdany.bbb.items.ItemRepo;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingRecipes
{
	public static RecipePetals recipePetalFlowerHydrafatus;
	public static RecipePetals recipePetalFlowerRedahlia;
	public static ShapedRecipes recipeElvenTablet;
	public static ShapelessRecipes recipeCanvas;
	public static ShapedRecipes recipeBag;
	
	public static void init()
	{
		recipePetalFlowerHydrafatus =
				BotaniaAPI.registerPetalRecipe(
					new ItemStack(BlockRepo.flowerHydrafatus),
					"manaPetalBlue", "petalBlue", "petalBlue",
					"manaPetalCyan", "petalCyan", "petalCyan",
					"manaPetalLightBlue", "petalLightBlue", "petalLightBlue"
				);
		recipePetalFlowerRedahlia =
				BotaniaAPI.registerPetalRecipe(
					new ItemStack(BlockRepo.flowerRedahlia),
					"manaPetalRed", "manaPetalRed", "petalRed",
					"petalBrown", "manaPetalLime", "petalLime",
					"runeSpringB", "runeGreedB"
				);
		recipeElvenTablet = new ShapedRecipes(3, 3,
					new ItemStack[] {
					new ItemStack(ModBlocks.livingrock), new ItemStack(ModBlocks.livingrock), new ItemStack(ModBlocks.livingrock),
					new ItemStack(ModBlocks.livingrock), new ItemStack(ModItems.manaResource, 1, 9), new ItemStack(ModBlocks.livingrock),
					new ItemStack(ModBlocks.livingrock), new ItemStack(ModBlocks.livingrock), new ItemStack(ModBlocks.livingrock)},
					new ItemStack(ItemRepo.elvenTablet)
				);
		GameRegistry.addRecipe(recipeElvenTablet);
		recipeCanvas = new ShapelessRecipes(
					new ItemStack(ItemRepo.canvas), Arrays.asList(
					new ItemStack(ModItems.manaResource, 1, 16),
					new ItemStack(Items.stick))
				);
		GameRegistry.addRecipe(recipeCanvas);
		recipeBag = new ShapedRecipes(3, 3,
					new ItemStack[] {
					new ItemStack(ItemRepo.canvas), new ItemStack(ItemRepo.canvas), new ItemStack(ItemRepo.canvas),
					new ItemStack(ItemRepo.canvas), null, new ItemStack(ItemRepo.canvas),
					new ItemStack(ItemRepo.canvas), new ItemStack(ItemRepo.canvas), new ItemStack(ItemRepo.canvas)},
					new ItemStack(ItemRepo.bag)
				);
		GameRegistry.addRecipe(recipeBag);
	}
}
