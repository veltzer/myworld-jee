package org.meta.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * This class wraps an objects, intercepts all calls to that object
 * and catches all exceptions thrown by the object turning them into
 * runtime exceptions.
 *
 * @author mark
 */
public class RuntimeExceptionProxy implements InvocationHandler {
	private Object obj;

	/**
	 *
	 * @param obj
	 */
	public RuntimeExceptionProxy(Object obj) {
		this.obj=obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 *
	 * @param <T> type to work on
	 * @param o The object to wrap
	 * @return The wrapped object (same interface as T)
	 */
	static public <T> T wrap(T o) {
		Class[] interfaces={ o.getClass() };
		RuntimeExceptionProxy p=new RuntimeExceptionProxy(o);
		@SuppressWarnings("unchecked")
		T proxy=(T)Proxy.newProxyInstance(
			o.getClass().getClassLoader(),
			interfaces,
			p
			);
		return proxy;
	}
}