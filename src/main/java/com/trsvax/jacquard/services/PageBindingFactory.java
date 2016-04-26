package com.trsvax.jacquard.services;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;

public class PageBindingFactory implements BindingFactory {

	@Override
	public Binding newBinding(String description, ComponentResources container,
			ComponentResources component, String expression, Location location) {
		return new PageBinding(location, expression, description, container);
	}

}
