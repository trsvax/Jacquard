package com.trsvax.jacquard.environment;

import org.apache.tapestry5.beaneditor.PropertyModel;

public class GridCellEnvironmentImpl implements GridCellEnviroment {
	private final Object object;
	private final PropertyModel model;
	
	public GridCellEnvironmentImpl(Object object, PropertyModel model) {
		assert(model != null);
		this.object = object;
		this.model = model;
		
	}

	@Override
	public Object object() {
		return object;
	}

	@Override
	public PropertyModel model() {
		return model;
	}
	

}
