package TZ.sys.invoker.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author Terra
 * @created 18.04.2015
 * 
 * @file CallFunc.java
 * @project Invoker
 * @identifier TZ.sys.invoker.reflect
 *
 */
public class CallFunc {
	
	private Object object;
	private Method method;

	public CallFunc(Object object, Method method) {
		this.object = object;
		this.method = method;
	}
	
	@SuppressWarnings("unchecked")
	public<type> type call(Object... parameters) {
		try {
			return (type)method.invoke(this.object, parameters);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
