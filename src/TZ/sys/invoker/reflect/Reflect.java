package TZ.sys.invoker.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import TZ.sys.invoker.exceptions.ReflectException;
import TZ.sys.invoker.loader.SysLoader;


/**
 * 
 * @author Terra
 * @created 19.02.2015
 * 
 * @file Reflect.java
 * @project G7C
 * @identifier TZ.Reflect
 *
 */
public class Reflect implements Reflectable {

	protected Class<?> reflectClass;
	protected Object reflect;
	
	public Reflect() {
		
	}
	
	public Reflect(String load) {
		this.reflect(load);
	}
	
	public Reflect(Object reflect) {
		this.reflect(reflect);
	}
	
	public Class<?> getReflectType() {
		return this.reflectClass;
	}
	
	@SuppressWarnings("unchecked")
	public<type> type getReflect() {
		try {
			return (type)this.reflect;
		} catch (ClassCastException e) {
			throw new ReflectException(e, this, "Reflection object can not cast into target type. (SOURCE: " + this.reflectClass + ")", 0);
		}
	}
	
	/**
	 * Load a reflecttype for this reflect
	 * 
	 * @param load - the name of the class
	 * @throws ReflectException ON ClassNotFoundException
	 * @return THIS
	 */
	public Reflect reflect(String load) {
		try {
			this.reflectClass = SysLoader.loader().loadClass(load);
			this.reflect = null;
		} catch (ClassNotFoundException e) {
			throw new ReflectException(e, this, "ClassLoader can not find the class '" + load + "'", ReflectException.CAUSE_CLASS_NOT_FOUND);
		}
		return this;
	}
	
	public Reflect reflect(Object reflect) {
		this.reflect = reflect;
		this.reflectClass = reflect.getClass();
		return this;
	}
	
	/**
	 * DEFAULT-PARAM force = true
	 * 
	 * @see instantiate(boolean, Object...)
	 * @param args - args for the constructor
	 * @return THIS
	 */
	public Reflect instantiate(Object... args) {
		return this.instantiate(false, args);
	}
	
	/**
	 * Create a NEW object of reflecttype into THIS object
	 * 
	 * @see Reflects.getConstructor(Class<?>, Class<?>[])
	 * @see Reflects.extractClasses(Object...)
	 * @see Reflects.getParameter(Object[], Class<?>[], boolean)
	 * @param force - IF already instantiate THAN create a NEW object
	 * @param args - args for the constructor
	 * @throws ReflectException ON InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
	 * @return THIS
	 */
	public Reflect instantiate(boolean force, Object... args) {
		if (this.reflect == null || force) {
			Constructor<?> c = null;
			try {
				if (args.length == 0) {
					this.reflect = this.reflectClass.newInstance();
				} else {
					c = Reflects.getConstructor(this.reflectClass, Reflects.extractClasses(args));
					this.reflect = c.newInstance(Reflects.getParameter(args, c.getParameterTypes(), c.isVarArgs()));
				}
			} catch (InstantiationException e) {
				throw new ReflectException(e, this, "Can not instantiate the object from class '" + this.reflectClass + "', width " + (args == null ? "0" : args.length) + " parameters, by construcktor '" + c + "'", 0);
			} catch (IllegalAccessException e) {
				throw new ReflectException(e, this, "Can not instantiate the object from class '" + this.reflectClass + "', width " + (args == null ? "0" : args.length) + " parameters, by construcktor '" + c + "'", ReflectException.CAUSE_PERMISSION);
			} catch (IllegalArgumentException e) {
				throw new ReflectException(e, this, "Can not instantiate the object from class '" + this.reflectClass + "', width " + (args == null ? "0" : args.length) + " parameters, by construcktor '" + c + "'", ReflectException.CAUSE_ARGUMENTS);
			} catch (InvocationTargetException e) {
				throw new ReflectException(e, this, "Can not instantiate the object from class '" + this.reflectClass + "', width " + (args == null ? "0" : args.length) + " parameters, by construcktor '" + c + "'", ReflectException.CAUSE_TARGET);
			}
		}
		return this;
	}
	
	
	/**
	 * Invoke a method of reflecttype(static) or reflectobject(non-static) 
	 * 
	 * @see Reflects.getFunctions(Class<?>, String, Class<?>[])
	 * @see Reflects.extractClasses(Object...)
	 * @param function - the method name
	 * @param parameters - params for the method
	 * @throws ReflectException ON SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
	 * @return THIS
	 */
	@SuppressWarnings("unchecked")
	public<type> type call(String function, Object... parameters) {
		Method method = null;
		try {
			method = Reflects.getFunctions(this.reflectClass, function, Reflects.extractClasses(parameters));
			return (type)method.invoke(this.reflect, parameters);
		} catch (SecurityException e) {
			throw new ReflectException(e, this, "Can not call the " + (this.reflect == null ? "static " : "") + "method '" + method + "' from class '" + this.reflectClass + "', width " + (parameters == null ? "0" : parameters.length) + " parameters", 0);
		} catch (IllegalAccessException e) {
			throw new ReflectException(e, this, "Can not call the " + (this.reflect == null ? "static " : "") + "method '" + method + "' from class '" + this.reflectClass + "', width " + (parameters == null ? "0" : parameters.length) + " parameters", ReflectException.CAUSE_PERMISSION);
		} catch (IllegalArgumentException e) {
			throw new ReflectException(e, this, "Can not call the " + (this.reflect == null ? "static " : "") + "method '" + method + "' from class '" + this.reflectClass + "', width " + (parameters == null ? "0" : parameters.length) + " parameters", ReflectException.CAUSE_ARGUMENTS);
		} catch (InvocationTargetException e) {
			throw new ReflectException(e, this, "Can not call the " + (this.reflect == null ? "static " : "") + "method '" + method + "' from class '" + this.reflectClass + "', width " + (parameters == null ? "0" : parameters.length) + " parameters", ReflectException.CAUSE_TARGET);
		}
	}
	
