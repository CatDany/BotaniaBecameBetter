package catdany.bbb.lexicon;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageText;
import catdany.bbb.CraftingRecipes;
import catdany.bbb.blocks.BlockRepo;

public class LexiconFlowerHydrafatus extends LexiconEntry
{
	public LexiconFlowerHydrafatus()
	{
		super("botania.entry.FlowerHydrafatus", LexiconHooks.categoryBBB);
		setIcon(new ItemStack(BlockRepo.flowerHydrafatus));
		addPage(new PageText("botania.page.FlowerHydrafatus0"));
		addPage(new PagePetalRecipe<RecipePetals>("botania.page.FlowerHydrafatus1", CraftingRecipes.recipePetalFlowerHydrafatus));
	}
}
