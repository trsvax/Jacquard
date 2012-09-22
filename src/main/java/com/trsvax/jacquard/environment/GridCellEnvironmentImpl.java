package com.trsvax.jacquard.environment;

import org.apache.tapestry5.beaneditor.PropertyModel;

public class GridCellEnvironmentImpl implements GridCellEnviroment {
	private final Object object;
	private final PropertyModel model;
	
	public GridCellEnvironmentImpl(Object object, PropertyModel model) {
		this.object = object;
		this.model = model;
		
	}

	@Override
	public Object value() {
		if ( model.getConduit() != null ) {
			return model.getConduit().get(object);
		} else {
			return null;
		}
	}

	@Override
	public PropertyModel model() {
		return model;
	}
	

}
