package catdany.bbb.lexicon;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;
import catdany.bbb.libs.ArrayUtils;

import com.google.common.collect.Lists;

public class LexiconDiceRebind extends LexiconEntry 
{
	private static final IRecipe[] exampleRecipes =
			{
				recipe("direwolf20", "Soaryn"),
				recipe("DrSigma", "CatDany"),
				recipe("Kinishina", "MJRamon"),
				recipe("CaptainSparklez", "OMGitsfirefoxx")
			};
	
	public LexiconDiceRebind()
	{
		super("botania.entry.DiceRebind", LexiconHooks.categoryBBB);
		setIcon(new ItemStack(ModItems.dice));
		setKnowledgeType(BotaniaAPI.elvenKnowledge);
		addPage(new PageText("botania.page.DiceRebind0"));
		addPage(new PageText("botania.page.DiceRebind1"));
		addPage(new PageCraftingRecipe("botania.page.DiceRebind2", ArrayUtils.getList(exampleRecipes)));
	}
	
	private static IRecipe recipe(String from, String to)
	{
		ItemStack diceIn = new ItemStack(ModItems.dice);
		ItemStack diceOut = new ItemStack(ModItems.dice);
		ItemStack nameTag = new ItemStack(Items.name_tag);
		((IRelic)diceIn.getItem()).bindToUsername(from, diceIn);
		((IRelic)diceOut.getItem()).bindToUsername(to, diceOut);
		nameTag.setStackDisplayName(to);
		return new ShapelessRecipes(diceOut, ArrayUtils.getList(diceIn, nameTag));
	}
}
