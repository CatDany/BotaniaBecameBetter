package catdany.bbb.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockBeacon;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.tile.mana.TilePool;
import catdany.bbb.BBB;
import catdany.bbb.Refs;
import catdany.bbb.libs.IconRegHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemTerraGaia extends Item
{
	public static final String NAME = "TerraGaia";
	public static final int CRAFT_DURATION_TICKS = 8 * 20;
	
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
	public boolean onEntityItemUpdate(EntityItem entity)
	{
		World world = entity.worldObj;
		double posX = entity.posX;
		double posY = entity.posY;
		double posZ = entity.posZ;
		int bX = (int)posX - 1;
		int bY = (int)posY - 1;
		int bZ = (int)posZ - 1;
		if (world.getBlock(bX, bY, bZ) instanceof BlockBeacon && bY > 200 && checkBeaconBase(world, bX, bY, bZ))
		{
			if (entity.getEntityItem().stackSize > 1)
			{
				entity.getEntityData().setInteger("ManaInfusionProgress", 0);
				entity.addVelocity(0.05D - Math.random() * 0.1D, 0.24D, 0.05D - Math.random() * 0.1D);
				return false;
			}
			ArrayList<IManaPool> pools = new ArrayList<IManaPool>();
			checkManaPool(pools, world, bX - 1, bY, bZ);
			checkManaPool(pools, world, bX + 1, bY, bZ);
			checkManaPool(pools, world, bX, bY, bZ - 1);
			checkManaPool(pools, world, bX, bY, bZ + 1);
			if (pools.size() >= 2)
			{
				renderParticles(world, bX, bY, bZ);
				if (!world.isRemote)
				{
					for (int i = 0; i < 2; i++)
					{
						IManaPool pool = pools.get(i);
						pool.recieveMana(-(TilePool.MAX_MANA / CRAFT_DURATION_TICKS));
						progressInfusion(entity, TilePool.MAX_MANA / CRAFT_DURATION_TICKS);
					}
					if (getProgressInfusion(entity) == TilePool.MAX_MANA * 2)
					{
						entity.setDead();
						EntityItem entityNew = new EntityItem(world, bX + 0.5, bY + 1.5, bZ + 0.5, new ItemStack(ItemRepo.novasteel));
						entityNew.delayBeforeCanPickup = 20;
						world.spawnEntityInWorld(entityNew);
						entityNew.worldObj.playSoundAtEntity(entityNew, "botania:terrasteelCraft", 1F, 1F);
						VanillaPacketDispatcher.dispatchTEToNearbyPlayers((TileEntity)pools.get(0));
						VanillaPacketDispatcher.dispatchTEToNearbyPlayers((TileEntity)pools.get(1));
					}
				}
			}
		}
		return false;
	}
	
	/*
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
	*/
	
	private boolean checkBeaconBase(World world, int bX, int bY, int bZ)
	{
		for (int i = -1; i <= 1; i++)
		{
			for (int j = -1; j <= 1; j++)
			{
				boolean f = world.getBlock(bX + i, bY - 1, bZ + j).isBeaconBase(world, bX + i, bY - 1, bZ + j, bX, bY, bZ);
				if (!f)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private void checkManaPool(List<IManaPool> list, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof IManaPool && ((IManaPool)tile).getCurrentMana() >= TilePool.MAX_MANA / CRAFT_DURATION_TICKS)
		{
			list.add((IManaPool)tile);
		}
	}
	
	private void progressInfusion(EntityItem entity, int mana)
	{
		if (!entity.getEntityData().hasKey("ManaInfusionProgress"))
		{
			entity.getEntityData().setInteger("ManaInfusionProgress", 0);
		}
		int currentMana = entity.getEntityData().getInteger("ManaInfusionProgress");
		entity.getEntityData().setInteger("ManaInfusionProgress", currentMana + mana);
	}
	
	private int getProgressInfusion(EntityItem entity)
	{
		if (!entity.getEntityData().hasKey("ManaInfusionProgress"))
		{
			entity.getEntityData().setInteger("ManaInfusionProgress", 0);
		}
		return entity.getEntityData().getInteger("ManaInfusionProgress");
	}
	
	private void renderParticles(World world, int bX, int bY, int bZ)
	{
		if (world.isRemote)
		{
			float[] colors = {0.9764705882352941F, 1.0F, 0.3607843137254902F};
			double pX = bX + 1.2D - Math.random() * 1.4D;
			double pY = bY + 1.2D - Math.random() * 1.4D;
			double pZ = bZ + 1.2D - Math.random() * 1.4D;
			float mX = (0.5F - world.rand.nextFloat()) / 6F;
			float mY = world.rand.nextFloat() / 12F;
			float mZ = (0.5F - world.rand.nextFloat()) / 6F;
			float size = 0.2F + world.rand.nextFloat() * 0.4F;
			Botania.proxy.wispFX(world, pX, pY, pZ, colors[0], colors[1], colors[2], size, mX, mY, mZ, 1.0F);
		}
	}
}
