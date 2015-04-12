package TZ.sys;

/**
 * 
 * @author terrazero
 * @created Apr 10, 2015
 * 
 * @file Init.java
 * @project Invoker
 * @identifier TZ.sys
 *
 */
public @interface Init {
	
	public String name();
	
	public String function() default "init";

	public int weight() default 0;
	
}
