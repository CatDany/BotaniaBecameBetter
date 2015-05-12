package catdany.bbb.libs;

import java.lang.reflect.Method;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomThread extends Thread
{
	public final Method customMethod;
	public final Object customInstance;
	public final Object[] customArgs;
	
	private final Logger log = LogManager.getLogger(this.getName());
	
	/**
	 * Constructs a new thread that will invoke a method
	 * @param method Invokable method
	 * @param instance Instance of object
	 * @param args Method arguments
	 * @param startNow Start thread now?
	 */
	public CustomThread(Method method, Object instance, Object[] args, boolean startNow)
	{
		super("CustomThread:" + UUID.randomUUID() + ":" + method.getClass() + "#" + method.getName());
		if (startNow)
		{
			start();
		}
		this.customMethod = method;
		this.customInstance = instance;
		this.customArgs = args;
	}
	
	/**
	 * Constructs a new thread that will invoke a static method
	 * @param method Invokable method
	 * @param args Method arguments
	 * @param startNow Start thread now?
	 */
	public CustomThread(Method method, Object[] args, boolean startNow)
	{
		this(method, null, args, startNow);
	}
	
	/**
	 * Constructs a new thread that will invoke a method with no arguments
	 * @param method Invokable method
	 * @param instance Instance of object
	 * @param startNow Start thread now?
	 */
	public CustomThread(Method method, Object instance, boolean startNow)
	{
		this(method, instance, new Object[0], startNow);
	}
	
	/**
	 * Constructs a new thread that will invoke a method with given name
	 * @param className Path to class of invokable method
	 * @param methodName Name of invokable method
	 * @param instance Instance of object
	 * @param startNow Start thread now?
	 * @throws ClassNotFoundException No class found with given name
	 * @throws NoSuchMethodException No method found with given name
	 */
	public CustomThread(String className, String methodName, Object instance, boolean startNow) throws ClassNotFoundException, NoSuchMethodException
	{
		this(Class.forName(className), methodName, instance, startNow);
	}
	
	/**
	 * Constructs a new thread that will invoke a static method with given name and no arguments
	 * @param className Path to class of invokable method
	 * @param methodName Name of invokable method
	 * @param startNow Start thread now?
	 * @throws ClassNotFoundException No class found with given name
	 * @throws NoSuchMethodException No method found with given name
	 */
	public CustomThread(String className, String methodName, boolean startNow) throws ClassNotFoundException, NoSuchMethodException
	{
		this(className, methodName, null, startNow);
	}
	
	/**
	 * Constructs a new thread that will invoke a method with given name and class
	 * @param classObj Class of method
	 * @param methodName Name of invokable method
	 * @param instance Instance of object
	 * @param startNow Start thread now?
	 * @throws NoSuchMethodException No method found with given name
	 */
	public CustomThread(Class classObj, String methodName, Object instance, boolean startNow) throws NoSuchMethodException
	{
		this(classObj.getMethod(methodName), instance, startNow);
	}
	
	/**
	 * Constructs a new thread that will invoke a static method with given name and no arguments
	 * @param classObj Class of method
	 * @param methodName Name of invokable method
	 * @param startNow Start thread now?
	 * @throws NoSuchMethodException No method found with given name
	 */
	public CustomThread(Class classObj, String methodName, boolean startNow) throws NoSuchMethodException
	{
		this(classObj, methodName, null, startNow);
	}
	
	@Override
	public void run()
	{
		try
		{
			customMethod.invoke(customInstance, customArgs);
		}
		catch (Throwable t)
		{
			Logger log = getThreadLogger();
			log.catching(t);
		}
	}
	
	public Logger getThreadLogger()
	{
		return log;
	}
	
	public CustomThread setThreadDaemon(boolean daemon)
	{
		setDaemon(daemon);
		return this;
	}
}