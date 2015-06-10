package catdany.bbb.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.core.helper.MathHelper;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.entity.EntityDoppleganger;
import vazkii.botania.common.entity.EntityMagicLandmine;
import vazkii.botania.common.entity.EntityMagicMissile;
import vazkii.botania.common.entity.EntityPixie;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.LibObfuscation;
import catdany.bbb.Log;
import catdany.bbb.items.ItemNovasteelArmor;
import catdany.bbb.items.ItemRepo;
import cpw.mods.fml.relauncher.ReflectionHelper;

//EntityDragon EntityLivingBase EntityCreature
public class EntityGaia extends EntityDoppleganger
{
	private static final int[][] PYLON_LOCATIONS =
	{
		{ 4, 1, 4 },
		{ 4, 1, -4 },
		{ -4, 1, 4 },
		{ -4, 1, -4 }
	};
	
	private static final float MAX_HP = 900F;
	
	private static final DamageSource dmgNotPrepared = new DamageSource("bbb.damage.gaia_no_novasteel").setDamageBypassesArmor().setDamageIsAbsolute();
	
	private boolean gaiaRegenNotified = false;
	
	public EntityGaia(World world)
	{
		super(world);
		experienceValue = 1000;
	}
	
	public static boolean spawn(EntityPlayer player, ItemStack stack, World world, int argX, int argY, int argZ)
	{
		Block block = world.getBlock(argX, argY, argZ);
		if (block == Blocks.beacon && isTruePlayer(player) && !world.isRemote)
		{
			if (world.difficultySetting == EnumDifficulty.PEACEFUL)
			{
				player.addChatMessage(new ChatComponentTranslation("botaniamisc.peacefulNoob").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
				return false;
			}

			for (int[] coords : PYLON_LOCATIONS)
			{
				int x = argX + coords[0];
				int y = argY + coords[1];
				int z = argZ + coords[2];

				Block blockat = world.getBlock(x, y, z);
				int meta = world.getBlockMetadata(x, y, z);
				if (blockat != ModBlocks.pylon || meta != 2)
				{
					player.addChatMessage(new ChatComponentTranslation("botaniamisc.needsCatalysts").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
					return false;
				}
			}

			stack.stackSize--;
			EntityGaia e = new EntityGaia(world);
			e.setPosition(argX + 0.5, argY + 3, argZ + 0.5);
			e.setInvulTime(SPAWN_TICKS);
			e.setHealth(1F);
			e.setSource(argX, argY, argZ);
			e.setMobSpawnTicks(MOB_SPAWN_TICKS);
			e.setHardMode(true);
			world.playSoundAtEntity(e, "mob.enderdragon.growl", 10F, 0.1F);
			world.spawnEntityInWorld(e);
			return true;
		}

		return false;
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(MAX_HP);
	}
	
	@Override
	protected void dropFewItems(boolean arg0, int arg1)
	{
		if (arg0)
		{
			entityDropItem(new ItemStack(ModItems.manaResource, 64, 5), 1F); // Gaia Spirit
			entityDropItem(new ItemStack(ModItems.manaResource, 40 + rand.nextInt(25)), 1F); // Manasteel
			entityDropItem(new ItemStack(ModItems.manaResource, 20 + rand.nextInt(11), 1), 1F); // Mana Pearl
			entityDropItem(new ItemStack(ModItems.manaResource, 8 + rand.nextInt(9), 2), 1F); // Mana Diamond
			entityDropItem(new ItemStack(ModItems.manaResource, 2 + rand.nextInt(3), 4), 1F); // Terrasteel
			entityDropItem(new ItemStack(ModItems.rune, 4 + rand.nextInt(5), rand.nextInt(16)), 1F); // Rune
			entityDropItem(new ItemStack(ModItems.pinkinator), 1F); // Pinkinator
			entityDropItem(new ItemStack(ModItems.overgrowthSeed, 6 + rand.nextInt(7)), 1F); // Overgrowth Seed
			entityDropItem(new ItemStack(ItemRepo.gaiaShard), 1F); // Gaia Shard
			if (ConfigHandler.relicsEnabled)
			{
				entityDropItem(new ItemStack(ModItems.dice), 1F);
			}
			if (Math.random() < 0.75)
			{
				int i = Item.getIdFromItem(Items.record_13);
				int j = Item.getIdFromItem(Items.record_wait);
				int k = i + rand.nextInt(j - i + 1);
				entityDropItem(new ItemStack(Item.getItemById(k)), 1F);
			}
			else
			{
				entityDropItem(new ItemStack(ModItems.recordGaia2), 1F);
			}
		}
	}
	
	@Override
	public void onLivingUpdate()
	{
		updateLiving();
		Log.debug("Client=%s | Health=%s", worldObj.isRemote, getHealth());
		
		if (!worldObj.isRemote && worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
		{
			setDead();
		}
		
		ChunkCoordinates source = getSource();
		boolean hard = isHardMode();
		
		if (worldObj.isRemote && !isPlayingMusic() && !isDead)
		{
			Botania.proxy.playRecordClientSided(worldObj, source.posX, source.posY, source.posZ, (ItemRecord)ModItems.recordGaia2);
			setPlayingMusic(true);
		}
		
		float range = 32F;
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(source.posX + 0.5 - range, source.posY + 0.5 - range, source.posZ + 0.5 - range, source.posX + 0.5 + range, source.posY + 0.5 + range, source.posZ + 0.5 + range));

		range = 12F;
		/*
		for (int i = 0; i < 360; i += 8)
		{
			float r = 0.6F;
			float g = 0F;
			float b = 0.2F;
			float m = 0.15F;
			float mv = 0.35F;

			float rad = i * (float) Math.PI / 180F;
			double x = source.posX + 0.5 - Math.cos(rad) * range;
			double y = source.posY + 0.5;
			double z = source.posZ + 0.5 - Math.sin(rad) * range;

			Botania.proxy.wispFX(worldObj, x, y, z, r, g, b, 0.5F, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * mv, (float) (Math.random() - 0.5F) * m);
		}
		*/
		boolean gaiaRegen = false;
		if (!worldObj.isRemote && isInWater() && getHealth() < getMaxHealth())
		{
			setHealth(getMaxHealth());
			gaiaRegen = true;
		}
		
		if (players.isEmpty() && !worldObj.playerEntities.isEmpty())
		{
			setDead();
		}
		else
		{
			for (EntityPlayer player : players)
			{
				if (!worldObj.isRemote && gaiaRegen && !gaiaRegenNotified)
				{
					player.addChatMessage(new ChatComponentTranslation("bbb.gaia.regen").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
					gaiaRegenNotified = true;
				}
				
				if (player.inventory.armorInventory[0] != null || player.inventory.armorInventory[1] != null || player.inventory.armorInventory[2] != null || player.inventory.armorInventory[3] != null)
				{
					setAnyWithArmor(true);
				}
				
				if (!ItemNovasteelArmor.isFullNovasteel(player))
				{
					player.attackEntityFrom(dmgNotPrepared, Float.MAX_VALUE);
				}
				
				if (getHealth() / getMaxHealth() < 0.2 && getInvulTime() == 0)
				{
					player.addPotionEffect(new PotionEffect(Potion.confusion.id, 12 * 20));
				}
				
				List<PotionEffect> remove = new ArrayList();
				Collection<PotionEffect> active = player.getActivePotionEffects();
				for (PotionEffect effect : active)
				{
					if (effect.getDuration() < 200 && effect.getIsAmbient() && !ReflectionHelper.<Boolean, Potion>getPrivateValue(Potion.class, Potion.potionTypes[effect.getPotionID()], LibObfuscation.IS_BAD_EFFECT))
					{
						remove.add(effect);
					}
				}
				active.removeAll(remove);
				
				player.capabilities.isFlying = player.capabilities.isFlying && player.capabilities.isCreativeMode;
				
				if (MathHelper.pointDistanceSpace(player.posX, player.posY, player.posZ, source.posX + 0.5, source.posY + 0.5, source.posZ + 0.5) >= range)
				{
					Vector3 sourceVector = new Vector3(source.posX + 0.5, source.posY + 0.5, source.posZ + 0.5);
					Vector3 playerVector = Vector3.fromEntityCenter(player);
					Vector3 motion = sourceVector.copy().sub(playerVector).copy().normalize();
					player.motionX = motion.x;
					player.motionY = 0.2;
					player.motionZ = motion.z;
				}
			}
		}

		if (isDead)
		{
			return;
		}

		int invul = getInvulTime();
		int mobTicks = getMobSpawnTicks();
		boolean spawnMissiles = hard && ticksExisted % 15 < 4;

		if (invul > 0 && mobTicks == MOB_SPAWN_TICKS)
		{
			if (invul < SPAWN_TICKS && invul > SPAWN_TICKS / 2 && worldObj.rand.nextInt(SPAWN_TICKS - invul + 1) == 0)
			{
				for (int i = 0; i < 2; i++)
				{
					spawnExplosionParticle();
				}
			}
			
			if (!worldObj.isRemote)
			{
				setHealth(getHealth() + (MAX_HP - 1F) / SPAWN_TICKS);
				setInvulTime(invul - 1);
			}
			motionY = 0;
		}
		else
		{
			if (isAggored())
			{
				boolean dying = getHealth() / getMaxHealth() < 0.2;
				if (dying && mobTicks > 0)
				{
					motionX = 0;
					motionY = 0;
					motionZ = 0;
					
					int reverseTicks = MOB_SPAWN_TICKS - mobTicks;
					if (reverseTicks < MOB_SPAWN_START_TICKS)
					{
						motionY = 0.2;
						setInvulTime(invul + 1);
					}
					
					if (reverseTicks > MOB_SPAWN_START_TICKS * 2 && mobTicks > MOB_SPAWN_END_TICKS && mobTicks % MOB_SPAWN_WAVE_TIME == 0 && !worldObj.isRemote)
					{
						for (int i = 0; i < 3 + worldObj.rand.nextInt(2); i++)
						{
							EntityLiving entity = null;
							switch(worldObj.rand.nextInt(2))
							{
							case 0:
								entity = new EntityZombie(worldObj);
								if (worldObj.rand.nextInt(hard ? 9 : 12) == 0)
								{
									entity = new EntityWitch(worldObj);
								}
								break;
							case 1:
								entity = new EntitySkeleton(worldObj);
								((EntitySkeleton) entity).setCurrentItemOrArmor(0, new ItemStack(Items.bow));
								if (worldObj.rand.nextInt(8) == 0)
								{
									((EntitySkeleton) entity).setSkeletonType(1);
									((EntitySkeleton) entity).setCurrentItemOrArmor(0, new ItemStack(hard ? ModItems.elementiumSword : Items.stone_sword));
								}
								break;
							case 3:
								if (!players.isEmpty())
								{
									for (int j = 0; j < 1 + worldObj.rand.nextInt(hard ? 8 : 5); j++)
									{
										EntityPixie pixie = new EntityPixie(worldObj);
										pixie.setProps(players.get(rand.nextInt(players.size())), this, 1, 8);
										pixie.setPosition(posX + width / 2, posY + 2, posZ + width / 2);
										worldObj.spawnEntityInWorld(pixie);
									}
								}
								break;
							}
							
							if(entity != null)
							{
								range = 6F;
								entity.setPosition(posX + 0.5 + Math.random() * range - range / 2, posY - 1, posZ + 0.5 + Math.random() * range - range / 2);
								worldObj.spawnEntityInWorld(entity);
							}
						}

						if (hard && ticksExisted % 3 < 2)
						{
							spawnMissile();
							spawnMissiles = false;
						}
					}
					setMobSpawnTicks(mobTicks - 1);
					setTPDelay(10);
				}
				else if (getTPDelay() > 0 && !worldObj.isRemote)
				{
					if(invul > 0)
					{
						setInvulTime(invul - 1);
					}

					setTPDelay(getTPDelay() - 1);
					if (getTPDelay() == 0 && getHealth() > 0)
					{
						int tries = 0;
						while (!teleportRandomly() && tries < 50)
						{
							tries++;
						}
						if (tries >= 50)
						{
							teleportTo(source.posX + 0.5, source.posY + 1.6, source.posZ + 0.5);
						}

						if (spawnLandmines())
						{
							for(int i = 0; i < 6; i++) {
								int x = source.posX - 10 + rand.nextInt(20);
								int z = source.posZ - 10 + rand.nextInt(20);
								int y = worldObj.getTopSolidOrLiquidBlock(x, z);

								EntityMagicLandmine landmine = new EntityMagicLandmine(worldObj);
								landmine.setPosition(x + 0.5, y, z + 0.5);
								landmine.summoner = this;
								worldObj.spawnEntityInWorld(landmine);
							}
						}

						if (!players.isEmpty())
						{
							for(int i = 0; i < (spawnPixies() ? worldObj.rand.nextInt(hard ? 6 : 3) : 1); i++) {
								EntityPixie pixie = new EntityPixie(worldObj);
								pixie.setProps(players.get(rand.nextInt(players.size())), this, 1, 8);
								pixie.setPosition(posX + width / 2, posY + 2, posZ + width / 2);
								worldObj.spawnEntityInWorld(pixie);
							}
						}

						setTPDelay(hard ? dying ? 20 : 45 : dying ? 40 : 60);
						setSpawnLandmines(true);
						setSpawnPixies(false);
					}
				}

				if(spawnMissiles)
				{
					spawnMissile();
				}
			}
			else
			{
				range = 3F;
				players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));
				if (!players.isEmpty())
				{
					damageEntity(DamageSource.causePlayerDamage(players.get(0)), 0);
				}
			}
		}
	}
	
	private void spawnMissile()
	{
		if(!worldObj.isRemote)
		{
			EntityMagicMissile missile = new EntityMagicMissile(this, true);
			missile.setPosition(posX + (Math.random() - 0.5 * 0.1), posY + 2.4 + (Math.random() - 0.5 * 0.1), posZ + (Math.random() - 0.5 * 0.1));
			if(missile.getTarget())
			{
				worldObj.playSoundAtEntity(this, "botania:missile", 0.6F, 0.8F + (float)Math.random() * 0.2F);
				worldObj.spawnEntityInWorld(missile);
			}
		}
	}
	
	private void updateLivingBase()
	{
		if (jumpTicks() > 0)
        {
            setJumpTicks(jumpTicks() - 1);
        }

        if (this.newPosRotationIncrements > 0)
        {
            double d0 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
            double d1 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
            double d2 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
            double d3 = net.minecraft.util.MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + d3 / (double)this.newPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        else if (!this.isClientWorld())
        {
            this.motionX *= 0.98D;
            this.motionY *= 0.98D;
            this.motionZ *= 0.98D;
        }

        if (Math.abs(this.motionX) < 0.005D)
        {
            this.motionX = 0.0D;
        }

        if (Math.abs(this.motionY) < 0.005D)
        {
            this.motionY = 0.0D;
        }

        if (Math.abs(this.motionZ) < 0.005D)
        {
            this.motionZ = 0.0D;
        }

        this.worldObj.theProfiler.startSection("ai");

        if (this.isMovementBlocked())
        {
            this.isJumping = false;
            this.moveStrafing = 0.0F;
            this.moveForward = 0.0F;
            this.randomYawVelocity = 0.0F;
        }
        else if (this.isClientWorld())
        {
            if (this.isAIEnabled())
            {
                this.worldObj.theProfiler.startSection("newAi");
                this.updateAITasks();
                this.worldObj.theProfiler.endSection();
            }
            else
            {
                this.worldObj.theProfiler.startSection("oldAi");
                this.updateEntityActionState();
                this.worldObj.theProfiler.endSection();
                this.rotationYawHead = this.rotationYaw;
            }
        }

        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("jump");

        if (this.isJumping)
        {
            if (!this.isInWater() && !this.handleLavaMovement())
            {
                if (this.onGround && this.jumpTicks() == 0)
                {
                    this.jump();
                    this.setJumpTicks(10);
                }
            }
            else
            {
                this.motionY += 0.03999999910593033D;
            }
        }
        else
        {
            this.setJumpTicks(0);
        }

        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("travel");
        this.moveStrafing *= 0.98F;
        this.moveForward *= 0.98F;
        this.randomYawVelocity *= 0.9F;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("push");

        if (!this.worldObj.isRemote)
        {
            this.collideWithNearbyEntities();
        }

        this.worldObj.theProfiler.endSection();
	}
	
	private void updateLiving()
	{
		updateLivingBase();
		this.worldObj.theProfiler.startSection("looting");

        if (!this.worldObj.isRemote && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
        {
            List list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(1.0D, 0.0D, 1.0D));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityItem entityitem = (EntityItem)iterator.next();

                if (!entityitem.isDead && entityitem.getEntityItem() != null)
                {
                    ItemStack itemstack = entityitem.getEntityItem();
                    int i = getArmorPosition(itemstack);

                    if (i > -1)
                    {
                        boolean flag = true;
                        ItemStack itemstack1 = this.getEquipmentInSlot(i);

                        if (itemstack1 != null)
                        {
                            if (i == 0)
                            {
                                if (itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword))
                                {
                                    flag = true;
                                }
                                else if (itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword)
                                {
                                    ItemSword itemsword = (ItemSword)itemstack.getItem();
                                    ItemSword itemsword1 = (ItemSword)itemstack1.getItem();

                                    if (itemsword.func_150931_i() == itemsword1.func_150931_i())
                                    {
                                        flag = itemstack.getItemDamage() > itemstack1.getItemDamage() || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                                    }
                                    else
                                    {
                                        flag = itemsword.func_150931_i() > itemsword1.func_150931_i();
                                    }
                                }
                                else
                                {
                                    flag = false;
                                }
                            }
                            else if (itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor))
                            {
                                flag = true;
                            }
                            else if (itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor)
                            {
                                ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
                                ItemArmor itemarmor1 = (ItemArmor)itemstack1.getItem();

                                if (itemarmor.damageReduceAmount == itemarmor1.damageReduceAmount)
                                {
                                    flag = itemstack.getItemDamage() > itemstack1.getItemDamage() || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                                }
                                else
                                {
                                    flag = itemarmor.damageReduceAmount > itemarmor1.damageReduceAmount;
                                }
                            }
                            else
                            {
                                flag = false;
                            }
                        }

                        if (flag)
                        {
                            if (itemstack1 != null && this.rand.nextFloat() - 0.1F < this.equipmentDropChances[i])
                            {
                                this.entityDropItem(itemstack1, 0.0F);
                            }

                            if (itemstack.getItem() == Items.diamond && entityitem.func_145800_j() != null)
                            {
                                EntityPlayer entityplayer = this.worldObj.getPlayerEntityByName(entityitem.func_145800_j());

                                if (entityplayer != null)
                                {
                                    entityplayer.triggerAchievement(AchievementList.field_150966_x);
                                }
                            }

                            this.setCurrentItemOrArmor(i, itemstack);
                            this.equipmentDropChances[i] = 2.0F;
                            setPersistenceRequired(true);
                            this.onItemPickup(entityitem, 1);
                            entityitem.setDead();
                        }
                    }
                }
            }
        }

