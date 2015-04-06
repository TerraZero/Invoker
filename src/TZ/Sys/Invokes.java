package TZ.Sys;

/**
 * 
 * @author Terra
 * @created 06.04.2015
 * 
 * @file Invokes.java
 * @project Invoker
 * @identifier TZ.Sys
 *
 */
public @interface Invokes {
	
	public String name();

	public Invoke[] invokes();
	
}
