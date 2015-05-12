package catdany.bbb.items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaItem;
import catdany.bbb.BBB;
import catdany.bbb.Log;
import catdany.bbb.Refs;
import catdany.bbb.libs.IconRegHelper;
import catdany.bbb.libs.MathUtils;

public class ItemElvenTablet extends Item implements IManaItem
{
	public static final String NAME = "ElvenTablet";
	public static final int MAX_MANA = 500000 * 4;
	
	public ItemElvenTablet()
	{
		super();
		setUnlocalizedName(Refs.MODID + ":" + NAME);
		setCreativeTab(BBB.modTab);
		setMaxDamage(4000);
		setMaxStackSize(1);
		setNoRepair();
		
		GameRegistry.registerItem(this, NAME);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = IconRegHelper.regItem(this, reg);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		float dur = (float)(getMaxDamage() - stack.getItemDamage()) / getMaxDamage();
		String s;
		if (dur == 0)
			s = "bbb.tablet.empty";
		else if (MathUtils.isInRange(0F, 0.1F, dur))
			s = "bbb.tablet.almostEmpty";
		else if (MathUtils.isInRange(0.1F, 0.25F, dur))
			s = "bbb.tablet.less25";
		else if (MathUtils.isInRange(0.25F, 0.4F, dur))
			s = "bbb.tablet.less40";
		else if (MathUtils.isInRange(0.4F, 0.6F, dur))
			s = "bbb.tablet.about50";
		else if (MathUtils.isInRange(0.6F, 0.75F, dur))
			s = "bbb.tablet.less60";
		else if (MathUtils.isInRange(0.75F, 0.9F, dur))
			s = "bbb.tablet.less90";
		else if (MathUtils.isInRange(0.75F, 0.9F, dur))
			s = "bbb.tablet.almostFull";
		else
			s = "bbb.tablet.full";
		list.add(StatCollector.translateToLocal(s));
	}
	
	@Override
	public void addMana(ItemStack stack, int amount)
	{
		setItemMana(stack, Math.min(MAX_MANA, Math.max(0, getItemMana(stack) + amount)));
		Log.debug("manatablet//%s", getMana(stack));
	}
	
	@Override
	public int getDamage(ItemStack stack)
	{
		float mana = getMana(stack);
		return 4000 - (int) (mana / MAX_MANA * 4000);
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack)
	{
		return getDamage(stack);
	}
	
	@Override
	public int getEntityLifespan(ItemStack itemStack, World world)
	{
		return Integer.MAX_VALUE;
	}
	
	@Override
	public boolean canExportManaToItem(ItemStack arg0, ItemStack arg1)
	{
		return true;
	}
	
	@Override
	public boolean canExportManaToPool(ItemStack arg0, TileEntity arg1)
	{
		return true;
	}
	
	@Override
	public boolean canReceiveManaFromItem(ItemStack arg0, ItemStack arg1)
	{
		return true;
	}
	
	@Override
	public boolean canReceiveManaFromPool(ItemStack arg0, TileEntity arg1)
	{
		return true;
	}
	
	@Override
	public int getMaxMana(ItemStack arg0)
	{
		return MAX_MANA;
	}
	
	@Override
	public int getMana(ItemStack stack)
	{
		return getItemMana(stack);
	}
	
	@Override
	public boolean isNoExport(ItemStack arg0)
	{
		return false;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		ItemStack empty = new ItemStack(item);
		setItemMana(empty, 0);
		ItemStack full = new ItemStack(item);
		setItemMana(full, MAX_MANA);
		list.add(empty);
		list.add(full);
	}
	
	public static void setItemMana(ItemStack stack, int mana)
	{
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("ManaStored", mana);
	}
	
	public static int getItemMana(ItemStack stack)
	{
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		if (!stack.getTagCompound().hasKey("ManaStored"))
		{
			stack.getTagCompound().setInteger("ManaStored", 0);
		}
		return stack.getTagCompound().getInteger("ManaStored");
	}
}