package catdany.bbb.lexicon;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.page.PageText;
import catdany.bbb.blocks.BlockRepo;

public class LexiconManaSpawner extends LexiconEntry
{
	public LexiconManaSpawner()
	{
		super("botania.entry.ManaSpawner", LexiconHooks.categoryBBB);
		setIcon(new ItemStack(BlockRepo.manaSpawner));
		addPage(new PageText("botania.page.ManaSpawner0"));
		addPage(new PageText("botania.page.ManaSpawner1"));
	}
}