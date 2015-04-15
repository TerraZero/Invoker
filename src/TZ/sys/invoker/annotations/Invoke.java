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
 * @file Invoke.java
 * @project Invoker
 * @identifier TZ.sys.invoker
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Invoke {

	public String invoke();
	
	public String function() default "invoke";
	
}
