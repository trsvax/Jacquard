package com.trsvax.jacquard.services;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.Environment;

public class CellBindingFactory implements BindingFactory {
	public final Environment environment;
	
	public CellBindingFactory(Environment environment) {
		this.environment = environment;
	}

	@Override
	public Binding newBinding(String description, ComponentResources container,
			ComponentResources component, String expression, Location location) {
		return new CellBinding(environment,expression,component);
	}

}
