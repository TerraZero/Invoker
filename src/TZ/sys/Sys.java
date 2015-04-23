package TZ.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TZ.sys.invoker.Invoker;
import TZ.sys.invoker.call.InvokeClass;
import TZ.sys.invoker.reflect.InvokeWrapper;

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
		List<InvokeWrapper<Mod>> mods = Invoker.list(Mod.class);
		
		mods.forEach((wrapper) -> {
			if (wrapper.annotation().boot().length() != 0) {
				wrapper.reflect().call(wrapper.annotation().boot());
			}
		});
		
		mods.forEach((wrapper) -> {
			if (wrapper.annotation().register().length() != 0) {
				wrapper.reflect().call(wrapper.annotation().register());
			}
		});
		
		mods.forEach((wrapper) -> {
			if (wrapper.annotation().init().length() != 0) {
				wrapper.reflect().call(wrapper.annotation().init());
			}
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
