package TZ.sys.invoker.call;

import java.util.ArrayList;
import java.util.List;

import TZ.sys.invoker.Invoker;
import TZ.sys.invoker.annotations.Alter;
import TZ.sys.invoker.annotations.Alters;
import TZ.sys.invoker.annotations.Invoke;
import TZ.sys.invoker.annotations.Invokes;

/**
 * 
 * @author terrazero
 * @created Apr 13, 2015
 * 
 * @file InvokeClass.java
 * @project Invoker
 * @identifier TZ.sys.invoker
 *
 */
public class InvokeClass {

	protected String name;
	protected List<InvokeCall> invokes;
	protected List<InvokeCall> alters;
	
    public InvokeClass(String name) {
    	this.name = name;
    	this.invokes = new ArrayList<InvokeCall>();
    	this.alters = new ArrayList<InvokeCall>();
    	this.regist(name);
    }
    
    public String name() {
    	return this.name;
    }
    
    private void regist(String name) {
    	Invoker.each(Invoke.class, (wrapper) -> {
    		if (wrapper.annotation().invoke().equals(name)) {
    			this.invokes.add(new InvokeCall(wrapper.annotation().function(), wrapper.reflect()));
    		}
    	});
    	
    	Invoker.each(Invokes.class, (wrapper) -> {
    		for (Invoke invoke : wrapper.annotation().value()) {
    			if (invoke.invoke().equals(name)) {
    				this.invokes.add(new InvokeCall(invoke.function(), wrapper.reflect()));
    			}
    		}
    	});
    	
    	Invoker.each(Alter.class, (wrapper) -> {
    		if (wrapper.annotation().alter().equals(name)) {
    			this.alters.add(new InvokeCall(wrapper.annotation().function(), wrapper.reflect()));
    		}
    	});
    	
    	Invoker.each(Alters.class, (wrapper) -> {
    		for (Alter alter : wrapper.annotation().value()) {
    			if (alter.alter().equals(name)) {
    				this.alters.add(new InvokeCall(alter.function(), wrapper.reflect()));
    			}
    		}
    	});
    }
    
    public void invoke(Object... parameters) {
    	for (InvokeCall invoke : this.invokes) {
    		invoke.call(parameters);
    	}
    }
    
    public void alter(Object... parameters) {
    	for (InvokeCall alter : this.alters) {
    		alter.call(parameters);
    	}
    }
    
    /* 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	return "Invoke(" + this.name + ":" + this.invokes.size() + ")";
    }
	
}
