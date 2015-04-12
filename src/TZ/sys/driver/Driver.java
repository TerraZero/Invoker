package TZ.sys.driver;

/**
 * 
 * @author terrazero
 * @created Apr 10, 2015
 * 
 * @file Driver.java
 * @project Invoker
 * @identifier TZ.sys.driver
 *
 */
public @interface Driver {

	public String typus() default "Driver";
	
	public String type();
	
	public String name();
	
}
