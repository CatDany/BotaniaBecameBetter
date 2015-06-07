package catdany.bbb;

import java.lang.reflect.Field;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import catdany.bbb.blocks.BlockRepo;
import catdany.bbb.crafting.RecipeSpecialTerraSword;
import catdany.bbb.items.ItemRepo;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class CraftingRecipes
{
	public static RecipePetals recipePetalFlowerHydrafatus;
	public static RecipePetals recipePetalFlowerRedahlia;
	public static ShapedRecipes recipeElvenTablet;
	public static ShapelessRecipes recipeCanvas;
	public static ShapedRecipes recipeBag;
	public static ShapedRecipes recipeNovasteelHelm;
	public static ShapelessRecipes recipeNovasteelHelmRevealing;
	public static ShapedRecipes recipeNovasteelChest;
	public static ShapedRecipes recipeNovasteelLegs;
	public static ShapedRecipes recipeNovasteelBoots;
	public static ShapelessRecipes recipeTerraGaia;
	public static RecipeSpecialTerraSword recipeSpecialTerraSword;
	
	public static void init()
	{
		// Hydrafatus
		recipePetalFlowerHydrafatus =
				BotaniaAPI.registerPetalRecipe(
					new ItemStack(BlockRepo.flowerHydrafatus),
					"manaPetalBlue", "petalBlue", "petalBlue",
					"manaPetalCyan", "petalCyan", "petalCyan",
					"manaPetalLightBlue", "petalLightBlue", "petalLightBlue"
				);
		// Redahlia
		recipePetalFlowerRedahlia =
				BotaniaAPI.registerPetalRecipe(
					new ItemStack(BlockRepo.flowerRedahlia),
					"manaPetalRed", "manaPetalRed", "petalRed",
					"petalBrown", "manaPetalLime", "petalLime",
					"runeSpringB", "runeGreedB"
				);
		// Elven Mana Tablet
		if (BBBCfg.enableElvenTablet)
		{
			recipeElvenTablet = new ShapedRecipes(3, 3,
					new ItemStack[] {
					new ItemStack(ModBlocks.livingrock), new ItemStack(ModBlocks.livingrock), new ItemStack(ModBlocks.livingrock),
					new ItemStack(ModBlocks.livingrock), new ItemStack(ModItems.manaResource, 1, 9), new ItemStack(ModBlocks.livingrock),
					new ItemStack(ModBlocks.livingrock), new ItemStack(ModBlocks.livingrock), new ItemStack(ModBlocks.livingrock)},
					new ItemStack(ItemRepo.elvenTablet)
				);
			GameRegistry.addRecipe(recipeElvenTablet);
		}
		// Mana Canvas
		recipeCanvas = new ShapelessRecipes(
					new ItemStack(ItemRepo.canvas), Arrays.asList(
					new ItemStack(ModItems.manaResource, 1, 16),
					new ItemStack(Items.stick))
				);
		GameRegistry.addRecipe(recipeCanvas);
		// Mana Canvas Bag
		recipeBag = new ShapedRecipes(3, 3,
					new ItemStack[] {
					new ItemStack(ItemRepo.canvas), new ItemStack(ItemRepo.canvas), new ItemStack(ItemRepo.canvas),
					new ItemStack(ItemRepo.canvas), null, new ItemStack(ItemRepo.canvas),
					new ItemStack(ItemRepo.canvas), new ItemStack(ItemRepo.canvas), new ItemStack(ItemRepo.canvas)},
					new ItemStack(ItemRepo.bag)
				);
		GameRegistry.addRecipe(recipeBag);
		// Novasteel Helm
		recipeNovasteelHelm = new ShapedRecipes(3, 2,
					new ItemStack[] {
					new ItemStack(ItemRepo.novasteel), new ItemStack(ItemRepo.novasteel), new ItemStack(ItemRepo.novasteel),
					new ItemStack(ItemRepo.novasteel), null, new ItemStack(ItemRepo.novasteel)},
					new ItemStack(ItemRepo.novasteelHelm)
				);
		GameRegistry.addRecipe(recipeNovasteelHelm);
		// Novasteel Helm of Revealing
		recipeNovasteelHelmRevealing = new ShapelessRecipes(
				new ItemStack(ItemRepo.novasteelHelmRevealing), Arrays.asList(
				new ItemStack(ItemRepo.novasteelHelm),
				new ItemStack(GameRegistry.findItem("Thaumcraft", "ItemGoggles")))
			);
		GameRegistry.addRecipe(recipeNovasteelHelmRevealing);
		// Novasteel Chestplate
		recipeNovasteelChest = new ShapedRecipes(3, 3,
				new ItemStack[] {
				new ItemStack(ItemRepo.novasteel), null, new ItemStack(ItemRepo.novasteel),
				new ItemStack(ItemRepo.novasteel), new ItemStack(ItemRepo.novasteel), new ItemStack(ItemRepo.novasteel),
				new ItemStack(ItemRepo.novasteel), new ItemStack(ItemRepo.novasteel), new ItemStack(ItemRepo.novasteel)},
				new ItemStack(ItemRepo.novasteelChest)
			);
		GameRegistry.addRecipe(recipeNovasteelChest);
		// Novasteel Leggings
		recipeNovasteelLegs = new ShapedRecipes(3, 3,
				new ItemStack[] {
				new ItemStack(ItemRepo.novasteel), new ItemStack(ItemRepo.novasteel), new ItemStack(ItemRepo.novasteel),
				new ItemStack(ItemRepo.novasteel), null, new ItemStack(ItemRepo.novasteel),
				new ItemStack(ItemRepo.novasteel), null, new ItemStack(ItemRepo.novasteel)},
				new ItemStack(ItemRepo.novasteelLegs)
			);
		GameRegistry.addRecipe(recipeNovasteelLegs);
		// Novasteel Boots
		recipeNovasteelBoots = new ShapedRecipes(3, 2,
				new ItemStack[] {
				new ItemStack(ItemRepo.novasteel), null, new ItemStack(ItemRepo.novasteel),
				new ItemStack(ItemRepo.novasteel), null, new ItemStack(ItemRepo.novasteel)},
				new ItemStack(ItemRepo.novasteelBoots)
			);
		GameRegistry.addRecipe(recipeNovasteelBoots);
		// Infused with Nether Star Piece of Terra-Gaia Matter
		recipeTerraGaia = new ShapelessRecipes(
				new ItemStack(ItemRepo.terraGaia), Arrays.asList(
				new ItemStack(ModItems.manaResource, 1, 14),
				new ItemStack(Items.nether_star))
			);
		GameRegistry.addRecipe(recipeTerraGaia);
		// SpecialTerraSword
		recipeSpecialTerraSword = new RecipeSpecialTerraSword();
		GameRegistry.addRecipe(recipeSpecialTerraSword);
	}
	
	private static final Field eventHandlerField = ReflectionHelper.findField(InventoryCrafting.class, BBBCfg.deobfReflect ? "eventHandler" : "field_70465_c");
	private static final Field containerPlayerPlayerField = ReflectionHelper.findField(ContainerPlayer.class, BBBCfg.deobfReflect ? "thePlayer" : "field_82862_h");
	private static final Field slotCraftingPlayerField = ReflectionHelper.findField(SlotCrafting.class, BBBCfg.deobfReflect ? "thePlayer": "field_75238_b");
	
	public static EntityPlayer getCraftingPlayer(InventoryCrafting craft)
	{
		try
		{
			Container container = (Container)eventHandlerField.get(craft);
			if (container instanceof ContainerPlayer)
			{
				return (EntityPlayer)containerPlayerPlayerField.get(container);
			}
		}
		catch (Throwable t)
		{
			Log.printStackTrace(t, false);
		}
		return null;
	}
}
