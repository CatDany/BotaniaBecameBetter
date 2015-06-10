package catdany.bbb.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.chunk.Chunk;
import catdany.bbb.libs.WorldUtils;

public class CommandSlimeChunk extends CommandBase
{
	public CommandSlimeChunk()
	{
		super();
	}
	
	@Override
	public String getCommandName()
	{
		return "slimechunk";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "Usage: /" + getCommandName();
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (sender instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)sender;
			Chunk chunk = player.worldObj.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);
			boolean isSlimeChunk = WorldUtils.isSlimeChunk(chunk);
			sender.addChatMessage(new ChatComponentText(isSlimeChunk ? "You're in a Slime chunk." : "You're NOT in a Slime chunk."));
		}
	}
	
	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}
}