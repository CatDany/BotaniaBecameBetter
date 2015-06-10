package catdany.bbb.lexicon;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;
import catdany.bbb.CraftingRecipes;
import catdany.bbb.items.ItemRepo;

public class LexiconElvenTablet extends LexiconEntry
{
	public LexiconElvenTablet()
	{
		super("botania.entry.ElvenTablet", LexiconHooks.categoryBBB);
		setIcon(new ItemStack(ItemRepo.elvenTablet));
		setKnowledgeType(BotaniaAPI.elvenKnowledge);
		addPage(new PageText("botania.page.ElvenTablet0"));
		addPage(new PageCraftingRecipe("botania.page.ElvenTablet1", CraftingRecipes.recipeElvenTablet));
	}
}