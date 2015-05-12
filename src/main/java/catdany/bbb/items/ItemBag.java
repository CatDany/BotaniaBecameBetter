package catdany.bbb.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import catdany.bbb.BBB;
import catdany.bbb.Log;
import catdany.bbb.Refs;
import catdany.bbb.gui.ContainerBag;
import catdany.bbb.gui.GuiHandler;
import catdany.bbb.libs.IconRegHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemBag extends Item
{
	public static final String NAME = "Bag";
	public static final int BAG_SIZE = 27;
	
	public ItemBag()
	{
		super();
		setUnlocalizedName(Refs.MODID + ":" + NAME);
		setCreativeTab(BBB.modTab);
		setMaxStackSize(1);
		
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
		//TODO: List all items when holding shift
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote && !(player.openContainer instanceof ContainerBag))
		{
			if (!stack.hasTagCompound())
			{
				stack.setTagCompound(new NBTTagCompound());
			}
			if (!stack.getTagCompound().hasKey("Items"))
			{
				NBTTagCompound tag = stack.getTagCompound();
		        NBTTagList nbttaglist = new NBTTagList();
		        tag.setTag("Items", nbttaglist);
			}
			GuiHandler.showGUI(player, GuiHandler.GUI_BAG);
		}
		return stack;
	}
	
	public static ItemStack getStackInSlot(ItemStack stack, int slot)
	{
		return getInventory(stack)[slot];
	}
	
	public static ItemStack[] getInventory(ItemStack stack)
	{
		ItemStack[] stacks = new ItemStack[BAG_SIZE];
		NBTTagCompound tag = stack.getTagCompound();
        NBTTagList nbttaglist = tag.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < stacks.length)
            {
                stacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        return stacks;
	}
}