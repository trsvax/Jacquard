package com.trsvax.jacquard.conduits;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.apache.tapestry5.PropertyConduit;

public class MapConduit implements PropertyConduit {
	final String key;
	final Class<?> clazz;
	
	public MapConduit(String key, Class<?> clazz) {
		this.key = key;
		this.clazz = clazz;
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> arg0) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object get(Object map) {
		return ((Map) map).get(key);
	}

	@Override
	public Class<?> getPropertyType() {
		return clazz;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void set(Object map, Object value) {
		((Map)map).put(map, value);
		
	}

}
