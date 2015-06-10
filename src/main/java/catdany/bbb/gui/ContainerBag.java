package catdany.bbb.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import catdany.bbb.items.ItemBag;
import catdany.bbb.misc.InventoryBag;

//ContainerChest
public class ContainerBag extends Container
{
	public final ItemStack bag;
	public final InventoryBag inv;
	public final EntityPlayer player;
	
	public ContainerBag(EntityPlayer player, ItemStack bag)
	{
		super();
		this.bag = bag;
		this.inv = new InventoryBag(player);
		if (!player.worldObj.isRemote)
		{
			inv.readFromNBT();
		}
		this.player = player;
		
		int numRows = ItemBag.BAG_SIZE / 9;
		int i = (numRows - 4) * 18;
        int j;
        int k;

        for (j = 0; j < numRows; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(inv, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i + 1));
            }
        }
        
    	for (j = 0; j < 9; ++j)
        {
    		Slot slot;
        	if (player.inventory.currentItem == j)
            {
        		slot = new SlotBagCurrent(player.inventory, j, 8 + j * 18, 161 + i + 1);
            }
        	else
        	{
        		slot = new Slot(player.inventory, j, 8 + j * 18, 161 + i + 1);
        	}
        	this.addSlotToContainer(slot);
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer player)
	{
		if (par3 != 2 || player.inventory.currentItem != par2)
		{
			return super.slotClick(par1, par2, par3, player);
		}
		else
		{
			return null;
        }
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
	{
		int numRows = ItemBag.BAG_SIZE / 9;
		
		ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ < numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, numRows * 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, numRows * 9, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
	}
}