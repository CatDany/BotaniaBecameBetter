package catdany.bbb.lexicon;

import net.minecraft.item.ItemStack;
import catdany.bbb.CraftingRecipes;
import catdany.bbb.items.ItemRepo;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class LexiconBag extends LexiconEntry
{
	public LexiconBag()
	{
		super("botania.entry.Bag", LexiconHooks.categoryBBB);
		setIcon(new ItemStack(ItemRepo.bag));
		setKnowledgeType(BotaniaAPI.basicKnowledge);
		addPage(new PageText("botania.page.Bag0"));
		addPage(new PageCraftingRecipe("botania.page.Bag1", CraftingRecipes.recipeCanvas));
		addPage(new PageCraftingRecipe("botania.page.Bag2", CraftingRecipes.recipeBag));
	}
}
