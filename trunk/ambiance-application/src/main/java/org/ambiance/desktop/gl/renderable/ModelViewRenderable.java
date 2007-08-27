package org.ambiance.desktop.gl.renderable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ModelViewRenderable implements InvocationHandler {

	static public Object newInstance() {
	  return Proxy.newProxyInstance(ModelViewRenderable.class.getClassLoader(),
	                                new Class[] {Renderable.class},
	                                new ModelViewRenderable());
	}
	
	public Object invoke(Object obj, Method m, Object[] args) throws Throwable {
		
		return null;
	}
	
}
