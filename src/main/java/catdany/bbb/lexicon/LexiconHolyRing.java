package catdany.bbb.lexicon;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;
import catdany.bbb.CraftingRecipes;
import catdany.bbb.items.ItemRepo;

public class LexiconHolyRing extends LexiconEntry
{
	public LexiconHolyRing()
	{
		super("botania.entry.HolyRing", LexiconHooks.categoryBBB);
		setIcon(new ItemStack(ItemRepo.holyRing));
		setKnowledgeType(BotaniaAPI.elvenKnowledge);
		addPage(new PageText("botania.page.HolyRing0"));
		addPage(new PageCraftingRecipe("botania.page.HolyRing1", CraftingRecipes.recipeHolyRing));
	}
}