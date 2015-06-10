package catdany.bbb.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.StatCollector;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.IManaNetwork;
import vazkii.botania.api.mana.IManaBlock;
import vazkii.botania.api.mana.IManaPool;
import catdany.bbb.blocks.BlockRepo;
import catdany.bbb.libs.MathUtils;

//EntityPig EntityAnimal EntityAgeable IAnimal
public class TileFlowerRedahlia extends TileEntity implements IManaBlock
{
	private static final int RANGE = 4;
	public static int MAX_MANA = 3000;
	
	public int manaStored = 0;
	
	int sizeLastCheck = -1;
	public ChunkCoordinates cachedPoolCoordinates;
	public TileEntity linkedPool;
	
	@Override
	public void updateEntity()
	{
		linkPool();
		
		if (linkedPool != null)
		{
			IManaPool pool = (IManaPool)linkedPool;
			int manaInPool = pool.getCurrentMana();
			int manaMissing = MAX_MANA - manaStored;
			int manaToRemove = Math.min(manaMissing, manaInPool);
			pool.recieveMana(-manaToRemove);
			addMana(manaToRemove);
		}
		
		final int cost = 1000 / 2;
		
		if (worldObj.getTotalWorldTime() % 20 == 0 && !worldObj.isRemote)
		{
			List<EntityAnimal> animals = worldObj.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.getBoundingBox(xCoord - RANGE, yCoord - RANGE, zCoord - RANGE, xCoord + RANGE + 1, yCoord + RANGE + 1, zCoord + RANGE + 1));
			if (animals.size() < 30)
			{
				ArrayList<EntityAnimal> breedable = new ArrayList<EntityAnimal>();
				Class<? extends EntityAnimal> animalType = null;
				for (EntityAnimal i : animals)
				{
					if (animalType == null)
					{
						animalType = i.getClass();
					}
					if (i.getClass() == animalType && i.getGrowingAge() == 0 && !i.isInLove())
					{
						breedable.add(i);
					}
				}
				if (breedable.size() >= 2)
				{
					if (addMana(-cost))
					{
						EntityAnimal male = breedable.get(0);
						EntityAnimal female = breedable.get(1);
						male.func_146082_f(null);
						female.func_146082_f(null);
					}
				}
			}
		}
	}
	
	@Override
	public int getCurrentMana()
	{
		return manaStored;
	}
	
	public boolean addMana(int amount)
	{
		int next = manaStored + amount;
		if (MathUtils.isInRange(0, MAX_MANA, manaStored))
		{
			manaStored = next;
			return true;
		}
		return false;
	}
	
	public void writeCustomNBT(NBTTagCompound tag)
	{
		tag.setInteger("ManaStored", manaStored);
	}
	
	public void readCustomNBT(NBTTagCompound tag)
	{
		manaStored = tag.getInteger("ManaStored");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeCustomNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readCustomNBT(tag);
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
		return packet;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readCustomNBT(pkt.func_148857_g());
	}
	
	public void renderHUD(Minecraft mc, ScaledResolution res)
	{
		String name = StatCollector.translateToLocal(BlockRepo.flowerRedahlia.getUnlocalizedName() + ".name");
		BotaniaAPI.internalHandler.drawSimpleManaHUD(0xff4444, getCurrentMana(), MAX_MANA, name, res);
	}
	
	public void linkPool()
	{
		final int range = 10;

		boolean needsNew = false;
		if (linkedPool == null)
		{
			needsNew = true;

			if (cachedPoolCoordinates != null)
			{
				needsNew = false;
				if (worldObj.blockExists(cachedPoolCoordinates.posX, cachedPoolCoordinates.posY, cachedPoolCoordinates.posZ))
				{
					needsNew = true;
					TileEntity tileAt = worldObj.getTileEntity(cachedPoolCoordinates.posX, cachedPoolCoordinates.posY, cachedPoolCoordinates.posZ);
					if (tileAt != null && tileAt instanceof IManaPool)
					{
						linkedPool = tileAt;
						needsNew = false;
					}
					cachedPoolCoordinates = null;
				}
			}
		}
	
		if (!needsNew && linkedPool != null)
		{
			TileEntity tileAt = worldObj.getTileEntity(linkedPool.xCoord, linkedPool.yCoord, linkedPool.zCoord);
			if(!(tileAt instanceof IManaPool))
			{
				linkedPool = null;
				needsNew = true;
			}
			else
			{
				linkedPool = tileAt;
			}
		}
	
		if(needsNew)
		{
			IManaNetwork network = BotaniaAPI.internalHandler.getManaNetworkInstance();
			int size = network.getAllPoolsInWorld(worldObj).size();
			if(BotaniaAPI.internalHandler.shouldForceCheck() || size != sizeLastCheck)
			{
				ChunkCoordinates coords = new ChunkCoordinates(xCoord, yCoord, zCoord);
				linkedPool = network.getClosestPool(coords, worldObj, range);
				sizeLastCheck = size;
			}
		}
	}
}
