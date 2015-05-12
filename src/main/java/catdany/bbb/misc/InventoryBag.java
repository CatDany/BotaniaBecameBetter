package catdany.bbb.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import catdany.bbb.Log;
import catdany.bbb.items.ItemBag;
import catdany.bbb.libs.ItemUtils;

//TileEntityChest
public class InventoryBag extends InventoryBasic
{
	public final EntityPlayer providedPlayer;
	
	public InventoryBag(EntityPlayer player)
	{
		super("bbb.container.bag", hasCustomName(player.inventory.getCurrentItem()), ItemBag.BAG_SIZE);
		this.providedPlayer = player;
	}
	
	public void readFromNBT()
    {
		NBTTagCompound tag = bag().getTagCompound();
        NBTTagList nbttaglist = tag.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < getSizeInventory())
            {
                setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
        
        Log.debug(">R %s", nbttaglist.tagCount());
    }

    public void writeToNBT()
    {
		NBTTagCompound tag = bag().getTagCompound();
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); ++i)
        {
            if (getStackInSlot(i) != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                getStackInSlot(i).writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tag.setTag("Items", nbttaglist);
        Log.debug(">W %s || %s", nbttaglist.tagCount(), providedPlayer.inventory.getCurrentItem() == bag());
    }
    
    @Override
    public void markDirty()
    {
    	super.markDirty();
    	if (!providedPlayer.worldObj.isRemote)
    	{
    		writeToNBT();
    	}
    }
    
    public ItemStack bag()
    {
    	ItemStack cur = providedPlayer.inventory.getCurrentItem();
    	return cur.getItem() instanceof ItemBag ? cur : null;
    }
    
    @Override
    public String getInventoryName()
    {
    	return hasCustomInventoryName() ? ItemUtils.getCustomName(providedPlayer.inventory.getCurrentItem()) : super.getInventoryName();
    }
	
	private static boolean hasCustomName(ItemStack bag)
	{
		return ItemUtils.getCustomName(bag) != null;
	}
}
