package com.trsvax.jacquard.conduits;

import java.lang.annotation.Annotation;

import org.apache.tapestry5.PropertyConduit;

public class ArrayConduit implements PropertyConduit {
	private final Class<?> clazz;
	private final int index;
	
	public ArrayConduit(Class<?> clazz, int index) {
		this.clazz = clazz;
		this.index = index;
	}
	
	public <T extends Annotation> T getAnnotation(Class<T> arg0) {
		return null;
	}

	public Object get(Object instance) {
		return ((Object[]) instance)[index];
	}

	public void set(Object instance, Object value) {
		((Object[]) instance)[index] = value;
	}

	public Class<?> getPropertyType() {
		return clazz;
	}
	
}
