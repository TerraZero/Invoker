package TZ.sys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mod {
	
	public String name();
	
	public String boot() default "";
	
	public String init() default "";
	
	public String register() default "";

	public int weight() default 0;
	
}