        this.worldObj.theProfiler.endSection();
	}
	
	private static boolean isPlayingMusic()
	{
		return ReflectionHelper.getPrivateValue(EntityDoppleganger.class, null, "isPlayingMusic");
	}
	
	private static void setPlayingMusic(boolean isPlayingMusic)
	{
		ReflectionHelper.setPrivateValue(EntityDoppleganger.class, null, isPlayingMusic, "isPlayingMusic");
	}
	
	private boolean anyWithArmor()
	{
		return ReflectionHelper.getPrivateValue(EntityDoppleganger.class, this, "anyWithArmor");
	}
	
	private void setAnyWithArmor(boolean anyWithArmor)
	{
		ReflectionHelper.setPrivateValue(EntityDoppleganger.class, this, anyWithArmor, "anyWithArmor");
	}
	
	private boolean spawnLandmines()
	{
		return ReflectionHelper.getPrivateValue(EntityDoppleganger.class, this, "anyWithArmor");
	}
	
	private void setSpawnLandmines(boolean spawnLandmines)
	{
		ReflectionHelper.setPrivateValue(EntityDoppleganger.class, this, spawnLandmines, "spawnLandmines");
	}
	
	private boolean spawnPixies()
	{
		return ReflectionHelper.getPrivateValue(EntityDoppleganger.class, this, "spawnPixies");
	}
	
	private void setSpawnPixies(boolean spawnPixies)
	{
		ReflectionHelper.setPrivateValue(EntityDoppleganger.class, this, spawnPixies, "spawnPixies");
	}
	
	private int jumpTicks()
	{
		return ReflectionHelper.getPrivateValue(EntityLivingBase.class, this, "jumpTicks");
	}
	
	private void setJumpTicks(int jumpTicks)
	{
		ReflectionHelper.setPrivateValue(EntityLivingBase.class, this, jumpTicks, "jumpTicks");
	}
	
	private void setPersistenceRequired(boolean persistenceRequired)
	{
		ReflectionHelper.setPrivateValue(EntityLiving.class, this, persistenceRequired, "persistenceRequired");
	}
}
