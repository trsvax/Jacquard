package com.trsvax.jacquard.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.DataType;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.slf4j.Logger;

import com.trsvax.jacquard.annotations.UserContent;

public class UserContentImpl<T> implements UserContentService<T> {
	private final ComponentClassResolver resolver;
	private final Map<String,Map<String,Object>> pages = new HashMap<String, Map<String,Object>>();
	private final BeanModelSource beanModelSource;

	
	private final Logger logger;
	
	public UserContentImpl(ComponentClassResolver resolver, Logger logger, BeanModelSource beanModelSource) {
		this.resolver = resolver;
		this.logger = logger;
		this.beanModelSource = beanModelSource;
		
	}

	@Override
	public Object getProperty(String pageName, String propertyName) {
		if (! pages.containsKey(pageName)) {
			return null;
		}
		return pages.get(pageName).get(propertyName);
	}
	
	public BeanModel<T> beanModel(String pageName, Messages messages) {
		@SuppressWarnings("unchecked")
		BeanModel<T> model = beanModelSource.createEditModel( (Class<T>) Object.class, messages);
		Class<T> pageClass = getPageClass(pageName);
		for ( Field field : pageClass.getDeclaredFields() ) {
			logger.info("field {}",field.getName());
			if ( field.getAnnotation(UserContent.class) != null ) {
				final String type  = getDataType(field);
				if ( type == null ) {
					continue;
				}
				model.add(field.getName(), makeConduit(field)).dataType(type);
			}
		}		
		return model;		
	}
	
	private String getDataType(Field field) {
		DataType dataType = field.getAnnotation(DataType.class);
		if ( dataType != null ) {
			return dataType.value();
		}
		Class<?> type = field.getType();
		if ( type.equals(String.class)) {
			return "text";
		}
		if ( type.equals(Integer.class)) {
			return "integer";
		}
		return null;
	}

	private PropertyConduit makeConduit(final Field field) {
		return new PropertyConduit() {
			
			@Override
			public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
				return field.getAnnotation(annotationClass);
			}
			
			@Override
			public void set(Object instance, Object value) {
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) instance;
				map.put(field.getName(), value);
				
			}
			
			@Override
			public Class<?> getPropertyType() {
				return field.getType();
			}
			
			@Override
			public Object get(Object instance) {
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) instance;
				return map.get(field.getName());
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getPageClass(String pageName) {
		try {
			return (Class<T>) Class.forName(resolver.resolvePageNameToClassName(pageName));
		} catch (ClassNotFoundException e) {
			logger.error("can't find page {} {}",pageName,e.getMessage());
			throw new RuntimeException("Invalid PageName " + pageName);
		}
	}

	

	@Override
	public void setContent(String pageName, Map<String, Object> content) {
		pages.put(pageName, content);
		
	}

	@Override
	public Map<String, Object> getContent(String pageName) {
		if ( ! pages.containsKey(pageName)) {
			pages.put(pageName, new HashMap<String, Object>());
		}
		return pages.get(pageName);
	}

}
