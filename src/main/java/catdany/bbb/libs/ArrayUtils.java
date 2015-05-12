package catdany.bbb.libs;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils
{
	public static <T>List<T> getList(T... array)
	{
		ArrayList<T> list = new ArrayList<T>();
		for (T i : array)
		{
			list.add(i);
		}
		return list;
	}
	
	public static <T>void add(List<T> initial, List<T> additional)
	{
		for (T i : additional)
		{
			initial.add(i);
		}
	}
	
	public static <T>void add(List<T> initial, T... additional)
	{
		for (T i : additional)
		{
			initial.add(i);
		}
	}
}