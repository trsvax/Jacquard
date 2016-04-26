package com.trsvax.jacquard.services;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.Location;

public class PageBinding extends AbstractBinding {
	
	private final ComponentResources resources;
	private final String value;
	private final String description;
	
	public PageBinding(Location location, String value, String description, ComponentResources resources) {
		super(location);
		this.resources = resources;
		this.value = value;
		this.description = description;
	}

	@Override
	public Object get() {
		String pageName = resources.getPageName();
		pageName = pageName.replace("/Index", "");	
		pageName += pageName.substring(pageName.lastIndexOf("/")) + value;				
		return pageName;
	}
	
	public String toString() {
		return String.format("PageBinding[%s: %s]", description, value); 
	}

}
