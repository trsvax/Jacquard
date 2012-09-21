package com.trsvax.jacquard.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ComponentEventLinkEncoder;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.PersistentFieldChange;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.slf4j.Logger;

import com.trsvax.jacquard.environment.GridEnvironment;

public class MultiStatePersistenceStrategyImpl implements MultiStatePersistenceStrategy {
	public final static String key = "com.trsvax.persist";
	private final Request request;
	private final Logger logger;
	private final Environment environment;
	private final ComponentEventLinkEncoder linkEncoder;
	private final ComponentSource componentSource;
	private final ValueEncoderSource encoderSource;


	
	public MultiStatePersistenceStrategyImpl(Request request, Logger logger, Environment environment,
			ComponentEventLinkEncoder linkEncoder,ComponentSource componentSource, ValueEncoderSource encoderSource) {
		this.request = request;
		this.logger = logger;
		this.environment = environment;
		this.linkEncoder = linkEncoder;
		this.componentSource = componentSource;
		this.encoderSource = encoderSource;
	}

	@Override
	public void discardChanges(String pageName) {
		request.setAttribute(key, new HashMap<String, Object>());		
	}

	@Override
	public Collection<PersistentFieldChange> gatherFieldChanges(String pageName) {		
		Collection<PersistentFieldChange> result = new ArrayList<PersistentFieldChange>();
		for ( Entry<String,String> entry : getMap().entrySet() ) {
			result.add(field(pageName,entry.getKey(),entry.getValue()));
		}
		return result;
	}

	private PersistentFieldChange field(final String pageName, final String key, final String value) {
		String[] keys = key.split(";");
		final String fieldName = keys.length == 1 ? key : keys[1];
		final String componentId = keys.length == 1 ? null : keys[0];
		return new PersistentFieldChange() {
			
			@Override
			public Object getValue() {			
				return decode(pageName,componentId,fieldName,value);
			}
			
			@Override
			public String getFieldName() {
				return fieldName;
			}
			
			@Override
			public String getComponentId() {
				return componentId;
			}
		};
	}

	@Override
	public void postChange(String pageName, String componentId, String fieldName, Object value) {
		if ( value == null ) {
			getMap().remove(key(componentId,fieldName));
		} else {
			getMap().put(key(componentId,fieldName), encode(value));
		}		
	}

	String key(String componentId, String fieldName) {
		if ( componentId == null ) {
			componentId = "";
		}
		return String.format("%s;%s",componentId,fieldName);
	}
	
	@SuppressWarnings("unchecked")
	Map<String,String> getMap() {
		if ( request.getAttribute(key) == null ) {
			Map<String,String> map = new HashMap<String, String>();
			request.setAttribute(key, map);
			
			if ( ! request.getMethod().equals("POST")) {
				for ( String name : request.getParameterNames() ) {
					if ( name.startsWith("t:")) {
						continue;
					}
					if ( ! name.contains(";")) {
						continue;
					}
					map.put(name, request.getParameter(name));
				}
			}
		}
		return (Map<String, String>) request.getAttribute(key);
	}

	@Override
	public void updatePage(Link link) {
		if ( environment.peek(GridEnvironment.class) != null || isEvent() ) {
			for ( Entry<String, String> entry : getMap().entrySet() ) {
				link.addParameter(entry.getKey(), entry.getValue());
			}		
		}
	}
	
	@Override
	public void updateEvent(Link link) {
		if ( environment.peek(GridEnvironment.class) != null ) {
			for ( Entry<String, String> entry : getMap().entrySet() ) {
				link.addParameter(entry.getKey(), entry.getValue());
			}		
		}
	}
	
	boolean isEvent() {		
		if ( linkEncoder.decodeComponentEventRequest(request) != null ) {
			return true;
		}	
		return false;
	}
	
	String encode(Object value) {
		@SuppressWarnings("unchecked")
		ValueEncoder<Object> encoder = (ValueEncoder<Object>) encoderSource.getValueEncoder(value.getClass());
		return encoder.toClient(value);
	}
	
	 Object decode(String pageName, String componentId, String fieldName, String value) {
		 Object component;
		 if ( componentId != null ) {			 
			 component = componentSource.getComponent(String.format("%s:%s",pageName,componentId));
		 } else {
			 component = componentSource.getPage(pageName);
		 }
		 try {			
			for ( Field field : component.getClass().getDeclaredFields() ) {
				if ( field.getName().equals(fieldName)) {					
					@SuppressWarnings("unchecked")
					ValueEncoder<Object> encoder = (ValueEncoder<Object>) encoderSource.getValueEncoder(component.getClass().getDeclaredField(fieldName).getType());
					return encoder.toValue(value);
				}
			}
			return null;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}	
	}
	
}
