package org.meta.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * This class creates a wrapper that synchronized all access to the object
 * which it wraps
 *
 * @author mark
 */
public class SynchronizedProxy implements InvocationHandler {
	private Object obj;

	/**
	 *
	 * @param obj
	 */
	public SynchronizedProxy(Object obj) {
		this.obj=obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		synchronized (proxy) {
			try {
				return method.invoke(obj, args);
			} catch	(Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	/**
	 *
	 * @param <T>
	 * @param o
	 * @return
	 */
	static public <T> T wrap(T o) {
		Class[] interfaces={ o.getClass() };
		SynchronizedProxy p=new SynchronizedProxy(o);
		@SuppressWarnings("unchecked")
		T proxy=(T)Proxy.newProxyInstance(
			o.getClass().getClassLoader(),
			interfaces,
			p
			);
		return proxy;
	}
}