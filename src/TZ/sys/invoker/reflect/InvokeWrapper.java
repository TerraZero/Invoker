package TZ.sys.invoker.reflect;

import java.lang.annotation.Annotation;

/**
 * 
 * @author terrazero
 * @created Apr 9, 2015
 * 
 * @file SysWrapper.java
 * @project Invoker
 * @identifier TZ.sys.invoker.loader
 *
 */
public class InvokeWrapper<annotation extends Annotation> {

	protected Reflectable reflectable;
	protected annotation annotation;
	
	public InvokeWrapper(Reflectable reflectable, annotation annotation) {
		this.reflectable = reflectable;
		this.annotation = annotation;
	}
	
	public Reflectable reflectable() {
		return this.reflectable;
	}
	
	public annotation annotation() {
		return this.annotation;
	}
	
	public Reflect reflect() {
		return this.reflectable.reflect();
	}
	
}
