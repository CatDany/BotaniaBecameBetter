package catdany.bbb.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockBeacon;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.common.block.tile.mana.TilePool;
import catdany.bbb.BBB;
import catdany.bbb.Refs;
import catdany.bbb.libs.IconRegHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemTerraGaia extends Item
{
	public static final String NAME = "TerraGaia";
	
	public ItemTerraGaia()
	{
		super();
		setUnlocalizedName(Refs.MODID + ":" + NAME);
		setCreativeTab(BBB.modTab);
		
		GameRegistry.registerItem(this, NAME);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = IconRegHelper.regItem(this, reg);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && player.isSneaking() && world.getBlock(x, y, z) instanceof BlockBeacon)
		{
			if (y < 200)
			{
				return false;
			}
			for (int i = -1; i <= 1; i++)
			{
				for (int j = -1; j <= 1; j++)
				{
					boolean f = world.getBlock(x + i, y - 1, z + j).isBeaconBase(world, x + i, y - 1, z + j, x, y, z);
					if (!f)
					{
						return false;
					}
				}
			}
			ArrayList<IManaPool> pools = new ArrayList<IManaPool>();
			checkManaPool(pools, world, x - 1, y, z);
			checkManaPool(pools, world, x + 1, y, z);
			checkManaPool(pools, world, x, y, z - 1);
			checkManaPool(pools, world, x, y, z + 1);
			if (pools.size() >= 2)
			{
				for (int i = 0; i < 2; i++)
				{
					IManaPool pool = pools.get(i);
					pool.recieveMana(-TilePool.MAX_MANA);
					VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, ((TileEntity)pool).xCoord, ((TileEntity)pool).yCoord, ((TileEntity)pool).zCoord);
				}
				stack.stackSize--;
				EntityItem item = new EntityItem(world, x + 0.5, y + 1.5, z + 0.5, new ItemStack(ItemRepo.novasteel));
				item.delayBeforeCanPickup = 20;
				world.spawnEntityInWorld(item);
				item.worldObj.playSoundAtEntity(item, "botania:terrasteelCraft", 1F, 1F);
				return true;
			}
		}
		return false;
	}
	
	private void checkManaPool(List<IManaPool> list, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof IManaPool && ((IManaPool)tile).getCurrentMana() >= TilePool.MAX_MANA)
		{
			list.add((IManaPool)tile);
		}
	}
}
