package catdany.bbb.libs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class RandomUtils
{
	private static Random random = new Random();
	private static SecureRandom secureRandom = new SecureRandom();
	
	public static int random(int min, int max, boolean useNewRandom)
	{
		Random random = useNewRandom ? new Random() : RandomUtils.random;
		return random.nextInt(max - min) + min;
	}
	
	public static Random getRandomFromObject(Object object)
	{
		return new Random(object.hashCode());
	}
	
	public static SecureRandom secureRandom()
	{
		return secureRandom;
	}
	
	public static UUID getUUIDFromObjects(Object... obj)
	{
		ByteBuf buf = Unpooled.buffer();
		for (Object i : obj)
		{
			buf.writeInt(obj.hashCode());
		}
		return UUID.nameUUIDFromBytes(buf.array());
	}
	
	public static UUID getUUIDFromString(String string)
	{
		return UUID.nameUUIDFromBytes(string.getBytes());
	}
}