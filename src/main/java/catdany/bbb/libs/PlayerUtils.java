package catdany.bbb.libs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.UsernameCache;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class PlayerUtils
{
	public static void print(EntityPlayer player, String msg)
	{
		if (player.worldObj.isRemote)
			return;
		
		player.addChatMessage(
				new ChatComponentText(msg)
				);
	}
	
	public static EntityPlayer getPlayer(String username)
	{
		WorldServer[] worlds = MinecraftServer.getServer().worldServers;
		for (WorldServer w : worlds)
		{
			for (Object objP : w.playerEntities.toArray())
			{
				EntityPlayer p = (EntityPlayer)objP;
				if (p.getCommandSenderName().equals(username))
				{
					return p;
				}
			}
		}
		return null;
	}
	
	public static EntityPlayer getPlayerByUUID(String uuid)
	{
		WorldServer[] worlds = MinecraftServer.getServer().worldServers;
		for (WorldServer w : worlds)
		{
			for (Object objP : w.playerEntities.toArray())
			{
				EntityPlayer p = (EntityPlayer)objP;
				if (compare(p, uuid))
				{
					return p;
				}
			}
		}
		return null;
	}
	
	public static String getUsernameByUUID(String uuid)
	{
		if (UsernameCache.containsUUID(UUID.fromString(uuid)))
		{
			return UsernameCache.getLastKnownUsername(UUID.fromString(uuid));
		}
		else
		{
			return "";
		}
	}
	
	public static List<EntityPlayer> getAllPlayers()
	{
		ArrayList<EntityPlayer> list = new ArrayList<EntityPlayer>();
		for (Object i : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
		{
			list.add((EntityPlayer)i);
		}
		return list;
	}
	
	public static ArrayList<EntityPlayer> getPlayersInRange(Coords e, int radius)
	{
		List list = e.world.getEntitiesWithinAABB(
				EntityPlayer.class,
				AxisAlignedBB.getBoundingBox(
						e.x-0.5f,
						e.y-0.5f,
						e.z-0.5f,
						e.x + 0.5f,
						e.y + 0.5f,
						e.z + 0.5f
						).expand(
						radius, radius, radius
						));
		ArrayList<EntityPlayer> listPlayers = new ArrayList<EntityPlayer>();
		listPlayers.addAll(list);
		return listPlayers;
	}
	
	public static ArrayList<ItemStack> getInventory(EntityPlayer player)
	{
		ArrayList<ItemStack> inv = new ArrayList<ItemStack>();
		for (ItemStack i : player.inventory.mainInventory)
		{
			inv.add(i);
		}
		for (ItemStack i : player.inventory.armorInventory)
		{
			inv.add(i);
		}
		return inv;
	}
	
	public static String getUUID(EntityPlayer player)
	{
		return player.getGameProfile().getId().toString();
	}
	
	public static boolean compare(EntityPlayer player, String uuid)
	{
		return getUUID(player).equals(uuid);
	}
	
	public static MovingObjectPosition getMovingObjectPosition(EntityPlayer player, boolean countFluids)
	{
		World world = player.worldObj;
		float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + (double)(world.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (player instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return world.func_147447_a(vec3, vec31, countFluids, !countFluids, false);
	}
	
	public static boolean isLocalPlayer(EntityPlayer player)
	{
		return Minecraft.getMinecraft().thePlayer.getCommandSenderName().equals(player.getCommandSenderName());
	}
	
	class Coords
	{
		public World world;
		public double x;
		public double y;
		public double z;
		
		public Coords() {}
		
		public Coords(World world, double x, double y, double z)
		{
			this.world = world;
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public Coords(int dim, double x, double y, double z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
			if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
			{
				this.world = Minecraft.getMinecraft().theWorld;
			}
			else
			{
				for (WorldServer i : MinecraftServer.getServer().worldServers)
				{
					if (WorldUtils.getDimensionId(i) == dim)
					{
						this.world = (World)i;
						break;
					}
				}
			}
		}
	}
}