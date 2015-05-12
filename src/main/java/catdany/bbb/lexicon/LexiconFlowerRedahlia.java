package catdany.bbb.lexicon;

import net.minecraft.item.ItemStack;
import catdany.bbb.CraftingRecipes;
import catdany.bbb.blocks.BlockRepo;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class LexiconFlowerRedahlia extends LexiconEntry
{
	public LexiconFlowerRedahlia()
	{
		super("botania.entry.FlowerRedahlia", LexiconHooks.categoryBBB);
		setIcon(new ItemStack(BlockRepo.flowerRedahlia));
		addPage(new PageText("botania.page.FlowerRedahlia0"));
		addPage(new PagePetalRecipe<RecipePetals>("botania.page.FlowerRedahlia1", CraftingRecipes.recipePetalFlowerRedahlia));
	}
}
