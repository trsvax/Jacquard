package com.trsvax.jacquard.services;

import java.lang.annotation.Annotation;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.services.Environment;

import com.trsvax.jacquard.environment.GridCellEnviroment;

public class CellBinding implements Binding {
	private final Environment environment;
	private final String expression;
	private final ComponentResources resources;

	public CellBinding(Environment environment, String expression, ComponentResources component) {
		this.environment = environment;
		this.expression = expression.toLowerCase();
		this.resources = component;		
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> arg0) {
		return null;
	}

	@Override
	public Object get() {
		GridCellEnviroment gridCellEnviroment = environment.peekRequired(GridCellEnviroment.class);
		if ( expression == null || expression.equals("value")) {
			return gridCellEnviroment.value();
		}
		if ( expression.equals("datatype")) {
			return gridCellEnviroment.model().getDataType();
		}
		if ( expression.equals("simplename")) {
			return gridCellEnviroment.model().getConduit().getPropertyType().getSimpleName();
		}
		return null;
	}

	@Override
	public Class getBindingType() {
		GridCellEnviroment gridCellEnviroment = environment.peekRequired(GridCellEnviroment.class);
		if ( expression == null || expression.equals("value")) {
			return gridCellEnviroment.value().getClass();
		}
		if ( expression.equals("datatype")) {
			return String.class;
		}
		if ( expression.equals("simplename")) {
			return String.class;
		}
		return null;		
	}

	@Override
	public boolean isInvariant() {
		return false;
	}

	@Override
	public void set(Object object) {
		//can't do this
	}

}
