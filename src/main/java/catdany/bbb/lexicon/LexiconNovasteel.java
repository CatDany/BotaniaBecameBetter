package catdany.bbb.lexicon;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageImage;
import vazkii.botania.common.lexicon.page.PageText;
import catdany.bbb.CraftingRecipes;
import catdany.bbb.Refs;
import catdany.bbb.items.ItemRepo;

public class LexiconNovasteel extends LexiconEntry
{
	public LexiconNovasteel()
	{
		super("botania.entry.Novasteel", LexiconHooks.categoryBBB);
		setIcon(new ItemStack(ItemRepo.novasteel));
		setKnowledgeType(BotaniaAPI.elvenKnowledge);
		addPage(new PageText("botania.page.Novasteel0"));
		addPage(new PageText("botania.page.Novasteel1"));
		addPage(new PageText("botania.page.Novasteel2"));
		addPage(new PageCraftingRecipe("botania.page.Novasteel3", CraftingRecipes.recipeTerraGaia));
		addPage(new PageImage("botania.page.Novasteel4", Refs.RESLOC + ":" + "textures/gui/NovasteelRitual.png"));
		addPage(new PageText("botania.page.Novasteel5"));
		addPage(new PageCraftingRecipe("botania.page.Novasteel6", CraftingRecipes.recipeNovasteelHelm));
		addPage(new PageCraftingRecipe("botania.page.Novasteel6", CraftingRecipes.recipeNovasteelChest));
		addPage(new PageCraftingRecipe("botania.page.Novasteel6", CraftingRecipes.recipeNovasteelLegs));
		addPage(new PageCraftingRecipe("botania.page.Novasteel6", CraftingRecipes.recipeNovasteelBoots));
	}
}
