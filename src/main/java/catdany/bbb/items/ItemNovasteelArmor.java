package catdany.bbb.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.IGoggles;
import thaumcraft.api.nodes.IRevealer;
import vazkii.botania.api.item.IPhantomInkable;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import catdany.bbb.BBB;
import catdany.bbb.Refs;
import catdany.bbb.libs.IconRegHelper;
import catdany.bbb.libs.KeyBoardHelper;
import catdany.bbb.libs.Paragraph;
import catdany.bbb.misc.ArmorType;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;

@Optional.InterfaceList(
		{
			@Optional.Interface(modid = "Thaumcraft", iface = "thaumcraft.api.IGoggles", striprefs = true),
			@Optional.Interface(modid = "Thaumcraft", iface = "thaumcraft.api.nodes.IRevealer", striprefs = true)
		}
	)
public class ItemNovasteelArmor extends ItemArmor implements IGoggles, IRevealer, IManaUsingItem, IPhantomInkable, ISpecialArmor
{
	public static final String NAME = "Novasteel";
	public static final int MANA_PER_DAMAGE = 100;
	public static final ArmorMaterial NOVASTEEL_MATERIAL = EnumHelper.addArmorMaterial("novasteel", 34, new int[] {3, 8, 6, 3}, 30);
	
	public static final String MODEL_0 = Refs.RESLOC + ":" + "textures/models/novasteel0.png";
	public static final String MODEL_1 = Refs.RESLOC + ":" + "textures/models/novasteel1.png";
	public static final String MODEL_2 = Refs.RESLOC + ":" + "textures/models/novasteel2.png";
	
	public static int RENDER_ID = 0;
	
	public final boolean isRevealing;
	
	public ItemNovasteelArmor(ArmorType type, boolean isRevealing)
	{
		super(NOVASTEEL_MATERIAL, RENDER_ID, type.getTypeIndex());
		setUnlocalizedName(Refs.MODID + ":" + NAME + type.getTypeName() + (isRevealing ? "Revealing" : ""));
		setCreativeTab(BBB.modTab);
		this.isRevealing = isRevealing;
		
		GameRegistry.registerItem(this, NAME + type.getTypeName() + (isRevealing ? "Revealing" : ""));
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = IconRegHelper.regItem(this, reg);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if (KeyBoardHelper.isShiftDown())
		{
			int piecesAmount = 0;
			boolean[] pieces = new boolean[4];
			for (int i = 0; i < 4; i++)
			{
				if (player.inventory.armorItemInSlot(i) != null && player.inventory.armorItemInSlot(i).getItem() instanceof ItemNovasteelArmor)
				{
					piecesAmount++;
					pieces[i] = true;
				}
			}
			list.add(StatCollector.translateToLocal("botaniamisc.armorset").replace("&", Paragraph.sign) + Paragraph.white + " " + StatCollector.translateToLocal("bbb.novasteel.armorSetEffect") + " (" + piecesAmount + "/4)");
			list.add(StatCollector.translateToLocal("bbb.novasteel.manaDiscount"));
			list.add(StatCollector.translateToLocal("bbb.novasteel.invulnerability"));
			if (hasGaiaShard(stack))
			{
				list.add(StatCollector.translateToLocal("bbb.gaiaShard.applied"));
			}
			list.add((pieces[3] ? Paragraph.light_green : "") + " - " + StatCollector.translateToLocal(ItemRepo.novasteelHelm.getUnlocalizedName() + ".name"));
			list.add((pieces[2] ? Paragraph.light_green : "") + " - " + StatCollector.translateToLocal(ItemRepo.novasteelChest.getUnlocalizedName() + ".name"));
			list.add((pieces[1] ? Paragraph.light_green : "") + " - " + StatCollector.translateToLocal(ItemRepo.novasteelLegs.getUnlocalizedName() + ".name"));
			list.add((pieces[0] ? Paragraph.light_green : "") + " - " + StatCollector.translateToLocal(ItemRepo.novasteelBoots.getUnlocalizedName() + ".name"));
		}
		else
		{
			list.add(StatCollector.translateToLocal("botaniamisc.shiftinfo").replace("&", Paragraph.sign));
		}
	}
	
	@Override
	public boolean hasEffect(ItemStack stack, int pass)
	{
		return true;
	}
	
	@Override
	public boolean showIngamePopups(ItemStack stack, EntityLivingBase living)
	{
		return isRevealing;
	}
	
	@Override
	public boolean showNodes(ItemStack stack, EntityLivingBase living)
	{
		return isRevealing;
	}
	
	@Override
	public boolean usesMana(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5)
	{
		if (player instanceof EntityPlayer)
		{
			onArmorTick(world, (EntityPlayer)player, stack);
		}
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (!world.isRemote && stack.getItemDamage() > 0 && (hasGaiaShard(stack) || ManaItemHandler.requestManaExact(stack, player, MANA_PER_DAMAGE * 2, true)))
		{
			stack.setItemDamage(stack.getItemDamage() - 1);
		}
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		if (!hasGaiaShard(stack))
		{
			ToolCommons.damageItem(stack, damage, entity, MANA_PER_DAMAGE);
		}
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		if(source.isUnblockable())
		{
			return new ArmorProperties(0, 0, 0);
		}
		return new ArmorProperties(0, damageReduceAmount / 25D, armor.getMaxDamage() + 1 - armor.getItemDamage());
	}
	
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
		return damageReduceAmount;
	}
	
	@Override
	public boolean hasPhantomInk(ItemStack stack)
	{
		return ItemNBTHelper.getBoolean(stack, "phantomInk", false);
	}
	
	@Override
	public void setPhantomInk(ItemStack stack, boolean flag)
	{
		ItemNBTHelper.setBoolean(stack, "phantomInk", true);
	}
	
	public boolean hasGaiaShard(ItemStack stack)
	{
		return stack.hasTagCompound() && stack.getTagCompound().getBoolean("GaiaShard");
	}
	
	public void setGaiaShard(ItemStack stack, boolean flag)
	{
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean("GaiaShard", flag);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if (hasPhantomInk(stack))
			return LibResources.MODEL_INVISIBLE_ARMOR;
		else if (isRevealing)
			return MODEL_2;
		else if (slot == 2)
			return MODEL_1;
		else
			return MODEL_0;
	}
	
	public static boolean isFullNovasteel(EntityLivingBase living)
	{
		for (int i = 1; i <= 4; i++)
		{
			if (living.getEquipmentInSlot(i) == null || !(living.getEquipmentInSlot(i).getItem() instanceof ItemNovasteelArmor))
			{
				return false;
			}
		}
		return true;
	}
}