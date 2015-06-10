package catdany.bbb.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.ISpecialFlower;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import catdany.bbb.BBB;
import catdany.bbb.Refs;
import catdany.bbb.lexicon.LexiconHooks;
import catdany.bbb.libs.IconRegHelper;
import catdany.bbb.tile.TileFlowerRedahlia;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockFlowerRedahlia extends BlockFlower implements ITileEntityProvider, ILexiconable, IWandHUD, ISpecialFlower, IWandable
{
	public static final String NAME = "FlowerRedahlia";
	
	public BlockFlowerRedahlia()
	{
		super(0);
		setBlockName(Refs.MODID + ":" + NAME);
		setCreativeTab(BBB.modTab);
		setHardness(0.1F);
		setStepSound(soundTypeGrass);
		setBlockBounds(0.3F, 0.0F, 0.3F, 0.8F, 1, 0.8F);
		
		GameRegistry.registerBlock(this, NAME);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		blockIcon = IconRegHelper.regBlock(this, reg);
	}
	
	@Override
	public int getRenderType()
	{
		return 1;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return blockIcon;
	}
    
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(item));
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileFlowerRedahlia();
	}
	
	@Override
	public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z)
	{
		TileFlowerRedahlia tile = (TileFlowerRedahlia)world.getTileEntity(x, y, z);
		tile.renderHUD(Minecraft.getMinecraft(), res);
	}
	
	@Override
	public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack stack)
	{
		return LexiconHooks.entryFlowerRedahlia;
	}
	
	@Override
	public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int meta)
	{
		if (!world.isRemote)
		{
			world.markBlockForUpdate(x, y, z);
		}
		return false;
	}
    
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
    }

    public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
    {
        super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        TileEntity tileentity = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
        return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }
}
