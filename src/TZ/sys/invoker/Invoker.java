package TZ.sys.invoker;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import TZ.sys.invoker.loader.SysLoader;
import TZ.sys.invoker.reflect.InvokeWrapper;
import TZ.sys.invoker.reflect.Reflectable;

/**
 * 
 * @author terrazero
 * @created Apr 9, 2015
 * 
 * @file Invoker.java
 * @project Invoker
 * @identifier TZ.sys.invoker
 *
 */
public class Invoker {
	
	public static void main(String[] args) {
		
	}
	
	public static<annotation extends Annotation> void each(Class<annotation> annotation, Consumer<InvokeWrapper<annotation>> consumer) {
		Invoker.each(SysLoader.sysloader().load(), annotation, consumer);
	}

	public static<annotation extends Annotation> void each(List<? extends Reflectable> reflectables, Class<annotation> annotation, Consumer<InvokeWrapper<annotation>> consumer) {
		for (Reflectable reflectable : reflectables) {
			annotation annot = reflectable.reflect().getAnnotation(annotation);
			if (annot != null) {
				consumer.accept(new InvokeWrapper<annotation>(reflectable, annot));
			}
		}
	}
	
	public static<annotation extends Annotation> List<InvokeWrapper<annotation>> list(Class<annotation> annotation) {
		return Invoker.list(SysLoader.sysloader().load(), annotation);
	}
	
	public static<annotation extends Annotation> List<InvokeWrapper<annotation>> list(List<? extends Reflectable> reflectables, Class<annotation> annotation) {
		return Invoker.list(reflectables, annotation, new ArrayList<InvokeWrapper<annotation>>());
	}
	
	public static<annotation extends Annotation> List<InvokeWrapper<annotation>> list(List<? extends Reflectable> reflectables, Class<annotation> annotation, List<InvokeWrapper<annotation>> output) {
		for (Reflectable reflectable : reflectables) {
			annotation annot = reflectable.reflect().getAnnotation(annotation);
			if (annot != null) {
				output.add(new InvokeWrapper<annotation>(reflectable, annot));
			}
		}
		return output;
	}
	
}
