package catdany.bbb.lexicon;

import net.minecraft.item.ItemStack;
import catdany.bbb.CraftingRecipes;
import catdany.bbb.Log;
import catdany.bbb.items.ItemRepo;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class LexiconNovasteel extends LexiconEntry
{
	public LexiconNovasteel()
	{
		super("botania.entry.Novasteel", LexiconHooks.categoryBBB);
		setIcon(new ItemStack(ItemRepo.novasteel));
		setKnowledgeType(BotaniaAPI.elvenKnowledge);
		addPage(new PageText("botania.page.Novasteel0"));
		addPage(new PageCraftingRecipe("botania.page.Novasteel1", CraftingRecipes.recipeNovasteelHelm));
		addPage(new PageCraftingRecipe("botania.page.Novasteel2", CraftingRecipes.recipeNovasteelChest));
		addPage(new PageCraftingRecipe("botania.page.Novasteel3", CraftingRecipes.recipeNovasteelLegs));
		addPage(new PageCraftingRecipe("botania.page.Novasteel4", CraftingRecipes.recipeNovasteelBoots));
	}
}
