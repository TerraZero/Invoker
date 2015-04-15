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
 * @file Invokes.java
 * @project Invoker
 * @identifier TZ.sys.invoker
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Invokes {

	public Invoke[] value();
	
}
