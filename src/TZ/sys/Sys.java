package TZ.sys;

import java.util.HashMap;
import java.util.Map;

import TZ.sys.invoker.Invoker;
import TZ.sys.invoker.call.InvokeClass;

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
	
	private static Map<String, InvokeClass> invokes;
	
	public static void main(String[] args) {
		Sys.init();
	}

	public static void init() {
		Invoker.each(Init.class, (wrapper) -> {
			wrapper.reflect().call(wrapper.annotation().function());
		});
	}
	
	public static InvokeClass invokes(String name) {
		if (Sys.invokes == null) {
			Sys.invokes = new HashMap<String, InvokeClass>();
		}
		InvokeClass invoke = Sys.invokes.get(name);
		if (invoke == null) {
			invoke = new InvokeClass(name);
			Sys.invokes.put(name, invoke);
		}
		return invoke;
	}
	
	public static void invoke(String name, Object... parameters) {
		InvokeClass invoke = Sys.invokes(name);
		invoke.invoke(parameters);
		invoke.alter(parameters);
	}
	
	public static void cascade(String[] invokes, Object... parameters) {
		for (String invoke : invokes) {
			Sys.invoke(invoke, parameters);
		}
	}
	
}
