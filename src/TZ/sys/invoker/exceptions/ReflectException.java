package TZ.sys.invoker.exceptions;

import TZ.sys.invoker.reflect.Reflect;

/**
 * 
 * @author terrazero
 * @created Apr 23, 2015
 * 
 * @file ReflectException.java
 * @project Invoker
 * @identifier TZ.sys.invoker.exceptions
 *
 */
public class ReflectException extends RuntimeException {
	
	public static final int CAUSE_PERMISSION = 1;
	public static final int CAUSE_ARGUMENTS = 2;
	public static final int CAUSE_TARGET = 3;
	
	public static final int CAUSE_CLASS_NOT_FOUND = 50;
	public static final int CAUSE_FIELD_NOT_FOUND = 51;

	private static final long serialVersionUID = 1L;
	
	protected Reflect reflect;
	protected Exception e;
	protected String message;
	protected int cause;
	
	public ReflectException(Exception e, Reflect reflect, String message, int cause) {
		this.e = e;
		this.reflect = reflect;
		this.message = message;
		this.cause = cause;
	}
	
	public Reflect reflect() {
		return this.reflect;
	}
	
	public Exception e() {
		return this.e;
	}
	
	public String message() {
		return this.message;
	}
	
	public String cause() {
		switch (this.cause) {
			case ReflectException.CAUSE_PERMISSION :
				return "cause by permission denied";
			case ReflectException.CAUSE_ARGUMENTS :
				return "cause by wrong arguments";
			case ReflectException.CAUSE_TARGET :
				return "cause by wrong target";
			case ReflectException.CAUSE_CLASS_NOT_FOUND :
				return "cause by class not found";
			case ReflectException.CAUSE_FIELD_NOT_FOUND : 
				return "cause by field not found";
			case 0 :
				return "";
			default :
				return "cause by " + this.cause;
		}
	}
	
	/* 
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return "ERROR: " + this.message + " (" + this.e + ") " + this.cause();
	}

}
