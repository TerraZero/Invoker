package TZ.sys.invoker.reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author terrazero
 * @created Apr 7, 2015
 * 
 * @file Param.java
 * @project Invoker
 * @identifier TZ.sys.invoker.reflect
 *
 */
public class Param {

	protected Map<String, Object> paras;
	
	public Param() {
		this.paras = new HashMap<String, Object>();
	}
	
	public Param set(String key, Object para) {
		this.paras.put(key, para);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public<type> type get(String key) {
		return (type)this.paras.get(key);
	}
	
	public boolean is(String key) {
		return this.paras.get(key) != null;
	}
	
}
