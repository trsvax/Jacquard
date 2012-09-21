package com.trsvax.jacquard;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.ServiceBindingOptions;
import org.apache.tapestry5.ioc.ServiceBuilder;
import org.apache.tapestry5.ioc.ServiceResources;
import org.apache.tapestry5.ioc.annotations.Marker;


public class LibraryBuilder<T> implements ServiceBuilder<T> {
	private final Class<?>[] interfaces;

	@SuppressWarnings("unchecked")
	public LibraryBuilder(Class<T> clazz, ServiceBinder binder) {
		interfaces = new Class[] { clazz };
		for ( Method m : clazz.getDeclaredMethods() ) {
			Binder args = m.getAnnotation(Binder.class);
			Marker marker = m.getAnnotation(Marker.class);
			if ( args != null ) {
				ServiceBindingOptions options = binder.bind(m.getReturnType(),args.implementation());
				if ( args.eagerLoad() ) {
					options.eagerLoad();
				}
				if ( args.preventDecoration() ) {
					options.preventDecoration();
				}
				if ( args.preventReloading() ) {
					options.preventReloading();
				}
				if ( args.scope() != null ) {
					options.scope(args.scope());
				}
				if ( args.id() != null ) {
					options.withId(args.id());
				}
				if ( marker != null ) {
					options.withMarker(marker.value());
				}
				if ( args.simpleId() ) {
					options.withSimpleId();
				}
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T buildService(ServiceResources resources) {
		return (T) Proxy.newProxyInstance(interfaces[0].getClassLoader(), interfaces, new Service(resources));
	}
	
	public class Service implements InvocationHandler {
		private final ServiceResources resources;

		public Service(ServiceResources resources) {
			this.resources = resources;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			Marker marker = method.getAnnotation(Marker.class);
			Class<?> serviceInterface = method.getReturnType();
			if ( marker != null ) {
				return resources.getService(serviceInterface, marker.value()); 
			}
			return resources.getService(serviceInterface);
		}
		
	}
}
