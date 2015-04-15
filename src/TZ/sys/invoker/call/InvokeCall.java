package TZ.sys.invoker.call;

import TZ.sys.invoker.reflect.Reflectable;

/**
 * 
 * @author terrazero
 * @created Apr 13, 2015
 * 
 * @file InvokeCall.java
 * @project Invoker
 * @identifier TZ.sys.invoker
 *
 */
public class InvokeCall {

	protected String function;
	protected Reflectable reflectable;
	
	public InvokeCall(String function, Reflectable reflectable) {
		this.function = function;
		this.reflectable = reflectable;
	}
	
	public String function() {
		return this.function;
	}
	
	public Reflectable reflectable() {
		return this.reflectable;
	}
	
	public void call(Object... parameters) {
		this.reflectable.reflect().call(this.function, parameters);
	}
	
}
