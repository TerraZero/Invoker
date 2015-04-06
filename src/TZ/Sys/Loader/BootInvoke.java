package TZ.Sys.Loader;

import TZ.Sys.Reflect.Reflect;

/**
 * 
 * @author Terra
 * @created 06.04.2015
 * 
 * @file BootInvoke.java
 * @project Invoker
 * @identifier TZ.Sys.Loader
 *
 */
public class BootInvoke {

	public final Reflect reflect;
	public final String function;
	
	public BootInvoke(Reflect reflect, String function) {
		this.reflect = reflect;
		this.function = function;
	}
	
}
