package com.trsvax.jacquard.services;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.services.Environment;

import com.trsvax.jacquard.environment.PagedLoopEnviroment;

public class PagerBinding<T> extends AbstractBinding implements Binding  {
	private final Environment environment;

	public PagerBinding(Environment environment) {
		this.environment = environment;
	}

	public Object get() {
		PagedLoopEnviroment<?> pagedLoopEnviroment = environment.peekRequired(PagedLoopEnviroment.class);
		return pagedLoopEnviroment.getPage();
	}

}
