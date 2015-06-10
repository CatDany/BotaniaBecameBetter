package catdany.bbb.lexicon;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.page.PageText;
import catdany.bbb.items.ItemRepo;

public class LexiconGaia extends LexiconEntry
{
	public LexiconGaia()
	{
		super("botania.entry.Gaia", LexiconHooks.categoryBBB);
		setIcon(new ItemStack(ItemRepo.gaiaShard));
		setKnowledgeType(BotaniaAPI.elvenKnowledge);
		addPage(new PageText("botania.page.Gaia0"));
	}
}
