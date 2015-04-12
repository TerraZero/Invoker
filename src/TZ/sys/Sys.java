package TZ.sys;

import TZ.sys.invoker.Invoker;

/**
 * 
 * @author terrazero
 * @created Apr 10, 2015
 * 
 * @file Sys.java
 * @project Invoker
 * @identifier TZ.sys
 *
 */
public class Sys {
	
	public static void main(String[] args) {
		Sys.init();
	}

	public static void init() {
		Invoker.each(Init.class, (wrapper) -> {
			wrapper.reflect().call(wrapper.annotation().function());
		});
	}
	
}
