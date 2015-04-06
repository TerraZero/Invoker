package TZ.Sys;

import TZ.Sys.Loader.BootInvoke;

/**
 * 
 * @author Terra
 * @created 06.04.2015
 * 
 * @file Invoked.java
 * @project Invoker
 * @identifier TZ.Sys
 *
 */
public class Invoked {

	public final BootInvoke boot;
	public final Object back;
	
	public Invoked(BootInvoke boot, Object back) {
		this.boot = boot;
		this.back = back;
	}
	
}