	public CallFunc getCall(String function, Class<?>... parameters) {
		return new CallFunc(this.reflect, Reflects.getFunctions(this.reflectClass, function, parameters));
	}
	
	/**
	 * Invoke all methods with the defined annotation
	 * 
	 * @see Reflects.getFunctions(Class<?>, Class<? extends Annotation>)
	 * @param annotation - the defined annotation
	 * @param parameters - the params for the invoke methods
	 * @throws ReflectException ON IllegalAccessException | IllegalArgumentException | InvocationTargetException
	 * @return THIS
	 */
	@Deprecated
	public Reflect call(Class<? extends Annotation> annotation, Object... parameters) {
		List<Method> functions = Reflects.getFunctions(this.reflectClass, annotation);
		
		if (!functions.isEmpty()) {
			for (Method function : functions) {
				try {
					function.invoke(this.reflect, parameters);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return this;
	}
	
	
	/**
	 * Get the value of a field from reflecttype(static) or reflectobject(non-static)
	 * 
	 * @param field - the field name
	 * @throws ReflectException ON IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException
	 * @return the value of the field
	 */
	@SuppressWarnings("unchecked")
	public<type> type get(String field) {
		try {
			return (type)this.reflectClass.getField(field).get(this.reflect);
		} catch (IllegalArgumentException e) {
			throw new ReflectException(e, this, "Can not get the value of " + (this.reflect == null ? "static " : "") + "field '" + field + "' from class '" + this.reflectClass, ReflectException.CAUSE_ARGUMENTS);
		} catch (IllegalAccessException e) {
			throw new ReflectException(e, this, "Can not get the value of " + (this.reflect == null ? "static " : "") + "field '" + field + "' from class '" + this.reflectClass, ReflectException.CAUSE_PERMISSION);
		} catch (NoSuchFieldException e) {
			throw new ReflectException(e, this, "Can not get the value of " + (this.reflect == null ? "static " : "") + "field '" + field + "' from class '" + this.reflectClass, ReflectException.CAUSE_FIELD_NOT_FOUND);
		} catch (SecurityException e) {
			throw new ReflectException(e, this, "Can not get the value of " + (this.reflect == null ? "static " : "") + "field '" + field + "' from class '" + this.reflectClass, 0);
		}
	}
	
	/**
	 * Set the value of a field from reflecttype(static) or reflectobject(non-static)
	 * 
	 * @param field - the field name
	 * @param set - the value to set
	 * @throws ReflectException ON IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException
	 * @return THIS
	 */
	public Reflect set(String field, Object set) {
		try {
			this.reflectClass.getField(field).set(this.reflect, set);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	
	
	public <annot extends Annotation> annot getAnnotation(Class<annot> annotation) {
		if (this.hasAnnotation(annotation)) {
			return this.reflectClass.getAnnotation(annotation);
		}
		return null;
	}
	
	public <annot extends Annotation> annot annotation(Class<annot> annotation) {
		return this.reflectClass.getAnnotation(annotation);
	}
	
	public boolean hasAnnotation(Class<? extends Annotation> annotation) {
		return this.reflectClass.isAnnotationPresent(annotation);
	}
	
	
	
	public boolean implement(Class<?> implement) {
		return Reflects.isImplement(this.reflectClass, implement);
	}

	/* 
	 * @see TZ.sys.invoker.reflect.Reflectable#reflect()
	 */
	@Override
	public Reflect reflect() {
		return this;
	}
	
}
