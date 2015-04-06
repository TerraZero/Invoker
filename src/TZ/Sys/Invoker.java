package TZ.Sys;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TZ.Sys.Loader.Boot;
import TZ.Sys.Loader.BootInvoke;
import TZ.Sys.Loader.SysLoader;

/**
 * 
 * @author Terra
 * @created 06.04.2015
 * 
 * @file Invoker.java
 * @project Invoker
 * @identifier TZ.Sys
 *
 */
public class Invoker {

	private static Invoker invoker;
	
	public static Invoker invoker() {
		if (Invoker.invoker == null) {
			Invoker.invoker = new Invoker();
		}
		return Invoker.invoker;
	}

	public static void init(String... invokes) {
		Invoker.invoker = new Invoker(invokes);
	}
	
	public static List<Invoked> invoke(String name, Object... parameters) {
		return Invoker.invoker().invokes(name, parameters);
	}
	
	protected String[] invokes;
	protected List<Boot> invoked;
	protected Map<String, List<BootInvoke>> register;
	
	public Invoker(String... invokes) {
		this.invokes = invokes;
		this.init();
	}
	
	public void init() {
		// add paths for invokes
		for (String path : invokes) {
			SysLoader.addLoaderSource(new File(path));
		}
		
		// get all invoke implements classes
		List<Boot> boots = SysLoader.sysloader().load();
		for (Boot boot : boots) {
			Invokes info = boot.reflect().getAnnotation(Invokes.class);
			if (info != null) {
				this.invoked.add(boot);
			}
		}
		
		this.register = new HashMap<String, List<BootInvoke>>();
	}
	
	public List<Invoked> invokes(String name, Object... parameters) {
		List<BootInvoke> regist = this.register.get(name);
		if (regist == null) {
			regist = new ArrayList<BootInvoke>();
			for (Boot boot : this.invoked) {
				Invokes info = boot.reflect().annotation(Invokes.class);
				for (Invoke invoke : info.invokes()) {
					if (invoke.name().equals(name)) {
						regist.add(new BootInvoke(boot.reflect(), invoke.function()));
					}
				}
			}
			this.register.put(name, regist);
		}
		List<Invoked> invoked = new ArrayList<Invoked>();
		for (BootInvoke boot : regist) {
			invoked.add(new Invoked(boot, boot.reflect.call(boot.function, parameters)));
		}
		return invoked;
	}
	
}
