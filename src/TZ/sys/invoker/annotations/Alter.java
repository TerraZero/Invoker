package TZ.sys.invoker.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author terrazero
 * @created Apr 13, 2015
 * 
 * @file Alter.java
 * @project Invoker
 * @identifier TZ.sys.invoker
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Alter {

	public String alter();
	
	public String function() default "alter";
	
}
